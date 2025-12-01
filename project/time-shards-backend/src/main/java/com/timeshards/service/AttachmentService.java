package com.timeshards.service;

import com.timeshards.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {
    Attachment upload(MultipartFile file, Long userId);
    List<Attachment> getAttachments(Long userId);
    void deleteAttachment(Long id);
}
