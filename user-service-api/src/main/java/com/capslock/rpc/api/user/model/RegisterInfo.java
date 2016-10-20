package com.capslock.rpc.api.user.model;

import lombok.Data;

/**
 * Created by capslock.
 */
@Data
public class RegisterInfo {
    private long userId;
    private final String deviceInfo;
    private final String os;
    private final int deviceType;
    private final int channelId;
}
