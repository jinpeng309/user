package com.capslock.rpc.service.user.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by alvin.
 */
@Component
public class ChangedDataBuilder {
    @Autowired
    private ObjectMapper objectMapper;

    public String buildUserInfoEditBlacklistChangedDataArray(final long userId, final boolean inBlacklist) {
        final ArrayNode changedDataArray = objectMapper.createArrayNode();
        final ObjectNode changedData = objectMapper.createObjectNode();
        return changedDataArray.add(changedData).toString();
    }

    public String buildUserInfoEditBlacklistChangedDataArray(final List<Long> userIdList, final boolean inBlacklist) {
        final ArrayNode changedDataArray = objectMapper.createArrayNode();
        final ObjectNode changedData = objectMapper.createObjectNode();
        return changedDataArray.add(changedData).toString();
    }
}
