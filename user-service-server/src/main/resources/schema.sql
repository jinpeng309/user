CREATE TABLE IF NOT EXISTS user (
  user_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
  country_code  INT    NOT NULL DEFAULT 86,
  phone_number  BIGINT NOT NULL,
  avatar        VARCHAR(128) NOT NULL,
  token         VARCHAR(32) NOT NULL,
  refresh_token VARCHAR(32) NOT NULL,
  UNIQUE INDEX phone_index (country_code, phone_number)
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS register_info (
  user_id        BIGINT PRIMARY KEY,
  device_type 	 INT    NOT NULL DEFAULT -1,
  channel_id  	 INT NOT NULL,
  device_info    VARCHAR(32) NOT NULL,
  os 			 VARCHAR(32) NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS user_info_delta (
  user_id     BIGINT ,
  json_data   VARCHAR(10240) NOT NULL,
  version     BIGINT NOT NULL,
  PRIMARY KEY (user_id, version)
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS user_blacklist (
  user_id     BIGINT  PRIMARY KEY,
  black_user_id   BIGINT NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

