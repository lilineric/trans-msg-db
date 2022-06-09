# trans-msg-db
Distributed transactions based on local message tables.
[中文介绍](https://github.com/lilineric/trans-msg-db/blob/main/README_ZH.md)

## Overview
Local message table is a solution to implement distributed transactions, the principle and implementation are very simple, do not need to implement the Try, Confirm/Cancel like TCC, dependening on local database transactions and MQ messages constantly retry to ensure the final consistency.
However, the local message table and the business table need to be created into same database. And do not support rollback.

Suitable for asynchronous scenarios where resource isolation is not required.

## Processes
### Order business
Let's simplify the order process. Suppose we need to give points and coupons to the user after a successful order: 
![Order Sequence](https://github.com/lilineric/trans-msg-db/blob/main/img/grant-points.png)
We usually push the order success message to MQ after the order is successfully placed, and the Member service and Coupon service subscribe to this message for processing, and the ACK mechanism of MQ (if you are using MQ that supports ACK) can ensure the consumer to retry for consumption failure.

But the question is what happens if the push MQ fails because of network issues?

Of course, we can put the push MQ message and the order placement in same transaction, and roll back the order if the MQ message push fails. However, MQ and database are not in the same network environment, which will greatly increase the probability of transaction failure.

What if you only require atomicity and don't care about the success rate of the transaction?

In fact, there is no way to guarantee atomicity, even through transactions.

It's clear semantics that pushing an MQ returns a failure or success, and the transaction rolls back or commits based on the push result, but what if it returns a timeout exception? This time there is no way to know whether it is a success or a failure, it may fail, or it may finally execute successfully, just the execution time exceeds the set timeout.

The distributed solution of local message table is to ensure the atomicity of business operations and push MQ through local transactions.

### Local message table
We build the message table in the business database, make sure the message table and the business table are in the same database, and put the order operation and the insert message table in the same transaction, so that the local transaction will make sure the atomicity. Then a job service polls the message table regularly, takes out the message and re-delivers it to the MQ, and sends a message to delete the records in the message table after the consumer has finished consuming. Here the retry mechanism leads to a high probability of duplicate messages, so the consuming must make sure idempotency.

![Local Message Table](https://github.com/lilineric/trans-msg-db/blob/main/img/grant-points-trans.png)

Like we said before, putting push MQ and order business in same transaction will increase the probability of transaction failure, why not (or has minimal impact) when using local message table?

Because the message table and the business table are in the same database and the same network environment, if there is a issue with the database or the network, then the order operation will fail even if it is not in the same transaction. It is highly unlikely that the DB operation to place an order will succeed while the operation to insert the message table will fail.

## Design
The service is provided as a Jar package, with annotated interceptors for transaction registration and commit. There is basically noninvasive for business at the code level, except for the need to create a new message table in the business database.

For trans-msg-db design, database operations, MQ message push and subscription, and transaction registration configuration are all provided as plug-ins, which are injected through the SPI (Service Provider Interface). This means that the default plug-in implementation is replaced and can support any database, MQ, etc. Similarly, the configuration information for transaction registration can be stored either in a configuration file or in a database, and the recommended way is to set the configuration in a configuration center (e.g. Apollo, Nacos, etc.).

![Process](https://github.com/lilineric/trans-msg-db/blob/main/img/trans-msg-db.png)

All job services run in the same Java process as the business services.
