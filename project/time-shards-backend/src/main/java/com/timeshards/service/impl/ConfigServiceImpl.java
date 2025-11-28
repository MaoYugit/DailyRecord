package com.timeshards.service.impl;

import com.timeshards.entity.Config;
import com.timeshards.mapper.ConfigMapper;
import com.timeshards.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public Map<String, String> getAllConfigs() {
        List<Config> configs = configMapper.findAll();
        Map<String, String> map = new HashMap<>();
        for (Config config : configs) {
            map.put(config.getConfigKey(), config.getConfigValue());
        }
        return map;
    }

    @Override
    public void updateConfig(String key, String value) {
        if (configMapper.findByKey(key) == null) {
            Config config = new Config();
            config.setConfigKey(key);
            config.setConfigValue(value);
            configMapper.insert(config);
        } else {
            configMapper.updateValue(key, value);
        }
    }
}
