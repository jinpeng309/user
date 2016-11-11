package com.capslock.rpc.service.user.repository.mapper;

import com.capslock.rpc.service.user.repository.mapper.model.UserInfoCacheData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by alvin.
 */
@Mapper
public interface UserInfoMapper {

    void addUserInfo(final UserInfoCacheData userInfoCacheData);

    UserInfoCacheData fetchUserInfo(final long userId);

    List<UserInfoCacheData> fetchUserInfoList(final List<Long> userIds);
}