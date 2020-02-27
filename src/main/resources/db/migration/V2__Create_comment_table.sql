create table comment
(
	id bigint auto_increment,
	topic_id bigint not null,
    content text,
	type int not null,
	user_id bigint not null,
	comment_create bigint not null,
	comment_modified bigint not null,
	agree_num bigint default 0,
	is_accept int default 0,
	constraint comment_pk
		primary key (id)
);