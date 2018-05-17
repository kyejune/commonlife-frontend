package com.kolon.comlife.push.service.impl;

import com.kolon.comlife.push.service.PushService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service("pushService")
public class PushServiceImpl implements PushService {
    @Resource(name="pushDAO")
    private PushDAO dao;

    @Override
    public int register(HashMap params) {
        return dao.register(params);
    }
}
