package com.capslock.rpc.api.user.model;

import lombok.Data;

/**
 * Created by alvin.
 */
@Data
public class Contact {
    private final String name;
    private final String encryptedPhoneNumber;
}
