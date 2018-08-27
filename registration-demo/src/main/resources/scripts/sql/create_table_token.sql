CREATE TABLE IF NOT EXISTS user (
  token_id BIGINT AUTO_INCREMENT,
  token VARCHAR(256) NOT NULL,
  expire_date datetime NOT NULL,
  PRIMARY KEY (id)
)