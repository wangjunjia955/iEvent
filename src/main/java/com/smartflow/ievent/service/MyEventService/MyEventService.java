package com.smartflow.ievent.service.MyEventService;

import com.smartflow.ievent.dto.EventForAddInputDTO;
import com.smartflow.ievent.dto.EventForListOutputDTO;
import com.smartflow.ievent.dto.MyEvent.AddEventEvaluationRecordInputDTO;
import com.smartflow.ievent.dto.MyEvent.MyEventDetailOutputDTO;
import com.smartflow.ievent.dto.MyEvent.MyEventInputForSearch;
import com.smartflow.ievent.dto.MyEvent.MyEventOutputForPageInit;
import com.smartflow.ievent.model.Event;
import com.smartflow.ievent.model.EventEvaluationRecord;


import java.text.ParseException;
import java.util.List;

public interface MyEventService {
    /**
     * 根据发生开始时间、发生结束时间、No、区域、站点、状态查询我的事件列表
     *
     * @param myEventInputForSearch
     * @return
     */
    public List<EventForListOutputDTO> getMyEventListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception;

    /**
     * 根据发生开始时间、发生结束时间、No、区域、站点、状态查询我的事件列表总条数
     *
     * @param myEventInputForSearch
     * @return
     * @throws Exception
     */
    public Integer getTotalCountOfMyEventListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception;

    /**
     * 根据事件id获取事件详情
     *
     * @param eventId
     * @return
     */
    public MyEventDetailOutputDTO getEventDetailById(Integer eventId);

    /**
     * 根据事件id获取事件详情
     *
     * @param eventId
     * @return
     */
    public Event getEventById(Integer eventId);

    /**
     * 修改事件评价表
     * @param eventEvaluationRecord
     */
    public void updateEventEvaluationRecord(EventEvaluationRecord eventEvaluationRecord);

    /**
     * 签收事件
     *
     * @param addEventEvaluationRecordInputDTO
     */
    public void signoffEvent(AddEventEvaluationRecordInputDTO addEventEvaluationRecordInputDTO);

    /**
     * 查询事件状态
     *
     * @param eventId
     * @return
     */
    public Integer getEventStateByEventId(Integer eventId);

    /**
     * 关闭事件
     * @param eventId 事件id
     */
    public void closeEvent(Integer eventId);
}
