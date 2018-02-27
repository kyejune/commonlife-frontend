package com.kolon.comlife.admin.manager.service.impl;

import com.kolon.comlife.admin.manager.model.AdminInfo;
import com.kolon.comlife.admin.manager.model.ManagerInfo;
import com.kolon.comlife.admin.manager.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("managerService")
public class ManagerServiceImpl implements ManagerService {
    public static final Logger logger = LoggerFactory.getLogger(ManagerServiceImpl.class);

    @Resource(name = "managerDAO")
    private ManagerDAO managerDAO;

    /**
     * 관리자 로그인 조회
     * @param managerInfo
     * @return
     */
    public ManagerInfo selectLoginManager(ManagerInfo managerInfo) {
        return managerDAO.selectLoginManager(managerInfo);
    }


    /**
     * 관리자 목록 조회
     * @param managerInfo
     * @return
     */
    public List<AdminInfo> selectManagerList(AdminInfo managerInfo) {
        return managerDAO.selectManagerList(managerInfo);
    }

    /**
     * 관리자 상세 조회
     * @param managerInfo
     * @return
     */
    public AdminInfo selectManagerDetail(AdminInfo managerInfo) {
        return managerDAO.selectManagerDetail(managerInfo);
    }

    /**
     * 관리자 등록
     * @param managerInfo
     * @return
     */
    public int insertManager(ManagerInfo managerInfo) {
        return managerDAO.insertManager(managerInfo);
    }


    /**
     * 관리자 수정
     * @param managerInfo
     * @return
     */
    public int updateManager(ManagerInfo managerInfo) {
        return managerDAO.updateManager(managerInfo);
    }

}
