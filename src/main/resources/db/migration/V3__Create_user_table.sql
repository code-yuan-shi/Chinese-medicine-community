create table user
  (
      id bigint auto_increment,
      account_id bigint,
	  token varchar(36),
	  pwd varchar(100),
	  email varchar(50),
      name varchar(50),
	  city varchar(50),
	  sex int,
	  avatar_url varchar(256),
	  bio varchar(256),
	  kiss_num bigint default 100,
      user_create bigint,
      user_modified bigint,
      role varchar(20),
      status int default 0,
      active_time bigint,
      active_code varchar(50),
      constraint user_pk
            primary key (id)
  );