# --- !Ups

CREATE TABLE `usertable` (
  `id`                     BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `username`                  VARCHAR(100) NOT NULL,
  `password`               VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_USERNAME` (`username`)
);

INSERT INTO `usertable` VALUES (1, 'diana', 'diana');

# --- !Downs

DROP TABLE User;