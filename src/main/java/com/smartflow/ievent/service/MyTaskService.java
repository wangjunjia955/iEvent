package com.smartflow.ievent.service;

import com.smartflow.ievent.dto.EditEventInitOutputDTO;
import com.smartflow.ievent.dto.EventListOfMyTaskOutputDTO;
import com.smartflow.ievent.dto.MyEvent.MyEventInputForSearch;
import com.smartflow.ievent.model.Event;
import com.smartflow.ievent.model.EventEvaluationRecord;

import java.util.List;

public interface MyTaskService {
    /**
     * 根据发生开始时间、发生结束时间、No、区域、站点、状态查询我的任务事件列表
     *
     * @param myEventInputForSearch
     * @return
     */
    public List<EventListOfMyTaskOutputDTO> getEventListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception;

    /**
     * 根据发生开始时间、发生结束时间、No、区域、站点、状态查询我的任务事件列表总条数
     *
     * @param myEventInputForSearch
     * @return
     * @throws Exception
     */
    public Integer getTotalCountOfEventListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception;

    /**
     * 编辑事件根据事件id初始化事件
     *
     * @param eventId
     * @return
     */
    public EditEventInitOutputDTO editEventInit(Integer eventId);

    /**
     * 修改事件
     *
     * @param event
     */
    public void updateEvent(Event event);

    /**
     * 添加事件评价表
     * @param eventEvaluationRecord
     */
    public void addEventEvaluationRecord(EventEvaluationRecord eventEvaluationRecord);
}
