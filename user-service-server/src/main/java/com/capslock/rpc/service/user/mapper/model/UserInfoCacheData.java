package com.capslock.rpc.service.user.mapper.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

import static com.capslock.rpc.service.user.mapper.model.UserInfoCacheDataDefinition.AVATAR;
import static com.capslock.rpc.service.user.mapper.model.UserInfoCacheDataDefinition.NICKNAME;
import static com.capslock.rpc.service.user.mapper.model.UserInfoCacheDataDefinition.USER_ID;

/**
 * Created by alvin.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ToString(callSuper = true)
public class UserInfoCacheData extends VersionedCacheData {
    @JsonProperty(USER_ID)
    private final long userId;
    @JsonProperty(NICKNAME)
    private Optional<String> nickname = Optional.empty();
    @JsonProperty(AVATAR)
    private Optional<String> avatar = Optional.empty();

    public UserInfoCacheData(@JsonProperty(USER_ID) final long userId,
            @JsonProperty(VersionedCacheDataDefinition.VERSION) final long version) {
        super(version);
        this.userId = userId;
    }
}
