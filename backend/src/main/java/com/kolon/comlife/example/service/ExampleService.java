package com.kolon.comlife.example.service;

import com.kolon.comlife.example.model.ExampleInfo;

import java.util.List;

public interface ExampleService {

    public ExampleInfo getExample( String name );

    public List<ExampleInfo> getExampleList();

    public ExampleInfo setExample(ExampleInfo example);

    public int updateExample(ExampleInfo example);

    public void deleteExample(ExampleInfo example);

}

