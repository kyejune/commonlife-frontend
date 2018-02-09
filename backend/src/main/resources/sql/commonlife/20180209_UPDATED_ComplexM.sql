-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: iotdev.cjrhbxndfnvb.ap-northeast-2.rds.amazonaws.com    Database: homeiot_cp
-- ------------------------------------------------------
-- Server version	5.5.5-10.0.24-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `COMPLEX_M`
--

DROP TABLE IF EXISTS `COMPLEX_M`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COMPLEX_M` (
  `CMPLX_ID` int(11) NOT NULL,
  `PRJCT_CD` varchar(10) NOT NULL,
  `CMPLX_CD` varchar(30) DEFAULT NULL,
  `CMPLX_NM` varchar(100) DEFAULT NULL,
  `CMPLX_S_NM` varchar(100) DEFAULT NULL,
  `CMPLX_GB_CD` varchar(30) DEFAULT NULL COMMENT '단지구분코드',
  `LATI_POS` double DEFAULT NULL,
  `LONG_POS` double DEFAULT NULL,
  `SIZ` varchar(10) DEFAULT NULL,
  `LND_SIZ` varchar(10) DEFAULT NULL,
  `UNIT_CNT` varchar(30) DEFAULT NULL,
  `ADDR` varchar(200) DEFAULT NULL,
  `ADDR_DTL` varchar(200) DEFAULT NULL,
  `ADDR1` varchar(200) DEFAULT NULL,
  `ADDR2` varchar(200) DEFAULT NULL,
  `LOC1_NM` varchar(30) DEFAULT NULL,
  `LOC2_NM` varchar(30) DEFAULT NULL,
  `LOC3_NM` varchar(30) DEFAULT NULL,
  `POST_NO` varchar(30) DEFAULT NULL,
  `IMG_SRC` varchar(200) DEFAULT NULL,
  `GW_CD` char(30) DEFAULT NULL,
  `GW_PRJCT_CD` varchar(30) DEFAULT NULL COMMENT 'GW프로젝트코드',
  `GW_LOC_NM` varchar(30) DEFAULT NULL COMMENT 'GW_지역명',
  `VOICE_ENGINE_CD` varchar(30) DEFAULT NULL,
  `CONN_URL` varchar(80) DEFAULT NULL,
  `MAP_SRC` varchar(300) DEFAULT NULL,
  `LOGO_IMG_SRC` varchar(200) DEFAULT NULL,
  `MNL_SRC` varchar(200) DEFAULT NULL,
  `AWS_ID` varchar(30) DEFAULT NULL,
  `DEVICE_APL_YN` char(1) DEFAULT NULL,
  `DEVICE_APL_DT` datetime DEFAULT NULL,
  `SIP_IP` varchar(30) DEFAULT NULL,
  `SIP_PORT` varchar(30) DEFAULT NULL,
  `SIP_PROTOCOL` varchar(30) DEFAULT NULL,
  `SEC_ID` varchar(30) DEFAULT NULL,
  `ADMIN_ID` varchar(30) DEFAULT NULL,
  `VIDEO_CALL_YN` varchar(30) DEFAULT NULL,
  `RMK` varchar(500) DEFAULT NULL,
  `REG_ID` varchar(20) DEFAULT NULL,
  `REG_DT` datetime DEFAULT NULL,
  `CHG_ID` varchar(20) DEFAULT NULL,
  `CHG_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`CMPLX_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COMPLEX_M`
--

