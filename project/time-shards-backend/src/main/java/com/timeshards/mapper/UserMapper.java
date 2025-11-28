package com.timeshards.mapper;

import com.timeshards.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM sys_user WHERE username = #{username} AND is_deleted = 0")
    User findByUsername(String username);

    @Select("SELECT * FROM sys_user WHERE id = #{id} AND is_deleted = 0")
    User findById(Long id);

    @Insert("INSERT INTO sys_user(username, password, email, nickname, avatar, bio, role, is_deleted, create_time) " +
            "VALUES(#{username}, #{password}, #{email}, #{nickname}, #{avatar}, #{bio}, #{role}, 0, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);
}
