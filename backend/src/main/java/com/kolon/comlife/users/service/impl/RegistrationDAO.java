package com.kolon.comlife.users.service.impl;

import com.kolon.comlife.users.model.AgreementInfo;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("registrationDAO")
public class RegistrationDAO {
    @Resource
    private SqlSession sqlSession;

    public List<AgreementInfo> getLatestAgreement() {
        return sqlSession.selectList("Users.Registration.selectLatestAgreement");
    }

}
