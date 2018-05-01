package com.kolon.comlife.admin.manager.service.impl;

import com.kolon.comlife.admin.manager.model.AdminInfo;
import com.kolon.comlife.admin.manager.model.ManagerInfo;
import com.kolon.comlife.admin.users.model.PostUserInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;
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
    public AdminInfo selectLoginManager(AdminInfo managerInfo)  {
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


    public List<PostUserInfo> getAdminListForPostById(List<Integer> adminIds ) {
        return sqlSession.selectList( "Manager.selectAdminListForPostById", adminIds );
    }


    /**
     * 관리자 상세 조회
     * @param managerInfo
     * @return
     */
    public AdminInfo selectManagerDetail(AdminInfo managerInfo) {
        return sqlSession.selectOne("Manager.selectManagerDetail", managerInfo);
    }

    /**
     * 관리자 등록
     * @param adminInfo
     * @return
     */
    public int insertManager(AdminInfo adminInfo) throws Exception {
        return sqlSession.insert("Manager.insertManager", adminInfo);
    }

    /**
     * 관리자 수정
     * @param adminInfo
     * @return
     */
    public int updateManager(AdminInfo adminInfo) {
        return sqlSession.update("Manager.updateManager", adminInfo);
    }


}
