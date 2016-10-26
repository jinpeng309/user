package com.capslock.rpc.service.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by alvin.
 */
@Mapper
public interface VersionedUserDataMapper {
    @Insert({"insert into ${table_name} ",
            "(user_id, json_data, version) ",
            "values ",
            "(${user_id}, '${data}', ${version})"
    })
    void store(@Param("table_name") final String tableName, @Param("user_id") final long uid, @Param("data") final String data,
            @Param("version") final long version);

    @Select({
            "select json_data from ${table_name} ",
            "where ",
            "user_id = ${user_id} and version = ${version}"
    })
    String findByVersion(@Param("table_name") final String tableName, @Param("user_id") final long uid, @Param("version") final long version);

    @Select({
            "select json_data from ${table_name} ",
            "where ",
            "user_id = ${user_id} ",
            "order by version desc ",
            "limit 1"
    })
    String find(@Param("table_name") final String tableName, @Param("user_id") final long uid);
}
