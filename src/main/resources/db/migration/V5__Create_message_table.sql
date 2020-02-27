create table message
  (
      id int auto_increment,
      send_user_id bigint,
      recv_user_id bigint,
      content text,
      topic_id bigint,
      comment_id bigint,
      type int,
      is_read int default 0,
      message_create bigint,
      constraint message_pk
            primary key (id)
  );