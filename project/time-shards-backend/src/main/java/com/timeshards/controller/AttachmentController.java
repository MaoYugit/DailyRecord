package com.timeshards.controller;

import com.timeshards.common.ApiResponse;
import com.timeshards.entity.Attachment;
import com.timeshards.service.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/attachments")
@Tag(name = "Attachment", description = "附件管理接口")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @Operation(summary = "上传文件", description = "上传文件到服务器")
    @PostMapping("/upload")
    public ApiResponse<Attachment> upload(@RequestParam("file") MultipartFile file, @RequestParam Long userId) {
        return ApiResponse.success(attachmentService.upload(file, userId));
    }

    @Operation(summary = "获取附件列表", description = "获取用户的附件列表")
    @GetMapping
    public ApiResponse<List<Attachment>> getAttachments(@RequestParam Long userId) {
        return ApiResponse.success(attachmentService.getAttachments(userId));
    }

    @Operation(summary = "删除附件", description = "删除附件记录")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteAttachment(@PathVariable Long id) {
        attachmentService.deleteAttachment(id);
        return ApiResponse.success("删除成功");
    }
}
