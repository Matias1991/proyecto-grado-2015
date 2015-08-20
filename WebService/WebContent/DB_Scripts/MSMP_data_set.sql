
/*
 * Para correr este script que crea un set de datos para pruebas, se deben seguir los siguientes pasos:
 * 1 - Ejecutar el script de crecion
 * 2 - Ejecutar el script de default data para las tablas de lookup
 * 3 - Ejecutar el script que crea el primer usuario admin
 * 4 - Ejecutar este script
 */

LOCK TABLES `Project` WRITE;
INSERT INTO `Project` VALUES (1,'SIIAS y Ceibal','2015-08-13 17:29:49','2015-08-13 17:29:49',''),(2,'Accesa','2015-08-13 17:29:58','2015-08-13 17:29:58',''),(3,'Gas Sayago Sop','2015-08-13 17:30:07','2015-08-13 17:30:07',''),(4,'AUPCI','2015-08-13 17:30:15','2015-08-13 17:30:15',''),(5,'Bueno Acuña','2015-08-13 17:30:23','2015-08-13 17:30:23',''),(6,'Arquitectos (30%)','2015-08-13 17:30:31','2015-08-13 17:30:31',''),(7,'ITC (25%)','2015-08-13 17:30:45','2015-08-13 17:30:45',''),(8,'HW Bueno Acuña','2015-08-13 17:30:56','2015-08-13 17:30:56',''),(9,'Quanam','2015-08-13 17:31:55','2015-08-13 17:31:55','');
UNLOCK TABLES;

LOCK TABLES `Category` WRITE;
INSERT INTO `Category` VALUES (1,1,'ANTEL',2000.00,'2015-08-13 03:00:00',NULL,1,'\0',NULL),(2,1,'OSE',1000.00,'2015-08-13 03:00:00',NULL,1,'\0',NULL),(3,1,'UTE',5000.00,'2015-08-13 03:00:00',NULL,1,'\0',NULL),(4,1,'Luis Vazquez',44000.00,'2015-08-13 03:00:00',9,2,'',NULL),(5,1,'Luis Vazquez',4211.00,'2015-08-13 03:00:00',1,2,'',NULL),(6,1,'Luis Vazquez',8540.00,'2015-08-13 03:00:00',2,2,'',NULL),(7,1,'Luis Vazquez',4270.00,'2015-08-13 03:00:00',5,2,'',NULL),(8,1,'PC',25000.00,'2015-08-13 03:00:00',2,2,'\0',NULL);
UNLOCK TABLES;

LOCK TABLES `Employed` WRITE;
INSERT INTO `Employed` VALUES (1,'Empleado comun 1','Empleado comun 1','empleado1@gmail.com','Domicilio empleado 1','09213123123','2015-08-13 17:08:49','2015-08-13 17:12:32',1,NULL),(2,'Empleado comun 2','Empleado comun 2','empleado2@gmail.com','Domicilio empleado 2','09213123123','2015-08-13 17:09:51','2015-08-13 17:12:57',1,NULL),(3,'Rafael','Manente','socio1@gmail.com','Domicilio Socio 1','0992312321','2015-08-13 17:12:02','2015-08-13 17:19:47',2,NULL),(4,'Miguel','Rojas','socio2@gmail.com','Domicilio Socio 2','0923131232','2015-08-13 17:14:13','2015-08-13 17:20:10',2,NULL),(5,'Carlos','Marciukaitis','','','','2015-08-13 17:17:00','2015-08-13 17:17:00',1,NULL),(6,'Sergio','Dutra','','','','2015-08-13 17:17:51','2015-08-13 17:17:51',1,NULL),(7,'Recurso','x','','','','2015-08-13 17:18:46','2015-08-13 17:18:46',1,NULL),(8,'Evelyn','Evelyn','','','','2015-08-13 17:26:43','2015-08-13 17:27:18',1,NULL),(9,'Williams','Martinez','','','','2015-08-13 17:28:19','2015-08-13 17:28:19',1,NULL),(10,'Gabriel','Gomez','','','','2015-08-13 17:29:00','2015-08-13 17:29:00',1,NULL);
UNLOCK TABLES;

