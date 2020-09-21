package com.smartflow.ievent;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Component
@PropertySource(value = "classpath:parameter.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "parameter")
public class ParameterConfig {
    /* private Map<String,Object> ImpactDegreeMap1 = new HashMap<>();
     private Map<String,Object> ImpactDegreeMap2 = new HashMap<>();
     private Map<String,Object> ImpactDegreeMap3 = new HashMap<>();
     private Map<String,Object> ImpactDegreeMap4 = new HashMap<>();*/
    //影响程度
    private List<Map<String, Object>> ImpactDegreeList = new ArrayList<>();
    //紧急程度
    private List<Map<String, Object>> UrgencyDegreeList = new ArrayList<>();
    //状态
    private List<Map<String, Object>> StateList = new ArrayList<>();
}
