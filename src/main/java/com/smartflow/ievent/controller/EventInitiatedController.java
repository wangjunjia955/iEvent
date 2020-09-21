package com.smartflow.ievent.controller;

import com.smartflow.ievent.dto.EventDetailOutputDTO;
import com.smartflow.ievent.dto.MyEvent.AddEventEvaluationRecordInputDTO;
import com.smartflow.ievent.dto.MyEvent.MyEventInputForSearch;
import com.smartflow.ievent.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 我发起的事件
 */
@Log4j2
@RequestMapping("/EventInitiated")
@RestController
public class EventInitiatedController extends BaseController {

    @Autowired
    EventService eventService;

    /**
     * 查询我发起的事件清单
     *
     * @param myEventInputForSearch
     * @return
     */
    @RequestMapping(value = "/GetEventInitiatedListByCondition", method = RequestMethod.POST)
    public Map<String, Object> getEventInitiatedListByCondition(@Valid @RequestBody MyEventInputForSearch myEventInputForSearch) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
//            Integer totalCount = eventService.getTotalCountEventInitiatedListByCondition(myEventInputForSearch);
//            List<EventForListOutputDTO> eventList = eventService.getEventInitiatedListByCondition(myEventInputForSearch);
//            map.put("RowCount", totalCount);
//            map.put("Tdto", eventList);
            json = this.setJson(200, "列表查询成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "列表查询失败：" + e.getMessage(), 0);
        }
        return json;
    }

    /**
     * 获取我发起的事件详情
     *
     * @return
     */
    @RequestMapping(value = "/GetEventInitiatedDetail/{eventId}", method = RequestMethod.GET)
    public Map<String, Object> getEventInitiatedDetail(@PathVariable Integer eventId) {
        Map<String, Object> json = new HashMap<>();
        try {
            EventDetailOutputDTO myEventDetailOutputDTO = eventService.getEventDetailById(eventId);
            if (myEventDetailOutputDTO != null) {
                json = this.setJson(200, "查询成功！", myEventDetailOutputDTO);
            } else {
                json = this.setJson(0, "数据库不存在此id对应的记录", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "查询失败：" + e.getMessage(), 0);
        }
        return json;
    }

    /**
     * 签收事件
     *
     * @return
     */
    @RequestMapping(value = "/SignOffEvent", method = RequestMethod.POST)
    public Map<String, Object> signOffEvent(@Valid @RequestBody AddEventEvaluationRecordInputDTO addEventEvaluationRecordInputDTO) {
        Map<String, Object> json = new HashMap<>();
        try {
            Integer state = eventService.getEventStateByEventId(addEventEvaluationRecordInputDTO.getEventId());
            if (state == null) {
                json = this.setJson(0, "数据库不存在相关的记录！", 0);
                return json;
            } else if (state == 150) {
                json = this.setJson(0, "该事件已签收，不可重复签收！", 0);
                return json;
            }
            eventService.signOffEvent(addEventEvaluationRecordInputDTO);
            json = this.setJson(200, "签收成功！", 1);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "签收失败：" + e.getMessage(), 0);
        }
        return json;
    }

}
