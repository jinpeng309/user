package com.capslock.rpc.service.user.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by alvin.
 */
@Mapper
public interface UserBlacklistMapper {
    @Insert({
            "insert into user_blacklist ",
            "(user_id, black_user_id) ",
            "values ",
            "(${ownerUid}, ${userId})"
    })
    void addUserIntoBlacklist(final long ownerUid, final long userId);

    @Select({
            "select black_user_id from user_blacklist ",
            "where ",
            "user_id = ${ownerUid}"
    })
    List<Long> fetchUserBlacklist(final long ownerUid);

    @Delete({
            "delete from user_blacklist ",
            "where ",
            "user_id = ${ownerUid} and black_user_id = ${userId}"
    })
    void removeUserInBlacklist(final long ownerUid, final long userId);

    boolean isInBlacklist(long ownerUid, long userId);
}
