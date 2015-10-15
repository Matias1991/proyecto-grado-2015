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

INSERT INTO `IVA_Type` (`Id`, `Name`) VALUES ('1', 'ZERO');
INSERT INTO `IVA_Type` (`Id`, `Name`) VALUES ('2', 'TEN');
INSERT INTO `IVA_Type` (`Id`, `Name`) VALUES ('3', 'TWENTY_TWO');

INSERT INTO `GlobalConfiguration` VALUES 
(1,'SMTP_GMAIL_USER','mspm.noreply@gmail.com','Correo electrónico asociado al sistema para el envio notificaciones'),
(2,'SMTP_GMAIL_PASSWORD','msmp1234','Contraseña del correo electrónico asociado al sistema para el envio notificaciones'),
(3,'MAX_ATTEMPTS_LOGIN','3','Maximo de intentos fallidos para ingresar al sistema'),
(4,'EXPIRATION_TIME_ATTEMPTS_IN_MINUTES','15','Tiene de expiración de intentos fallidos para ingresar al sistema'),
(5,'PERCENTAGE_PERSONAL_RETIREMENT_CONTRIBUTION','0.15','Porcentaje aporte jubilatorio personal'),
(6,'PERCENTAGE_EMPLOYERS_CONTRIBUTIONS_RETIREMENT','0.075','Porcentaje aporte jubilatorio patronal'),
(7,'PERCENTAGE_EMPLOYERS_FONASA_CONTRIBUTION','0.05','Porcentaje aporte FONASA patronal'),
(8,'PERCENTAGE_FRL_CONTRIBUTION','0.00125','Porcentaje aporte FRL'),
(9,'PERCENTAGE_TICKETS_EMPLOYERS','0.075','Porcentaje tickets patronal'),
(10,'VAR_1_PREV','1.67','Variable "VAR_1_PREV" para el cálculo del salario de los empleados'),
(11,'VAR_2_PREV','0.80375','Variable "VAR_2_PREV" para el cálculo del salario de los empleados'),
(12,'VAR_3_PREV','0.07625','Variable "VAR_3_PREV" para el cálculo del salario de los empleados'),
(13,'PERCENTAGE_INCIDENCE_SALARY','0.13355','Porcentaje incidencia de sueldo'),
(14,'PERCENTAGE_RESERVE','0.25','Porcentaje de reserva'),
(15,'PERCENTAGE_SALE','0.05','Porcentaje de venta'),
(16,'FICTA_UTILITY','0.132','Utilidad Ficta'),
(17,'PERCENTAGE_IRAE','0.25','Porcentaje IRAE'),
(18,'MINIMUN_IRAE','3500','Monto minimo IRAE');
