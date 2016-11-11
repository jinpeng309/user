package com.capslock.rpc.service.user.repository.mapper;

import com.capslock.rpc.api.user.model.RegisterInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by capslock.
 */
@Mapper
public interface RegisterInfoMapper {

    @Insert({"insert into register_info ",
            "(user_id, device_type, channel_id, device_info, os) ",
            "values ",
            "(#{userId}, #{deviceType}, #{channelId}, #{deviceInfo}, #{os})"})
    void addRegisterInfo(final RegisterInfo registerInfo);
}
