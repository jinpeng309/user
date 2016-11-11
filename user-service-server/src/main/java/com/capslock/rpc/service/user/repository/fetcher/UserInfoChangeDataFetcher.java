package com.capslock.rpc.service.user.repository.fetcher;

import com.capslock.rpc.service.user.repository.mapper.UserInfoChangeDataMapper;
import com.capslock.rpc.service.user.repository.mapper.model.VersionedCacheData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by alvin.
 */
@Component
public class UserInfoChangeDataFetcher {
    @Autowired
    private UserInfoChangeDataMapper userInfoChangeDataMapper;

    public List<VersionedCacheData> fetchChangedDataList(final long ownerUid, final long beginSequenceId) {
        return userInfoChangeDataMapper.fetchChangedDataList(ownerUid, beginSequenceId);
    }

}
