package com.capslock.rpc.service.user.mapper;

import com.capslock.rpc.service.user.mapper.model.UserInfoCacheData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Created by alvin.
 */
@Component
public class UserInfoMapper {
    private static final String USER_INFO_TABLE_NAME = "user_info";
    @Autowired
    private VersionedUserDataMapper versionedUserDataMapper;
    @Autowired
    private ObjectMapper objectMapper;

    public void addUserInfo(final UserInfoCacheData userInfoCacheData) throws IOException {
        final String data = objectMapper.writeValueAsString(userInfoCacheData);
        versionedUserDataMapper.store(USER_INFO_TABLE_NAME, userInfoCacheData.getUserId(), data, userInfoCacheData.getVersion());
    }

    public UserInfoCacheData fetchUserInfo(final long userId) throws IOException {
        final String data = versionedUserDataMapper.find(USER_INFO_TABLE_NAME, userId);
        return objectMapper.readValue(data, UserInfoCacheData.class);
    }

    public List<UserInfoCacheData> fetchUserInfoList(final List<Long> userIds) {
        return Lists.newArrayList();

    }
}
