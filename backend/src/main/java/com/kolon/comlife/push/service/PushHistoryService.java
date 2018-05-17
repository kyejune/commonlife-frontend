package com.kolon.comlife.push.service;

import com.kolon.comlife.push.model.PushHistoryInfo;

import java.util.HashMap;
import java.util.List;

public interface PushHistoryService {
    public List<PushHistoryInfo> index(HashMap params);
}
