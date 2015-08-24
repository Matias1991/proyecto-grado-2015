USE `meerkatsys_MSMP`;

INSERT INTO `UserType` (`Id`, `Name`) VALUES ('1', 'ADMINISTRATOR');
INSERT INTO `UserType` (`Id`, `Name`) VALUES ('2', 'PARTNER');
INSERT INTO `UserType` (`Id`, `Name`) VALUES ('3', 'MANAGER');

INSERT INTO `UserStatus` (`Id`, `Name`) VALUES ('1', 'ACTIVE');
INSERT INTO `UserStatus` (`Id`, `Name`) VALUES ('2', 'BLOCKED');

INSERT INTO `EmployedType` (`Id`, `Name`) VALUES ('1', 'COMMON');
INSERT INTO `EmployedType` (`Id`, `Name`) VALUES ('2', 'PARTNER');

INSERT INTO `CategoryType` (`Id`, `Name`) VALUES ('1', 'COMPANY');
INSERT INTO `CategoryType` (`Id`, `Name`) VALUES ('2', 'PROJECT');

INSERT INTO `DistributionType` (`Id`, `Value`, `Description`) VALUES ('1', '50','Reparto equitativo para ambos socios');
INSERT INTO `DistributionType` (`Id`, `Value`, `Description`) VALUES ('2', '2/3','Reparto mayoritario para el socio activo');
INSERT INTO `DistributionType` (`Id`, `Value`, `Description`) VALUES ('3', '1/3','Reparto minoritario para el socio inactivo');