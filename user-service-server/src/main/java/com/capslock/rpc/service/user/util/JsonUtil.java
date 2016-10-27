package com.capslock.rpc.service.user.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;
import java.util.List;

/**
 * Created by alvin.
 */
public class JsonUtil {

    public static JsonNode merge(final JsonNode sourceNode, final JsonNode deltaNode) {
        Iterator<String> fieldNames = deltaNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode jsonNode = sourceNode.get(fieldName);
            if (jsonNode != null && jsonNode.isObject()) {
                merge(jsonNode, deltaNode.get(fieldName));
            } else {
                if (sourceNode instanceof ObjectNode) {
                    JsonNode value = deltaNode.get(fieldName);
                    ((ObjectNode) sourceNode).set(fieldName, value);
                }
            }

        }
        return sourceNode;
    }

    public static JsonNode merge(final JsonNode sourceNode, final List<JsonNode> deltaNodes) {
        JsonNode result = sourceNode;
        for (final JsonNode deltaNode : deltaNodes) {
            result = merge(result, deltaNode);
        }
        return result;
    }
}
