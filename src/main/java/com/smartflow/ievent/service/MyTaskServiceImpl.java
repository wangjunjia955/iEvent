package com.smartflow.ievent.service;

import com.smartflow.ievent.dao.MyEvent.MyEventDao;
import com.smartflow.ievent.dao.MyTaskDao;
import com.smartflow.ievent.dto.EditEventInitOutputDTO;
import com.smartflow.ievent.dto.EventForListOutputDTO;
import com.smartflow.ievent.dto.EventListOfMyTaskOutputDTO;
import com.smartflow.ievent.dto.MyEvent.MyEventInputForSearch;
import com.smartflow.ievent.model.Event;
import com.smartflow.ievent.model.EventEvaluationRecord;
import com.smartflow.ievent.util.GetPropertyNameByIdUtil;
import com.smartflow.ievent.util.ParseFieldToMapUtil;
import com.smartflow.ievent.util.PropertyUtil;
import com.smartflow.ievent.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class MyTaskServiceImpl implements MyTaskService {
    @Autowired
    MyTaskDao myTaskDao;
    @Autowired
    MyEventDao myEventDao;
    @Autowired
    PropertyUtil propertyUtil;

    GetPropertyNameByIdUtil getPropertyNameByIdUtil = new GetPropertyNameByIdUtil();

    @Override
    public List<EventListOfMyTaskOutputDTO> getEventListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception {
        List<Event> eventList = myTaskDao.getEventListByCondition(myEventInputForSearch);
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
    public Integer getTotalCountOfEventListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception {
        return myTaskDao.getTotalCountOfEventListByCondition(myEventInputForSearch);
    }

    @Override
    public EditEventInitOutputDTO editEventInit(Integer eventId) {
        Event event = myEventDao.getEventById(eventId);
        EditEventInitOutputDTO editEventInitOutputDTO = new EditEventInitOutputDTO();
        if (event != null) {
            editEventInitOutputDTO.setEventId(event.getId());
            editEventInitOutputDTO.setNo(event.getEventCode());
            editEventInitOutputDTO.setArea(event.getArea().getAreaNumber());
            editEventInitOutputDTO.setStation(event.getStation() == null ? null : event.getStation().getStationNumber());
            editEventInitOutputDTO.setTitle(event.getTitle());
            editEventInitOutputDTO.setDescription(event.getDescription());
            if(event.getEventCategory() != null){
                List<String> categoryIdList = Arrays.asList(event.getEventCategory().getParentCategory().split(","));
                editEventInitOutputDTO.setEventCategoryId(categoryIdList.stream().map(Integer::parseInt).collect(Collectors.toList()));
            }
            editEventInitOutputDTO.setImpactDegree(event.getImpact());
            editEventInitOutputDTO.setUrgencyDegree(event.getUrgency());
            editEventInitOutputDTO.setActionStartedDateTime(event.getActionStartedDateTime());
            editEventInitOutputDTO.setActionFinishedDateTime(event.getActionFinishedDateTime());
            editEventInitOutputDTO.setClarify(event.getClarify());
            editEventInitOutputDTO.setSolution(event.getSolution());
            EventEvaluationRecord eventEvaluationRecord = event.getEventEvaluationRecord();
            if(eventEvaluationRecord != null){
                editEventInitOutputDTO.setIsSolved(eventEvaluationRecord.getState() == 1 ? true : false);
            }
            return editEventInitOutputDTO;
        } else {
            return null;
        }
    }

    @Override
    public void updateEvent(Event event) {
        myTaskDao.updateEvent(event);
    }

    @Override
    public void addEventEvaluationRecord(EventEvaluationRecord eventEvaluationRecord) {
        myTaskDao.addEventEvaluationRecord(eventEvaluationRecord);
    }
}
