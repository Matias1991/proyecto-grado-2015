﻿
LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES (2,'Matias','Acosta','matiasacosta91@gmail.com','VN/6IE/Ng/4=','mati',NULL,NULL,2,1),(3,'Santiago','Maidana','smaidana@gmail.com','j6JVXNCgND8=','smaidana',0,'2015-01-31 23:32:27',2,1),(4,'Yamila','Ghan','yami.ghan@gmail.com','j6JVXNCgND8=','yghan',0,'2015-10-01 23:32:00',2,1),(5,'Javier','Javier','javier@gmail.com','nKfS6Je4Qm9jskSjo2u4mQ==','javier',NULL,NULL,2,1),(6,'Miguel','Rojas','mrojas@gmail.com','sS3bPxLNlV1jskSjo2u4mQ==','mrojas',NULL,NULL,2,1),(8,'gerente1','gerente1','gerente1@gmail.com','p7l6GXtud6I=','gerente1',0,'2015-09-10 21:48:32',3,1),(9,'gerente2','gerente2','gerente2@gmail.com','p7l6GXtud6I=','gerente2',0,'2015-09-10 21:31:22',3,1),(10,'gerente3','gerente3','gerente3@gmail.com','p7l6GXtud6I=','gerente3',0,'2015-09-10 21:31:29',3,1),(11,'gerente4','gerente4','gerente4@gmail.com','p7l6GXtud6I=','gerente4',0,'2015-09-10 21:31:35',3,1),(12,'gerente5','gerente5','gerente5@gmail.com','p7l6GXtud6I=','gerente5',0,'2015-09-10 21:31:43',3,1);
UNLOCK TABLES;

LOCK TABLES `employed` WRITE;
INSERT INTO `employed` VALUES (1,'Carlos','Marciukaitis','','','','2015-01-31 22:56:57','2015-01-31 22:56:57',1,'\0'),(2,'Sergio','Dutra','','','','2015-01-31 23:00:33','2015-01-31 23:00:33',1,'\0'),(3,'Recurso','X','','','','2015-01-31 23:10:09','2015-01-31 23:10:09',1,'\0'),(4,'Rafael','Manente','','','','2015-01-31 23:12:59','2015-01-31 23:12:59',2,'\0'),(5,'Miguel','Rojas','','','','2015-01-31 23:13:24','2015-01-31 23:13:24',2,'\0'),(6,'Williams','Martz','','','','2015-01-31 23:14:00','2015-01-31 23:14:00',1,'\0');
UNLOCK TABLES;

LOCK TABLES `salarysummary` WRITE;
INSERT INTO `salarysummary` VALUES (1,1,1,13800.00,3060.00,2070.00,1035.00,828.00,690.00,17.25,17.25,0.00,229.50,0.00,2915.25,1971.75,10884.75,1855.13,1842.99,274.44,0.00,13944.75,22804.31,217.18,217.00,105,'2015-01-31 22:56:57'),(2,1,2,20240.00,5443.00,3036.00,1518.00,1214.40,1012.00,25.30,25.30,726.00,408.22,0.00,5001.70,2963.53,15238.30,2720.85,2703.05,488.17,0.00,20681.30,34558.60,154.28,155.00,224,'2015-01-31 23:00:33'),(3,1,3,18400.00,3479.00,2760.00,1380.00,1104.00,920.00,23.00,23.00,0.00,260.93,0.00,3887.00,2583.93,14513.00,2473.50,2457.32,312.02,0.00,17992.00,29705.77,176.82,177.00,168,'2015-01-31 23:10:09'),(4,1,4,20240.00,0.00,3036.00,1518.00,0.00,0.00,25.30,25.30,0.00,0.00,0.00,3061.30,1543.30,17178.70,0.00,0.00,0.00,0.00,17178.70,21783.30,97.25,460.00,224,'2015-01-31 23:12:59'),(5,1,5,20240.00,0.00,3036.00,1518.00,0.00,0.00,25.30,25.30,0.00,0.00,0.00,3061.30,1543.30,17178.70,0.00,0.00,0.00,0.00,17178.70,21783.30,97.25,460.00,224,'2015-01-31 23:13:24'),(6,1,6,20240.00,5238.00,3036.00,1518.00,1619.20,1012.00,25.30,25.30,0.00,392.85,0.00,4680.50,2948.15,15559.50,2720.85,2703.05,469.78,0.00,20797.50,34319.84,154.59,414.00,222,'2015-01-31 23:14:00');
UNLOCK TABLES;

