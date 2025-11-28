package com.timeshards.mapper;

import com.timeshards.entity.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TagMapper {

    @Select("SELECT * FROM blog_tag ORDER BY create_time DESC")
    List<Tag> findAll();

    @Select("SELECT * FROM blog_tag WHERE id = #{id}")
    Tag findById(Long id);

    @Insert("INSERT INTO blog_tag(name, slug, create_time) VALUES(#{name}, #{slug}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Tag tag);

    @Delete("DELETE FROM blog_tag WHERE id = #{id}")
    void deleteById(Long id);
}
