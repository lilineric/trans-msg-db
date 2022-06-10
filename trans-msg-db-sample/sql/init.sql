create database order_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use order_db;
create table t_order (
  id bigint auto_increment primary key not null comment 'ID',
  uid bigint not null comment 'user id',
  title varchar(128) not null comment 'order title',
  amount bigint not null comment 'order amount',
  status varchar(16) not null comment 'order status',
  created datetime(6) not null default current_timestamp(6),
  modified datetime(6) not null default current_timestamp(6) on update current_timestamp(6),
  key idx_order_uid (uid)
) engine=innodb;

