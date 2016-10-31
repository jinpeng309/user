package com.capslock.rpc.service.user.mapper;

import com.capslock.rpc.api.user.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * Created by capslock.
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where user_id = #{userId}")
    @Results(id = "User", value = {
            @Result(property = "userId", column = "user_id", id = true),
            @Result(property = "countryCode", column = "country_code"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "token", column = "token"),
            @Result(property = "refreshToken", column = "refresh_token"),
            @Result(property = "avatar", column = "avatar")
    })
    User fetchUserById(@Param("userId") final long userId);

    @Select("select * from user where country_code = #{countryCode} and phone_number = #{phoneNumber}")
    @ResultMap("User")
    User fetchUserByPhoneNumber(@Param("countryCode") final int countryCode, @Param("phoneNumber") final long phoneNumber);

    @Insert({"insert into user ",
            "(country_code, phone_number, avatar, token, refresh_token) ",
            "values ",
            "(#{countryCode}, #{phoneNumber}, #{avatar}, #{token}, #{refreshToken})"})
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    void addUser(final User user);
}
