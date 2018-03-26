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

----- 시스템/서비스 프로퍼티 ------
CREATE TABLE `SERVICE_PROPERTIES` (
  `PROP_GROUP` varchar(100) NOT NULL,
  `PROP_KEY` varchar(100) NOT NULL,
  `PROP_VALUE` varchar(1000) DEFAULT NULL,
  `PROP_DESC` varchar(500) DEFAULT NULL,
  `USE_YN` char(10) NOT NULL DEFAULT 'N',
  `REG_DTTM` datetime DEFAULT current_timestamp(),
  `REG_USERID` varchar(50) DEFAULT NULL,
  `UPD_DTTM` datetime DEFAULT NULL,
  `UPD_USERID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`PROP_KEY`,`PROP_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


---- LIKE -----
CREATE TABLE `LIKE` (
  `LIKE_IDX` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `PARENT_TYPE` varchar(31) NOT NULL DEFAULT '',
  `PARENT_IDX` int(11) unsigned NOT NULL,
  `USR_ID` int(11) unsigned NOT NULL,
  `DEL_YN` char(1) NOT NULL DEFAULT 'N',
  `REG_DTTM` datetime NOT NULL DEFAULT current_timestamp(),
  `UPD_DTTM` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`LIKE_IDX`),
  UNIQUE KEY `idxunique` (`PARENT_IDX`,`USR_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8



---- POST -----
CREATE TABLE `POST` (
  `POST_IDX` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '인덱스',
  `USR_ID` int(11) NOT NULL COMMENT '사용자 인덱스',
  `CMPLX_ID` int(11) DEFAULT NULL COMMENT '단지 인덱스',
  `POST_TYPE` varchar(63) NOT NULL DEFAULT '' COMMENT '포스트 유형 feed | event | news',
  `CONTENT` longtext NOT NULL COMMENT '본문',
  `DEL_YN` char(1) NOT NULL DEFAULT 'N' COMMENT '소프트 딜리트 키',
  `REG_DTTM` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시점',
  `UPD_DTTM` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최종수정시점',
  PRIMARY KEY (`POST_IDX`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

---- POST_FILE -----
CREATE TABLE `POST_FILE` (
  `POST_FILE_IDX` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '포스트 파일 인덱스',
  `POST_IDX` bigint(20) unsigned DEFAULT NULL COMMENT '포스트 인덱스',
  `USR_ID` int(11) NOT NULL COMMENT '사용자 인덱스',
  `HOST` varchar(2047) DEFAULT NULL COMMENT '도메인 호스트 (선택사항)',
  `MIME_TYPE` varchar(255) NOT NULL DEFAULT '' COMMENT 'MIME Type',
  `FILE_NAME` varchar(255) DEFAULT NULL COMMENT '파일명 (선택사항)',
  `FILE_PATH` varchar(2047) NOT NULL DEFAULT '' COMMENT '저장 경로',
  `DEL_YN` char(1) NOT NULL DEFAULT 'N' COMMENT '소프트 딜리트 키',
  `REG_DTTM` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시점',
  `UPD_DTTM` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최종수정시점',
  PRIMARY KEY (`POST_FILE_IDX`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;