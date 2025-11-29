package com.timeshards.mapper;

import com.timeshards.entity.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ConfigMapper {

    /**
     * 获取所有配置 (用于系统启动缓存)
     */
    List<Config> selectAll();

    /**
     * 根据 Key 获取单个配置
     */
    Config selectByKey(@Param("configKey") String configKey);

    /**
     * 新增配置项 (吸纳你的功能)
     */
    int insert(Config config);

    /**
     * 更新配置值
     */
    int updateValue(@Param("configKey") String configKey, @Param("configValue") String configValue);
}