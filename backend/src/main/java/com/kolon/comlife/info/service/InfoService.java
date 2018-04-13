package com.kolon.comlife.info.service;

import com.kolon.comlife.info.model.InfoMain;
import com.kolon.comlife.postFile.service.exception.NoDataException;
import com.kolon.common.model.AuthUserInfo;

public interface InfoService {

    InfoMain getInfoMain(AuthUserInfo authUserInfo ) throws NoDataException;

}
