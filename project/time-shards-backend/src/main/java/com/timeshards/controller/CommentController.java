package com.timeshards.controller;

import com.timeshards.common.ApiResponse;
import com.timeshards.entity.Comment;
import com.timeshards.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "Comment", description = "评论管理接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(summary = "获取评论列表", description = "根据文章ID或状态获取评论")
    @GetMapping
    public ApiResponse<List<Comment>> getComments(
            @RequestParam(required = false) Long articleId,
            @RequestParam(required = false) Integer status) {
        return ApiResponse.success(commentService.getComments(articleId, status));
    }

    @Operation(summary = "发表评论", description = "发表新评论")
    @PostMapping
    public ApiResponse<Comment> createComment(@RequestBody Comment comment) {
        return ApiResponse.success(commentService.createComment(comment));
    }

    @Operation(summary = "审核评论", description = "修改评论状态")
    @PutMapping("/{id}/status")
    public ApiResponse<String> auditComment(@PathVariable Long id, @RequestParam Integer status) {
        commentService.auditComment(id, status);
        return ApiResponse.success("操作成功");
    }

    @Operation(summary = "删除评论", description = "删除评论")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ApiResponse.success("删除成功");
    }
}
