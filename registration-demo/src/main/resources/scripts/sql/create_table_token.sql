CREATE TABLE IF NOT EXISTS token (
  token_id BIGINT AUTO_INCREMENT,
  token VARCHAR(256) NOT NULL,
  expire_date datetime NOT NULL,
  PRIMARY KEY (token_id)
)