LOCK TABLES `COMPLEX_M` WRITE;
/*!40000 ALTER TABLE `COMPLEX_M` DISABLE KEYS */;
INSERT INTO `COMPLEX_M` (`CMPLX_ID`, `PRJCT_CD`, `CMPLX_CD`, `CMPLX_NM`, `CMPLX_S_NM`, `CMPLX_GB_CD`, `LATI_POS`, `LONG_POS`, `SIZ`, `LND_SIZ`, `UNIT_CNT`, `ADDR`, `ADDR_DTL`, `ADDR1`, `ADDR2`, `LOC1_NM`, `LOC2_NM`, `LOC3_NM`, `POST_NO`, `IMG_SRC`, `GW_CD`, `GW_PRJCT_CD`, `GW_LOC_NM`, `VOICE_ENGINE_CD`, `CONN_URL`, `MAP_SRC`, `LOGO_IMG_SRC`, `MNL_SRC`, `AWS_ID`, `DEVICE_APL_YN`, `DEVICE_APL_DT`, `SIP_IP`, `SIP_PORT`, `SIP_PROTOCOL`, `SEC_ID`, `ADMIN_ID`, `VIDEO_CALL_YN`, `RMK`, `REG_ID`, `REG_DT`, `CHG_ID`, `CHG_DT`) VALUES (119,'CV10101','7000','Cvnet 단지(TCP)','씨브이넷아파트',NULL,37.5576862,126.92348700000002,NULL,NULL,'','서울 마포구 동교로 171 (동교동)','','서울시','마포구','','','','13837','','cvnet',NULL,NULL,'',NULL,NULL,'apt1502349010965',NULL,'cvnet@kolon.com',NULL,NULL,'118.223.28.76','25061','','01050100','01000100','Y','','admin','2017-03-31 14:26:42','ssrho','2017-11-08 10:21:34'),(125,'DONGWON','1234','동원테스트현장','동원테스트현장','AD01101',37.3945833,126.96039280000002,NULL,NULL,'2','경기 안양시 동안구 시민대로 273 (관양동, 효성인텔리안)','','경기','안양시','경기','안양시','','14054','apt1515375112535','commax','','','',NULL,NULL,'apt1506047271149','apt1515374728671','joyer@kolon.com',NULL,NULL,'','','','','','','','bdw1002','2017-06-01 16:36:12','bdw1002','2018-02-08 10:38:50'),(126,'SAM1234','4000','삼성스마트가전','삼성',NULL,37.5104873,127.04599229999997,NULL,NULL,'','서울 강남구 봉은사로 418 (삼성동, 조양빌딩)','','서울','강남구','서울','강남구','','06153','','commax',NULL,NULL,'',NULL,NULL,'apt1501544428869',NULL,'ssrho@kolon.com',NULL,NULL,'','','','','','','','bdw1002','2017-07-04 18:10:56','ssrho','2017-09-26 12:02:49'),(127,'PJ-TBS1','TBS1','따복하우스1차 베니트 개발','따복 1차 베니트',NULL,37.4255245,126.99038900000005,NULL,NULL,'3','경기 과천시 코오롱로 11 (별양동, 코오롱)','10층 (별양동 코오롱타워 본관)','경기','과천시','경기','과천시','','13837','','commax',NULL,NULL,'',NULL,NULL,NULL,NULL,'joyer@kolon.com',NULL,NULL,'','','','','','','TBS1 : Test+Benit+Service+1','hyunjae_woo','2017-11-06 16:34:43','hyunjae_woo','2017-11-08 13:11:19'),(128,'CV00001','CVNT','CVNET(Rest)','CVREST',NULL,NULL,NULL,NULL,NULL,'100','경기 수원시 권선구 경수대로 14 (대황교동, 대황교동 가스충전소)','','서울특별시','강남구','서울특별시','강남구','','16661','','CVNET',NULL,NULL,'MB01101',NULL,NULL,NULL,NULL,'joyer@kolon.com',NULL,NULL,'','','','','','','','ssrho','2017-11-08 10:23:27','inmyeong_jeong','2017-11-15 16:04:59'),(129,'PJ-TCS1','TCS1','따복하우스1차 코맥스 개발','코맥스 개발.',NULL,37.4308517,127.1550545,NULL,NULL,'1','경기 성남시 중원구 둔촌대로 494 (상대원동)','','경기','성남시','경기','성남시','','13229','','commax',NULL,NULL,'',NULL,NULL,NULL,NULL,'joyer@kolon.com',NULL,NULL,'','','','','','','TCS1 : Test+Commax+Service+1','hyunjae_woo','2017-11-08 13:20:48','bdw1002','2017-11-16 14:32:24'),(130,'LOAD1234','LOAD','Load Runner 현장','Load Runner 현장',NULL,37.4255245,126.99038900000005,NULL,NULL,'1000','경기 과천시 코오롱로 11 (별양동, 코오롱)','10층','경기','과천시','경기','과천시','','13837','','commax',NULL,NULL,'',NULL,NULL,NULL,NULL,'joyer@kolon.com',NULL,NULL,'','','','','','','','bdw1002','2017-11-10 10:58:14','bdw1002','2017-11-10 14:28:20'),(131,'YS0001','YS01','역삼Tree하우스 테스트','역삼','AD01102',37.4899896,127.03216090000001,NULL,NULL,'10','서울 강남구 강남대로 298 (역삼동, 푸르덴셜타워)','','서울','강남구','서울','강남구','','06253','apt1515375112535','commax','','','',NULL,NULL,NULL,'apt1515374728671','joyer@kolon.com',NULL,NULL,'','','','','','','','bdw1002','2018-02-08 13:57:43','bdw1002','2018-02-08 14:02:13'),(132,'EL0002','EL02','을지로Tree하우스 테스트','을지로점','AD01102',37.5657017,126.9862187,NULL,NULL,'','서울 중구 을지로 76 (을지로2가, 유안타증권)','','서울','중구','서울','중구','','04538','apt1515375112535','commax','','','',NULL,NULL,NULL,'apt1515374728671','joyer@kolon.com',NULL,NULL,'','','','','','','','bdw1002','2018-02-08 14:07:00','bdw1002','2018-02-08 14:08:45'),(133,'DB0001','DB99','안양따복 하우스 테스트','안양따복','AD01102',37.3945833,126.96039280000002,NULL,NULL,'10','경기 안양시 동안구 시민대로 273 (관양동, 효성인텔리안)','','경기','안양시','경기','안양시','','14054','apt1515375112535','commax','','','',NULL,NULL,NULL,'apt1515374728671','joyer@kolon.com',NULL,NULL,'','','','','','','','bdw1002','2018-02-08 14:12:50','bdw1002','2018-02-08 14:14:39');
/*!40000 ALTER TABLE `COMPLEX_M` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-09 11:09:47
