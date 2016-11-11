package com.capslock.rpc.service.user.def;

/**
 * Created by alvin.
 */
public class UserInfoChangedDataProtocol {
    /**
     * Don't let anyone instantiate this class.
     */
    private UserInfoChangedDataProtocol() {
        // This constructor is intentionally empty.
    }
    public static final String NAME = "uich";
    public static final String SEQUENCE_ID = "cs";
    public static final String CHANGES = "ich";
}
