create table message
  (
      id int auto_increment,
      comment_id bigint not null,
      constraint message_pk
            primary key (id)
  );