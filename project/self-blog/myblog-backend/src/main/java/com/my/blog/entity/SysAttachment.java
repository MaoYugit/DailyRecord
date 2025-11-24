package com.my.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 附件实体类
 * 对应数据库表: sys_attachment
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysAttachment {

    /**
     * 主键ID
     * 对应字段: id (bigint)
     */
    private Long id;

    /**
     * 上传用户ID
     * 对应字段: user_id (bigint)
     */
    private Long userId;

    /**
     * 原文件名
     * 对应字段: original_name (varchar)
     */
    private String originalName;

    /**
     * 物理路径
     * 对应字段: file_path (varchar)
     */
    private String filePath;

    /**
     * 访问URL
     * 对应字段: file_url (varchar)
     */
    private String fileUrl;

    /**
     * 文件类型
     * 对应字段: file_type (varchar)
     */
    private String fileType;

    /**
     * 文件大小(字节)
     * 对应字段: file_size (bigint)
     */
    private Long fileSize;

    /**
     * 存储位置: 0-本地, 1-阿里云, 2-七牛云
     * 对应字段: storage_location (tinyint)
     */
    private Integer storageLocation;

    /**
     * 创建时间
     * 对应字段: create_time (datetime)
     */
    private LocalDateTime createTime;
}