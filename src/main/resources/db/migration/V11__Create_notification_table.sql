create table notification
(
	id BIGINT auto_increment,
	notifler BIGINT not null,
	receiver BIGINT not null,
	outerId BIGINT not null,
	type int not null,
	gmt_create BIGINT not null,
	status int default 0 not null,
	constraint notification_pk
		primary key (id)
);
/*
comment on column notification.notifler is '通知人';

comment on column notification.receiver is '接受消息的人';

comment on column notification.outerId is '接收类型Id';

comment on column notification.type is '接受类型';

comment on column notification.status is '状态';*/