package com.kolon.comlife.admin.support.service.impl;

import com.kolon.comlife.admin.support.model.SupportCategoryInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Board Data Access Object
 */
@Repository("supportDAO")
public class SupportDAO {
    @Resource
    private SqlSession sqlSession;

    public List<SupportCategoryInfo> selectCategoryListByComplexId(SupportCategoryInfo categoryInfo) {
        return sqlSession.selectList("Support.selectCategoryListByComplexId", categoryInfo);
    }

    public int updateCategoryDisplayOrder( SupportCategoryInfo categoryInfo ) {
        return sqlSession.update("Support.updateCategoryDisplayOrder", categoryInfo);
    }


}
