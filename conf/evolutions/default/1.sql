# --- !Ups

CREATE TABLE `users` (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
  `email` TEXT NOT NULL,
  `password` TEXT NOT NULL,
  `accessToken` TEXT NOT NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table `tokens` (
  `uuid` INT(10) NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `expirationTime` TIMESTAMP NOT NULL,
  `isSignUp` BOOLEAN NOT NULL
);


# --- !Downs

DROP TABLE `users`;
DROP TABLE `tokens`;
