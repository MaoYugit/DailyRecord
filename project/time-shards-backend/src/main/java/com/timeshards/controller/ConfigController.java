package com.timeshards.controller;

import com.timeshards.common.ApiResponse;
import com.timeshards.service.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/configs")
@Tag(name = "Config", description = "系统配置接口")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @Operation(summary = "获取所有配置", description = "获取系统所有配置项")
    @GetMapping
    public ApiResponse<Map<String, String>> getConfigs() {
        return ApiResponse.success(configService.getAllConfigs());
    }

    @Operation(summary = "更新配置", description = "更新或添加配置项")
    @PostMapping
    public ApiResponse<String> updateConfig(@RequestBody Map<String, String> payload) {
        // payload 格式: { "key": "value", "description": "desc" } 
        // 简化起见，这里假设 payload 是 key-value 对，description 暂时忽略或通过特定约定传递
        // 实际场景建议定义一个 ConfigRequest DTO
        payload.forEach((k, v) -> configService.updateConfig(k, v, null));
        return ApiResponse.success("配置更新成功");
    }
}
