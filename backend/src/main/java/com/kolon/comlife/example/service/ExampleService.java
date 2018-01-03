package com.kolon.comlife.example.service;

import com.kolon.comlife.example.model.ExampleInfo;

import java.util.List;

public interface ExampleService {

    public ExampleInfo getExample();

    public List<ExampleInfo> getExampleList();

    public void setExample(ExampleInfo example);

}

