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
    UNIQUE KEY `email` (`Email`),
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

CREATE TABLE IF NOT EXISTS Project
(
	Id				INTEGER NOT NULL AUTO_INCREMENT,
	Name			VARCHAR(50) NULL,
	Description		VARCHAR(250) NULL,
	ManagerId		INTEGER NOT NULL,
	SellerId		INTEGER NOT NULL,
	CreatedDateTimeUTC  TIMESTAMP NULL,
	UpdatedDateTimeUTC  TIMESTAMP NULL,
	Enabled		BIT NULL,
	PRIMARY KEY (Id),
	FOREIGN KEY FK_Project_ManagerId (ManagerId) REFERENCES User (Id),
	FOREIGN KEY FK_Project_SellerId (SellerId) REFERENCES Employed (Id),
	UNIQUE KEY `projectName` (`Name`)
);

CREATE TABLE IF NOT EXISTS Category
(
	Id                  INTEGER NOT NULL AUTO_INCREMENT,
	Version              INTEGER NOT NULL,
	Description         VARCHAR(120) NULL,
	AmountPeso 			DECIMAL(10,2) NULL,
	AmountDollar 		DECIMAL(10,2) NULL,
	IsCurrencyDollar	BIT NULL,
	TypeExchange		DECIMAL(10,2) NULL,
	AppliedDateTimeUTC  TIMESTAMP NULL,
	ProjectId			INTEGER NULL,
	CategoryType	INTEGER NOT NULL,
	IsRRHH			BIT NULL,
	UpdatedDateTimeUTC  TIMESTAMP NULL,
	PRIMARY KEY (Id, Version),
	FOREIGN KEY FK_Category_CategoryType (CategoryType) REFERENCES CategoryType (Id),
	FOREIGN KEY FK_Category_Project (ProjectId) REFERENCES Project (Id)
);

CREATE TABLE IF NOT EXISTS Bill
(
	Id                  INTEGER NOT NULL AUTO_INCREMENT,
	Code				VARCHAR(50) NULL,
	Description         VARCHAR(120) NULL,
	AmountPeso 			DECIMAL(10,2) NULL,
	AmountDollar 		DECIMAL(10,2) NULL,
	IsCurrencyDollar	BIT NULL,
	TypeExchange		DECIMAL(10,2) NULL,
	AmountChargedPeso 	DECIMAL(10,2) NULL,
	AmountChargedDollar DECIMAL(10,2) NULL,
	AppliedDateTimeUTC  TIMESTAMP NULL,
	IsLiquidated		BIT NULL,
	ProjectId			INTEGER NULL,
	PRIMARY KEY (Id, ProjectId),
	UNIQUE KEY `code` (`Code`),
	FOREIGN KEY FK_Bill_Project (ProjectId) REFERENCES Project (Id)
);

CREATE TABLE IF NOT EXISTS Charge
(
	Id                  INTEGER NOT NULL AUTO_INCREMENT,
	Number				VARCHAR(50) NULL,
	Description         VARCHAR(120) NULL,
	AmountPeso 			DECIMAL(10,2) NULL,
	AmountDollar 		DECIMAL(10,2) NULL,
	IsCurrencyDollar	BIT NULL,
	TypeExchange		DECIMAL(10,2) NULL,
	CreatedDateTimeUTC  TIMESTAMP NULL,
	BillId			INTEGER NULL,
	PRIMARY KEY (Id, BillId),
	UNIQUE KEY `number` (`Number`),
	FOREIGN KEY FK_Charge_Bill (BillId) REFERENCES Bill (Id)
);

CREATE TABLE IF NOT EXISTS DistributionType
(
	Id                  INTEGER NOT NULL,
	Value               VARCHAR(40) NULL,
	Description			VARCHAR(250) NULL,
	PRIMARY KEY (Id)
);

CREATE TABLE IF NOT EXISTS Partner_Project
(
	ProjectId			INTEGER NOT NULL,
	EmployedId			INTEGER NOT NULL,
	DistributionTypeId	INTEGER NOT NULL,
	Version				INTEGER NOT NULL,
	Enabled				BIT NULL,
	CreatedDateTimeUTC	TIMESTAMP NULL,
	UpdatedDateTimeUTC	TIMESTAMP NULL,
	PRIMARY KEY (proyectId,employedId,distributionTypeId,version),
	FOREIGN KEY FK_ParterProject_Project (proyectId) REFERENCES Project (Id),
	FOREIGN KEY FK_PartnerProject_Employed (employedId) REFERENCES Employed (Id),
	FOREIGN KEY FK_PartnerProject_DistributionType (distributionTypeId) REFERENCES DistributionType (Id)
);

CREATE TABLE IF NOT EXISTS Employed_Project
(
	ProjectId			INTEGER NOT NULL,
	EmployedId			INTEGER NOT NULL,
	Version				INTEGER NOT NULL,
	Hours               INTEGER NULL,	
	Enabled				BIT NULL,
	CreatedDateTimeUTC	TIMESTAMP NULL,
	UpdatedDateTimeUTC	TIMESTAMP NULL,
	PRIMARY KEY (proyectId,employedId,version),
	FOREIGN KEY FK_EmployedProject_Project (proyectId) REFERENCES Project (Id),
	FOREIGN KEY FK_EmployedProject_Employed (employedId) REFERENCES Employed (Id)	
);



