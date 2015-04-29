# --- !Ups

CREATE TABLE `users` (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
  `email` TEXT NOT NULL,
  `password` TEXT NULL,
  `accessToken` TEXT NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table `tokens` (
  `uuid` INT(10) NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `expirationTime` TIMESTAMP NOT NULL,
  `isSignUp` BOOLEAN NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table `token` (
  `uuid` TEXT NOT NULL,
  `email` TEXT NOT NULL,
  `creationTime` TIMESTAMP NOT NULL,
  `expirationTime` TIMESTAMP NOT NULL,
  `isSignUp` BOOLEAN NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table `user` (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
    `userId` TEXT NOT NULL,
    `providerId` TEXT NOT NULL,
    `firstName` TEXT NOT NULL,
    `lastName` TEXT NOT NULL,
    `fullName` TEXT NOT NULL,
    `email` TEXT,
    `avatarUrl` TEXT,
    `authMethod` TEXT NOT NULL,
    `token` TEXT,
    `secret` TEXT,
    `accessToken` TEXT,
    `tokenType` TEXT,
    `expiresIn` INTEGER,
    `refreshToken` TEXT,
    `hasher` TEXT,
    `password` TEXT,
    `salt` TEXT,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# --- !Downs

DROP TABLE `users`;
DROP TABLE `tokens`;
drop table `token`;
drop table `user`;

