CREATE TABLE qiandao (
  id bigint  auto_increment,
  user_id bigint,
  total bigint default 0,
  qiandao_create bigint,
 constraint qiandao_pk
        primary key (id)
);