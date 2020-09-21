package com.smartflow.ievent.controller;

import com.smartflow.ievent.dto.EventForListOutputDTO;
import com.smartflow.ievent.dto.MyEvent.AddEventEvaluationRecordInputDTO;
import com.smartflow.ievent.dto.MyEvent.MyEventDetailOutputDTO;
import com.smartflow.ievent.dto.MyEvent.MyEventInputForSearch;
import com.smartflow.ievent.service.MyEventService.MyEventService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我发起的事件
 */
@Log4j2
@RestController
@RequestMapping("/MyEvent")
public class MyEventController extends BaseController {

    @Autowired
    MyEventService myEventService;

    /**
     * 根据发生开始时间、发生结束时间、No、区域、站点、状态查询我的事件列表
     * @param myEventInputForSearch
     * @return
     */
    @RequestMapping(value = "/GetMyEventListByCondition", method = RequestMethod.POST)
    public Map<String, Object> getPageInit(@Valid @RequestBody MyEventInputForSearch myEventInputForSearch) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            Integer rowCount = myEventService.getTotalCountOfMyEventListByCondition(myEventInputForSearch);
            List<EventForListOutputDTO> myEventList = myEventService.getMyEventListByCondition(myEventInputForSearch);
            map.put("RowCount", rowCount);
            map.put("Tdto", myEventList);
            json = this.setJson(200, "查询成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "查询失败：" + e.getMessage(), -1);
        }
        return json;
    }

    /**
     * 获取事件详情
     *
     * @return
     */
    @RequestMapping(value = "/GetEventDetail/{eventId}", method = RequestMethod.GET)
    public Map<String, Object> getEventDetail(@PathVariable Integer eventId) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            MyEventDetailOutputDTO myEventDetailOutputDTO = myEventService.getEventDetailById(eventId);
            if (myEventDetailOutputDTO != null) {
                json = this.setJson(200, "查询成功！", myEventDetailOutputDTO);
            } else {
                json = this.setJson(0, "数据库不存在此id对应的记录", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "查询失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 签收
     *
     * @return
     */
    @RequestMapping(value = "/Signoff", method = RequestMethod.POST)
    public Map<String, Object> signoff(@RequestBody AddEventEvaluationRecordInputDTO addEventEvaluationRecordInputDTO) {
        Map<String, Object> json = new HashMap<>();
        try {
            Integer state = myEventService.getEventStateByEventId(addEventEvaluationRecordInputDTO.getEventId());
            if (state == null) {
                json = this.setJson(0, "数据库不存在相关的记录！", 1);
                return json;
            } else if (state == 150) {
                json = this.setJson(0, "该事件已签收，不可重复签收！", 1);
                return json;
            }
            myEventService.signoffEvent(addEventEvaluationRecordInputDTO);
            json = this.setJson(200, "签收成功！", 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "签收失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 关闭事件
     *
     * @param eventId
     * @return
     */
    @RequestMapping(value = "/Close/{eventId}", method = RequestMethod.GET)
    public Map<String, Object> close(@PathVariable Integer eventId) {
        Map<String, Object> json = new HashMap<>();
        try {
            myEventService.closeEvent(eventId);
            json = this.setJson(200, "关闭成功！", 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "关闭失败：" + e.getMessage(), 1);
        }
        return json;
    }
}
