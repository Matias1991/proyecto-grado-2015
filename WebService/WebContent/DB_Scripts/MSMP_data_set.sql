/*
 * Para correr este script que crea un set de datos para pruebas, se deben seguir los siguientes pasos:
 * 1 - Ejecutar el script de crecion
 * 2 - Ejecutar el script de default data para las tablas de lookup
 * 3 - Ejecutar el script que crea el primer usuario admin
 * 4 - Ejecutar este script
 */

LOCK TABLES `User` WRITE;
INSERT INTO `User` VALUES (2,'Matias','Acosta','matiasacosta91@gmail.com','VN/6IE/Ng/4=','mati',NULL,NULL,2,1),(3,'Santiago','Maidana','smaidana@gmail.com','j6JVXNCgND8=','smaidana',NULL,NULL,2,1),(4,'Yamila','Ghan','yami.ghan@gmail.com','j6JVXNCgND8=','yghan',NULL,NULL,2,1),(5,'Javier','Javier','javier@gmail.com','nKfS6Je4Qm9jskSjo2u4mQ==','javier',NULL,NULL,2,1),(6,'Miguel','Rojas','mrojas@gmail.com','sS3bPxLNlV1jskSjo2u4mQ==','mrojas',NULL,NULL,2,1),
/*USUARIOS GERENTES*/
(8,'gerente1','gerente1','gerente1@gmail.com','p7l6GXtud6I=','gerente1',0,'2015-09-10 15:48:32',3,1),(9,'gerente2','gerente2','gerente2@gmail.com','p7l6GXtud6I=','gerente2',0,'2015-09-10 15:31:22',3,1),(10,'gerente3','gerente3','gerente3@gmail.com','p7l6GXtud6I=','gerente3',0,'2015-09-10 15:31:29',3,1),(11,'gerente4','gerente4','gerente4@gmail.com','p7l6GXtud6I=','gerente4',0,'2015-09-10 15:31:35',3,1),(12,'gerente5','gerente5','gerente5@gmail.com','p7l6GXtud6I=','gerente5',0,'2015-09-10 15:31:43',3,1);
UNLOCK TABLES;

LOCK TABLES `Employed` WRITE;
INSERT INTO `Employed` VALUES (1,'Empleado comun 1','Empleado comun 1','empleado1@gmail.com','Domicilio empleado 1','09213123123','2015-08-13 17:08:49','2015-08-13 17:12:32',1,NULL),(2,'Empleado comun 2','Empleado comun 2','empleado2@gmail.com','Domicilio empleado 2','09213123123','2015-08-13 17:09:51','2015-08-13 17:12:57',1,NULL),(3,'Rafael','Manente','socio1@gmail.com','Domicilio Socio 1','0992312321','2015-08-13 17:12:02','2015-08-13 17:19:47',2,NULL),(4,'Miguel','Rojas','socio2@gmail.com','Domicilio Socio 2','0923131232','2015-08-13 17:14:13','2015-08-13 17:20:10',2,NULL),(5,'Carlos','Marciukaitis','','','','2015-08-13 17:17:00','2015-08-13 17:17:00',1,NULL),(6,'Sergio','Dutra','','','','2015-08-13 17:17:51','2015-08-13 17:17:51',1,NULL),(7,'Recurso','x','','','','2015-08-13 17:18:46','2015-08-13 17:18:46',1,NULL),(8,'Evelyn','Evelyn','','','','2015-08-13 17:26:43','2015-08-13 17:27:18',1,NULL),(9,'Williams','Martinez','','','','2015-08-13 17:28:19','2015-08-13 17:28:19',1,NULL),(10,'Gabriel','Gomez','','','','2015-08-13 17:29:00','2015-08-13 17:29:00',1,NULL);
UNLOCK TABLES;

