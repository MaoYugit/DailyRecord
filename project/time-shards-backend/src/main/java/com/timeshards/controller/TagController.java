package com.timeshards.controller;

import com.timeshards.common.ApiResponse;
import com.timeshards.entity.Tag;
import com.timeshards.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag", description = "标签管理接口")
public class TagController {

    @Autowired
    private TagService tagService;

    @Operation(summary = "获取所有标签", description = "获取所有标签列表")
    @GetMapping
    public ApiResponse<List<Tag>> getTags() {
        return ApiResponse.success(tagService.getAllTags());
    }

    @Operation(summary = "创建标签", description = "创建新标签")
    @PostMapping
    public ApiResponse<Tag> createTag(@RequestBody Tag tag) {
        return ApiResponse.success(tagService.createTag(tag));
    }

    @Operation(summary = "删除标签", description = "根据ID删除标签")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ApiResponse.success("删除成功");
    }
}
