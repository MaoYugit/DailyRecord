package com.timeshards.service;

import java.util.Map;

public interface ConfigService {
    Map<String, String> getAllConfigs();
    void updateConfig(String key, String value, String description);
}
