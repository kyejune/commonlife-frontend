---- 사용자 보안 정책 정보 ----
CREATE TABLE `CL_USR_REG_AGREEMENT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USE_YN` char(1) NOT NULL DEFAULT 'N',
  `CONTENT` varchar(8000) DEFAULT NULL,
  `CONTENT_DATE` varchar(45) DEFAULT NULL,
  `REG_ADMIN_ID` varchar(45) DEFAULT NULL,
  `REG_DTTM` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
