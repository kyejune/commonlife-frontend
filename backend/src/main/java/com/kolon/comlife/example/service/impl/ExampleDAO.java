package com.kolon.comlife.example.service.impl;

import com.kolon.comlife.example.model.ExampleInfo;
//import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
//import javax.annotation.Resource;

/**
 */
@Repository("exampleDAO")
public class ExampleDAO {

    ExampleInfo example = new ExampleInfo("empty");

/*
    @Resource
    private SqlSession sqlSession;
*/

    public ExampleInfo selectExample() {
        return example;
    }

    public void insertExample(ExampleInfo newExample) {
        this.example = newExample;
        return;
    }

}
