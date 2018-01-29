package com.kolon.comlife.example.service.impl;

import com.kolon.comlife.example.model.ExampleInfo;
//import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
//import javax.annotation.Resource;

/**
 */
@Repository("exampleDAO")
public class ExampleDAO {

    @Resource
    private SqlSession sqlSession;

    ExampleInfo example = new ExampleInfo("empty");

/*
    @Resource
    private SqlSession sqlSession;
*/

    public ExampleInfo selectExample(String name) {
        this.example.setName(name);
        return sqlSession.selectOne("Example.selectExample", this.example);
    }

    public List<ExampleInfo> selectExampleList() {
        return sqlSession.selectList("Example.selectExampleList");
    }

    public ExampleInfo insertExample(ExampleInfo newExample) {
        this.example = newExample;
        sqlSession.insert("Example.insertExample", this.example);
        return this.example;
    }

    public int updateExample(ExampleInfo updateExample) {
        this.example = updateExample;
        return sqlSession.update("Example.updateExample", this.example);
    }

    public void deleteExample(ExampleInfo deleteExample) {
        this.example = deleteExample;
        sqlSession.delete("Example.deleteExample", this.example);
        return;
    }

}
