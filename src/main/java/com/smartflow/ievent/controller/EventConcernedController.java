package com.smartflow.ievent.controller;

import com.smartflow.ievent.dto.*;
import com.smartflow.ievent.service.*;
import com.smartflow.ievent.util.PropertyUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

/**
 * 我关注的事件
 */
@Log4j2
@RequestMapping("/EventConcerned")
@RestController
public class EventConcernedController extends BaseController {

    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;
    @Autowired
    AreaService areaService;

    @Autowired
    StationService stationService;

    @Autowired
    PropertyUtil propertyUtil;
    /**
     * 初始化区域、站点、状态下拉框
     * 我发起的事件（我的事件）、我的任务
     * @return
     */
    @RequestMapping(value = "/EventConcerned/GetEventConcernedConditionInit/{userId}", method = RequestMethod.GET)
    public Map<String, Object> getEventListByConditionInit(@PathVariable Integer userId) {
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
            //查询按钮权限
            List<String> buttonList = roleService.getVisitButtonListByUserId(userId);
            String buttonCode = "71-1";
            boolean buttonPermission = true;
            if(!CollectionUtils.isEmpty(buttonList)) {
                for (String button : buttonList) {
                    if (button != null) {
                        String[] buttonArray = button.split(",");
                        if(Arrays.asList(buttonArray).contains(buttonCode)){
                            buttonPermission = true;
                        }else{
                            buttonPermission = false;
                            break;
                        }
                        //List<String> visitBtnList = Arrays.asList(button.split(","));
                    }else{
                        buttonPermission = false;
                        break;
                    }
                }
            }
            map.put("ButtonPermission", buttonPermission);
            json = this.setJson(200, "初始化成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "初始化失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 查询我关注的事件列表
     * @param eventConcernedListInputDTO
     * @return
     */
    @RequestMapping("/GetMyEventConcernedListByCondition")
    public Map<String,Object> getMyEventConcernedListByCondition(@Valid @RequestBody EventConcernedListInputDTO eventConcernedListInputDTO){
        Map<String,Object> json = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        try{
            Integer totalCount = eventService.getTotalCountEventConcernedListByCondition(eventConcernedListInputDTO, true);
            List<EventListOfMyTaskOutputDTO> eventList = eventService.getEventConcernedListByCondition(eventConcernedListInputDTO, true);
            map.put("RowCount", totalCount);
            map.put("Tdto", eventList);
            json = this.setJson(200, "列表查询成功！", map);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "列表查询失败：" + e.getMessage(), 0);
        }
        return json;
    }

    /**
     * 查询关注的事件列表
     * @param eventConcernedListInputDTO
     * @return
     */
    @RequestMapping("/GetEventConcernedListByCondition")
    public Map<String,Object> getEventConcernedListByCondition(@Valid @RequestBody EventConcernedListInputDTO eventConcernedListInputDTO){
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            Integer totalCount = eventService.getTotalCountEventConcernedListByCondition(eventConcernedListInputDTO, false);
            List<EventListOfMyTaskOutputDTO> eventList = eventService.getEventConcernedListByCondition(eventConcernedListInputDTO, false);
            map.put("RowCount", totalCount);
            map.put("Tdto", eventList);
            json = this.setJson(200, "列表查询成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "列表查询失败：" + e.getMessage(), 0);
        }
        return json;
    }

    /**
     * 自领任务
     * @param receiveTaskInputDTO
     * @return
     */
    @RequestMapping(value = "/SelfReceiveTask", method = RequestMethod.POST)
    public Map<String,Object> selfReceiveTask(@Valid @RequestBody ReceiveTaskInputDTO receiveTaskInputDTO){
        Map<String, Object> json = new HashMap<>();
        try {
            List<Integer> eventIdList = receiveTaskInputDTO.getEventIdList();
            //只可以领取状态为已发布(100)、已报告(110)的事件
            for (Integer eventId:eventIdList) {
                Integer state = eventService.getEventStateByEventId(eventId);
                if(state != 100 || state != 110){
                    json = this.setJson(0, "只可以领取状态为已发布和已报告的任务！", -1);
                    return json;
                }
            }
            //修改事件状态为已分配
            eventService.updateEventActionOwnerByEventId(receiveTaskInputDTO.getUserId(), eventIdList);
            json = this.setJson(200, "自领任务成功！", 1);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "自领任务失败："+e.getMessage(), 0);
        }
        return json;
    }

    /**
     * 指派任务初始化
     * @return
     */
    @RequestMapping(value = "/AssignTaskInit", method = RequestMethod.POST)
    public Map<String,Object> assignTaskInit(@Valid @RequestBody ReceiveTaskInputDTO receiveTaskInputDTO) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            List<AssignTaskInitOutputDTO> assignTaskInitOutputDTOList = eventService.getAssignTaskDetailListByIds(receiveTaskInputDTO.getEventIdList());

            List<Map<String, Object>> roleGroupList = roleService.getRoleListByUserId(receiveTaskInputDTO.getUserId());
            //List<Map<String,Object>> allUserList = userService.getUserList(0);//查询所有人员
            List<Map<String, Object>> roleList = new ArrayList<>();
            if(roleGroupList != null && !roleGroupList.isEmpty()){
                Iterator<Map<String,Object>> it = roleGroupList.iterator();
               /* Map<String, Object> staffMap = new HashMap<>();
                staffMap.put("value", 0);
                staffMap.put("label", "所有");*/
                while(it.hasNext()){
                    Map<String,Object> roleGroupMap = it.next();
                    roleGroupMap.put("value", roleGroupMap.get("key"));
                    roleGroupMap.remove("key");
                    roleGroupMap.put("label", roleGroupMap.get("label"));
                    List<Map<String,Object>> userList = userService.getUserListByRoleId(Integer.parseInt(roleGroupMap.get("value").toString()));
                    /*userList.add(staffMap);*/
                    roleGroupMap.put("children", userList);
                    roleList.add(roleGroupMap);
                }
            }
            map.put("TaskList", assignTaskInitOutputDTOList);
            map.put("RoleList", roleList);
            json = this.setJson(200, "指派任务初始化成功", map);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "指派任务初始化失败："+e.getMessage(), 0);
        }
        return json;
    }
    /**
     * 指派任务
     * @param assignTaskInputDTO
     * @return
     */
    @RequestMapping(value = "/AssignTask", method = RequestMethod.POST)
    public Map<String,Object> assignTask(@Valid @RequestBody AssignTaskInputDTO assignTaskInputDTO) {
        Map<String,Object> json = new HashMap<>();
        try{
            eventService.updateEventAssigneeAndActionOwnerByEventId(assignTaskInputDTO);
            json = this.setJson(200, "指派任务成功", 1);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "指派任务初始化失败："+e.getMessage(), 0);
        }
        return json;
    }
}
