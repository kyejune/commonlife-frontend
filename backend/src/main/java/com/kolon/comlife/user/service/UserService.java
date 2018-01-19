package com.kolon.comlife.user.service;

import com.kolon.comlife.user.model.UserInfo;

import java.util.List;

public interface UserService {
    public List<UserInfo> getUserListById(List<Integer> ids );
}
