package com.smartflow.ievent.controller;

import com.smartflow.ievent.dto.*;
import com.smartflow.ievent.model.Event;
import com.smartflow.ievent.service.*;
import com.smartflow.ievent.util.PropertyUtil;
import com.smartflow.ievent.util.SendEmailUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/MySubscribe")
public class MySubscribeController extends BaseController {

    @Autowired
    EventSubscriptionService eventSubscriptionService;
    @Autowired
    EventSymptomService eventSymptomService;
    @Autowired
    EventCategoryService eventCategoryService;
    @Autowired
    PropertyUtil propertyUtil;
    @Autowired
    RoleService roleService;
    @Autowired
    EventService eventService;
    /**
     * 查询我的订阅列表
     *
     * @param mySubscriptionListConditionInputDTO
     * @return
     */
    @RequestMapping(value = "GetMySubscriptionListByCondition", method = RequestMethod.POST)
    public Map<String, Object> getMySubscriptionListByCondition(@RequestBody MySubscriptionListConditionInputDTO mySubscriptionListConditionInputDTO) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            List<MySubscriptionListConditionOutputDTO> eventSubscriptionList = eventSubscriptionService.getEventSubscriptionList(mySubscriptionListConditionInputDTO);
            Integer rowCount = eventSubscriptionService.getTotalCountEventSubscriptionList(mySubscriptionListConditionInputDTO);
            map.put("RowCount", rowCount);
            map.put("Tdto", eventSubscriptionList);
            json = this.setJson(200, "查询成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "查询失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 添加事件订阅初始化事件类别、事件症状、影响度、紧急度下拉框
     *
     * @return
     */
    @RequestMapping(value = "AddEventSubscriptionInit/{userId}", method = RequestMethod.GET)
    public Map<String, Object> addEventSubscriptionInit(@PathVariable Integer userId) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            //事件类别
            List<Map<String, Object>> EventCategoryList = eventCategoryService.getEventCategoryInit();
            //症状
            List<Map<String, Object>> EventSymptomList = eventSymptomService.getEventSymptomInit();
            //影响程度
            List<Map<String, Object>> ImpactDegreeList = propertyUtil.getImpactDegreeList();
            //紧急程度
            List<Map<String, Object>> UrgencyDegreeList = propertyUtil.getUrgencyDegreeList();
            //订阅组
            List<Map<String,Object>> SubscribeRoleList = roleService.getRoleListByUserId(userId);
            map.put("EventCategoryList", EventCategoryList);
            map.put("EventSymptomList", EventSymptomList);
            map.put("ImpactDegreeList", ImpactDegreeList);
            map.put("UrgencyDegreeList", UrgencyDegreeList);
            map.put("SubscribeRoleList", SubscribeRoleList);
            json = this.setJson(200, "查询成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "查询失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 添加事件订阅
     *
     * @return
     */
    @RequestMapping(value = "AddEventSubscription", method = RequestMethod.POST)
    public Map<String, Object> addEventSubscription(@Valid @RequestBody AddEventSubscriptionInputDTO addEventSubscriptionInputDTO) {
        Map<String, Object> json = new HashMap<>();
        try {
            eventSubscriptionService.addEventSubscription(addEventSubscriptionInputDTO);
            json = this.setJson(200, "添加成功！", 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "添加失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 编辑事件订阅初始化数据
     * @param updateEventSubscriptionInitInputDTO
     * @return
     */
    @RequestMapping(value = "UpdateEventSubscriptionInit", method = RequestMethod.POST)
    public Map<String, Object> updateEventSubscriptionInit(@RequestBody UpdateEventSubscriptionInitInputDTO updateEventSubscriptionInitInputDTO) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            EditEventSubscriptionInitOutputDTO editEventSubscriptionInitOutputDTO = eventSubscriptionService.getEventSubscriptionById(updateEventSubscriptionInitInputDTO.getEventSubscriptionId());
            if (editEventSubscriptionInitOutputDTO == null) {
                json = this.setJson(0, "编辑初始化数据失败：数据库不存在要修改的数据", 1);
                return json;
            }
            //事件类别
            List<Map<String, Object>> EventCategoryList = eventCategoryService.getEventCategoryInit();
            //症状
            List<Map<String, Object>> EventSymptomList = eventSymptomService.getEventSymptomInit();
            //影响程度
            List<Map<String, Object>> ImpactDegreeList = propertyUtil.getImpactDegreeList();
            //紧急程度
            List<Map<String, Object>> UrgencyDegreeList = propertyUtil.getUrgencyDegreeList();
            //订阅组
            List<Map<String,Object>> SubscribeRoleList = roleService.getRoleListByUserId(updateEventSubscriptionInitInputDTO.getUserId());
            map.put("EventCategoryList", EventCategoryList);
            map.put("EventSymptomList", EventSymptomList);
            map.put("ImpactDegreeList", ImpactDegreeList);
            map.put("UrgencyDegreeList", UrgencyDegreeList);
            map.put("SubscribeRoleList", SubscribeRoleList);
            map.put("Tdto", editEventSubscriptionInitOutputDTO);
            json = this.setJson(200, "编辑初始化成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "编辑初始化失败：" + e.getMessage(), 0);
        }
        return json;
    }

    /**
     * 编辑事件订阅
     *
     * @param editEventSubscriptionInputDTO
     * @return
     */
    @RequestMapping(value = "UpdateEventSubscription", method = RequestMethod.PUT)
    public Map<String, Object> updateEventSubscription(@Valid @RequestBody EditEventSubscriptionInputDTO editEventSubscriptionInputDTO) {
        Map<String, Object> json = new HashMap<>();
        try {
            eventSubscriptionService.updateEventSubscription(editEventSubscriptionInputDTO);
            json = this.setJson(200, "编辑成功！", 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "编辑失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 取消订阅
     *
     * @param eventSubscriptionId
     * @return
     */
    @RequestMapping(value = "CancelSubscription/{eventSubscriptionId}", method = RequestMethod.DELETE)
    public Map<String, Object> cancelSubscription(@PathVariable Integer eventSubscriptionId) {
        Map<String, Object> json = new HashMap<>();
        try {
            eventSubscriptionService.cancelSubscription(eventSubscriptionId);
            json = this.setJson(200, "取消成功！", 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "取消失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 反馈流程 Feedback process
     */
    public Map<String,Object> getEvent(){
        Map<String, Object> json = new HashMap<>();
        try {
            List<Event> eventList = eventService.getEventListByEventSubscriptionRules();
            SendEmailUtil.send();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "取消失败：" + e.getMessage(), 1);
        }
        return json;
    }
}
