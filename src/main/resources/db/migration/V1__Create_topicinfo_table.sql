create table topicinfo
(
    id bigint auto_increment,
    title varchar(50),
    content text,
    topic_create bigint,
    topic_modified bigint,
    user_id bigint,
	category_id bigint,
	kind_id bigint,
    view_count int default 0,
    is_good int default 0,
	is_end int default 0,
	is_top int default 0,
	status int default 0,
    experience bigint,
    constraint topic_pk
        primary key (id)
);