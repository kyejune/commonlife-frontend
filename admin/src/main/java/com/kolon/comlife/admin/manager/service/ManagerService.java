package com.kolon.comlife.admin.manager.service;

import com.kolon.comlife.admin.manager.model.AdminInfo;

import java.util.List;



public interface ManagerService {

    /**
     * 관리자 로그인 조회
     * @param managerInfo
     * @return
     */
    AdminInfo selectLoginManager( AdminInfo managerInfo);

    /**
     * 관리자 목록 조회
     * @param managerInfo
     * @return
     */
    List<AdminInfo> selectManagerList( AdminInfo managerInfo);

    /**
     * 관리자 상세 조회
     * @param managerInfo
     * @return
     */
    AdminInfo selectManagerDetail( AdminInfo managerInfo);

    /**
     * 관리자 등록
     * @param managerInfo
     * @return
     */
    int insertManager( AdminInfo  managerInfo) throws Exception;

    /**
     * 관리자 수정
     * @param managerInfo
     * @return
     */
    int updateManager( AdminInfo managerInfo);

}