LOCK TABLES `Project` WRITE;
INSERT INTO `Project` VALUES 
(1,'SIIAS y Ceibal','Proyecto de SIIAS y Ceibal',NULL,0,0,3,'2015-09-02 17:08:05','2015-09-02 17:08:05','\0'),(2,'Accesa','Proyecto de Accesa',NULL,0,1,4,'2015-09-02 17:08:43','2015-09-02 17:08:43','\0'),(3,'Gas Sayago Sop','Proyecto de Gas Sayago Sop',NULL,0,0,4,'2015-09-02 17:10:04','2015-09-02 17:10:04','\0'),(4,'AUPCI','Proyecto de AUPCI',NULL,0,1,4,'2015-09-02 17:10:29','2015-09-02 17:10:29','\0'),(5,'Bueno Acuña','Proyecto de Bueno Acuña',NULL,0,0,4,'2015-09-02 17:11:09','2015-09-02 17:11:09','\0'),(6,'Arquitectos (30%)','Proyecto de Arquitectos (30%)',NULL,0,1,4,'2015-09-02 17:11:49','2015-09-02 17:11:49','\0'),(7,'ITC (25%)','Proyecto de ITC (25%)',NULL,0,0,3,'2015-09-02 17:12:31','2015-09-02 17:12:31','\0'),(8,'HW Bueno Acuña','Proyecto de HW Bueno Acuña',NULL,0,1,3,'2015-09-02 17:13:35','2015-09-02 17:13:35','\0'),(9,'Quanam','Proyecto de Quanam',NULL,0,0,4,'2015-09-02 17:13:57','2015-09-02 17:13:57','\0'),(10,'BPS','Proyecto BPS',NULL,0,1,4,'2015-09-02 17:52:59','2015-09-02 17:52:59','\0'),(11,'Scotia Bank','Proyecto Scotia Bank',NULL,0,0,3,'2015-09-02 17:53:26','2015-09-02 17:53:26','\0'),(12,'Gonzalez','Proyecto Gonzalez',NULL,0,1,3,'2015-09-02 17:53:57','2015-09-02 17:53:57','\0'),
/*PROYECTOS QUE TIENEN GERENTES*/
(13,'PROYECTO GERENTE 1','PROYECTO\nGerente 1',8,100000.00,'',4,'2015-09-10 15:33:08','2015-09-10 15:33:08','\0'),(14,'PROYECTO GERENTE 2','PROYECTO\nGerente 2',9,0.00,'\0',5,'2015-09-10 15:34:29','2015-09-10 15:34:29','\0'),(15,'PROYECTO GERENTE 3','PROYECTO\nGerente 3',10,0.00,'',6,'2015-09-10 15:35:10','2015-09-10 15:35:10','\0');
UNLOCK TABLES;

