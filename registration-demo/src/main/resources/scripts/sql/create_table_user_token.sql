CREATE TABLE IF NOT EXISTS user_token (
  id BIGINT AUTO_INCREMENT,
  user_id VARCHAR(256) NOT NULL,
  token_id VARCHAR(256) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY (user_id, token_id)
)