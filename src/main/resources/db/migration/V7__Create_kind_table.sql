create table kind
  (
      id bigint auto_increment,
      kindname varchar(100),
      kind_create bigint,
      kind_modified bigint,
      constraint kind_pk
            primary key (id)
  );