
LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES (2,'Matias','Acosta','matiasacosta91@gmail.com','VN/6IE/Ng/4=','mati',NULL,NULL,2,1),(3,'Santiago','Maidana','smaidana@gmail.com','j6JVXNCgND8=','smaidana',0,'2015-10-01 21:57:52',2,1),(4,'Yamila','Ghan','yami.ghan@gmail.com','j6JVXNCgND8=','yghan',0,'2015-10-01 20:32:00',2,1),(5,'Javier','Javier','javier@gmail.com','nKfS6Je4Qm9jskSjo2u4mQ==','javier',NULL,NULL,2,1),(6,'Miguel','Rojas','mrojas@gmail.com','sS3bPxLNlV1jskSjo2u4mQ==','mrojas',NULL,NULL,2,1),(8,'gerente1','gerente1','gerente1@gmail.com','p7l6GXtud6I=','gerente1',0,'2015-09-10 18:48:32',3,1),(9,'gerente2','gerente2','gerente2@gmail.com','p7l6GXtud6I=','gerente2',0,'2015-09-10 18:31:22',3,1),(10,'gerente3','gerente3','gerente3@gmail.com','p7l6GXtud6I=','gerente3',0,'2015-09-10 18:31:29',3,1),(11,'gerente4','gerente4','gerente4@gmail.com','p7l6GXtud6I=','gerente4',0,'2015-09-10 18:31:35',3,1),(12,'gerente5','gerente5','gerente5@gmail.com','p7l6GXtud6I=','gerente5',0,'2015-09-10 18:31:43',3,1);
UNLOCK TABLES;

LOCK TABLES `employed` WRITE;
INSERT INTO `employed` VALUES (1,'Carlos','Marciukaitis','','','','2015-10-01 19:38:05','2015-10-01 19:38:05',1,'\0'),(2,'Sergio','Dutra','','','','2015-10-01 19:39:27','2015-10-01 19:45:55',1,'\0'),(3,'Recurso','X','','','','2015-10-01 19:41:09','2015-10-01 19:41:09',1,'\0'),(4,'Rafael','Manente','','','','2015-10-01 19:42:33','2015-10-01 19:42:33',2,'\0'),(5,'Miguel','Rojas','','','','2015-10-01 19:43:21','2015-10-01 19:43:21',2,'\0'),(6,'Williams','Martínez','','','','2015-10-01 19:45:00','2015-10-01 19:46:08',1,'\0');
UNLOCK TABLES;

LOCK TABLES `project` WRITE;
INSERT INTO `project` VALUES (1,'Quanam',NULL,NULL,800000.00,'\0',3,'2015-10-01 19:52:58','2015-10-01 22:09:12','\0'),(2,'Scotia Bank',NULL,NULL,150000.00,'\0',3,'2015-10-01 19:53:42','2015-10-01 20:37:18','\0'),(3,'BPS',NULL,NULL,250000.00,'\0',4,'2015-10-01 19:54:34','2015-10-01 20:56:22','\0'),(4,'Gonzalez',NULL,NULL,50000.00,'\0',5,'2015-10-01 19:55:37','2015-10-01 19:55:37','\0'),(5,'SIIAS y Ceibal',NULL,NULL,480000.00,'\0',4,'2015-10-01 19:57:20','2015-10-01 21:07:05','\0'),(6,'Accessa',NULL,NULL,0.00,'\0',4,'2015-10-01 19:57:53','2015-10-01 19:57:53','\0'),(7,'Gas Sayago Sop',NULL,NULL,0.00,'\0',4,'2015-10-01 20:07:31','2015-10-01 20:07:31','\0'),(8,'AUPCI',NULL,NULL,0.00,'\0',3,'2015-10-01 20:08:09','2015-10-01 20:08:09','\0'),(9,'Bueno Acuña',NULL,NULL,0.00,'\0',4,'2015-10-01 20:09:04','2015-10-01 20:09:04','\0'),(10,'Arquitectos (30%)',NULL,NULL,50000.00,'\0',3,'2015-10-01 20:10:16','2015-10-01 20:10:16','\0'),(11,'HW Bueno Acuña',NULL,NULL,10000.00,'',4,'2015-10-01 20:10:46','2015-10-01 21:16:21','\0'),(12,'ITC (25%)',NULL,NULL,5000.00,'',4,'2015-10-01 20:11:44','2015-10-01 20:11:44','\0');
UNLOCK TABLES;

