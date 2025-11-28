package com.timeshards.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Attachment {
    private Long id;
    private Long userId;
    private String originalName;
    private String filePath;
    private String fileUrl;
    private String fileType;
    private Long fileSize;
    private Integer storageLocation; // 0-本地, 1-阿里云, 2-七牛云
    private LocalDateTime createTime;
}
