package com.capslock.rpc.service.user.mapper.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by alvin.
 */
@Data
public class VersionedCacheData {
    @JsonProperty(VersionedCacheDataDefinition.VERSION)
    private final long version;
}
