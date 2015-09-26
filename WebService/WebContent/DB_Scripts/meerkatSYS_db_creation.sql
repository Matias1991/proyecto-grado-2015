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
	Deleted				 BIT NULL,
	PRIMARY KEY (Id),
	FOREIGN KEY R_Employed_EmployedType (EmployedTypeId) REFERENCES EmployedType (Id)
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

CREATE TABLE IF NOT EXISTS IVA_Type
(
	Id                   INTEGER NOT NULL,
	Name                 VARCHAR(20) NOT NULL,
	PRIMARY KEY (Id)
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
	ManagerId		INTEGER NULL,
	Amount 			DECIMAL(10,2) NULL,
	IsCurrencyDollar	BIT NULL,
	SellerId		INTEGER NOT NULL,
	CreatedDateTimeUTC  TIMESTAMP NULL,
	UpdatedDateTimeUTC  TIMESTAMP NULL,
	Closed		BIT NULL,
	PRIMARY KEY (Id),
	FOREIGN KEY FK_Project_ManagerId (ManagerId) REFERENCES User (Id),
	FOREIGN KEY FK_Project_SellerId (SellerId) REFERENCES Employed (Id)	
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
	IVA_TypeId			INTEGER NULL,
	AppliedDateTimeUTC  TIMESTAMP NULL,
	ProjectId			INTEGER NULL,
	CategoryType	INTEGER NOT NULL,
	IsRRHH			BIT NULL,
	UpdatedDateTimeUTC  TIMESTAMP NULL,
	PRIMARY KEY (Id, Version),
	FOREIGN KEY FK_Category_CategoryType (CategoryType) REFERENCES CategoryType (Id),
	FOREIGN KEY FK_Category_Project (ProjectId) REFERENCES Project (Id),
	FOREIGN KEY FK_Category_IVA_Type (IVA_TypeId) REFERENCES IVA_Type (Id)
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
	IVA_TypeId			INTEGER NULL,
	AppliedDateTimeUTC  TIMESTAMP NULL,
	IsLiquidated		BIT NULL,
	ProjectId			INTEGER NULL,
	PRIMARY KEY (Id, ProjectId),
	FOREIGN KEY FK_Bill_Project (ProjectId) REFERENCES Project (Id),
	FOREIGN KEY FK_Bill_IVA_Type (IVA_TypeId) REFERENCES IVA_Type (Id)
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
	PRIMARY KEY (ProjectId,employedId,distributionTypeId,version),
	FOREIGN KEY FK_ParterProject_Project (ProjectId) REFERENCES Project (Id),
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
	PRIMARY KEY (ProjectId,employedId,version),
	FOREIGN KEY FK_EmployedProject_Project (ProjectId) REFERENCES Project (Id),
	FOREIGN KEY FK_EmployedProject_Employed (employedId) REFERENCES Employed (Id)	
);

CREATE TABLE IF NOT EXISTS ProjectLiquidation
(
	Id					INTEGER NOT NULL AUTO_INCREMENT,
	ProjectId			INTEGER NOT NULL,
	TotalBills			DECIMAL(10,2) NOT NULL,	
	OutsourcedCost		DECIMAL(10,2) NOT NULL,
	CategoriesCost		DECIMAL(10,2) NOT NULL,	
	EmployeesCost		DECIMAL(10,2) NOT NULL,
	Profit				DECIMAL(10,2) NOT NULL,
	Reserve				DECIMAL(10,2) NOT NULL,
	SellingCost			DECIMAL(10,2) NOT NULL,	
	Partner1Id			INTEGER NOT NULL,
	Partner1Earning	DECIMAL(10,2) NOT NULL,
	Partner2Id			INTEGER NOT NULL,
	Partner2Earning	DECIMAL(10,2) NOT NULL,
	IsCurrencyDollar	BIT NULL,	
	AppliedDateTimeUTC  TIMESTAMP NOT NULL,
	CreatedDateTimeUTC	TIMESTAMP NULL,
	PRIMARY KEY (Id),
    UNIQUE KEY `projectApplied` (`ProjectId`,`AppliedDateTimeUTC`),	
	FOREIGN KEY FK_ProjectLiquidation_Project (ProjectId) REFERENCES Project (Id),
	FOREIGN KEY FK_ProjectLiquidation_Employed_1(Partner1Id) REFERENCES Employed (Id),
	FOREIGN KEY FK_ProjectLiquidation_Employed_2 (Partner2Id) REFERENCES Employed (Id)	
);

CREATE TABLE IF NOT EXISTS CompanyLiquidation
(
	Id						INTEGER NOT NULL AUTO_INCREMENT,
	CompanyCategory			DECIMAL(10,2) NOT NULL,	
	Contribution			DECIMAL(10,2) NOT NULL,
	SalaryNotPartners		DECIMAL(10,2) NOT NULL,	
	Irae					DECIMAL(10,2) NOT NULL,	
	IVASale					DECIMAL(10,2) NOT NULL,	
	IVAPurchase				DECIMAL(10,2) NOT NULL,	
	Partner1Id				INTEGER NOT NULL,
	Partner1EarningsDollar	DECIMAL(10,2) NOT NULL,	
	Partner1EarningsPeso	DECIMAL(10,2) NOT NULL,	
	Partner2Id				INTEGER NOT NULL,
	Partner2EarningsDollar	DECIMAL(10,2) NOT NULL,	
	Partner2EarningsPeso	DECIMAL(10,2) NOT NULL,	
	TypeExchange			DECIMAL(10,2) NOT NULL,
	AppliedDateTimeUTC		TIMESTAMP NOT NULL,
	CreatedDateTimeUTC 		TIMESTAMP NULL,
	PRIMARY KEY(Id),
	UNIQUE KEY `Applied` (`AppliedDateTimeUTC`),
	FOREIGN KEY FK_CompanyLiquidation_Employed_1(Partner1Id) REFERENCES Employed (Id),
	FOREIGN KEY FK_CompanyLiquidation_Employed_2 (Partner2Id) REFERENCES Employed (Id)		
);


