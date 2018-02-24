
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

