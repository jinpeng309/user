package com.capslock.rpc.service.user.merger;

import com.capslock.rpc.service.user.def.UserInfoChangedDataProtocol;
import com.capslock.rpc.service.user.util.JsonUtil;
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
public class ChangeDataMerger {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JsonUtil jsonUtil;

    public String mergeUserInfoChangeData(final long latestSequenceId, final List<String> rawDataList) {
        final ObjectNode userInfoChangedData = objectMapper.createObjectNode();
        userInfoChangedData.put(UserInfoChangedDataProtocol.SEQUENCE_ID, latestSequenceId);

        final ArrayNode changeArray = userInfoChangedData.putArray(UserInfoChangedDataProtocol.CHANGES);
        jsonUtil.mergeJsonDataByUid(jsonUtil.mapToJsonNode(rawDataList)).forEach(changeArray::add);

        return userInfoChangedData.toString();
    }
}