LOCK TABLES `SalarySummary` WRITE;
INSERT INTO `SalarySummary` VALUES (1,1,1,20000.00,5000.00,3000.00,1500.00,900.00,1000.00,25.00,25.00,0.00,375.00,0.00,3925.00,2900.00,16075.00,2688.59,2671.00,448.44,0.00,21075.00,33708.03,187.27,0.00,180,'2015-08-13 17:08:49'),(2,1,2,20000.00,5000.00,3000.00,1500.00,900.00,1000.00,25.00,25.00,0.00,375.00,0.00,3925.00,2900.00,16075.00,2688.59,2671.00,448.44,0.00,21075.00,33708.03,187.27,0.00,180,'2015-08-13 17:09:51'),(3,1,3,22000.00,5000.00,3300.00,1650.00,990.00,1100.00,27.50,27.50,0.00,375.00,0.00,4317.50,3152.50,17682.50,2957.45,2938.10,448.44,0.00,22682.50,36496.49,243.31,0.00,150,'2015-08-13 17:12:02'),(4,1,4,50000.00,7000.00,7500.00,3750.00,2250.00,2500.00,62.50,62.50,0.00,525.00,0.00,9812.50,6837.50,40187.50,6721.48,6677.50,627.81,0.00,47187.50,77864.29,409.81,0.00,190,'2015-08-13 17:14:13'),(5,1,5,15000.00,3326.00,2250.00,1125.00,675.00,750.00,18.75,18.75,0.00,249.45,0.00,2943.75,2143.20,12056.25,2016.44,2003.25,298.30,0.00,15382.25,24787.19,105.03,0.00,236,'2015-08-13 17:17:00'),(6,1,6,22000.00,5916.00,3300.00,1650.00,990.00,1100.00,27.50,27.50,0.00,443.70,0.00,4317.50,3221.20,17682.50,2957.45,2938.10,530.59,0.00,23598.50,37563.34,223.59,0.00,168,'2015-08-13 17:17:51'),(7,1,7,20000.00,3782.00,3000.00,1500.00,600.00,1000.00,25.00,25.00,0.00,283.65,0.00,3625.00,2808.65,16375.00,2688.59,2671.00,339.20,0.00,20157.00,32289.44,168.17,0.00,192,'2015-08-13 17:18:46'),(8,1,8,29000.00,3000.00,4350.00,2175.00,870.00,1450.00,36.25,36.25,0.00,225.00,0.00,5256.25,3886.25,23743.75,3898.46,3872.95,269.06,0.00,26743.75,43926.72,292.84,0.00,150,'2015-08-13 17:26:43'),(9,1,9,22000.00,5693.00,3300.00,1650.00,660.00,1100.00,27.50,27.50,0.00,426.97,0.00,3987.50,3204.48,18012.50,2957.45,2938.10,510.59,0.00,23705.50,37303.62,248.69,0.00,150,'2015-08-13 17:28:19'),(10,1,10,15000.00,2000.00,2250.00,1125.00,450.00,750.00,18.75,18.75,0.00,150.00,0.00,2718.75,2043.75,12281.25,2016.44,2003.25,179.38,0.00,14281.25,23242.82,145.27,0.00,160,'2015-08-13 17:29:00');
UNLOCK TABLES;

LOCK TABLES `User` WRITE;
INSERT INTO `User` VALUES (2,'Matias','Acosta','matiasacosta91@gmail.com','VN/6IE/Ng/4=','mati',NULL,NULL,2,1),(3,'Santiago','Maidana','smaidana@gmail.com','MixKSUy90CxjskSjo2u4mQ==','smaidana',NULL,NULL,2,1),(4,'Yamila','Ghan','yami.ghan@gmail.com','j6JVXNCgND8=','yghan',NULL,NULL,2,1),(5,'Javier','Javier','javier@gmail.com','nKfS6Je4Qm9jskSjo2u4mQ==','javier',NULL,NULL,2,1),(6,'Miguel','Rojas','mrojas@gmail.com','sS3bPxLNlV1jskSjo2u4mQ==','mrojas',NULL,NULL,2,1);
UNLOCK TABLES;

LOCK TABLES `Bill` WRITE;
INSERT INTO `Bill` VALUES (8,'A213123213','FACTURA EN PESOS',20000.00,0.00,'\0',0.00,'2015-08-01 03:00:00','\0',2),(9,'A23123123','FACTURA EN DOLRES',5610.00,200.00,'',28.05,'2015-08-01 03:00:00','\0',3);
UNLOCK TABLES;

