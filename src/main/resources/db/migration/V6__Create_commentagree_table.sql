create table commentagree
  (
      id int auto_increment,
      userid bigint not null,
	  comment_id bigint not null,
      constraint commentagree_pk
            primary key (id)
  );