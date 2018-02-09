-- TEST SCHEMA --
SET @database_name = COMMONLIFE_DEV_YKIM

DROP DATABASE IF EXISTS @database_name
CREATE DATABASE @database_name
    CHARACTER SET = 'utf8'
    COLLATE = 'utf8_general_ci';

USE @database_name;


------------------------------------------------------------------------------------------------------
--- todo: TO BE DELETED  - Example Table  ------------------------------------------------------------
------------------------------------------------------------------------------------------------------
CREATE TABLE EXAMPLE( id int NOT NULL AUTO_INCREMENT, name VARCHAR(100), primary key(id) );
CREATE UNIQUE INDEX `name_UNIQUE` on EXAMPLE (`name` ASC);
INSERT INTO EXAMPLE VALUES ('test1');
INSERT INTO EXAMPLE VALUES ('test2');
------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------
-- CommonLife New/Extended Tables --------------------------------------------------------------------
------------------------------------------------------------------------------------------------------

---- 사용자 보안 정책 정보 ----
CREATE TABLE `USR_REG_AGREEMENT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USE_YN` char(1) NOT NULL DEFAULT 'N',
  `CONTENT` varchar(8000) DEFAULT NULL,
  `CONTENT_DATE` varchar(45) DEFAULT NULL,
  `REG_ADMIN_ID` varchar(45) DEFAULT NULL,
  `REG_DTTM` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

---- COMPLEX_M의 확장 테이블 ----
CREATE TABLE `COMPLEX_M_EXT` (
  `CMPLX_EXT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CMPLX_ID` int(11) DEFAULT NULL,
  `CL_MAP_SRC` varchar(500) DEFAULT NULL,
  `CL_LOGO_IMG_SRC` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`CMPLX_EXT_ID`),
  UNIQUE KEY `CMPLX_IDX` (`CMPLX_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 단지그룹 (민간과 공공 2개만 존재합니다.) --
CREATE TABLE `COMPLEX_GRP_TYPE_M` (
  `CMPLX_GRP_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CMPLX_GRP_TYPE` varchar(100) DEFAULT NULL,
  `CMPLX_GRP_TYPE_DESC` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`CMPLX_GRP_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `COMPLEX_GRP_TYPE_M`(CMPLX_GRP_TYPE, CMPLX_GRP_TYPE_DESC)
VALUES ('따복하우스', '공공:따복하우스 단지그룹');

INSERT INTO `COMPLEX_GRP_TYPE_M`(CMPLX_GRP_TYPE, CMPLX_GRP_TYPE_DESC)
VALUES ('CommonLife', '민간:커먼라이프 단지그룹');
