package com.timeshards.service;

import com.timeshards.entity.Config;
import java.util.List;
import java.util.Map;

public interface ConfigService {
    Map<String, String> getAllConfigs();
    void updateConfig(String key, String value);
}
