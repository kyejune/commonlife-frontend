package com.kolon.comlife.users.service;

import com.kolon.comlife.users.model.UserInfo;

import java.util.List;

public interface UserService {
    public List<UserInfo> getUserListById(List<Integer> ids );
}
