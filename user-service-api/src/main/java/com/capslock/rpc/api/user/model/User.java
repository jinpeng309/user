package com.capslock.rpc.api.user.model;

import lombok.Data;

/**
 * Created by capslock.
 */
@Data
public class User {
    private long userId;
    private int countryCode;
    private long phoneNumber;
    private String token;
    private String refreshToken;
    private String avatar;
}
