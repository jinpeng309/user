package com.capslock.rpc.service.user.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ArrayListMultimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by alvin.
 */
@Component
public class JsonUtil {
    @Autowired
    private ObjectMapper objectMapper;

    public JsonNode mergeJsonField(final JsonNode sourceNode, final JsonNode deltaNode) {
        final Iterator<String> fieldNames = deltaNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode jsonNode = sourceNode.get(fieldName);
            if (jsonNode != null && jsonNode.isObject()) {
                mergeJsonField(jsonNode, deltaNode.get(fieldName));
            } else if (sourceNode instanceof ObjectNode) {
                JsonNode value = deltaNode.get(fieldName);
                ((ObjectNode) sourceNode).set(fieldName, value);
            }

        }
        return sourceNode;
    }

    public JsonNode mergeJsonField(final JsonNode sourceNode, final Collection<JsonNode> changedDataList) {
        JsonNode result = sourceNode;
        for (final JsonNode deltaNode : changedDataList) {
            result = mergeJsonField(result, deltaNode);
        }
        return result;
    }

    public JsonNode mergeJsonField(final Collection<JsonNode> changedDataList) {
        JsonNode result = objectMapper.createObjectNode();
        return mergeJsonField(result, changedDataList);
    }

    public List<JsonNode> mapToJsonNode(final List<String> rawDataList) {
        final List<JsonNode> jsonNodeList = new ArrayList<>(rawDataList.size());
        for (final String rawData : rawDataList) {
            try {
                jsonNodeList.add(objectMapper.readTree(rawData));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonNodeList;
    }

    public List<JsonNode> mergeJsonDataByUid(final List<JsonNode> dataList) {
        final List<JsonNode> result = new ArrayList<>();
        final ArrayListMultimap<Long, JsonNode> dataMap = ArrayListMultimap.create();
        dataList.forEach(data -> {
            final long uid = data.get("uid").asLong();
            dataMap.put(uid, data);
        });
        dataMap.asMap().values().forEach(sameUserDataList -> {
            result.add(mergeJsonField(sameUserDataList));
        });
        return result;
    }
}
