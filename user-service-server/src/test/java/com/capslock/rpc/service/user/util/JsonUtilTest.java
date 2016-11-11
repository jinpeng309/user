package com.capslock.rpc.service.user.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import mockit.Deencapsulation;
import org.junit.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by alvin.
 */
public class JsonUtilTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JsonUtil jsonUtil = new JsonUtil();

    {
        Deencapsulation.setField(jsonUtil, "objectMapper", objectMapper);
    }

    @Test
    public void testMergeJsonDataByUid() {
        final long uid1 = 1;
        final long uid2 = 2;
        final String data1 = "DATA1";
        final String data2 = "DATA2";
        final String data3 = "DATA3";
        final String json1 = "{\"uid\":" + uid1 + ",\"data\":\"" + data1 + "\"}";
        final String json2 = "{\"uid\":" + uid1 + ",\"data\":\"" + data2 + "\"}";
        final String json3 = "{\"uid\":" + uid2 + ",\"data\":\"" + data3 + "\"}";

        List<JsonNode> result = jsonUtil.mergeJsonDataByUid(jsonUtil.mapToJsonNode(
                Lists.newArrayList(json1, json2, json3)));
        assertThat(result.size()).isEqualTo(2);
        final JsonNode node1 = result.get(0);
        assertThat(node1.get("uid").asLong()).isEqualTo(uid1);
        assertThat(node1.get("data").asText()).isEqualTo(data2);
        final JsonNode node2 = result.get(1);
        assertThat(node2.get("uid").asLong()).isEqualTo(uid2);
        assertThat(node2.get("data").asText()).isEqualTo(data3);
    }
}