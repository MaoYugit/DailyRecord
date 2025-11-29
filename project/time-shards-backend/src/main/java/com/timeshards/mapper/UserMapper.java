package com.timeshards.mapper;

import com.timeshards.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户数据访问层
 * 遵循 MyBatis 标准命名规范 (Select/Insert/Update/Delete)
 */
@Mapper
public interface UserMapper {

    /**
     * 根据主键查询用户信息
     * 自动过滤已逻辑删除的用户
     *
     * @param id 用户ID
     * @return 用户实体 或 null
     */
    User selectById(@Param("id") Long id);

    /**
     * 根据用户名查询用户信息
     * 用于登录认证或注册查重
     *
     * @param username 用户名
     * @return 用户实体 或 null
     */
    User selectByUsername(@Param("username") String username);

    /**
     * 新增用户
     * 使用数据库自增主键
     *
     * @param user 用户实体
     * @return 影响行数 (成功为1)
     */
    int insert(User user);

    /**
     * 根据主键动态更新用户信息
     * 仅更新 user 对象中非 null 的字段
     *
     * @param user 用户实体 (必须包含 id)
     * @return 影响行数
     */
    int updateById(User user);

    /**
     * 逻辑删除用户
     * 将 is_deleted 更新为 1
     *
     * @param id 用户ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
}