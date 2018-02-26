package com.kolon.comlife.admin.manager.service.impl;

import com.kolon.comlife.admin.manager.model.AdminInfo;
import com.kolon.comlife.admin.manager.model.ManagerInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;



@Repository("managerDAO")
public class ManagerDAO {
    @Resource
    private SqlSession sqlSession;

    /**
     * 관리자 로그인 조회
     * @param managerInfo
     * @return
     */
    public ManagerInfo selectLoginManager(ManagerInfo managerInfo) {
        return sqlSession.selectOne("Manager.selectLoginManager", managerInfo);
    }


    /**
     * 관리자 목록 조회
     * @param managerInfo
     * @return
     */
    public List<AdminInfo> selectManagerList(AdminInfo managerInfo) {
        return sqlSession.selectList("Manager.selectManagerList", managerInfo);
    }

    /**
     * 관리자 상세 조회
     * @param managerInfo
     * @return
     */
    public ManagerInfo selectManagerDetail(ManagerInfo managerInfo) {
        return sqlSession.selectOne("Manager.selectManagerDetail", managerInfo);
    }


    /**
     * 관리자 등록
     * @param managerInfo
     * @return
     */
    public int insertManager(ManagerInfo managerInfo) {
        return sqlSession.insert("Manager.insertManager", managerInfo);
    }

    /**
     * 관리자 수정
     * @param managerInfo
     * @return
     */
    public int updateManager(ManagerInfo managerInfo) {
        return sqlSession.update("Manager.updateManager", managerInfo);
    }


}