LOCK TABLES `project` WRITE;
INSERT INTO `project` VALUES (1,'Quanam',NULL,NULL,800000.00,'\0',NULL,'2015-01-31 23:24:52','2015-01-31 23:24:52','\0'),(2,'Scotia Bank',NULL,NULL,0.00,'\0',NULL,'2015-01-31 23:27:15','2015-01-31 23:27:15','\0'),(3,'BPS',NULL,NULL,0.00,'\0',4,'2015-01-31 23:27:37','2015-01-31 23:27:37','\0'),(4,'Gonzalez',NULL,NULL,0.00,'\0',5,'2015-01-31 23:27:53','2015-01-31 23:27:53','\0'),(5,'SIIAS y Ceibal',NULL,NULL,0.00,'\0',4,'2015-01-31 23:28:17','2015-01-31 23:28:17','\0'),(6,'Accessa',NULL,NULL,0.00,'\0',4,'2015-01-31 23:28:39','2015-01-31 23:28:39','\0'),(7,'Gas Sayago Sop',NULL,NULL,0.00,'\0',4,'2015-01-31 23:28:56','2015-01-31 23:28:56','\0'),(8,'AUPCI',NULL,NULL,0.00,'\0',NULL,'2015-01-31 23:29:13','2015-01-31 23:29:13','\0'),(9,'Bueno Acuña',NULL,NULL,0.00,'\0',4,'2015-01-31 23:29:35','2015-01-31 23:29:35','\0'),(10,'Arquitectos (30%)',NULL,NULL,0.00,'\0',NULL,'2015-01-31 23:30:03','2015-01-31 23:30:03','\0'),(11,'HW Bueno Acuña',NULL,NULL,0.00,'',4,'2015-01-31 23:30:24','2015-01-31 23:30:24','\0'),(12,'ITC (25%)',NULL,NULL,0.00,'',4,'2015-01-31 23:30:38','2015-01-31 23:30:38','\0');
UNLOCK TABLES;

LOCK TABLES `employed_project` WRITE;
INSERT INTO `employed_project` VALUES (1,1,1,90,'','2015-01-31 23:24:52','2015-01-31 23:24:52'),(1,2,1,120,'','2015-01-31 23:24:52','2015-01-31 23:24:52'),(1,3,1,168,'','2015-01-31 23:24:52','2015-01-31 23:24:52'),(1,4,1,100,'','2015-01-31 23:24:52','2015-01-31 23:24:52'),(1,5,1,100,'','2015-01-31 23:24:52','2015-01-31 23:24:52'),(2,6,1,250,'','2015-01-31 23:27:15','2015-01-31 23:27:15'),(5,2,1,23,'','2015-01-31 23:28:17','2015-01-31 23:28:17'),(11,2,1,58,'','2015-01-31 23:30:24','2015-01-31 23:30:24');
UNLOCK TABLES;

LOCK TABLES `partner_project` WRITE;
INSERT INTO `partner_project` VALUES (1,4,1,1,'','2015-01-31 23:24:52','2015-01-31 23:24:52'),(1,5,1,1,'','2015-01-31 23:24:52','2015-01-31 23:24:52'),(2,4,1,1,'','2015-01-31 23:27:15','2015-01-31 23:27:15'),(2,5,1,1,'','2015-01-31 23:27:15','2015-01-31 23:27:15'),(3,4,1,1,'','2015-01-31 23:27:37','2015-01-31 23:27:37'),(3,5,1,1,'','2015-01-31 23:27:38','2015-01-31 23:27:38'),(4,4,3,1,'','2015-01-31 23:27:53','2015-01-31 23:27:53'),(4,5,2,1,'','2015-01-31 23:27:53','2015-01-31 23:27:53'),(5,4,1,1,'','2015-01-31 23:28:17','2015-01-31 23:28:17'),(5,5,1,1,'','2015-01-31 23:28:18','2015-01-31 23:28:18'),(6,4,1,1,'','2015-01-31 23:28:39','2015-01-31 23:28:39'),(6,5,1,1,'','2015-01-31 23:28:39','2015-01-31 23:28:39'),(7,4,1,1,'','2015-01-31 23:28:56','2015-01-31 23:28:56'),(7,5,1,1,'','2015-01-31 23:28:56','2015-01-31 23:28:56'),(8,4,2,1,'','2015-01-31 23:29:13','2015-01-31 23:29:13'),(8,5,3,1,'','2015-01-31 23:29:13','2015-01-31 23:29:13'),(9,4,1,1,'','2015-01-31 23:29:35','2015-01-31 23:29:35'),(9,5,1,1,'','2015-01-31 23:29:35','2015-01-31 23:29:35'),(10,4,3,1,'','2015-01-31 23:30:03','2015-01-31 23:30:03'),(10,5,2,1,'','2015-01-31 23:30:03','2015-01-31 23:30:03'),(11,4,1,1,'','2015-01-31 23:30:24','2015-01-31 23:30:24'),(11,5,1,1,'','2015-01-31 23:30:24','2015-01-31 23:30:24'),(12,4,1,1,'','2015-01-31 23:30:38','2015-01-31 23:30:38'),(12,5,1,1,'','2015-01-31 23:30:38','2015-01-31 23:30:38');
UNLOCK TABLES;