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
    `firstName` TEXT,
    `lastName` TEXT,
    `fullName` TEXT,
    `email` TEXT,
    `avatarUrl` TEXT,
    `authMethod` TEXT NOT NULL,
    `oauth1Id` LONG,
    `oauth2Id` LONG,
    `passwordId` LONG,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table `account` (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
  `userId` TEXT NOT NULL,
  `business` BOOLEAN NOT NULL DEFAULT false,
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

create table social_account (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
  `userId` LONGTEXT NOT NULL,
  `provider` TEXT NOT NULL,
  `accountId` TEXT NOT NULL,
  `accessToken` TEXT,
  `username` TEXT,
  `profilePicture` TEXT,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table `product` (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
  `userId` LONGTEXT NOT NULL,
  `name` TEXT NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table languages (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
  `userId` INT(10) NOT NULL,
  `name` TEXT NOT NULL,
  `code` TEXT NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `updated` TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table cards_sets (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
  `userId` INT(10) NOT NULL,
  `name` TEXT NOT NULL,
  `termsLangId` INT(10),
  `definitionsLangId` INT(10),
  `description` TEXT ,
  `studying` tinyint(1) NOT NULL DEFAULT '0',
  `created` TIMESTAMP NOT NULL,
  `updated` TIMESTAMP NOT NULL,
  FOREIGN KEY (termsLangId) REFERENCES languages(id),
  FOREIGN KEY (definitionsLangId) REFERENCES languages(id),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table flashcards (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
  `userId` INT(10) NOT NULL,
  `cardsSetId` INT(10) NOT NULL,
  `term` TEXT NOT NULL,
  `transcription` TEXT ,
  `definition` TEXT NOT NULL ,
  `studied` BOOLEAN DEFAULT FALSE,
  `rightCount` INT DEFAULT 0,
  `wrongCount` INT DEFAULT 0,
  `created` TIMESTAMP NOT NULL,
  `updated` TIMESTAMP NOT NULL,
  FOREIGN KEY (cardsSetId) REFERENCES cards_sets(id) ON DELETE CASCADE,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table courses (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
  `userId` INT(10) NOT NULL,
  `name` TEXT NOT NULL,
  `shortname` VARCHAR(128) NOT NULL UNIQUE,
  `description` TEXT ,
  `created` TIMESTAMP NOT NULL,
  `updated` TIMESTAMP NOT NULL,
  PRIMARY KEY (id),
  INDEX (shortname(64))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table `student_groups` (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
  `userId` INT(10) NOT NULL,
  `name` TEXT NOT NULL,
  `description` TEXT ,
  `created` TIMESTAMP NOT NULL,
  `updated` TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table `chinese_dictionary` (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
  `traditional` TEXT NOT NULL,
  `simplified` TEXT ,
  `pinyin` TEXT,
  `pinyin_search` TEXT,
  PRIMARY KEY (id),
  INDEX (pinyin_search(64))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table courses_teachers (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
  `userId` INT(10) NOT NULL,
  `courseId` INT(10) NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `updated` TIMESTAMP NOT NULL,
  FOREIGN KEY (userId) REFERENCES user(id) ON DELETE CASCADE,
  FOREIGN KEY (courseId) REFERENCES courses(id) ON DELETE CASCADE,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table studying_sets (
  `id` INT(10) AUTO_INCREMENT NOT NULL,
  `userId` INT(10) NOT NULL,
  `setId` INT(10) NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `updated` TIMESTAMP NOT NULL,
  FOREIGN KEY (userId) REFERENCES user(id),
  FOREIGN KEY (setId) REFERENCES cards_sets(id),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table flashcards_in_studying_sets (
  `fcId` INT(10) AUTO_INCREMENT NOT NULL,
  `ssId` INT(10) NOT NULL,
  `status` BOOLEAN,
  `created` TIMESTAMP NOT NULL,
  `updated` TIMESTAMP NOT NULL,
  FOREIGN KEY (fcId) REFERENCES flashcards(id),
  FOREIGN KEY (ssId) REFERENCES studying_sets(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO study_platform.languages (id, name, code, created, updated, userId) VALUES (1, 'English', 'en', '2015-06-19 12:37:03', '2015-06-19 13:04:50', 1);
INSERT INTO study_platform.languages (id, name, code, created, updated, userId) VALUES (2, 'Russian', 'ru', '2015-06-19 12:37:04', '2015-06-19 13:05:16', 1);
INSERT INTO study_platform.languages (id, name, code, created, updated, userId) VALUES (3, 'Chinese', 'zh', '2015-06-19 12:37:05', '2015-06-19 13:13:45', 1);

# --- !Downs
drop table `token`;
drop table `profile`;
drop table `user`;
drop table `oauth1`;
drop TABLE `oauth2`;
drop TABLE `password`;
drop table `authenticator`;
drop table `social_account`;
drop table `product`;
drop table `account`;

drop TABLE `cards_sets`;
drop TABLE `flashcards`;
drop TABLE `languages`;

drop TABLE `study_classes`;
