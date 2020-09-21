package com.smartflow.ievent.dao.MyEvent;

import com.smartflow.ievent.dto.MyEvent.MyEventInputForSearch;
import com.smartflow.ievent.model.Event;
import com.smartflow.ievent.model.EventEvaluationRecord;

import java.util.Date;
import java.util.List;

public interface MyEventDao {

    /**
     * 根据发生开始时间、发生结束时间、No、区域、站点、状态查询我的事件列表
     *
     * @param myEventInputForSearch
     * @return
     */
    public List<Event> getMyEventListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception;

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
    public Event getEventById(Integer eventId);

    /**
     * 签收、评价新增事件评价记录表
     *
     * @param eventEvaluationRecord
     */
    public void addEventEvaluationRecord(EventEvaluationRecord eventEvaluationRecord);

    /**
     * 修改事件评价表
     * @param eventEvaluationRecord
     */
    public void updateEventEvaluationRecord(EventEvaluationRecord eventEvaluationRecord);

    /**
     * 根据事件id修改事件状态
     *
     * @param eventId 事件id
     * @param state 事件状态
     */
    public void updateEventStateByEventId(Integer eventId, Integer signerId, Integer state);

    /**
     * 查询事件状态
     *
     * @param eventId 事件id
     * @return
     */
    public Integer getEventStateByEventId(Integer eventId);

    /**
     * 关闭事件
     * @param eventId 事件id
     */
    public void closeEvent(Integer eventId);

}
