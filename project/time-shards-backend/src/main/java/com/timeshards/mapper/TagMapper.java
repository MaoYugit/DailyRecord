package com.timeshards.mapper;

import com.timeshards.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TagMapper {

    // 基础查询
    Tag selectById(@Param("id") Long id);

    // 查重用 (关键)
    Tag selectByName(@Param("name") String name);
    Tag selectBySlug(@Param("slug") String slug);

    // 列表查询 (按时间倒序)
    List<Tag> selectAll();

    // 热门标签查询 (统计文章关联数，取前 Limit 个)
    List<Tag> selectHotTags(@Param("limit") int limit);

    // 写入
    int insert(Tag tag);

    // 修改 (例如修正错别字)
    int updateById(Tag tag);

    // 删除
    int deleteById(@Param("id") Long id);
}