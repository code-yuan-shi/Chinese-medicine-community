CREATE TABLE reward (
  id bigint  auto_increment,
  reward_user_id bigint,
  receive_user_id bigint,
  reward_num bigint,
  reward_create bigint,
 constraint reward_pk
        primary key (id)
);