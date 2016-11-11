package com.capslock.rpc.service.user.repository.mapper.model;

import lombok.Data;

/**
 * Created by alvin.
 */
@Data
public class VersionedCacheData {
    private final long version;
    private final String data;
}
