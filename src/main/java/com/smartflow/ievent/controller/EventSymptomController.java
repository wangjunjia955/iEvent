package com.smartflow.ievent.controller;

import com.smartflow.ievent.dto.*;
import com.smartflow.ievent.model.EventSymptom;
import com.smartflow.ievent.service.EventSymptomService;
import com.smartflow.ievent.util.GetPropertyNameByIdUtil;
import com.smartflow.ievent.util.PropertyUtil;
import lombok.extern.log4j.Log4j2;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@Log4j2
@RestController
@RequestMapping("/EventSymptom")
public class EventSymptomController extends BaseController {

    @Autowired
    EventSymptomService eventSymptomService;
    @Autowired
    PropertyUtil propertyUtil;

    GetPropertyNameByIdUtil getPropertyNameByIdUtil = new GetPropertyNameByIdUtil();

    /**
     * 获取症状列表
     *
     * @param pageConditionDTO
     * @return
     */
    @RequestMapping(value = "/GetEventSymptomListByCondition", method = RequestMethod.POST)
    public Map<String, Object> getEventSymptomList(@Valid @RequestBody PageConditionInputDTO pageConditionDTO) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        List<EventSymptomListOutputDTO> eventSymptomDTOList = new ArrayList<>();
        PropertyUtil propertyUtil = new PropertyUtil();
        try {
            Integer totalCount = eventSymptomService.getTotalCountEventSymptomList();
            List<EventSymptom> eventSymptomList = eventSymptomService.getEventSymptomList(pageConditionDTO);
            for (EventSymptom eventSymptom : eventSymptomList) {
                EventSymptomListOutputDTO eventSymptomDTO = new EventSymptomListOutputDTO();
                eventSymptomDTO.setId(eventSymptom.getId());
                eventSymptomDTO.setSymptomCode(eventSymptom.getSymptomCode());
                eventSymptomDTO.setName(eventSymptom.getName());
                eventSymptomDTO.setDescription(eventSymptom.getDescription());
                eventSymptomDTO.setDefaultImpactDegree(getPropertyNameByIdUtil.getImpactDegreeValueByKey(eventSymptom.getDefaultImpact()));
                eventSymptomDTO.setDefaultUrgencyDegree(getPropertyNameByIdUtil.getUrgencyDegreeValueByKey(eventSymptom.getDefaultUrgency()));
                eventSymptomDTOList.add(eventSymptomDTO);
            }
            map.put("Tdto", eventSymptomDTOList);
            map.put("RowCount", totalCount);
            json = this.setJson(200, "查询成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "查询失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 添加症状初始化影响程度、紧急程度下拉框
     *
     * @return
     */
    @RequestMapping(value = "/AddEventSymptomInit", method = RequestMethod.GET)
    public Map<String, Object> addEventSymptomInit() {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            //影响程度
            List<Map<String, Object>> ImpactDegreeList = propertyUtil.getImpactDegreeList();
            //紧急程度
            List<Map<String, Object>> UrgencyDegreeList = propertyUtil.getUrgencyDegreeList();
            map.put("ImpactDegreeList", ImpactDegreeList);
            map.put("UrgencyDegreeList", UrgencyDegreeList);
            json = this.setJson(200, "添加初始化成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "添加初始化失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 添加症状列表
     *
     * @param addEventSymptomDTO
     * @return
     */
    @RequestMapping(value = "/AddEventSymptom", method = RequestMethod.POST)
    public Map<String, Object> addEventSymptom(@Valid @RequestBody AddEventSymptomInputDTO addEventSymptomDTO) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            if (eventSymptomService.isExistSymptomCode(addEventSymptomDTO.getSymptomCode())) {
                json = this.setJson(0, "症状代码不能重复", 1);
                return json;
            }
            EventSymptom eventSymptom = new EventSymptom();
            eventSymptom.setSymptomCode(addEventSymptomDTO.getSymptomCode());
            eventSymptom.setName(addEventSymptomDTO.getName());
            eventSymptom.setDescription(addEventSymptomDTO.getDescription());
            eventSymptom.setDefaultImpact(addEventSymptomDTO.getImpactDegree());
            eventSymptom.setDefaultUrgency(addEventSymptomDTO.getUrgencyDegree());
            eventSymptom.setState(1);
            eventSymptomService.addEventSymptom(eventSymptom);
            json = this.setJson(200, "添加成功！", 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "添加失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 编辑初始化症状
     *
     * @param symptomId
     * @return
     */
    @RequestMapping(value = "/EditEventSymptomInit/{symptomId}", method = RequestMethod.GET)
    public Map<String, Object> editEventSymptomInit(@PathVariable Integer symptomId) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            //影响程度
            List<Map<String, Object>> ImpactDegreeList = propertyUtil.getImpactDegreeList();
            //紧急程度
            List<Map<String, Object>> UrgencyDegreeList = propertyUtil.getUrgencyDegreeList();
            map.put("ImpactDegreeList", ImpactDegreeList);
            map.put("UrgencyDegreeList", UrgencyDegreeList);
            EventSymptom eventSymptom = eventSymptomService.getEventSymptomById(symptomId);
            EditInitEventSymptomOutputDTO editInitEventSymptomDTO = new EditInitEventSymptomOutputDTO();
            if (eventSymptom != null) {
                editInitEventSymptomDTO.setId(eventSymptom.getId());
                editInitEventSymptomDTO.setSymptomCode(eventSymptom.getSymptomCode());
                editInitEventSymptomDTO.setName(eventSymptom.getName());
                editInitEventSymptomDTO.setDescription(eventSymptom.getDescription());
                editInitEventSymptomDTO.setImpactDegree(eventSymptom.getDefaultImpact() == null ? "" : eventSymptom.getDefaultImpact().toString());
                editInitEventSymptomDTO.setUrgencyDegree(eventSymptom.getDefaultUrgency() == null ? "" : eventSymptom.getDefaultUrgency().toString());
                map.put("Tdto", editInitEventSymptomDTO);
                json = this.setJson(200, "编辑初始化成功！", map);
            } else {
                json = this.setJson(0, "数据库没有此记录", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "编辑初始化失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 修改症状
     *
     * @param editEventSymptomDTO
     * @return
     */
    @RequestMapping(value = "/EditEventSymptom", method = RequestMethod.PUT)
    public Map<String, Object> editEventSymptom(@Valid @RequestBody EditEventSymptomInputDTO editEventSymptomDTO) {
        Map<String, Object> json = new HashMap<>();
        try {
            if (eventSymptomService.isExistSymptomCode(editEventSymptomDTO.getSymptomCode())) {
                json = this.setJson(0, "症状代码不能重复", 1);
                return json;
            }
            EventSymptom eventSymptom = eventSymptomService.getEventSymptomById(editEventSymptomDTO.getId());
            eventSymptom.setSymptomCode(editEventSymptomDTO.getSymptomCode());
            eventSymptom.setName(editEventSymptomDTO.getName());
            eventSymptom.setDescription(editEventSymptomDTO.getDescription());
            eventSymptom.setDefaultImpact(editEventSymptomDTO.getImpactDegree());
            eventSymptom.setDefaultUrgency(editEventSymptomDTO.getUrgencyDegree());
            eventSymptomService.updateEventSymptom(eventSymptom);
            json = this.setJson(200, "编辑成功！", 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "编辑失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 删除事件症状
     * @param symptomId
     * @return
     */
    @RequestMapping(value = "/DeleteEventSymptom/{symptomId}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteEventSymptom(@PathVariable Integer symptomId) {
        Map<String, Object> json = new HashMap<>();
        try {
            EventSymptom eventSymptom = eventSymptomService.getEventSymptomById(symptomId);
            if (eventSymptom != null) {
                eventSymptomService.deleteEventSymptom(eventSymptom);
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
