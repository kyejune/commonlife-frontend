package com.kolon.comlife.example.service;

import com.kolon.comlife.example.model.ExampleInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ExampleService {

    public ExampleInfo getExample( String name );

    public List<ExampleInfo> getExampleList();

    public ExampleInfo setExample(ExampleInfo example);

    public int updateExample(ExampleInfo example);

    public void deleteExample(ExampleInfo example);

    @Transactional
    public void transactionTestExample(ExampleInfo example, boolean txFailedFlag ) throws Exception;

}

