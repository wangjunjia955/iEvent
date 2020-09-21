package com.smartflow.ievent.util;

import com.smartflow.ievent.ParameterConfig;
import com.smartflow.ievent.controller.HelloController;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@EnableConfigurationProperties(value = ParameterConfig.class)
/*@ConditionalOnClass(HelloController.class)
@ConditionalOnProperty(prefix = "hello", value = "enable", matchIfMissing = true)*/
public class PropertyUtil {
    @Autowired
    private ParameterConfig parameterConfig;

    @Bean
    public List<Map<String, Object>> getImpactDegreeList() {
        List<Map<String,Object>> mapList = parameterConfig.getImpactDegreeList();
        for (Map<String,Object> map:mapList) {
            //map.keySet().stream().map(Integer::parseInt).collect(Collectors.toList());
            Integer key = Integer.parseInt(map.get("key").toString());
            map.put("key", key);
        }
        return mapList;
    }

    @Bean
    public List<Map<String, Object>> getUrgencyDegreeList() {
        List<Map<String,Object>> mapList = parameterConfig.getUrgencyDegreeList();
        for (Map<String,Object> map:mapList) {
            //map.keySet().stream().map(Integer::parseInt).collect(Collectors.toList());
            Integer key = Integer.parseInt(map.get("key").toString());
            map.put("key", key);
        }
        return mapList;
    }

    @Bean
    public List<Map<String, Object>> getStateList() {
        List<Map<String,Object>> mapList = parameterConfig.getStateList();
        for (Map<String,Object> map:mapList) {
            //map.keySet().stream().map(Integer::parseInt).collect(Collectors.toList());
            Integer key = Integer.parseInt(map.get("key").toString());
            map.put("key", key);
        }
        return mapList;
    }

}
