package com.my.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置实体类
 * 对应数据库表: sys_config
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysConfig {

    /**
     * 主键ID
     * 对应字段: id (bigint)
     */
    private Long id;

    /**
     * 配置键
     * 对应字段: config_key (varchar)
     */
    private String configKey;

    /**
     * 配置值
     * 对应字段: config_value (longtext)
     */
    private String configValue;

    /**
     * 描述
     * 对应字段: description (varchar)
     */
    private String description;
}