package com.timeshards.mapper;

import com.timeshards.entity.Attachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface AttachmentMapper {

    /**
     * 新增附件记录
     */
    int insert(Attachment attachment);

    /**
     * 删除附件记录 (物理删除)
     * Service层需配合删除云端/本地文件
     */
    int deleteById(@Param("id") Long id);

    /**
     * 查询某用户的附件列表
     * 按上传时间倒序排列
     */
    List<Attachment> selectByUserId(@Param("userId") Long userId);
}