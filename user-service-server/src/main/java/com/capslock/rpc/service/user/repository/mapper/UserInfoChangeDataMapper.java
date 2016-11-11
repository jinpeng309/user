package com.capslock.rpc.service.user.repository.mapper;

import com.capslock.rpc.service.user.repository.mapper.model.VersionedCacheData;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by alvin.
 */
@Component
public class UserInfoChangeDataMapper {
    private static final String TABLE_NAME = "user_info_delta";
    @Autowired
    private VersionedUserDataMapper versionedUserDataMapper;

    public void addChangeData(final long ownerUid, final String data, final long sequenceId) {
        versionedUserDataMapper.store(TABLE_NAME, ownerUid, data, sequenceId);
    }

    public List<VersionedCacheData> fetchChangedDataList(final long ownerUid, final long beginSequenceId) {
        return Lists.newArrayList();
    }
}
