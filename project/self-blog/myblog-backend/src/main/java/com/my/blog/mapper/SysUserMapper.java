package com.my.blog.mapper;

import com.my.blog.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户 Mapper 接口
 */
@Mapper
public interface SysUserMapper {

    // 1. 根据ID查询
    SysUser selectById(Long id);

    // 2. 根据用户名查询
    SysUser selectByUsername(String username);

    // 3. 新增用户
    int insert(SysUser sysUser);

    // 4. 更新用户
    int update(SysUser sysUser);

    // 5. 逻辑删除 (修改 is_deleted 状态)
    int deleteById(Long id);

    // 6. 查询列表 (示例)
    List<SysUser> selectList();
}