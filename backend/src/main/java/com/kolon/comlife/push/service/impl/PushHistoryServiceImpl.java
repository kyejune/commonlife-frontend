package com.kolon.comlife.push.service.impl;

import com.kolon.comlife.push.model.PushHistoryInfo;
import com.kolon.comlife.push.service.PushHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("pushHistoryService")
public class PushHistoryServiceImpl implements PushHistoryService {
    public static final Logger logger = LoggerFactory.getLogger(PushHistoryServiceImpl.class);

    @Resource(name = "pushHistoryDAO")
    private PushHistoryDAO dao;

    @Override
    public List<PushHistoryInfo> index(HashMap params) {
        return dao.index(params);
    }
}