LOCK TABLES `Employed_Project` WRITE;
INSERT INTO `Employed_Project` VALUES (1,1,1,40,'','2015-09-02 17:08:05','2015-09-02 17:08:05'),(1,2,1,40,'','2015-09-02 17:08:05','2015-09-02 17:08:05'),(2,1,1,50,'','2015-09-02 17:08:43','2015-09-02 17:08:43'),(2,2,1,50,'','2015-09-02 17:08:43','2015-09-02 17:08:43'),(2,4,1,70,'','2015-09-02 17:08:43','2015-09-02 17:08:43'),(3,7,1,40,'','2015-09-02 17:10:04','2015-09-02 17:10:04'),(3,8,1,40,'','2015-09-02 17:10:04','2015-09-02 17:10:04'),(3,10,1,50,'','2015-09-02 17:10:04','2015-09-02 17:10:04'),(5,4,1,20,'','2015-09-02 17:11:09','2015-09-02 17:11:09'),(5,5,1,20,'','2015-09-02 17:11:09','2015-09-02 17:11:09'),(5,6,1,20,'','2015-09-02 17:11:09','2015-09-02 17:11:09'),(5,7,1,50,'','2015-09-02 17:11:09','2015-09-02 17:11:09'),(5,8,1,70,'','2015-09-02 17:11:09','2015-09-02 17:11:09'),(6,1,1,50,'','2015-09-02 17:11:49','2015-09-02 17:11:49'),(6,2,1,40,'','2015-09-02 17:11:49','2015-09-02 17:11:49'),(7,2,1,40,'','2015-09-02 17:12:31','2015-09-02 17:12:31'),(7,1,1,50,'','2015-09-02 17:12:31','2015-09-02 17:12:31'),(7,5,1,70,'','2015-09-02 17:12:31','2015-09-02 17:12:31'),(7,4,1,50,'','2015-09-02 17:12:31','2015-09-02 17:12:31'),(7,6,1,40,'','2015-09-02 17:12:31','2015-09-02 17:12:31'),(8,2,1,40,'','2015-09-02 17:13:35','2015-09-02 17:13:35'),(8,3,1,50,'','2015-09-02 17:13:35','2015-09-02 17:13:35'),(8,4,1,40,'','2015-09-02 17:13:35','2015-09-02 17:13:35'),(8,5,1,60,'','2015-09-02 17:13:35','2015-09-02 17:13:35'),(10,2,1,40,'','2015-09-02 17:52:59','2015-09-02 17:52:59'),(10,4,1,50,'','2015-09-02 17:52:59','2015-09-02 17:52:59'),(10,3,1,50,'','2015-09-02 17:52:59','2015-09-02 17:52:59'),(12,3,1,20,'','2015-09-02 17:53:57','2015-09-02 17:53:57'),(12,2,1,20,'','2015-09-02 17:53:57','2015-09-02 17:53:57'),(12,5,1,80,'','2015-09-02 17:53:57','2015-09-02 17:53:57'),
/*EMPLEADOS ASOCIADOS A PROYECTOS QUE TIENEN GERENTES*/
(13,2,1,50,'','2015-09-10 15:33:08','2015-09-10 15:33:08'),(13,4,1,40,'','2015-09-10 15:33:08','2015-09-10 15:33:08'),(13,6,1,80,'','2015-09-10 15:33:08','2015-09-10 15:33:08'),(14,2,1,20,'','2015-09-10 15:34:29','2015-09-10 15:34:29'),(14,4,1,30,'','2015-09-10 15:34:29','2015-09-10 15:34:29'),(14,5,1,40,'','2015-09-10 15:34:29','2015-09-10 15:34:29'),(14,7,1,50,'','2015-09-10 15:34:29','2015-09-10 15:34:29'),(15,3,1,20,'','2015-09-10 15:35:10','2015-09-10 15:35:10'),(15,4,1,30,'','2015-09-10 15:35:10','2015-09-10 15:35:10'),(15,6,1,40,'','2015-09-10 15:35:10','2015-09-10 15:35:10');
UNLOCK TABLES;

