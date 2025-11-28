package com.timeshards.mapper;

import com.timeshards.entity.Config;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ConfigMapper {

    @Select("SELECT * FROM sys_config")
    List<Config> findAll();

    @Select("SELECT * FROM sys_config WHERE config_key = #{key}")
    Config findByKey(String key);

    @Insert("INSERT INTO sys_config(config_key, config_value, description) VALUES(#{configKey}, #{configValue}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Config config);

    @Update("UPDATE sys_config SET config_value = #{configValue} WHERE config_key = #{configKey}")
    void updateValue(@Param("configKey") String configKey, @Param("configValue") String configValue);
}
