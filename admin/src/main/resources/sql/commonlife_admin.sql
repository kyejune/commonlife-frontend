
CREATE TABLE `CL_ADMIN_GRP` (
  `GRP_ID` int NOT NULL,
  `GRP_NM` varchar(50) NOT NULL,
  `DESC` varchar(500) DEFAULT NULL,
  `REG_DTTM` datetime DEFAULT NOW(),
  PRIMARY KEY (`GRP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `CL_ADMIN_GRP`  VALUES (0, 'Super Admin', 'COMMONLife 서비스 최상위 관리자 그룹 입니다.', NOW() );
INSERT INTO `CL_ADMIN_GRP`  VALUES (1, 'Complex Admin', 'COMMONLife 서비스 현장 관리자 그룹 입니다.', NOW() );


CREATE TABLE `CL_ADMIN_INFO` (
  `ADMIN_IDX` int(11) NOT NULL AUTO_INCREMENT,
  `ADMIN_ID` varchar(50) NOT NULL,
  `ADMIN_PW` varbinary(512) NOT NULL,
  `ADMIN_NM` varchar(50) NOT NULL,
  `ADMIN_EMAIL` varchar(200) NOT NULL,
  `GRP_ID` int not null,
  `USE_YN` char(1) NOT NULL DEFAULT 'N',
  `DESC` varchar(500) DEFAULT NULL,
  `REG_DTTM` datetime DEFAULT current_timestamp(),
  `REG_ADMIN_IDX` int(11) NOT NULL,
  `UPD_DTTM` datetime DEFAULT current_timestamp(),
  `UPD_ADMIN_IDX` int(11) NOT NULL,
  PRIMARY KEY (`ADMIN_IDX`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `CL_ADMIN_INFO` VALUES (
  0,
  'default_admin',
  PASSWORD('P@SSword907CL'),
  'default super admin',
  'superadmin@service.comminlife.io',
  0,
  'Y',
  'COMMON Life 슈퍼 관리자입니다. 본 관리자의 암호를 바꾸고, 새로운 관리자를 생성하여 사용하시기 바랍니다.',
  NOW(),
  0,
  NOW(),
  0
);

CREATE TABLE `RESERVATION_SCHEMES` (
  `IDX` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `PARENT_IDX` bigint(20) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `RESERVATION_TYPE` varchar(255) DEFAULT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  `SUMMARY` varchar(255) DEFAULT NULL,
  `DESCRIPTION` text DEFAULT NULL,
  `IS_OPEN` char(1) DEFAULT NULL,
  `START_DT` date DEFAULT NULL COMMENT '예약 시작일',
  `START_TIME` time DEFAULT NULL COMMENT '예약 시작 시각',
  `END_DT` date DEFAULT NULL COMMENT '예약 종료일',
  `END_TIME` time DEFAULT NULL COMMENT '예약 종료 시각',
  `AVAILABLE_IN_WEEKEND` char(1) NOT NULL DEFAULT 'N' COMMENT '주말 예약 가능 여부',
  `AVAILABLE_IN_HOLIDAY` char(1) NOT NULL DEFAULT 'N' COMMENT '휴일 예약 가능 여부',
  `POINT` float DEFAULT NULL,
  `AMOUNT` int(11) DEFAULT NULL,
  `IN_STOCK` int(11) DEFAULT NULL,
  `MAX_QTY` int(11) DEFAULT NULL,
  `ACTIVATE_DURATION` int(11) DEFAULT NULL COMMENT '예약 가능 일자',
  `MAINTENANCE_START_AT` datetime DEFAULT NULL,
  `MAINTENANCE_END_AT` datetime DEFAULT NULL,
  `DEL_YN` char(1) NOT NULL DEFAULT 'N',
  `REG_DTTM` datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일',
  `UPD_DTTM` datetime NOT NULL DEFAULT current_timestamp() COMMENT '수정일',
  PRIMARY KEY (`IDX`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

