package com.smartflow.ievent.util;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Component
public class GetPropertyNameByIdUtil {

    private static PropertyUtil propertyUtil;

    @Autowired
    public void init(PropertyUtil propertyUtil) {
        GetPropertyNameByIdUtil.propertyUtil = propertyUtil;
    }

    public String getImpactDegreeValueByKey(Integer impactDegreeKey) {
        String impactDegree = null;
        if (!CollectionUtils.isEmpty(propertyUtil.getImpactDegreeList())) {
            for (Map<String, Object> map : propertyUtil.getImpactDegreeList()) {
                JSONObject jsonObject = JSONObject.fromObject(map);
                Integer key = (Integer) jsonObject.get("key");
                if (key.equals(impactDegreeKey)) {
                    impactDegree = (String) jsonObject.get("label");
                    return impactDegree;
                }
            }
        }
        return null;
    }

    public String getUrgencyDegreeValueByKey(Integer urgencyDegreeKey) {
        String urgencyDegree = null;
        if (!CollectionUtils.isEmpty(propertyUtil.getUrgencyDegreeList())) {
            for (Map<String, Object> map : propertyUtil.getUrgencyDegreeList()) {
                JSONObject jsonObject = JSONObject.fromObject(map);
                Integer key = (Integer) jsonObject.get("key");
                if (key.equals(urgencyDegreeKey)) {
                    urgencyDegree = (String) jsonObject.get("label");
                    return urgencyDegree;
                }
            }
        }
        return null;
    }

    public String getStateValueByKey(Integer stateKey) {
        String state = null;
        if (!CollectionUtils.isEmpty(propertyUtil.getStateList())) {
            for (Map<String, Object> map : propertyUtil.getStateList()) {
                JSONObject jsonObject = JSONObject.fromObject(map);
                Integer key = (Integer) jsonObject.get("key");
                if (key.equals(stateKey)) {
                    state = (String) jsonObject.get("label");
                    return state;
                }
            }
        }
        return null;
    }
}
