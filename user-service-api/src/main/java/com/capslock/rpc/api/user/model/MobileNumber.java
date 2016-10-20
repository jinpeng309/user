package com.capslock.rpc.api.user.model;

import lombok.Data;

/**
 * Created by alvin.
 */
@Data
public class MobileNumber {
    private final int countryCode;
    private final long phoneNumber;
}
