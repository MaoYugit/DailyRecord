package com.timeshards.service.impl;

import com.timeshards.common.BusinessException;
import com.timeshards.entity.Attachment;
import com.timeshards.mapper.AttachmentMapper;
import com.timeshards.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Value("${upload.path:./uploads/}")
    private String uploadPath;

    @Override
    public Attachment upload(MultipartFile file, Long userId) {
        String originalName = file.getOriginalFilename();
        String suffix = "";
        if (originalName != null && originalName.contains(".")) {
            suffix = originalName.substring(originalName.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID().toString() + suffix;
        String filePath = uploadPath + fileName;
        String fileUrl = "/uploads/" + fileName; // 简单映射

        File dest = new File(filePath);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new BusinessException("文件上传失败: " + e.getMessage());
        }

        Attachment attachment = new Attachment();
        attachment.setUserId(userId);
        attachment.setOriginalName(originalName);
        attachment.setFilePath(filePath);
        attachment.setFileUrl(fileUrl);
        attachment.setFileType(file.getContentType());
        attachment.setFileSize(file.getSize());
        attachment.setStorageLocation(0); // 本地

        attachmentMapper.insert(attachment);
        return attachment;
    }

    @Override
    public List<Attachment> getAttachments(Long userId) {
        return attachmentMapper.selectByUserId(userId);
    }

    @Override
    public void deleteAttachment(Long id) {
        // TODO: 删除物理文件
        attachmentMapper.deleteById(id);
    }
}
