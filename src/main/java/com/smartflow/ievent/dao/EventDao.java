package com.smartflow.ievent.dao;

import com.smartflow.ievent.dto.*;
import com.smartflow.ievent.dto.MyEvent.MyEventInputForSearch;
import com.smartflow.ievent.model.Event;

import java.text.ParseException;
import java.util.List;

public interface EventDao {
    /**
     * 根据区域、发生开始时间、发生结束时间、是否显示已完结查询事件概览总条数
     *
     * @param eventForConditionInputDTO
     * @return
     */
    public Integer getTotalCountEventListByCondition(EventForConditionInputDTO eventForConditionInputDTO) throws ParseException;

    /**
     * 根据区域、发生开始时间、发生结束时间、是否显示已完结查询事件概览
     *
     * @param eventForConditionInputDTO
     * @return
     */
    public List<Event> getEventListByCondition(EventForConditionInputDTO eventForConditionInputDTO) throws ParseException;

    /**
     * 新建事件
     *
     * @param event
     */
    public void addEvent(Event event);

    /**
     * 根据事件id获取事件
     *
     * @param eventInputDTO
     * @return
     */
    public List<Event> getEventListByEventIds(ExportEventInputDTO eventInputDTO);

    /**
     * 根据症状条件查询事件
     * @param faultDistributionConditionDTO
     * @param symptomId
     * @return
     * @throws Exception
     */
    public List<EventDownDTO> getEventBySymptomCondition(FaultDistributionConditionInputDTO faultDistributionConditionDTO, Integer symptomId) throws Exception;

    /**
     * 获取停机事件
     * @return
     *//*
    public List<EventDownDTO> getDownEvent(FaultDistributionConditionInputDTO faultDistributionConditionDTO) throws Exception;

    *//**
     * 获取停机的工站名
     * @param stationIdList
     * @return
     *//*
    public List<String> getDownStationName(List<Integer> stationIdList);*/

    /**
     * 获取事件症状（状态）发生时间
     * @param maintenanceAnalysisConditionInputDTO
     * @param stationId
     * @return
     * @throws Exception
     */
    public List<EventSymptomIssueTimeDTO> getEventSymptomIssueTimeDTO(MaintenanceAnalysisConditionInputDTO maintenanceAnalysisConditionInputDTO, Integer stationId) throws Exception;

//    /**
//     * 获取我发起的事件列表总条数
//     * @param myEventInputForSearch
//     * @return
//     */
//    public Integer getTotalCountEventInitiatedListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception;
//
//    /**
//     * 获取我发起的事件列表
//     * @param myEventInputForSearch
//     * @return
//     */
//    public List<Event> getEventInitiatedListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception;

    /**
     * 获取(我)关注的事件列表总条数
     * @param eventConcernedListInputDTO
     * @param isMeFlag true:查询我关注的事件  false:查询关注的事件
     * @return
     */
    public Integer getTotalCountEventConcernedListByCondition(EventConcernedListInputDTO eventConcernedListInputDTO, boolean isMeFlag);

    /**
     *
     * @param eventConcernedListInputDTO
     * @return
     */
    /**
     * 获取(我)关注的事件列表
     * @param eventConcernedListInputDTO
     * @param isMeFlag true:查询我关注的事件  false:查询关注的事件
     * @return
     */
    public List<Event> getEventConcernedListByCondition(EventConcernedListInputDTO eventConcernedListInputDTO, boolean isMeFlag);

    /**
     * 查询事件状态
     *
     * @param eventId 事件id
     * @return
     */
    public Integer getEventStateByEventId(Integer eventId);

    /**
     * 根据事件id获取事件详情
     *
     * @param eventId
     * @return
     */
    public Event getEventById(Integer eventId);

    /**
     * 根据事件id修改事件状态
     *
     * @param eventId 事件id
     * @param state 事件状态
     */
    public void updateEventStateByEventId(Integer eventId, Integer signerId, Integer state);

    /**
     * 根据事件id修改事件处理人(自领任务)
     * @param userId
     * @param eventIdList
     */
    public void updateEventActionOwnerByEventId(Integer userId, List<Integer> eventIdList);

    /**
     * 根据事件id获取事件详情
     * @param eventIdList
     * @return
     */
    public List<Event> getEventListByEventIds(List<Integer> eventIdList);

    /**
     * 根据事件id修改事件分配人、处理人、处理组
     * @param assignTaskInputDTO
     */
    public void updateEventAssigneeAndActionOwnerByEventId(AssignTaskInputDTO assignTaskInputDTO);

    /**
     * 依据订阅规则，获取事件列表（发送反馈邮件）
     */
    public List<Event> getEventListByEventSubscriptionRules();
}
