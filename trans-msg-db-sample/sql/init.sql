create database if not exists order_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use order_db;
create table if not exists t_order (
  id bigint auto_increment primary key not null comment 'ID',
  uid bigint not null comment 'user id',
  title varchar(128) not null comment 'order title',
  amount bigint not null comment 'order amount',
  status varchar(16) not null comment 'order status',
  created datetime(6) not null default current_timestamp(6),
  modified datetime(6) not null default current_timestamp(6) on update current_timestamp(6),
  key idx_order_uid (uid)
) engine=innodb;


create table if not exists tx_transaction (
  trans_id varchar(64) primary key not null comment 'trans id',
  trans_type varchar(32) not null comment 'business trans type',
  trans_message text not null comment 'trans message',
  producer varchar(32) not null comment 'producer',
  branch_trans_status varchar(16) not null comment 'branch trans status' default 'INIT',
  status varchar(16) not null comment 'status' default 'SENDING',
  retry_count int not null comment 'retry count' default 0,
  last_retry_time datetime(6) null,
  created datetime(6) not null default current_timestamp(6),
  modified datetime(6) not null default current_timestamp(6) on update current_timestamp(6)
) engine=innodb;


create table if not exists tx_branch_transaction (
  branch_trans_id varchar(64) primary key not null comment 'branch trans id',
  trans_id varchar(64) not null comment 'trans id',
  consumer varchar(32) not null comment 'consumer',
  result varchar(16) not null comment 'result' default 'INIT',
  created datetime(6) not null default current_timestamp(6),
  modified datetime(6) not null default current_timestamp(6) on update current_timestamp(6),
  unique key udx_branch_trans_id_consumer (trans_id, consumer)
) engine=innodb;

create table if not exists tx_transaction_history (
  trans_id varchar(64) primary key not null comment 'trans id',
  trans_type varchar(32) not null comment 'business trans type',
  trans_message text not null comment 'trans message',
  producer varchar(32) not null comment 'producer',
  branch_trans_status varchar(16) not null comment 'branch trans status' default 'INIT',
  status varchar(16) not null comment 'status' default 'SENDING',
  retry_count int not null comment 'retry count' default 0,
  created datetime(6) not null,
  modified datetime(6) not null,
  finish_time datetime(6) not null default current_timestamp(6),
  key idx_trans_producer_trans_type (producer, trans_type)
) engine=innodb;

create table if not exists tx_branch_transaction_history (
  branch_trans_id varchar(64) primary key not null comment 'branch trans id',
  trans_id varchar(64) not null comment 'trans id',
  consumer varchar(32) not null comment 'consumer',
  result varchar(16) not null comment 'result' default 'INIT',
  created datetime(6) not null,
  modified datetime(6) not null,
  finish_time datetime(6) not null default current_timestamp(6),
  unique key udx_branch_trans_id_consumer (trans_id, consumer)
) engine=innodb;