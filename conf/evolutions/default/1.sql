# --- !Ups


create table `token` (
  `uuid` TEXT NOT NULL,
  `email` TEXT NOT NULL,
  `creationTime` TIMESTAMP NOT NULL,
  `expirationTime` TIMESTAMP NOT NULL,
  `isSignUp` BOOLEAN NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table `profile` (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
    `userId` TEXT NOT NULL,
    `providerId` TEXT NOT NULL,
    `firstName` TEXT NOT NULL,
    `lastName` TEXT NOT NULL,
    `fullName` TEXT NOT NULL,
    `email` TEXT,
    `avatarUrl` TEXT,
    `authMethod` TEXT NOT NULL,
    `oauth1Id` LONG,
    `oauth2Id` LONG,
    `passwordId` LONG,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table `authenticator` (
  `id` TEXT NOT NULL,
  `userId` TEXT NOT NULL,
  `expirationDate` TIMESTAMP NOT NULL,
  `lastUsed` TIMESTAMP NOT NULL,
  `creationDate` TIMESTAMP NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table `user` (
  `id` TEXT NOT NULL,
  `mainId` TEXT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table `oauth1` (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
  `token` TEXT,
  `secret` TEXT,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table `oauth2` (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
  `accessToken` TEXT,
  `tokenType` TEXT,
  `expiresIn` INTEGER,
  `refreshToken` TEXT,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table `password` (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
  `hasher` TEXT,
  `password` TEXT,
  `salt` TEXT,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table `business_profile` (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
  `email` TEXT,
  `hasher` TEXT,
  `password` TEXT,
  `salt` TEXT,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# --- !Downs


drop table `token`;
drop table `profile`;
drop table `oauth1`;
drop TABLE `oauth2`;
drop TABLE `password`;
drop table `authenticator`;
drop table `business_profile`;
