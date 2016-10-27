package com.capslock.rpc.api.user.model;

import lombok.Data;

import java.util.Optional;

/**
 * Created by alvin.
 */
@Data
public class UserInfo {
    private final long userId;
    private final MobileNumber mobileNumber;
    private Optional<String> nickname = Optional.empty();
    private Optional<String> avatar = Optional.empty();
    private boolean inBlackList;
}