LOCK TABLES `Partner_Project` WRITE;
INSERT INTO `Partner_Project` VALUES (1,3,1,1,'','2015-09-02 15:58:35','2015-09-02 15:58:35'),(1,4,1,1,'','2015-09-02 15:58:35','2015-09-02 15:58:35'),(2,3,1,1,'','2015-09-02 17:08:05','2015-09-02 17:08:05'),(2,4,1,1,'','2015-09-02 17:08:05','2015-09-02 17:08:05'),(3,3,1,1,'','2015-09-02 17:08:43','2015-09-02 17:08:43'),(3,4,1,1,'','2015-09-02 17:08:43','2015-09-02 17:08:43'),(4,3,1,1,'','2015-09-02 17:10:04','2015-09-02 17:10:04'),(4,4,1,1,'','2015-09-02 17:10:04','2015-09-02 17:10:04'),(5,3,1,1,'','2015-09-02 17:10:29','2015-09-02 17:10:29'),(5,4,1,1,'','2015-09-02 17:10:29','2015-09-02 17:10:29'),(6,3,1,1,'','2015-09-02 17:11:09','2015-09-02 17:11:09'),(6,4,1,1,'','2015-09-02 17:11:09','2015-09-02 17:11:09'),(7,3,1,1,'','2015-09-02 17:11:49','2015-09-02 17:11:49'),(7,4,1,1,'','2015-09-02 17:11:49','2015-09-02 17:11:49'),(8,3,1,1,'','2015-09-02 17:12:31','2015-09-02 17:12:31'),(8,4,1,1,'','2015-09-02 17:12:31','2015-09-02 17:12:31'),(9,3,1,1,'','2015-09-02 17:13:35','2015-09-02 17:13:35'),(9,4,1,1,'','2015-09-02 17:13:35','2015-09-02 17:13:35'),(10,3,1,1,'','2015-09-02 17:52:59','2015-09-02 17:52:59'),(10,4,1,1,'','2015-09-02 17:52:59','2015-09-02 17:52:59'),(11,3,1,1,'','2015-09-02 17:53:26','2015-09-02 17:53:26'),(11,4,1,1,'','2015-09-02 17:53:26','2015-09-02 17:53:26'),(12,3,1,1,'','2015-09-02 17:53:57','2015-09-02 17:53:57'),(12,4,1,1,'','2015-09-02 17:53:57','2015-09-02 17:53:57'),
/*SOCIOS ASOCIADOS A PROYECTOS QUE TIENEN GERENTES*/
(13,3,2,1,'','2015-09-10 15:33:08','2015-09-10 15:33:08'),(13,4,3,1,'','2015-09-10 15:33:08','2015-09-10 15:33:08'),(14,3,1,1,'','2015-09-10 15:34:29','2015-09-10 15:34:29'),(14,4,1,1,'','2015-09-10 15:34:29','2015-09-10 15:34:29'),(15,3,1,1,'','2015-09-10 15:35:10','2015-09-10 15:35:10'),(15,4,1,1,'','2015-09-10 15:35:10','2015-09-10 15:35:10');
UNLOCK TABLES;

LOCK TABLES `Category` WRITE;
INSERT INTO `Category` VALUES (1,1,'ANTEL',2000.00, 0.00, '\0', 0.00, 1, '2015-08-01 00:00:00',NULL,1,'\0',NULL), (2,1,'OSE',1000.00, 0.00, '\0', 0.00, 1,'2015-08-01 00:00:00',NULL,1,'\0',NULL),(3,1,'UTE',5000.00, 0.00, '\0', 0.00,  1,'2015-08-01 00:00:00',NULL,1,'\0',NULL),(4,1,'Luis Vazquez',44000.00, 0.00, '\0', 0.00,  1,'2015-08-01 00:00:00',9,2,'',NULL),(5,1,'Luis Vazquez',4211.00, 0.00, '\0', 0.00,  1,'2015-08-01 00:00:00',1,2,'',NULL),(6,1,'Luis Vazquez',8540.00, 0.00, '\0', 0.00,  1,'2015-08-01 00:00:00',2,2,'',NULL),(7,1,'Luis Vazquez',4270.00, 0.00, '\0', 0.00, 1, '2015-08-01 00:00:00',5,2,'',NULL),(8,1,'PC',25000.00, 0.00, '\0', 0.00,  1, '2015-08-01 00:00:00',2,2,'\0',NULL);
UNLOCK TABLES;