LOCK TABLES `employed_project` WRITE;
INSERT INTO `employed_project` VALUES (1,1,1,90,'','2015-10-01 22:07:16','2015-10-01 22:09:12'),(1,2,1,120,'','2015-10-01 22:07:16','2015-10-01 22:09:12'),(1,3,1,168,'','2015-10-01 22:07:17','2015-10-01 22:07:17'),(1,4,1,100,'','2015-10-01 22:07:17','2015-10-01 22:07:17'),(1,5,1,100,'','2015-10-01 22:07:17','2015-10-01 22:07:17'),(2,6,1,250,'','2015-10-01 19:53:42','2015-10-01 20:37:18'),(5,2,1,23,'','2015-10-01 21:07:05','2015-10-01 21:07:05'),(11,2,1,58,'','2015-10-01 21:16:21','2015-10-01 21:16:21');
UNLOCK TABLES;

LOCK TABLES `partner_project` WRITE;
INSERT INTO `partner_project` VALUES (1,4,1,1,'','2015-10-01 19:52:58','2015-10-01 19:52:58'),(1,5,1,1,'','2015-10-01 19:52:58','2015-10-01 19:52:58'),(2,4,1,1,'','2015-10-01 19:53:42','2015-10-01 19:53:42'),(2,5,1,1,'','2015-10-01 19:53:42','2015-10-01 19:53:42'),(3,4,1,1,'','2015-10-01 19:54:34','2015-10-01 19:54:34'),(3,5,1,1,'','2015-10-01 19:54:34','2015-10-01 19:54:34'),(4,4,3,1,'','2015-10-01 19:55:37','2015-10-01 19:55:37'),(4,5,2,1,'','2015-10-01 19:55:37','2015-10-01 19:55:37'),(5,4,1,1,'','2015-10-01 19:57:20','2015-10-01 19:57:20'),(5,5,1,1,'','2015-10-01 19:57:20','2015-10-01 19:57:20'),(6,4,1,1,'','2015-10-01 19:57:53','2015-10-01 19:57:53'),(6,5,1,1,'','2015-10-01 19:57:53','2015-10-01 19:57:53'),(7,4,1,1,'','2015-10-01 20:07:31','2015-10-01 20:07:31'),(7,5,1,1,'','2015-10-01 20:07:31','2015-10-01 20:07:31'),(8,4,2,1,'','2015-10-01 20:08:09','2015-10-01 20:08:09'),(8,5,3,1,'','2015-10-01 20:08:09','2015-10-01 20:08:09'),(9,4,1,1,'','2015-10-01 20:09:04','2015-10-01 20:09:04'),(9,5,1,1,'','2015-10-01 20:09:04','2015-10-01 20:09:04'),(10,4,3,1,'','2015-10-01 20:10:16','2015-10-01 20:10:16'),(10,5,2,1,'','2015-10-01 20:10:16','2015-10-01 20:10:16'),(11,4,1,1,'','2015-10-01 20:10:46','2015-10-01 20:10:46'),(11,5,1,1,'','2015-10-01 20:10:46','2015-10-01 20:10:46'),(12,4,1,1,'','2015-10-01 20:11:44','2015-10-01 20:11:44'),(12,5,1,1,'','2015-10-01 20:11:44','2015-10-01 20:11:44');
UNLOCK TABLES;

LOCK TABLES `category` WRITE;
INSERT INTO `category` VALUES (1,1,'SELS','Servicio mensual',30000.00,0.00,'\0',0.00,3,'2015-10-01 00:00:00',3,2,'','2015-10-01 20:38:33'),(2,1,'Luis Vazquez','Itegracion',7000.00,0.00,'\0',0.00,3,'2015-10-01 00:00:00',6,2,'','2015-10-01 21:09:43'),(3,1,'SED','Adaptación a dispositivos móviles',2500.00,0.00,'\0',0.00,3,'2015-10-01 00:00:00',7,2,'','2015-10-01 21:11:22'),(4,1,'Luis Vazquez','Ajustes por nueva ley',3500.00,0.00,'\0',0.00,3,'2015-10-01 00:00:00',9,2,'','2015-10-01 21:13:33'),(5,1,'Luis Vazquez','Adaptacion a multiplataforma',12600.00,450.00,'',28.00,3,'2015-10-01 00:00:00',12,2,'','2015-10-01 22:03:44'),(6,1,'Contabilidad','',5358.00,0.00,'\0',0.00,3,'2015-10-01 00:00:00',NULL,1,'\0','2015-10-01 22:09:48'),(7,1,'Luis Vazquez','Correccion comportamiento',44000.00,0.00,'\0',0.00,3,'2015-10-01 00:00:00',1,2,'','2015-10-01 22:10:19'),(8,1,'BSE','',1263.00,0.00,'\0',0.00,3,'2015-10-01 00:00:00',NULL,1,'\0','2015-10-01 22:10:49');
UNLOCK TABLES;

