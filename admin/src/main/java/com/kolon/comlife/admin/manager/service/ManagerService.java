package com.kolon.comlife.admin.manager.service;

import com.kolon.comlife.admin.manager.model.ManagerInfo;

import java.util.List;



public interface ManagerService {

    /**
     * 관리자 로그인 조회
     * @param managerInfo
     * @return
     */
    ManagerInfo selectLoginManager(ManagerInfo managerInfo);

    /**
     * 관리자 목록 조회
     * @param managerInfo
     * @return
     */
    List<ManagerInfo> selectManagerList(ManagerInfo managerInfo);

    /**
     * 관리자 상세 조회
     * @param managerInfo
     * @return
     */
    ManagerInfo selectManagerDetail(ManagerInfo managerInfo);

    /**
     * 관리자 등록
     * @param managerInfo
     * @return
     */
    int insertManager(ManagerInfo managerInfo);

    /**
     * 관리자 수정
     * @param managerInfo
     * @return
     */
    int updateManager(ManagerInfo managerInfo);

}
