CREATE TABLE IF NOT EXISTS user (
  user_id BIGINT AUTO_INCREMENT,
  name VARCHAR(128) NOT NULL,
  password VARCHAR(256) NOT NULL,
  email VARCHAR(256) NOT NULL,
  admin_flg BOOLEAN NOT NULL,
  PRIMARY KEY (user_id),
  UNIQUE KEY (email)
)