LOCK TABLES `salarysummary` WRITE;
INSERT INTO `salarysummary` VALUES (1,1,1,15000.00,3326.00,2250.00,1125.00,900.00,750.00,18.75,18.75,0.00,249.45,0.00,3168.75,2143.20,11831.25,2016.44,2003.25,298.30,0.00,15157.25,24787.19,236.07,470.00,105,'2015-10-14 01:10:23'),(2,1,2,22000.00,5916.00,3300.00,1650.00,1320.00,1100.00,27.50,27.50,0.00,443.70,0.00,4647.50,3221.20,17352.50,2957.45,2938.10,530.59,0.00,23268.50,37563.34,167.69,300.00,224,'2015-10-14 01:10:57'),(3,1,3,20000.00,3782.00,3000.00,1500.00,1200.00,1000.00,25.00,25.00,0.00,283.65,0.00,4225.00,2808.65,15775.00,2688.59,2671.00,339.20,0.00,19557.00,32289.44,192.20,400.00,168,'2015-10-14 01:11:29'),(4,1,4,22000.00,0.00,3300.00,1650.00,0.00,0.00,27.50,27.50,0.00,0.00,0.00,3327.50,1677.50,18672.50,0.00,0.00,0.00,0.00,18672.50,23677.50,47.36,224.00,500,'2015-10-14 01:12:11'),(5,1,5,22000.00,0.00,3300.00,1650.00,0.00,0.00,27.50,27.50,0.00,0.00,0.00,3327.50,1677.50,18672.50,0.00,0.00,0.00,0.00,18672.50,23677.50,105.70,500.00,224,'2015-10-14 01:12:42'),(6,1,6,22000.00,5693.00,3300.00,1650.00,1760.00,1100.00,27.50,27.50,0.00,426.97,0.00,5087.50,3204.48,16912.50,2957.45,2938.10,510.59,0.00,22605.50,37303.62,168.03,450.00,222,'2015-10-14 01:13:35');
UNLOCK TABLES;

LOCK TABLES `bill` WRITE;
INSERT INTO `bill` VALUES (1,'A001','Servicio mensual',200000.00,0.00,'\0',0.00,3,'2015-10-01 00:00:00','\0',1),(2,'A002','Sevicio mensual',50000.00,0.00,'\0',0.00,3,'2015-10-01 00:00:00','\0',2),(3,'A003','Servicio mensual',45000.00,0.00,'\0',0.00,3,'2015-10-01 00:00:00','\0',3),(4,'A004','Servicio mensual',7000.00,0.00,'\0',0.00,3,'2015-10-01 00:00:00','\0',4),(5,'A005','Servicio mensual',5000.00,0.00,'\0',0.00,3,'2015-10-01 00:00:00','\0',5),(6,'A006','Servicio mensual',10000.00,0.00,'\0',0.00,3,'2015-10-01 00:00:00','\0',6),(7,'A007','servicio',8500.00,0.00,'\0',0.00,3,'2015-10-01 00:00:00','\0',7),(8,'A008','servicio',3500.00,0.00,'\0',0.00,3,'2015-10-01 00:00:00','\0',8),(9,'A009','servicio',6000.00,0.00,'\0',0.00,3,'2015-10-01 00:00:00','\0',9),(10,'A010','servicio',15000.00,0.00,'\0',0.00,3,'2015-10-01 00:00:00','\0',10),(11,'A011','servicio',14000.00,500.00,'',28.00,3,'2015-10-01 00:00:00','\0',11),(12,'A012','servicio',14000.00,500.00,'',28.00,3,'2015-10-01 00:00:00','\0',12);
UNLOCK TABLES;

LOCK TABLES `charge` WRITE;
INSERT INTO `charge` VALUES (1,'001','adelanto',100000.00,'2015-10-01 23:06:57',1),(2,'002','servicio',350.00,'2015-10-01 23:07:29',11),(3,'003','adelanto',30500.00,'2015-10-01 23:08:09',2);
UNLOCK TABLES;

LOCK TABLES `companyliquidation` WRITE;
UNLOCK TABLES;

LOCK TABLES `projectliquidation` WRITE;
UNLOCK TABLES;