package com.skronawi.keyvalueservice.service;

import com.skronawi.keyvalueservice.api.KeyValueService;
import com.skronawi.keyvalueservice.api.KeyValueServiceConfiguration;
import com.skronawi.keyvalueservice.core.KeyValueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySources({
        @PropertySource("classpath:keyvalueservice.properties"),
        @PropertySource(value = "file:keyvalueservice.properties", ignoreResourceNotFound = true)
})
public class KeyValueServiceProvisioning {

    @Autowired
    private Environment environment;

    @Bean
    public KeyValueService keyValueService() {
        Map<String, String> keyValues = getGatheredConfig();
        KeyValueServiceConfiguration keyValueServiceConfiguration =
                KeyValueServiceConfiguration.Builder.from(keyValues);
        KeyValueServiceImpl keyValueService = new KeyValueServiceImpl();
        keyValueService.getLifeCycle().init(keyValueServiceConfiguration);
        return keyValueService;
    }

    private Map<String, String> getGatheredConfig() {
        Map<String, Object> map = new HashMap<>();
        for (org.springframework.core.env.PropertySource<?> ps :
                ((AbstractEnvironment) environment).getPropertySources()) {
            if (ps instanceof MapPropertySource) {
                map.putAll(((MapPropertySource) ps).getSource());
            }
        }
        Map<String, String> keyValues = new HashMap<>();
        for (AbstractMap.Entry<String, Object> entry : map.entrySet()) {
            keyValues.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return keyValues;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
