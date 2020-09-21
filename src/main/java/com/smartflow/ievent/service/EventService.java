package com.smartflow.ievent.service;

import com.smartflow.ievent.dto.EventSymptomIssueTimeDTO;
import com.smartflow.ievent.dto.*;
import com.smartflow.ievent.dto.MyEvent.AddEventEvaluationRecordInputDTO;
import com.smartflow.ievent.dto.MyEvent.MyEventInputForSearch;
import com.smartflow.ievent.model.Event;
import org.hibernate.query.Query;

import java.text.ParseException;
import java.util.List;

public interface EventService {

    /**
     * 根据区域、发生开始时间、发生结束时间、是否显示已完结查询事件概览总条数
     *
     * @param eventForConditionInputDTO
     * @return
     */
    public Integer getTotalCountEventListByCondition(EventForConditionInputDTO eventForConditionInputDTO) throws ParseException;

    /**
     * 根据区域、发生开始时间、发生结束时间、是否显示已完结显示 事件概览
     *
     * @param eventForConditionInputDTO
     * @return
     */
    public List<EventForListOutputDTO> getEventListByCondition(EventForConditionInputDTO eventForConditionInputDTO) throws ParseException;

    /**
     * 新建事件
     *
     * @param eventForAddInputDTO
     */
    public void addEvent(EventForAddInputDTO eventForAddInputDTO) throws ParseException;

    /**
     * 根据事件id获取事件
     *
     * @param eventInputDTO
     * @return
     */
    public List<String[]> getEventListByEventIds(ExportEventInputDTO eventInputDTO);

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
//    public List<EventForListOutputDTO> getEventInitiatedListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception;

    /**
     * 获取我关注的事件列表总条数
     * @param eventConcernedListInputDTO
     * @return
     */
    public Integer getTotalCountEventConcernedListByCondition(EventConcernedListInputDTO eventConcernedListInputDTO, boolean isMeFlag);

    /**
     *  获取(我)关注的事件列表
     * @param eventConcernedListInputDTO
     * @param isMeFlag true:查询我关注的事件  false:查询关注的事件
     * @return
     */
    public List<EventListOfMyTaskOutputDTO> getEventConcernedListByCondition(EventConcernedListInputDTO eventConcernedListInputDTO, boolean isMeFlag);

    /**
     * 根据事件id获取事件详情
     *
     * @param eventId
     * @return
     */
    public EventDetailOutputDTO getEventDetailById(Integer eventId);

    /**
     * 查询事件状态
     *
     * @param eventId
     * @return
     */
    public Integer getEventStateByEventId(Integer eventId);

    /**
     * 签收事件
     *
     * @param addEventEvaluationRecordInputDTO
     */
    public void signOffEvent(AddEventEvaluationRecordInputDTO addEventEvaluationRecordInputDTO);

    /**
     * 根据事件id修改事件处理人(自领任务)
     * @param userId
     * @param eventIdList
     */
    public void updateEventActionOwnerByEventId(Integer userId, List<Integer> eventIdList);

    /**
     * 根据事件id获取分配的任务详情
     * @param eventIdList
     * @return
     */
    public List<AssignTaskInitOutputDTO> getAssignTaskDetailListByIds( List<Integer> eventIdList);

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