LOCK TABLES `SalarySummary` WRITE;
INSERT INTO `SalarySummary` VALUES (1,1,1,20000.00,5000.00,3000.00,1500.00,900.00,1000.00,25.00,25.00,0.00,375.00,0.00,3925.00,2900.00,16075.00,2688.59,2671.00,448.44,0.00,21075.00,33708.03,187.27,0.00,180,'2015-08-13 17:08:49'),(2,1,2,20000.00,5000.00,3000.00,1500.00,900.00,1000.00,25.00,25.00,0.00,375.00,0.00,3925.00,2900.00,16075.00,2688.59,2671.00,448.44,0.00,21075.00,33708.03,187.27,0.00,180,'2015-08-13 17:09:51'),(3,1,3,22000.00,5000.00,3300.00,1650.00,990.00,1100.00,27.50,27.50,0.00,375.00,0.00,4317.50,3152.50,17682.50,2957.45,2938.10,448.44,0.00,22682.50,36496.49,243.31,0.00,150,'2015-08-13 17:12:02'),(4,1,4,50000.00,7000.00,7500.00,3750.00,2250.00,2500.00,62.50,62.50,0.00,525.00,0.00,9812.50,6837.50,40187.50,6721.48,6677.50,627.81,0.00,47187.50,77864.29,409.81,0.00,190,'2015-08-13 17:14:13'),(5,1,5,15000.00,3326.00,2250.00,1125.00,675.00,750.00,18.75,18.75,0.00,249.45,0.00,2943.75,2143.20,12056.25,2016.44,2003.25,298.30,0.00,15382.25,24787.19,105.03,0.00,236,'2015-08-13 17:17:00'),(6,1,6,22000.00,5916.00,3300.00,1650.00,990.00,1100.00,27.50,27.50,0.00,443.70,0.00,4317.50,3221.20,17682.50,2957.45,2938.10,530.59,0.00,23598.50,37563.34,223.59,0.00,168,'2015-08-13 17:17:51'),(7,1,7,20000.00,3782.00,3000.00,1500.00,600.00,1000.00,25.00,25.00,0.00,283.65,0.00,3625.00,2808.65,16375.00,2688.59,2671.00,339.20,0.00,20157.00,32289.44,168.17,0.00,192,'2015-08-13 17:18:46'),(8,1,8,29000.00,3000.00,4350.00,2175.00,870.00,1450.00,36.25,36.25,0.00,225.00,0.00,5256.25,3886.25,23743.75,3898.46,3872.95,269.06,0.00,26743.75,43926.72,292.84,0.00,150,'2015-08-13 17:26:43'),(9,1,9,22000.00,5693.00,3300.00,1650.00,660.00,1100.00,27.50,27.50,0.00,426.97,0.00,3987.50,3204.48,18012.50,2957.45,2938.10,510.59,0.00,23705.50,37303.62,248.69,0.00,150,'2015-08-13 17:28:19'),(10,1,10,15000.00,2000.00,2250.00,1125.00,450.00,750.00,18.75,18.75,0.00,150.00,0.00,2718.75,2043.75,12281.25,2016.44,2003.25,179.38,0.00,14281.25,23242.82,145.27,0.00,160,'2015-08-13 17:29:00');
UNLOCK TABLES;

LOCK TABLES `Bill` WRITE;
INSERT INTO `Bill` VALUES (8,'A213123213','FACTURA EN PESOS',20000.00,0.00,'\0',0.00,2,'2015-08-01','\0',1),(9,'A23123123','FACTURA EN DOLRES',5796.00,200.00,'',28.98,2,'2015-08-01','\0',6),(10,'A239','FACTURA A239',5000.00,0.00,'\0',0.00,2,'2015-09-01','\0',1),(11,'A240','FACTURA A240',1304100.00,45000.00,'',28.98,2,'2015-09-01','\0',10),(12,'A237','FACTURA A237',202860.00,7000.00,'',28.98,2,'2015-09-01','\0',12),(13,'A236','FACTURA A236',280900.00,10000.00,'',28.09,2,'2015-09-01','\0',2),(14,'A242','FACTURA A242',8500.00,0.00,'\0',0.00,2,'2015-09-01','\0',3),(15,'A234','FACTURA A234',98315.00,3500.00,'',28.09,2,'2015-09-01','\0',4),(16,'A235-1','FACTURA A235-1',6000.00,0.00,'\0',0.00,2,'2015-09-01','\0',5),(17,'A235-2','FACTURA A235-2',8816.00,0.00,'\0',0.00,2,'2015-09-01','\0',5),
/*FACTURAS ASOCIADAS A PROYECTOS CON GERENTE 1*/
(18,'G34234324','factura para proyecto de gerente 1',56180.00,2000.00,'',28.09,3,'2015-09-01','\0',13),
(19,'GH32424234','Factura para proyecto gerente',5618.00,200.00,'',28.09,3,'2015-10-01','\0',13),
(20,'k324234324','factura para gerente 1',28980.00,1000.00,'',28.98,3,'2015-09-01','\0',13),
(21,'F324324324','FACTURA GERENTE 1',28980.00,1000.00,'',28.98,3,'2015-09-01','\0',13),
/*FACTURAS ASOCIADAS A PROYECTOS CON GERENTE 2*/
(22,'SADA324234','FACTURA GERENTE 2',1000.00,0.00,'\0',0.00,3,'2015-09-01','\0',14),
(23,'32SFSFDSF','FACTURA GERENTE 2',10000.00,0.00,'\0',0.00,3,'2015-09-01','\0',14),
(24,'SADASDSAD','FACTURA',20000.00,0.00,'\0',0.00,3,'2015-09-01','\0',14),
(25,'DS324324','FACTURA POR $75.000',75000.00,0.00,'\0',0.00,3,'2015-09-01','\0',14);
UNLOCK TABLES;

