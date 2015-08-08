CREATE DATABASE if NOT EXISTS `meerkatsys_MSMP` DEFAULT CHARACTER SET utf8;
USE `meerkatsys_MSMP`;

CREATE TABLE IF NOT EXISTS UserType
(
	Id                   INTEGER NOT NULL,
	Name                 VARCHAR(20) NOT NULL,
	PRIMARY KEY (Id)
);

CREATE TABLE IF NOT EXISTS UserStatus
(
	Id                   INTEGER NOT NULL,
	Name                 VARCHAR(20) NOT NULL,
	PRIMARY KEY (Id)
);

CREATE TABLE IF NOT EXISTS User
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
	UNIQUE KEY `userName` (`UserName`),
	FOREIGN KEY FK_User_UserType (UserTypeId) REFERENCES UserType (Id),
    FOREIGN KEY FK_User_UserStatus (UserStatusId) REFERENCES UserStatus (Id)
);

CREATE TABLE IF NOT EXISTS EmployedType
(
	Id                   INTEGER NOT NULL,
	Name                 VARCHAR(20) NULL,
	PRIMARY KEY (Id)
);

CREATE TABLE IF NOT EXISTS Employed
(
	Id                   INTEGER NOT NULL AUTO_INCREMENT,
	Name                 VARCHAR(50) NULL,
	LastName             VARCHAR(50) NULL,
	Email                VARCHAR(50) NULL,
	Address              VARCHAR(50) NULL,
	Cellphone            VARCHAR(50) NULL,
	CreatedDateTimeUTC   TIMESTAMP NULL,
	UpdatedDateTimeUTC   TIMESTAMP NULL,
	EmployedTypeId       INTEGER NOT NULL,
	UserId               INTEGER NULL,
	PRIMARY KEY (Id),
	FOREIGN KEY R_Employed_EmployedType (EmployedTypeId) REFERENCES EmployedType (Id),
	FOREIGN KEY R_Employed_User (UserId) REFERENCES User (Id)
);

CREATE TABLE IF NOT EXISTS SalarySummary
(
	Id                   INTEGER NOT NULL AUTO_INCREMENT,
	Version              INTEGER NOT NULL,
	EmployedId           INTEGER NOT NULL,
	NominalSalary        DECIMAL(10,2) NULL,
	Tickets              DECIMAL(10,2) NULL,
	PersonalRetirementContribution DECIMAL(10,2) NULL,
	EmployersContributionsRetirement DECIMAL(10,2) NULL,
	PersonalFONASAContribution DECIMAL(10,2) NULL,
	EmployersFONASAContribution DECIMAL(10,2) NULL,
	PersonalFRLContribution DECIMAL(10,2) NULL,
	EmployersFRLContribution DECIMAL(10,2) NULL,
	IRPF                 DECIMAL(10,2) NULL,
	TicketsEmployers     DECIMAL(10,2) NULL,
	BSE                  DECIMAL(10,2) NULL,
	TotalDiscounts       DECIMAL(10,2) NULL,
	TotalEmployerContributions DECIMAL(10,2) NULL,
	NominalWithoutContributions DECIMAL(10,2) NULL,
	DismissalPrevention  DECIMAL(10,2) NULL,
	IncidenceSalary      DECIMAL(10,2) NULL,
	IncidenceTickets     DECIMAL(10,2) NULL,
	RET                  DECIMAL(10,2) NULL,
	SalaryToPay			 DECIMAL(10,2) NULL,
	CostMonth            DECIMAL(10,2) NULL,
	CostRealHour         DECIMAL(10,2) NULL,
	CostSaleHour         DECIMAL(10,2) NULL,    
	Hours                INTEGER NULL,
    CreatedDateTimeUTC   TIMESTAMP NULL,
	PRIMARY KEY (Id,Version,EmployedId),
	FOREIGN KEY R_SalarySummary_Employed (EmployedId) REFERENCES Employed (Id)
);

CREATE TABLE IF NOT EXISTS CategoryType
(
	Id                   INTEGER NOT NULL,
	Name                 VARCHAR(40) NULL,
	PRIMARY KEY (Id)
);

CREATE TABLE IF NOT EXISTS Category
(
	Id                  INTEGER NOT NULL AUTO_INCREMENT,
	Description         VARCHAR(120) NULL,
	Amount 				DECIMAL(10,2) NULL,
	CreatedDateTimeUTC  TIMESTAMP NULL,
	ProjectId			INTEGER NULL,
	CategoryType	INTEGER NOT NULL,
	PRIMARY KEY (Id),
	FOREIGN KEY FK_Category_CategoryType (CategoryType) REFERENCES CategoryType (Id)
);

CREATE TABLE IF NOT EXISTS Project
(
	Id				INTEGER NOT NULL AUTO_INCREMENT,
	Name			VARCHAR(50) NULL,
	CreatedDateTimeUTC  TIMESTAMP NULL,
	UpdatedDateTimeUTC  TIMESTAMP NULL,
	Enabled		BIT NULL,
	PRIMARY KEY (Id),
	UNIQUE KEY `projectName` (`Name`)
);