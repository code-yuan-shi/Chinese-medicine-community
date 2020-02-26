create table collect
  (
      id bigint auto_increment,
      user_id bigint,
      topic_id bigint,
      collect_create bigint,
      collect_modified bigint,
      constraint collect_pk
            primary key (id)
  );