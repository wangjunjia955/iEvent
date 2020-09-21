package com.smartflow.ievent.service;

import com.smartflow.ievent.dao.*;
import com.smartflow.ievent.dto.*;
import com.smartflow.ievent.dto.MyEvent.AddEventEvaluationRecordInputDTO;
import com.smartflow.ievent.dto.MyEvent.MyEventInputForSearch;
import com.smartflow.ievent.model.Event;
import com.smartflow.ievent.model.EventEvaluationRecord;
import com.smartflow.ievent.util.GetPropertyNameByIdUtil;
import com.smartflow.ievent.util.ParseFieldToMapUtil;
import com.smartflow.ievent.util.PropertyUtil;
import com.smartflow.ievent.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EventServiceImpl implements EventService {
    @Autowired
    EventDao eventDao;

    @Autowired
    PropertyUtil propertyUtil;

    @Autowired
    StationDao stationDao;

    @Autowired
    AreaDao areaDao;

    @Autowired
    EventCategoryDao eventCatetoryDao;

    @Autowired
    EventSymptomDao eventSymptomDao;

    @Autowired
    UserDao userDao;

    @Autowired
    EventEvaluationRecordDao eventEvaluationRecordDao;

    GetPropertyNameByIdUtil getPropertyNameByIdUtil = new GetPropertyNameByIdUtil();

    @Override
    public Integer getTotalCountEventListByCondition(EventForConditionInputDTO eventForConditionInputDTO) throws ParseException {
        return eventDao.getTotalCountEventListByCondition(eventForConditionInputDTO);
    }

    @Override
    public List<EventForListOutputDTO> getEventListByCondition(EventForConditionInputDTO eventForConditionInputDTO) throws ParseException {
        List<Event> eventList = eventDao.getEventListByCondition(eventForConditionInputDTO);
        List<EventForListOutputDTO> eventForListOutputDTOList = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
//        int count = 1;
        for (Event event : eventList) {
            EventForListOutputDTO eventForListOutputDTO = new EventForListOutputDTO();
            eventForListOutputDTO.setId(event.getId());
//            eventForListOutputDTO.setNo(count);
            eventForListOutputDTO.setNo(event.getEventCode());
            eventForListOutputDTO.setAreaNumber(parseFieldToMapUtil.parseFiledToString(event.getArea().getAreaNumber(), event.getArea().getDescription()));
            if(event.getStation() != null){
                eventForListOutputDTO.setStationNumber(parseFieldToMapUtil.parseFiledToString(event.getStation().getStationNumber(), event.getStation().getName()));
            }
            eventForListOutputDTO.setSymptom(parseFieldToMapUtil.parseFiledToString(event.getEventSymptom().getSymptomCode(), event.getEventSymptom().getName()));
            eventForListOutputDTO.setImpactDegree(getPropertyNameByIdUtil.getImpactDegreeValueByKey(event.getImpact()));
            eventForListOutputDTO.setUrgencyDegree(getPropertyNameByIdUtil.getUrgencyDegreeValueByKey(event.getUrgency()));
            eventForListOutputDTO.setTitle(event.getTitle());
            eventForListOutputDTO.setReportUser(event.getReportUser() == null ? null : event.getReportUser().getUserName());
            eventForListOutputDTO.setAssignee(event.getAssignee() == null ? null : event.getAssignee().getUserName());
            eventForListOutputDTO.setIssueDateTime(event.getIssueDateTime());
            eventForListOutputDTO.setPassedTime(StringUtil.getHourMinutesApart(event.getIssueDateTime(), new Date()));
            eventForListOutputDTO.setReportDateTime(event.getReportDateTime());
            eventForListOutputDTO.setAssignedDateTime(event.getAssignedDateTime());
            eventForListOutputDTO.setActionStartedDateTime(event.getActionStartedDateTime());
            eventForListOutputDTO.setActionFinishedDateTime(event.getActionFinishedDateTime());
            eventForListOutputDTO.setSignedOffDateTime(event.getSignedOffDateTime());
            eventForListOutputDTO.setState(getPropertyNameByIdUtil.getStateValueByKey(event.getState()));
            eventForListOutputDTO.setEventColor(StringUtil.getEventColor(event.getIssueDateTime(), new Date()));
            eventForListOutputDTOList.add(eventForListOutputDTO);
        }
        return eventForListOutputDTOList;
    }

    @Override
    public void addEvent(EventForAddInputDTO eventForAddInputDTO) throws ParseException {
        Event event = new Event();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        event.setEventCode(sdf.format(new Date()));
        event.setArea(areaDao.getAreaById(eventForAddInputDTO.getAreaId()));
        event.setStation(eventForAddInputDTO.getStationId() == null ? null : stationDao.getStationById(eventForAddInputDTO.getStationId()));
        event.setEventSymptom(eventSymptomDao.getEventSymptomById(eventForAddInputDTO.getEventSymptomId()));
        List<Integer> eventCategoryList = eventForAddInputDTO.getEventCategoryId();
        event.setEventCategory(eventCatetoryDao.getEventCategoryById(eventCategoryList.get(eventCategoryList.size()-1)));
        event.setImpact(eventForAddInputDTO.getImpactDegree());
        event.setUrgency(eventForAddInputDTO.getUrgencyDegree());
        event.setIssueDateTime(StringUtil.formatDateTime(eventForAddInputDTO.getIssueDateTime()));
        event.setTitle(eventForAddInputDTO.getTitle());
        event.setDescription(eventForAddInputDTO.getDescription());
        event.setReportDateTime(new Date());
        event.setActionOwner(userDao.getUserById(eventForAddInputDTO.getUserId()));
        event.setState(100);//Issued
        eventDao.addEvent(event);
    }

    @Override
    public List<String[]> getEventListByEventIds(ExportEventInputDTO eventInputDTO) {
        List<Event> eventList = eventDao.getEventListByEventIds(eventInputDTO);
        List<String[]> eventStrList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Integer count = 1;
        for (Event event : eventList) {
            String[] eventStr = new String[20];
//            eventStr[0] = event.getId().toString();
//            eventStr[0] = count.toString();
            eventStr[0] = event.getEventCode();
            eventStr[1] = event.getArea().getAreaNumber();
            eventStr[2] = event.getStation() == null ? null : event.getStation().getStationNumber();
            eventStr[3] = event.getEventSymptom().getSymptomCode();
            eventStr[4] = getPropertyNameByIdUtil.getImpactDegreeValueByKey(event.getImpact());
            eventStr[5] = getPropertyNameByIdUtil.getUrgencyDegreeValueByKey(event.getUrgency());
            eventStr[6] = event.getTitle();
            eventStr[7] = event.getReportUser() == null ? null : event.getReportUser().getUserName();
            eventStr[8] = event.getAssignee() == null ? null : event.getAssignee().getUserName();
            eventStr[9] = sdf.format(event.getIssueDateTime());
            eventStr[10] = event.getActionStartedDateTime() == null ? null : StringUtil.getHourMinutesApart(event.getIssueDateTime(), event.getActionStartedDateTime());
            eventStr[11] = event.getReportDateTime() == null ? null : sdf.format(event.getReportDateTime());
            eventStr[12] = event.getAssignedDateTime() == null ? null : sdf.format(event.getAssignedDateTime());
            eventStr[13] = event.getActionStartedDateTime() == null ? null : sdf.format(event.getActionStartedDateTime());
            eventStr[14] = event.getActionFinishedDateTime() == null ? null : sdf.format(event.getActionFinishedDateTime());
            eventStr[15] = event.getSignedOffDateTime() == null ? null : sdf.format(event.getSignedOffDateTime());
            eventStr[16] = getPropertyNameByIdUtil.getStateValueByKey(event.getState());
            eventStrList.add(eventStr);
        }
        return eventStrList;
    }

    @Override
    public List<EventDownDTO> getEventBySymptomCondition(FaultDistributionConditionInputDTO faultDistributionConditionDTO, Integer symptomId) throws Exception {
        return eventDao.getEventBySymptomCondition(faultDistributionConditionDTO, symptomId);
    }

    /* @Override
    public List<EventDownDTO> getDownEvent(FaultDistributionConditionInputDTO faultDistributionConditionDTO) throws Exception{
        return eventDao.getDownEvent(faultDistributionConditionDTO);
    }

    @Override
    public List<String> getDownStationName(List<Integer> stationIdList) {
        return eventDao.getDownStationName(stationIdList);
    }*/

    @Override
    public List<EventSymptomIssueTimeDTO> getEventSymptomIssueTimeDTO(MaintenanceAnalysisConditionInputDTO maintenanceAnalysisConditionInputDTO, Integer stationId) throws Exception {
        return eventDao.getEventSymptomIssueTimeDTO(maintenanceAnalysisConditionInputDTO, stationId);
    }

//    @Override
//    public Integer getTotalCountEventInitiatedListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception {
//        return eventDao.getTotalCountEventInitiatedListByCondition(myEventInputForSearch);
//    }
//
//    @Override
//    public List<EventForListOutputDTO> getEventInitiatedListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception {
//        List<Event> eventList = eventDao.getEventInitiatedListByCondition(myEventInputForSearch);
//        List<EventForListOutputDTO> eventInitiatedListOutputDTOList = new ArrayList<>();
//        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
//        for (Event event : eventList) {
//            EventForListOutputDTO eventForListOutputDTO = new EventForListOutputDTO();
//            eventForListOutputDTO.setId(event.getId());
//            eventForListOutputDTO.setNo(event.getEventCode());
//            eventForListOutputDTO.setAreaNumber(parseFieldToMapUtil.parseFiledToString(event.getArea().getAreaNumber(), event.getArea().getDescription()));
//            if(event.getStation() != null){
//                eventForListOutputDTO.setStationNumber(parseFieldToMapUtil.parseFiledToString(event.getStation().getStationNumber(), event.getStation().getName()));
//            }
//            eventForListOutputDTO.setSymptom(parseFieldToMapUtil.parseFiledToString(event.getEventSymptom().getSymptomCode(), event.getEventSymptom().getName()));
//            eventForListOutputDTO.setImpactDegree(getPropertyNameByIdUtil.getImpactDegreeValueByKey(event.getImpact()));
//            eventForListOutputDTO.setUrgencyDegree(getPropertyNameByIdUtil.getUrgencyDegreeValueByKey(event.getUrgency()));
//            eventForListOutputDTO.setPassedTime(StringUtil.getHourMinutesApart(event.getIssueDateTime(), new Date()));
//            eventForListOutputDTO.setEventColor(StringUtil.getEventColor(event.getIssueDateTime(), new Date()));
//
//            eventForListOutputDTO.setSymptom(parseFieldToMapUtil.parseFiledToString(event.getEventSymptom().getSymptomCode(), event.getEventSymptom().getName()));
//            eventForListOutputDTO.setImpactDegree(getPropertyNameByIdUtil.getImpactDegreeValueByKey(event.getImpact()));
//            eventForListOutputDTO.setUrgencyDegree(getPropertyNameByIdUtil.getUrgencyDegreeValueByKey(event.getUrgency()));
//            eventForListOutputDTO.setTitle(event.getTitle());
//            eventForListOutputDTO.setReportUser(event.getReportUser() == null ? null : event.getReportUser().getUserName());
//            eventForListOutputDTO.setAssignee(event.getAssignee() == null ? null : event.getAssignee().getUserName());
//            eventForListOutputDTO.setIssueDateTime(event.getIssueDateTime());
//            eventForListOutputDTO.setPassedTime(StringUtil.getHourMinutesApart(event.getIssueDateTime(), new Date()));
//            eventForListOutputDTO.setReportDateTime(event.getReportDateTime());
//            eventForListOutputDTO.setAssignedDateTime(event.getAssignedDateTime());
//            eventForListOutputDTO.setActionStartedDateTime(event.getActionStartedDateTime());
//            eventForListOutputDTO.setActionFinishedDateTime(event.getActionFinishedDateTime());
//            eventForListOutputDTO.setSignedOffDateTime(event.getSignedOffDateTime());
//            eventForListOutputDTO.setState(getPropertyNameByIdUtil.getStateValueByKey(event.getState()));
//            eventForListOutputDTO.setEventColor(StringUtil.getEventColor(event.getIssueDateTime(), new Date()));
//            eventInitiatedListOutputDTOList.add(eventForListOutputDTO);
//        }
//        return eventInitiatedListOutputDTOList;
//    }
    @Override
    public Integer getTotalCountEventConcernedListByCondition(EventConcernedListInputDTO eventConcernedListInputDTO, boolean isMeFlag) {
        return eventDao.getTotalCountEventConcernedListByCondition(eventConcernedListInputDTO, isMeFlag);
    }

    @Override
    public List<EventListOfMyTaskOutputDTO> getEventConcernedListByCondition(EventConcernedListInputDTO eventConcernedListInputDTO, boolean isMeFlag) {
        List<Event> eventList = eventDao.getEventConcernedListByCondition(eventConcernedListInputDTO, isMeFlag);
        List<EventListOfMyTaskOutputDTO> eventListOfMyTaskOutputDTOs = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (!CollectionUtils.isEmpty(eventList)) {
            for (Event event : eventList) {
                EventListOfMyTaskOutputDTO eventListOfMyTaskOutputDTO = new EventListOfMyTaskOutputDTO();
                eventListOfMyTaskOutputDTO.setId(event.getId());
                eventListOfMyTaskOutputDTO.setNo(event.getEventCode());
                eventListOfMyTaskOutputDTO.setAreaNumber(parseFieldToMapUtil.parseFiledToString(event.getArea().getAreaNumber(), event.getArea().getDescription()));
                if(event.getStation() != null){
                    eventListOfMyTaskOutputDTO.setStationNumber(parseFieldToMapUtil.parseFiledToString(event.getStation().getStationNumber(), event.getStation().getName()));
                }
                eventListOfMyTaskOutputDTO.setImpactDegree(getPropertyNameByIdUtil.getImpactDegreeValueByKey(event.getImpact()));
                eventListOfMyTaskOutputDTO.setUrgencyDegree(getPropertyNameByIdUtil.getUrgencyDegreeValueByKey(event.getUrgency()));
                eventListOfMyTaskOutputDTO.setReportUser(event.getReportUser() == null ? null : event.getReportUser().getUserName());
                eventListOfMyTaskOutputDTO.setAssignee(event.getAssignee() == null ? null : event.getAssignee().getUserName());
                eventListOfMyTaskOutputDTO.setIssueDateTime(event.getIssueDateTime());
                eventListOfMyTaskOutputDTO.setPassedTime(StringUtil.getHourMinutesApart(event.getIssueDateTime(), new Date()));
                eventListOfMyTaskOutputDTO.setReportDateTime(event.getReportDateTime());
                eventListOfMyTaskOutputDTO.setAssignedDateTime(event.getAssignedDateTime());
                eventListOfMyTaskOutputDTO.setActionStartedDateTime(event.getActionStartedDateTime());
                eventListOfMyTaskOutputDTO.setActionFinishedDateTime(event.getActionFinishedDateTime());
                eventListOfMyTaskOutputDTO.setSignedOffDateTime(event.getSignedOffDateTime());
                eventListOfMyTaskOutputDTO.setState(getPropertyNameByIdUtil.getStateValueByKey(event.getState()));
                eventListOfMyTaskOutputDTOs.add(eventListOfMyTaskOutputDTO);
            }
        }
        return eventListOfMyTaskOutputDTOs;
    }

    @Override
    public EventDetailOutputDTO getEventDetailById(Integer eventId) {
        Event event = eventDao.getEventById(eventId);
        EventDetailOutputDTO eventDetailOutputDTO = new EventDetailOutputDTO();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (event != null) {
            eventDetailOutputDTO.setNo(event.getEventCode());
            eventDetailOutputDTO.setArea(parseFieldToMapUtil.parseFiledToString(event.getArea().getAreaNumber(), event.getArea().getDescription()));
            if(event.getStation() != null){
                eventDetailOutputDTO.setStation(parseFieldToMapUtil.parseFiledToString(event.getStation().getStationNumber(), event.getStation().getName()));
            }
            eventDetailOutputDTO.setSymptom(parseFieldToMapUtil.parseFiledToString(event.getEventSymptom().getSymptomCode(), event.getEventSymptom().getName()));
            eventDetailOutputDTO.setDescription(event.getDescription());
            eventDetailOutputDTO.setEventCategory(event.getEventCategory() == null ? null : event.getEventCategory().getName());
            eventDetailOutputDTO.setImpactDegree(getPropertyNameByIdUtil.getImpactDegreeValueByKey(event.getImpact()));
            eventDetailOutputDTO.setUrgencyDegree(getPropertyNameByIdUtil.getUrgencyDegreeValueByKey(event.getUrgency()));
            eventDetailOutputDTO.setReportDateTime(event.getReportDateTime());
            eventDetailOutputDTO.setReportUser(event.getReportUser() == null ? null : event.getReportUser().getUserName());
            eventDetailOutputDTO.setActionFinishedDateTime(event.getActionFinishedDateTime());
            eventDetailOutputDTO.setActionOwner(event.getActionOwner() == null ? null : event.getActionOwner().getUserName());
            eventDetailOutputDTO.setClarify(event.getClarify());
            eventDetailOutputDTO.setSolution(event.getSolution());
            boolean isSolved = false;
            EventEvaluationRecord eventEvaluationRecord = event.getEventEvaluationRecord();
            if(eventEvaluationRecord != null){
                if(eventEvaluationRecord.getState() == 1){
                    isSolved = true;
                }else if(eventEvaluationRecord.getState() == 0){
                    isSolved = false;
                }
            }
            eventDetailOutputDTO.setIsSolved(isSolved);
            return eventDetailOutputDTO;
        } else {
            return null;
        }
    }

    @Override
    public Integer getEventStateByEventId(Integer eventId) {
        return eventDao.getEventStateByEventId(eventId);
    }


    @Override
    public void signOffEvent(AddEventEvaluationRecordInputDTO addEventEvaluationRecordInputDTO) {
        Integer eventId = addEventEvaluationRecordInputDTO.getEventId();
        Integer userId = addEventEvaluationRecordInputDTO.getUserId();
        eventDao.updateEventStateByEventId(eventId, userId, 150);
        Event event = eventDao.getEventById(eventId);
        EventEvaluationRecord eventEvaluationRecord = event.getEventEvaluationRecord();
        if(eventEvaluationRecord != null) {
            eventEvaluationRecord.setEvaluateGrade(addEventEvaluationRecordInputDTO.getGrade());
            eventEvaluationRecord.setEvaluateComment(addEventEvaluationRecordInputDTO.getComment());
            eventEvaluationRecord.setUser(userDao.getUserById(userId));
            eventEvaluationRecord.setState(addEventEvaluationRecordInputDTO.getIsSolved() == true ? 1 : 0);
            eventEvaluationRecordDao.updateEventEvaluationRecord(eventEvaluationRecord);
        }else{
            eventEvaluationRecord = new EventEvaluationRecord();
            eventEvaluationRecord.setEvent(event);
            eventEvaluationRecord.setEvaluateGrade(addEventEvaluationRecordInputDTO.getGrade());
            eventEvaluationRecord.setEvaluateComment(addEventEvaluationRecordInputDTO.getComment());
            eventEvaluationRecord.setCreationDateTime(new Date());
            eventEvaluationRecord.setUser(userDao.getUserById(userId));
            eventEvaluationRecord.setState(addEventEvaluationRecordInputDTO.getIsSolved() == true ? 1 : 0);
            eventEvaluationRecordDao.addEventEvaluationRecord(eventEvaluationRecord);
        }
    }

    @Override
    public void updateEventActionOwnerByEventId(Integer userId, List<Integer> eventIdList) {
        eventDao.updateEventActionOwnerByEventId(userId, eventIdList);
    }

    @Override
    public List<AssignTaskInitOutputDTO> getAssignTaskDetailListByIds(List<Integer> eventIdList) {
        List<Event> eventList = eventDao.getEventListByEventIds(eventIdList);
        List<AssignTaskInitOutputDTO> assignTaskInitOutputDTOList = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (!CollectionUtils.isEmpty(eventList)) {
            for (Event event:eventList){
                AssignTaskInitOutputDTO assignTaskInitOutputDTO = new AssignTaskInitOutputDTO();
                assignTaskInitOutputDTO.setEventId(event.getId());
                assignTaskInitOutputDTO.setNo(event.getEventCode());
                assignTaskInitOutputDTO.setArea(parseFieldToMapUtil.parseFiledToString(event.getArea().getAreaNumber(), event.getArea().getDescription()));
                if(event.getStation() != null){
                    assignTaskInitOutputDTO.setStation(parseFieldToMapUtil.parseFiledToString(event.getStation().getStationNumber(), event.getStation().getName()));
                }
                assignTaskInitOutputDTO.setTitle(event.getTitle());
                assignTaskInitOutputDTO.setDescription(event.getDescription());
                assignTaskInitOutputDTO.setEventCategory(event.getEventCategory() == null ? null : event.getEventCategory().getName());
                assignTaskInitOutputDTO.setImpactDegree(getPropertyNameByIdUtil.getImpactDegreeValueByKey(event.getImpact()));
                assignTaskInitOutputDTO.setUrgencyDegree(getPropertyNameByIdUtil.getUrgencyDegreeValueByKey(event.getUrgency()));
                assignTaskInitOutputDTOList.add(assignTaskInitOutputDTO);
            }
        }
        return assignTaskInitOutputDTOList;
    }

    @Override
    public void updateEventAssigneeAndActionOwnerByEventId(AssignTaskInputDTO assignTaskInputDTO) {
        eventDao.updateEventAssigneeAndActionOwnerByEventId(assignTaskInputDTO);
    }

    @Override
    public List<Event> getEventListByEventSubscriptionRules() {
        return eventDao.getEventListByEventSubscriptionRules();
    }
}