LOCK TABLES `Charge` WRITE;
INSERT INTO `Charge` VALUES (1,'R-1-A235-2','COBRO 5000',5000.00,0.00,'\0',0.00,'2015-09-10 14:54:17',17),(2,'R-2-A235-2','COBRO 2000',2000.00,0.00,'\0',0.00,'2015-09-10 14:54:32',17),(3,'R-1-A235-1','COBRO 4500',4500.00,0.00,'\0',0.00,'2015-09-10 14:54:59',16),(4,'R-1-A234','COBRO 2000',56180.00,2000.00,'',28.09,'2015-09-10 14:55:31',15),(5,'R-1-A242','COBRO 7000',7000.00,0.00,'\0',0.00,'2015-09-10 14:56:00',14),(6,'R-2-A234','COBRO 1000',29980.00,1000.00,'',29.98,'2015-09-10 14:56:32',15),(7,'R-1-A236','COBRO 8000',231840.00,8000.00,'',28.98,'2015-09-10 14:57:21',13),(8,'R-2-A236','COBRO 1000',29980.00,1000.00,'',29.98,'2015-09-10 14:57:51',13),(9,'R-1-A239','COBRO 2500',2500.00,0.00,'\0',0.00,'2015-09-10 14:58:12',10),
/*COBROS ASOCIADOS A FACTURAS ASOCIADAS A PROYECTOS CON GERENTES 1*/
(10,'R-1-GH32424234','COBRO 200',4396.00,200.00,'',21.98,'2015-09-10 15:40:21',19),(11,'R-1-G34234324','COBRO 100',2987.00,100.00,'',29.87,'2015-09-10 15:40:47',18),(12,'R-1-F324324324','COBRO 1000',28870.00,1000.00,'',28.87,'2015-09-10 15:52:11',21),(13,'R-2-F324324324','COBRO 100',2987.00,100.00,'',29.87,'2015-09-10 15:52:29',21),(14,'R-2-G34234324','COBRO 150',4330.50,150.00,'',28.87,'2015-09-10 15:53:08',18),
/*COBROS ASOCIADOS A FACTURAS ASOCIADAS A PROYECTOS CON GERENTES 2*/
(15,'S2SDSAD','COBRO 100',100.00,0.00,'\0',0.00,'2015-09-10 16:42:37',22),(16,'ASD324234','COBRO 500',500.00,0.00,'\0',0.00,'2015-09-10 16:42:55',22),(17,'324DSFSFDSF','COBRO 60000',60000.00,0.00,'\0',0.00,'2015-09-10 16:43:12',25),(18,'DFDSF4543543','COBRO 15000',15000.00,0.00,'\0',0.00,'2015-09-10 16:43:26',25);
UNLOCK TABLES;

