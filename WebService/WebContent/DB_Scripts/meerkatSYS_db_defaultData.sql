USE `meerkatsys_MSMP`;

INSERT INTO `UserType` (`Id`, `Name`) VALUES ('1', 'ADMINISTRATOR');
INSERT INTO `UserType` (`Id`, `Name`) VALUES ('2', 'PARTNER');
INSERT INTO `UserType` (`Id`, `Name`) VALUES ('3', 'MANAGER');

INSERT INTO `UserStatus` (`Id`, `Name`) VALUES ('1', 'ACTIVE');
INSERT INTO `UserStatus` (`Id`, `Name`) VALUES ('2', 'BLOCKED');

INSERT INTO `EmployedType` (`Id`, `Name`) VALUES ('1', 'COMMON');
INSERT INTO `EmployedType` (`Id`, `Name`) VALUES ('2', 'PARTNER');

INSERT INTO `CategoryDistributionType` (`Id`, `Name`) VALUES ('1', 'COMPANY');
INSERT INTO `CategoryDistributionType` (`Id`, `Name`) VALUES ('2', 'PROJECT');