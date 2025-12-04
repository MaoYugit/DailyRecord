package com.timeshards.controller;

import com.timeshards.common.ApiResponse;
import com.timeshards.common.BusinessException;
import com.timeshards.entity.Attachment;
import com.timeshards.entity.User;
import com.timeshards.mapper.UserMapper; // 务必确认引入了 UserMapper
import com.timeshards.service.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/attachments")
@Tag(name = "Attachment", description = "附件管理接口")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    // 新增：注入 UserMapper 用于查询当前用户ID
    @Autowired
    private UserMapper userMapper;

    @Operation(summary = "上传文件", description = "上传文件到服务器")
    @PostMapping("/upload")
    // 修改点：去掉了 @RequestParam Long userId
    public ApiResponse<Attachment> upload(@RequestParam("file") MultipartFile file) {

        // 1. 获取当前登录用户名
        String username = null;
        try {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            throw new BusinessException("请先登录");
        }

        // 2. 查询用户 ID
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在或登录已失效");
        }

        // 3. 调用 Service (传入查出来的 ID)
        return ApiResponse.success(attachmentService.upload(file, user.getId()));
    }

    @Operation(summary = "获取附件列表", description = "获取用户的附件列表")
    @GetMapping
    // 建议：这个接口也可以改为不传 userId，只查当前用户的。不过暂时保持原样也可以。
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