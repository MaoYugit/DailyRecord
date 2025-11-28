package com.timeshards.entity;

import lombok.Data;

@Data
public class Config {
    private Long id;
    private String configKey;
    private String configValue;
    private String description;
}
