package com.smartflow.ievent.controller;

import com.smartflow.ievent.dto.*;
import com.smartflow.ievent.model.EventCatetory;
import com.smartflow.ievent.service.EventCategoryService;
import com.smartflow.ievent.service.EventEscalationProcessService;
import com.smartflow.ievent.service.RoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/EventCatetory")
public class EventCategoryController extends BaseController {

    @Autowired
    EventCategoryService eventCategoryService;

    @Autowired
    RoleService roleService;

    @Autowired
    EventEscalationProcessService eventEscalationProcessService;

    /**
     * 查询事件类别列表
     *
     * @param pageConditionDTO
     * @return 事件类别列表
     */
    @RequestMapping(value = "/GetEventCategoryListByCondition", method = RequestMethod.POST)
    public Map<String, Object> getEventCategoryList(@Valid @RequestBody PageConditionInputDTO pageConditionDTO) {
        Map<String, Object> json = new HashMap<>();
        try {
            Map<String, Object> map = new HashMap<>();
            Integer rowCount = eventCategoryService.getTotalCountEventCategoryList();
            List<EventCategoryListOutputDTO> eventCategoryList = eventCategoryService.getEventCategoryList(pageConditionDTO);
            map.put("Tdto", eventCategoryList);
            map.put("RowCount", rowCount);
            json = this.setJson(200, "查询成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "查询失败：" + e.getMessage(), 1);
        }
        return json;
    }


    @RequestMapping(value = "/AddEventCategoryInit", method = RequestMethod.GET)
    public Map<String, Object> addEventCategoryInit() {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            //角色
            List<Map<String, Object>> roleList = roleService.getRoleInit();
            //事件类别
            List<Map<String, Object>> eventCategoryList = eventCategoryService.getEventCategoryInit();
            map.put("RoleList", roleList);
            map.put("EventCatetoryList", eventCategoryList);
            json = this.setJson(200, "新增初始化成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "新增初始化失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 新增事件类别
     * @param addEventCatetoryDTO
     * @return
     */
    @RequestMapping(value = "/AddEventCategory", method = RequestMethod.POST)
    public Map<String, Object> addEventCategory(@Valid @RequestBody AddEventCatetoryInputDTO addEventCatetoryDTO) {
        Map<String, Object> json = new HashMap<>();
        try {
            if (eventCategoryService.isExistCategoryCode(addEventCatetoryDTO.getCategoryCode())) {
//                result.rejectValue("CategoryCode", "misFormat", "类别代码不能重复");
                json = this.setJson(0, "类别代码不能重复", 1);
                return json;
            }
            EventCatetory eventCatetory = new EventCatetory();
            eventCatetory.setCategoryCode(addEventCatetoryDTO.getCategoryCode());
            eventCatetory.setName(addEventCatetoryDTO.getName());
            eventCatetory.setDescription(addEventCatetoryDTO.getDescription());
            eventCatetory.setRole(roleService.getRoleById(addEventCatetoryDTO.getRoleId()));
            List<Integer> parentCategoryIdList = addEventCatetoryDTO.getParentCategoryId();
            if(!CollectionUtils.isEmpty(parentCategoryIdList)){
                eventCatetory.setEventCategory(eventCategoryService.getEventCategoryById(parentCategoryIdList.get(parentCategoryIdList.size()-1)));
            }
            eventCatetory.setEventEscalationProcess(eventEscalationProcessService.getEventEscalationProcessById(1));
            eventCatetory.setState(1);
            eventCatetory.setParentCategory(StringUtils.collectionToDelimitedString(parentCategoryIdList, ","));
            eventCategoryService.addEventCategory(eventCatetory);
            json = this.setJson(200, "添加成功！", 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "添加失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 编辑事件类别
     *
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/EditEventCategoryInit/{categoryId}", method = RequestMethod.GET)
    public Map<String, Object> editEventCategoryInit(@PathVariable Integer categoryId) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            //角色
            List<Map<String, Object>> roleList = roleService.getRoleInit();
            //事件类别
            List<Map<String, Object>> eventCategoryList = eventCategoryService.getEventCategoryInit();
            EventCatetory eventCatetory = eventCategoryService.getEventCategoryById(categoryId);
            map.put("RoleList", roleList);
            map.put("EventCatetoryList", eventCategoryList);
            EditInitEventCatetoryOutputDTO editInitEventCatetoryDTO = new EditInitEventCatetoryOutputDTO();
            if (eventCatetory != null) {
                editInitEventCatetoryDTO.setId(eventCatetory.getId());
                editInitEventCatetoryDTO.setCategoryCode(eventCatetory.getCategoryCode());
                editInitEventCatetoryDTO.setName(eventCatetory.getName());
                editInitEventCatetoryDTO.setDescription(eventCatetory.getDescription());
                editInitEventCatetoryDTO.setRoleId(eventCatetory.getRole() == null ? "" : eventCatetory.getRole().getId().toString());
                if(!StringUtils.isEmpty(eventCatetory.getParentCategory())){
                    List<String> parentCategoryList = Arrays.asList(eventCatetory.getParentCategory().split(","));
                    if(parentCategoryList != null){
                        editInitEventCatetoryDTO.setParentCategoryId(parentCategoryList.stream().map(Integer::parseInt).collect(Collectors.toList()));
                    }
                }
//                if(eventCatetory.getEventCategory() != null){
//                    if(eventCatetory.getEventCategory().getEventCategory() != null){
//                        parentCategoryList.add(eventCatetory.getEventCategory().getId());
//                        if(eventCatetory.getEventCategory().getEventCategory().getEventCategory() != null){
//                            parentCategoryList.add(eventCatetory.getEventCategory().getEventCategory().getId());
//                        }
//                    }else{
//                        parentCategoryList.add(0);
//                    }
//                    parentCategoryList.add(eventCatetory.getId());
//                }
//                editInitEventCatetoryDTO.setParentCategoryId(parentCategoryList);
            }
            map.put("Tdto", editInitEventCatetoryDTO);
            json = this.setJson(200, "编辑初始化成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "编辑初始化失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 修改事件类别
     *
     * @param editEventCatetoryDTO
     * @return
     */
    @RequestMapping(value = "/EditEventCategory", method = RequestMethod.PUT)
    public Map<String, Object> editEventCategory(@Valid @RequestBody EditEventCatetoryInputDTO editEventCatetoryDTO) {
        Map<String, Object> json = new HashMap<>();
        try {
            if (eventCategoryService.isExistCategoryCode(editEventCatetoryDTO.getCategoryCode())) {
                json = this.setJson(0, "类别代码不能重复", 1);
                return json;
            }
            EventCatetory eventCatetory = eventCategoryService.getEventCategoryById(editEventCatetoryDTO.getId());
            eventCatetory.setId(editEventCatetoryDTO.getId());
            eventCatetory.setCategoryCode(editEventCatetoryDTO.getCategoryCode());
            eventCatetory.setName(editEventCatetoryDTO.getName());
            eventCatetory.setDescription(editEventCatetoryDTO.getDescription());
            eventCatetory.setRole(roleService.getRoleById(editEventCatetoryDTO.getRoleId()));

            List<Integer> parentCategoryIdList = editEventCatetoryDTO.getParentCategoryId();
            if(!CollectionUtils.isEmpty(parentCategoryIdList)){
                eventCatetory.setEventCategory(eventCategoryService.getEventCategoryById(parentCategoryIdList.get(parentCategoryIdList.size()-1)));
            }
            eventCatetory.setEventEscalationProcess(eventEscalationProcessService.getEventEscalationProcessById(1));
            eventCatetory.setParentCategory(StringUtils.collectionToDelimitedString(parentCategoryIdList, ","));
            eventCategoryService.updateEventCategory(eventCatetory);
            json = this.setJson(200, "修改成功！", 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "修改失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 删除事件类别
     *
     * @param categoryId 事件类别id
     * @return
     */
    @RequestMapping(value = "/DeleteEventCategory/{categoryId}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteEventCategory(@PathVariable Integer categoryId) {
        Map<String, Object> json = new HashMap<>();
        try {
            EventCatetory eventCategory = eventCategoryService.getEventCategoryById(categoryId);
            if (eventCategory != null) {
                List<EventCatetory> eventCategorySonList = eventCategoryService.getEventCategoryListByParentEventCategoryId(categoryId);
                for (EventCatetory sonEventCategory : eventCategorySonList) {
                    eventCategoryService.deleteEventCategory(sonEventCategory);
                }
                eventCategoryService.deleteEventCategory(eventCategory);
                json = this.setJson(200, "删除成功！", 0);
            } else {
                json = this.setJson(0, "删除失败：数据库不存在要删除的记录", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "删除失败：" + e.getMessage(), 1);
        }
        return json;
    }
}
