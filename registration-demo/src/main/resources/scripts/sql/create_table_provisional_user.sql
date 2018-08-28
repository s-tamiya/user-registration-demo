CREATE TABLE IF NOT EXISTS provisional_user (
  id BIGINT AUTO_INCREMENT,
  email VARCHAR(256) NOT NULL,
  password VARCHAR(256) NOT NULL,
  token VARCHAR(256) NOT NULL,
  expire_date date NOT NULL,
  create_date date NOT NULL,  
  PRIMARY KEY (id),
  UNIQUE KEY (email)
)