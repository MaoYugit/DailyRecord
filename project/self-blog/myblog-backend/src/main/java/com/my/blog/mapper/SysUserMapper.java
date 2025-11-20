package com.my.blog.mapper;

import com.my.blog.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表 DAO 接口
 * 这里的每个方法对应 XML 中的一个 SQL 语句
 */
@Mapper // 告诉 Spring Boot 这是一个 MyBatis 的 Mapper 接口，启动时会自动扫描并创建实现类
public interface SysUserMapper {

    /**
     * 新增用户
     * @param sysUser 用户对象
     * @return 影响行数 (1表示成功)
     */
    int insert(SysUser sysUser);

    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户对象
     */
    SysUser selectById(Long id);
}