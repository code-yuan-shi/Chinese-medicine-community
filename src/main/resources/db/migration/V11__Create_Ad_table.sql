CREATE TABLE ad (
  id int  auto_increment,
  title varchar(256) DEFAULT NULL,
  url varchar(256) DEFAULT NULL,
  image varchar(256) DEFAULT NULL,
  ad_start bigint,
  ad_end bigint,
  ad_create bigint,
  ad_modified bigint,
  status int,
  pos varchar(10) NOT NULL,
  constraint ad_pk
        primary key (id)
);