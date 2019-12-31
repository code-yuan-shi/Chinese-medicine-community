create table category
  (
      id bigint auto_increment,
      catename varchar(100),
      category_create bigint,
      category_modified bigint,
      constraint category_pk
            primary key (id)
  );