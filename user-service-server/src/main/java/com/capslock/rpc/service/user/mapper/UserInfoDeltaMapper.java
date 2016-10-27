package com.capslock.rpc.service.user.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alvin.
 */
@Component
public class UserInfoDeltaMapper {
    private static final String TABLE_NAME = "user_info_delta";
    @Autowired
    private VersionedUserDataMapper versionedUserDataMapper;
    @Autowired
    private ObjectMapper objectMapper;

    public void addDelta(final long ownerUid, final String data, final long seq) {

    }
}
