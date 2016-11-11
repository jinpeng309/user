package com.capslock.rpc.service.user.repository.updater;

import com.capslock.rpc.service.user.repository.mapper.UserInfoChangeDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alvin.
 */
@Component
public class UserInfoChangeDataUpdater {
    @Autowired
    private UserInfoChangeDataMapper userInfoChangeDataMapper;

    public void addChangeData(final long ownerUid, final String data, final long sequenceId) {
        userInfoChangeDataMapper.addChangeData(ownerUid, data, sequenceId);
    }
}
