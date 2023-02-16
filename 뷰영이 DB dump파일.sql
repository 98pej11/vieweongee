-- MySQL dump 10.13  Distrib 8.0.30, for macos12 (x86_64)
--
-- Host: 127.0.0.1    Database: vieweongee
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alarm`
--

DROP TABLE IF EXISTS `alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alarm` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(50) NOT NULL,
  `datetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `see` tinyint(1) DEFAULT '0',
  `study_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg7jqp45jwb4yfuimx2p03oqlw` (`study_id`),
  KEY `FKd6g1gp6sn8nt3ku8y2mgu41vs` (`user_id`),
  CONSTRAINT `FKd6g1gp6sn8nt3ku8y2mgu41vs` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKg7jqp45jwb4yfuimx2p03oqlw` FOREIGN KEY (`study_id`) REFERENCES `study` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alarm`
--

LOCK TABLES `alarm` WRITE;
/*!40000 ALTER TABLE `alarm` DISABLE KEYS */;
/*!40000 ALTER TABLE `alarm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(200) NOT NULL,
  `datetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `study_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1ayk9xmp3rro4mtxke2hkd44r` (`study_id`),
  KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
  CONSTRAINT `FK1ayk9xmp3rro4mtxke2hkd44r` FOREIGN KEY (`study_id`) REFERENCES `study` (`id`),
  CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'저 참여하고 싶어요!!!?','2023-02-16 14:08:48',58,2),(2,'자기소개서는 필수인가요?','2023-02-16 14:11:55',58,3),(3,'전공인가요 비전공인가요???','2023-02-16 14:12:29',59,3),(4,'진짜 꼭 합격하고 싶어요ㅠ','2023-02-16 14:14:11',59,4),(5,'마곡 집이랑 가까운데 정말 가고 싶어요! 신청하겠습니다!','2023-02-16 14:45:29',60,5),(6,'다들 화이팅 하시고 꼭 SSAFY 합격하시면 좋겠습니다! (ssafy 8기가..)','2023-02-16 14:48:18',59,1),(7,'마감되었나요?','2023-02-16 15:16:03',58,4);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meeting`
--

DROP TABLE IF EXISTS `meeting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meeting` (
  `study_id` bigint NOT NULL,
  `link` varchar(255) NOT NULL,
  `start` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`study_id`),
  CONSTRAINT `FK2gdy0hs8o6kb3ewl4ckilubwo` FOREIGN KEY (`study_id`) REFERENCES `study` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meeting`
--

LOCK TABLES `meeting` WRITE;
/*!40000 ALTER TABLE `meeting` DISABLE KEYS */;
/*!40000 ALTER TABLE `meeting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(2000) NOT NULL,
  `datetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `title` varchar(25) NOT NULL,
  `type` varchar(10) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcvf4mh5se36inrxn7xlh2brfv` (`user_id`),
  CONSTRAINT `FKcvf4mh5se36inrxn7xlh2brfv` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participant`
--

DROP TABLE IF EXISTS `participant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `participant` (
  `filename` varchar(100) DEFAULT NULL,
  `save` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `study_id` bigint NOT NULL,
  PRIMARY KEY (`study_id`,`user_id`),
  KEY `FKj2ywtc5meno2ouhf5pcq9rsbh` (`user_id`),
  CONSTRAINT `FKhvnb8yps3lsjqqgqujcq0y2w4` FOREIGN KEY (`study_id`) REFERENCES `study` (`id`),
  CONSTRAINT `FKj2ywtc5meno2ouhf5pcq9rsbh` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participant`
--

LOCK TABLES `participant` WRITE;
/*!40000 ALTER TABLE `participant` DISABLE KEYS */;
INSERT INTO `participant` VALUES ('자소서샘플.png','https://s3.ap-northeast-2.amazonaws.com/vieweongee/72bd1df471e542d39823688910b9100a.png',1,58),('KakaoTalk_20230216_110932552.png','https://s3.ap-northeast-2.amazonaws.com/vieweongee/fdab573bb688488cafcb052dc0c0afd4.png',2,58),(NULL,NULL,3,58),(NULL,NULL,4,58),(NULL,NULL,2,59),(NULL,NULL,4,59),(NULL,NULL,5,59),(NULL,NULL,1,60),(NULL,NULL,4,60),(NULL,NULL,5,60);
/*!40000 ALTER TABLE `participant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `progress`
--

DROP TABLE IF EXISTS `progress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `progress` (
  `status` tinyint(1) DEFAULT '0',
  `user_id` bigint NOT NULL,
  `study_id` bigint NOT NULL,
  PRIMARY KEY (`study_id`,`user_id`),
  KEY `FK1tqjxovekjvbd5oa1ohreqqk4` (`user_id`),
  CONSTRAINT `FK1tqjxovekjvbd5oa1ohreqqk4` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKee07uyk5ojb6ntl4lfa4rccg9` FOREIGN KEY (`study_id`) REFERENCES `study` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `progress`
--

LOCK TABLES `progress` WRITE;
/*!40000 ALTER TABLE `progress` DISABLE KEYS */;
INSERT INTO `progress` VALUES (1,1,58),(1,2,58),(1,3,58),(1,4,58),(0,2,59),(0,4,59),(0,5,59),(0,1,60),(0,4,60),(0,5,60);
/*!40000 ALTER TABLE `progress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reply`
--

DROP TABLE IF EXISTS `reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reply` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(200) NOT NULL,
  `datetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `comment_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6w0ns67lrq1jdiwi5xvtj1vxx` (`comment_id`),
  KEY `FKapyyxlgntertu5okpkr685ir9` (`user_id`),
  CONSTRAINT `FK6w0ns67lrq1jdiwi5xvtj1vxx` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`),
  CONSTRAINT `FKapyyxlgntertu5okpkr685ir9` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reply`
--

LOCK TABLES `reply` WRITE;
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
INSERT INTO `reply` VALUES (1,'전공 비전공 무관하게 받고 있습니다!','2023-02-16 14:13:18',3,2),(2,'네 신청해주세요!','2023-02-16 14:47:25',1,1),(3,'네 필수입니다! 업로드 해주세요!','2023-02-16 14:47:35',2,1);
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scorecard`
--

DROP TABLE IF EXISTS `scorecard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scorecard` (
  `ability` int NOT NULL DEFAULT '3',
  `attitude` int NOT NULL DEFAULT '3',
  `feedback` varchar(1000) DEFAULT NULL,
  `interviewer` int NOT NULL DEFAULT '0',
  `loyalty` int NOT NULL DEFAULT '3',
  `solving` int NOT NULL DEFAULT '3',
  `teamwork` int NOT NULL DEFAULT '3',
  `user_id` bigint NOT NULL,
  `study_id` bigint NOT NULL,
  PRIMARY KEY (`study_id`,`user_id`),
  KEY `FK5yedng6a1gyq8xjl7hr4yumet` (`user_id`),
  CONSTRAINT `FK5yedng6a1gyq8xjl7hr4yumet` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKiuc29kbustn0u5f9wxbjess3i` FOREIGN KEY (`study_id`) REFERENCES `study` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scorecard`
--

LOCK TABLES `scorecard` WRITE;
/*!40000 ALTER TABLE `scorecard` DISABLE KEYS */;
INSERT INTO `scorecard` VALUES (15,13,'기업 분석이 더 필요할 것 같아요.',3,9,13,13,1,58),(15,14,'시선 처리만 신경쓰시면 좋은 결과 얻으실 것 같습니다.',3,13,12,11,2,58),(3,4,'면접 준비가 정말 안 되어있어요... 준비가 많이 필요해보입니다.',3,5,6,10,3,58),(7,9,'직무 역량을 조금 더 어필하세요!',3,15,12,11,4,58);
/*!40000 ALTER TABLE `scorecard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `study`
--

DROP TABLE IF EXISTS `study`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `study` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ability` tinyint(1) DEFAULT '1',
  `attitude` tinyint(1) DEFAULT '1',
  `company` varchar(15) NOT NULL,
  `confirm` tinyint(1) DEFAULT '0',
  `content` varchar(500) DEFAULT NULL,
  `job` varchar(50) NOT NULL,
  `loyalty` tinyint(1) DEFAULT '1',
  `personnel` int NOT NULL,
  `regist_datetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `running_time` int NOT NULL,
  `solving` tinyint(1) DEFAULT '1',
  `study_datetime` datetime NOT NULL,
  `teamwork` tinyint(1) DEFAULT '1',
  `title` varchar(25) NOT NULL,
  `type` varchar(5) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkhcyngf15w120k4wx6pp21ijn` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `study`
--

LOCK TABLES `study` WRITE;
/*!40000 ALTER TABLE `study` DISABLE KEYS */;
INSERT INTO `study` VALUES (58,1,1,'삼성SDS',1,'삼성 스터디 진행해 봅시다!!\n1명식 돌아가면서 빠르게 해봐요!!','소프트웨어',1,4,'2023-02-16 13:59:39',2,1,'2023-02-17 12:00:00',1,'삼성 스터디 모집합니다!!','일대다',1),(59,1,1,'SSAFY',0,'SSAFY 면접 스터디 모집합니다!!\n자기소개서는 필수로 올려주세요!!\n연봉 1200이 되어봐요!','소프트웨어',1,6,'2023-02-16 14:10:57',2,1,'2023-02-21 15:30:00',1,'SSAFY 10기 면스~!','다대다',2),(60,1,1,'LG CNS',0,'2:2로 빠르게 진행해보려고 합니다.\n자기소개서 올려주시고, 화이팅 해봐요.','경영지원직',1,4,'2023-02-16 14:42:38',2,1,'2023-02-20 19:00:00',1,'마곡에서 근무할 사람?','다대다',4);
/*!40000 ALTER TABLE `study` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `summary`
--

DROP TABLE IF EXISTS `summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `summary` (
  `user_id` bigint NOT NULL,
  `ability_average` float NOT NULL DEFAULT '0',
  `ability_count` int NOT NULL DEFAULT '0',
  `ability_total` int NOT NULL DEFAULT '0',
  `attitude_average` float NOT NULL DEFAULT '0',
  `attitude_count` int NOT NULL DEFAULT '0',
  `attitude_total` int NOT NULL DEFAULT '0',
  `loyalty_average` float NOT NULL DEFAULT '0',
  `loyalty_count` int NOT NULL DEFAULT '0',
  `loyalty_total` int NOT NULL DEFAULT '0',
  `solving_average` float NOT NULL DEFAULT '0',
  `solving_count` int NOT NULL DEFAULT '0',
  `solving_total` int NOT NULL DEFAULT '0',
  `teamwork_average` float NOT NULL DEFAULT '0',
  `teamwork_count` int NOT NULL DEFAULT '0',
  `teamwork_total` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FK4aq2hcb2llq9e2adpump1e6gy` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `summary`
--

LOCK TABLES `summary` WRITE;
/*!40000 ALTER TABLE `summary` DISABLE KEYS */;
INSERT INTO `summary` VALUES (1,5,3,15,4.33,3,13,3,3,9,4.33,3,13,4,3,13),(2,5,3,15,4.67,3,14,4.33,3,13,4,3,12,3.67,3,11),(3,1,3,3,1.33,3,4,1.67,3,5,2,3,6,3.33,3,10),(4,2.33,3,7,3,3,9,5,3,15,4,3,12,3.67,3,0),(5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
/*!40000 ALTER TABLE `summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(30) NOT NULL,
  `jwt_token` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `provider` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'ssafy@ssafy.com','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSRUZSRVNIIiwiZXhwIjoxNjc3ODc0Mzk5fQ.zlfQZ-DWQ314sOVeAd62dQglnDDohGBXHHuA7rsZCJ3XQYEfQjkGiQecfIXfq5RST1FEoXz_W9p4jw-FiBhNvg','ssafy','$2a$10$3jrDQZCwZ3poD4XlJFoAYeoC7sxVZjDN.LMJSHBHETFJbUkl5gwkG','global'),(2,'test@test.com',NULL,'붱붱','$2a$10$EcDQMRD25B.sn.DgVRXhle8N//0z0Qo.LIDRa1VFrUdpU2D5LzSSm','global'),(3,'vieweongee@gmail.com',NULL,'뷰옹이','$2a$10$wP1H1P3RXbOIEOg5sski6.nNYkekyZJpzQWWEyfFBsusxr4JvSPdm','global'),(4,'bumpingbamboo@naver.com','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSRUZSRVNIIiwiZXhwIjoxNjc3ODc0NTU1fQ._Om70hR4DJgXk-TEgOdzgCV5gL5IrUI8Yxa9G5_tAW2kE6tARhx3IKybHR7Z0vb8GCW3fZpcP-K_M_ZBw4JS-w','폭발하는 대나무','$2a$10$sHchpGkzImb70qVbIvS6aeCB4jsOTBDWZxBHunfhKr6HpsZHsbgu6','global'),(5,'A_ismine@kakao.com',NULL,'취업은 나의 것','$2a$10$n21fu0MZdfoFxgTANYSCmOvvTuY4bnTCOliLKLYszXBRyW/SHfWvy','global');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-17  0:38:30
