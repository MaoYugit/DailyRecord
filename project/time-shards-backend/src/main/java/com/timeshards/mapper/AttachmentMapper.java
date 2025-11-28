package com.timeshards.mapper;

import com.timeshards.entity.Attachment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AttachmentMapper {

    @Select("SELECT * FROM sys_attachment WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<Attachment> findByUserId(Long userId);

    @Insert("INSERT INTO sys_attachment(user_id, original_name, file_path, file_url, file_type, file_size, storage_location, create_time) " +
            "VALUES(#{userId}, #{originalName}, #{filePath}, #{fileUrl}, #{fileType}, #{fileSize}, #{storageLocation}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Attachment attachment);
}
