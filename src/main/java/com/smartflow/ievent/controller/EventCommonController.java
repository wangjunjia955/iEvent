package com.smartflow.ievent.controller;

import com.smartflow.ievent.dto.EditEventInitOutputDTO;
import com.smartflow.ievent.dto.EditEventInputDTO;
import com.smartflow.ievent.dto.EventForAddInputDTO;
import com.smartflow.ievent.model.Event;
import com.smartflow.ievent.model.EventEvaluationRecord;
import com.smartflow.ievent.service.*;
import com.smartflow.ievent.service.MyEventService.MyEventService;
import com.smartflow.ievent.util.PropertyUtil;
import com.smartflow.ievent.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Log4j2
@RestController
public class EventCommonController extends BaseController {

    @Autowired
    EventService eventService;
    @Autowired
    AreaService areaService;
    @Autowired
    StationService stationService;
    @Autowired
    EventSymptomService eventSymptomService;
    @Autowired
    EventCategoryService eventCategoryService;
    @Autowired
    PropertyUtil propertyUtil;
    @Autowired
    MyEventService myEventService;
    @Autowired
    MyTaskService myTaskService;
    @Autowired
    UserService userService;

    /**
     * 初始化区域、站点、状态下拉框
     * 我发起的事件（我的事件）、我的任务
     * @return
     */
    @RequestMapping(value = {"/MyEvent/GetMyEventListByConditionInit", "/MyTask/GetMyTaskConditionInit", "/EventConcerned/GetEventConcernedConditionInit"}, method = RequestMethod.GET)
    public Map<String, Object> getEventListByConditionInit() {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            //区域
            List<Map<String, Object>> AreaList = areaService.getAreaInit();
            //站点
            List<Map<String, Object>> StationList = stationService.getStationInit();
            //状态
            List<Map<String, Object>> StateList = propertyUtil.getStateList();
            map.put("AreaList", AreaList);
            map.put("StationList", StationList);
            map.put("StateList", StateList);
            json = this.setJson(200, "初始化成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "初始化失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 添加事件初始化
     * 事件概览、我发起的事件（我的事件）
     * @return
     */
    @RequestMapping(value = {"/EventFor/AddEventInit", "/MyEvent/AddEventInit", "/EventInitiated/AddEventInit"}, method = RequestMethod.GET)
    public Map<String, Object> addEventInit() {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            List<Map<String, Object>> areaList = areaService.getAreaInit();
//            List<Map<String,Object>> factoryAreaList = areaService.getFactoryAreaInit();
            List<Map<String, Object>> stationList = stationService.getStationInit();
            List<Map<String, Object>> eventSymptomList = eventSymptomService.getEventSymptomInit();
            List<Map<String, Object>> eventCategorylist = eventCategoryService.getEventCategoryInit();
            List<Map<String, Object>> impactList = propertyUtil.getImpactDegreeList();
            List<Map<String, Object>> urgencyList = propertyUtil.getUrgencyDegreeList();
            map.put("AreaList", areaList);
            map.put("StationList", stationList);
            map.put("EventSymptomList", eventSymptomList);
            map.put("EventCategoryList", eventCategorylist);
            map.put("ImpactDegreeList", impactList);
            map.put("UrgencyDegreeList", urgencyList);
            json = this.setJson(200, "事件初始化成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "事件初始化失败：" + e.getMessage(), 1);
        }
        return json;
    }
    /**
     * 添加事件
     * 事件概览、我发起的事件（我的事件）
     * @param eventForAddInputDTO
     * @return
     */
    @RequestMapping(value = {"/EventFor/AddEvent", "/MyEvent/AddEvent", "/EventInitiated/AddEventInit"}, method = RequestMethod.POST)
    public Map<String, Object> addEvent(@Valid @RequestBody EventForAddInputDTO eventForAddInputDTO) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            eventService.addEvent(eventForAddInputDTO);
            json = this.setJson(200, "事件添加成功：", 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "事件添加失败：" + e.getMessage(), 1);
        }
        return json;
    }


