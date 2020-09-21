package com.smartflow.ievent.controller;

import com.smartflow.ievent.dao.EventSubscriptionDao;
import com.smartflow.ievent.service.AreaService;
import com.smartflow.ievent.service.EventCategoryService;
import com.smartflow.ievent.service.EventSymptomService;
import com.smartflow.ievent.service.StationService;
import com.smartflow.ievent.util.GetPropertyNameByIdUtil;
import com.smartflow.ievent.util.PropertyUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
public class HelloController extends BaseController {
    @Autowired
    PropertyUtil propertyUtil;
    @Autowired
    AreaService areaService;
    @Autowired
    StationService stationService;
    @Autowired
    EventSymptomService eventSymptomService;
    @Autowired
    EventSubscriptionDao eventSubscriptionDao;
    @Autowired
    EventCategoryService eventCatetoryService;

    GetPropertyNameByIdUtil getPropertyNameByIdUtil = new GetPropertyNameByIdUtil();

    @RequestMapping("/hello")
    public Map<String, Object> hello() {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        //区域
        List<Map<String, Object>> AreaList = areaService.getAreaInit();
        //站点
        List<Map<String, Object>> StationList = stationService.getStationInit();
        //事件类别
        List<Map<String, Object>> EventCategoryList = eventCatetoryService.getEventCategoryInit();
        //症状
        List<Map<String, Object>> EventSymptomList = eventSymptomService.getEventSymptomInit();
        //影响程度
        List<Map<String, Object>> ImpactDegreeList = propertyUtil.getImpactDegreeList();
        //紧急程度
        List<Map<String, Object>> UrgencyDegreeList = propertyUtil.getUrgencyDegreeList();
        //状态
        List<Map<String, Object>> StateList = propertyUtil.getStateList();
        List<Map<String, Object>> factoryAreaList = areaService.getFactoryAreaInit();
        map.put("EventSymptomList", EventSymptomList);
        map.put("AreaList", AreaList);
        map.put("StationList", StationList);
        map.put("EventCategoryList", EventCategoryList);
        map.put("ImpactDegreeList", ImpactDegreeList);
        map.put("UrgencyDegreeList", UrgencyDegreeList);
        map.put("StateList", StateList);
        map.put("FactoryAreaList", factoryAreaList);
        String urgency = getPropertyNameByIdUtil.getUrgencyDegreeValueByKey(1);
        String impact = getPropertyNameByIdUtil.getImpactDegreeValueByKey(1);
        String state = getPropertyNameByIdUtil.getStateValueByKey(150);
        System.out.println(urgency + impact + state);
        json = this.setJson(200, "success", map);
        return json;
    }

    @RequestMapping("/getEventSymptom")
    public Map<String, Object> getEventSymptomInit() {
        Map<String, Object> json = new HashMap<>();
        try {
            List<Map<String, Object>> eventSymptomList = eventSymptomService.getEventSymptomInit();
            json = this.setJson(200, "success", eventSymptomList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(200, "fail", -1);
        }
        return json;
    }

}
