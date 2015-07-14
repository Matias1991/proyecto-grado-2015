CREATE DATABASE if NOT EXISTS `meerkatsys_MSMP` DEFAULT CHARACTER SET utf8;
USE `meerkatsys_MSMP`;

CREATE TABLE UserType
(
	Id                   INTEGER NOT NULL,
	Name                 VARCHAR(20) NOT NULL,
	PRIMARY KEY (Id)
);

CREATE TABLE UserStatus
(
	Id                   INTEGER NOT NULL,
	Name                 VARCHAR(20) NOT NULL,
	PRIMARY KEY (Id)
);

CREATE TABLE User
(
	Id                   INTEGER NOT NULL AUTO_INCREMENT,
	Name                 VARCHAR(50) NOT NULL,
	LastName             VARCHAR(50) NULL,
	Email                VARCHAR(50) NULL,
	Password             VARCHAR(100) NOT NULL,
	UserName             VARCHAR(50) NOT NULL,
	Attempts             INTEGER(10) NULL,
	LastAttemptDateTimeUTC TIMESTAMP NULL,
	UserTypeId           INTEGER NOT NULL,
    UserStatusId           INTEGER NOT NULL,
	PRIMARY KEY (Id),
	FOREIGN KEY FK_User_UserType (UserTypeId) REFERENCES UserType (Id),
    FOREIGN KEY FK_User_UserStatus (UserStatusId) REFERENCES UserStatus (Id)
);