    /**
     * 修改事件初始化事件类别、影响度、紧急度下拉框
     * 我关注的事件、关注的事件、我的任务
     * @param eventId
     * @return
     */
    @RequestMapping(value = {"/EventConcerned/GetEditEventInit/{eventId}","/MyTask/GetEditEventInit/{eventId}"}, method = RequestMethod.GET)
    public Map<String, Object> getEditEventInit(@PathVariable Integer eventId) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            EditEventInitOutputDTO editEventInitOutputDTO = myTaskService.editEventInit(eventId);
            if (editEventInitOutputDTO == null) {
                json = this.setJson(0, "数据库没有要修改的数据记录", 1);
                return json;
            }
            List<Map<String, Object>> eventCategoryList = eventCategoryService.getEventCategoryInit();
            List<Map<String, Object>> impactList = propertyUtil.getImpactDegreeList();
            List<Map<String, Object>> urgencyList = propertyUtil.getUrgencyDegreeList();
            map.put("Tdto", editEventInitOutputDTO);
            map.put("EventCategoryList", eventCategoryList);
            map.put("ImpactDegreeList", impactList);
            map.put("UrgencyDegreeList", urgencyList);
            json = this.setJson(200, "初始化数据成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "初始化数据失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 修改事件
     * 我关注的事件、关注的事件、我的任务
     * @param editEventInputDTO
     * @return
     */
    @RequestMapping(value = {"/EventConcerned/UpdateEvent", "/MyTask/UpdateEvent"}, method = RequestMethod.PUT)
    public Map<String, Object> updateEvent(@Valid @RequestBody EditEventInputDTO editEventInputDTO) {
        Map<String, Object> json = new HashMap<>();
        try {
            Event event = myEventService.getEventById(editEventInputDTO.getEventId());
            if (event != null) {
                List<Integer> eventCategoryIdList = editEventInputDTO.getEventCategoryId();
                event.setEventCategory(eventCategoryService.getEventCategoryById(eventCategoryIdList.get(eventCategoryIdList.size()-1)));
                event.setImpact(editEventInputDTO.getImpactDegree());
                event.setUrgency(editEventInputDTO.getUrgencyDegree());
                event.setActionStartedDateTime(StringUtil.formatUTCDateTime(editEventInputDTO.getActionStartedDateTime()));
                event.setActionFinishedDateTime(StringUtil.formatUTCDateTime(editEventInputDTO.getActionFinishedDateTime()));
                event.setClarify(editEventInputDTO.getClarify());
                event.setSolution(editEventInputDTO.getSolution());
                event.setState(140);
                myTaskService.updateEvent(event);
                EventEvaluationRecord eventEvaluationRecord = event.getEventEvaluationRecord();
                if(eventEvaluationRecord != null) {
                    eventEvaluationRecord.setEvent(event);
                    eventEvaluationRecord.setUser(StringUtils.isEmpty(editEventInputDTO.getUserId()) ? null : userService.getUserById(editEventInputDTO.getUserId()));
                    eventEvaluationRecord.setState(editEventInputDTO.isIsSolved() == true ? 1 : 0);//已解决
                    myEventService.updateEventEvaluationRecord(eventEvaluationRecord);
                }else{
                    eventEvaluationRecord = new EventEvaluationRecord();
                    eventEvaluationRecord.setEvent(event);
                    eventEvaluationRecord.setCreationDateTime(new Date());
                    eventEvaluationRecord.setUser(StringUtils.isEmpty(editEventInputDTO.getUserId()) ? null : userService.getUserById(editEventInputDTO.getUserId()));
                    eventEvaluationRecord.setState(editEventInputDTO.isIsSolved() == true ? 1 : 0);//已解决
                    myTaskService.addEventEvaluationRecord(eventEvaluationRecord);
                }
                json = this.setJson(200, "事件更新成功！", 0);
            } else {
                json = this.setJson(0, "要修改的数据不存在", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "事件更新失败：" + e.getMessage(), 1);
        }
        return json;
    }

}
