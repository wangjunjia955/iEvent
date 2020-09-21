package com.smartflow.ievent.service.MyEventService;

import com.smartflow.ievent.dao.MyEvent.MyEventDao;
import com.smartflow.ievent.dao.UserDao;
import com.smartflow.ievent.dto.EventForListOutputDTO;
import com.smartflow.ievent.dto.MyEvent.AddEventEvaluationRecordInputDTO;
import com.smartflow.ievent.dto.MyEvent.MyEventDetailOutputDTO;
import com.smartflow.ievent.dto.MyEvent.MyEventInputForSearch;
import com.smartflow.ievent.dto.MyEvent.MyEventOutputForPageInit;
import com.smartflow.ievent.model.Event;
import com.smartflow.ievent.model.EventEvaluationRecord;
import com.smartflow.ievent.util.Event.MyEventUtil;
import com.smartflow.ievent.util.GetPropertyNameByIdUtil;
import com.smartflow.ievent.util.ParseFieldToMapUtil;
import com.smartflow.ievent.util.PropertyUtil;
import com.smartflow.ievent.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MyEventServiceImpl implements MyEventService {
    @Autowired
    MyEventDao myEventDao;

    @Autowired
    PropertyUtil propertyUtil;

    @Autowired
    UserDao userDao;

    GetPropertyNameByIdUtil getPropertyNameByIdUtil = new GetPropertyNameByIdUtil();

    @Override
    public List<EventForListOutputDTO> getMyEventListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception {
        List<Event> eventList = myEventDao.getMyEventListByCondition(myEventInputForSearch);
        List<EventForListOutputDTO> eventForListOutputDTOList = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (!CollectionUtils.isEmpty(eventList)) {
            for (Event event : eventList) {
                EventForListOutputDTO eventForListOutputDTO = new EventForListOutputDTO();
                eventForListOutputDTO.setId(event.getId());
                eventForListOutputDTO.setNo(event.getEventCode());
                eventForListOutputDTO.setAreaNumber(parseFieldToMapUtil.parseFiledToString(event.getArea().getAreaNumber(), event.getArea().getDescription()));
                if(event.getStation() != null){
                    eventForListOutputDTO.setStationNumber(parseFieldToMapUtil.parseFiledToString(event.getStation().getStationNumber(), event.getStation().getName()));
                }eventForListOutputDTO.setSymptom(parseFieldToMapUtil.parseFiledToString(event.getEventSymptom().getSymptomCode(), event.getEventSymptom().getName()));
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
                eventForListOutputDTO.setEventColor(StringUtil.getEventColor(event.getIssueDateTime(),new Date()));
                eventForListOutputDTOList.add(eventForListOutputDTO);
            }
        }
        return eventForListOutputDTOList;
    }

    @Override
    public Integer getTotalCountOfMyEventListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception {
        return myEventDao.getTotalCountOfMyEventListByCondition(myEventInputForSearch);
    }

    @Override
    public MyEventDetailOutputDTO getEventDetailById(Integer eventId) {
        Event event = myEventDao.getEventById(eventId);
        MyEventDetailOutputDTO myEventDetailOutputDTO = new MyEventDetailOutputDTO();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (event != null) {
            myEventDetailOutputDTO.setNo(event.getEventCode());
            myEventDetailOutputDTO.setArea(parseFieldToMapUtil.parseFiledToString(event.getArea().getAreaNumber(), event.getArea().getDescription()));
            if(event.getStation() != null){
                myEventDetailOutputDTO.setStation(parseFieldToMapUtil.parseFiledToString(event.getStation().getStationNumber(), event.getStation().getName()));
            }
            myEventDetailOutputDTO.setTitle(event.getTitle());
            myEventDetailOutputDTO.setDescription(event.getDescription());
            myEventDetailOutputDTO.setEventCategory(event.getEventCategory() == null ? null : event.getEventCategory().getName());
            myEventDetailOutputDTO.setImpactDegree(getPropertyNameByIdUtil.getImpactDegreeValueByKey(event.getImpact()));
            myEventDetailOutputDTO.setUrgencyDegree(getPropertyNameByIdUtil.getUrgencyDegreeValueByKey(event.getUrgency()));
            myEventDetailOutputDTO.setActionStartedDateTime(event.getActionStartedDateTime());
            myEventDetailOutputDTO.setActionFinishedDateTime(event.getActionFinishedDateTime());
            myEventDetailOutputDTO.setClarify(event.getClarify());
            myEventDetailOutputDTO.setSolution(event.getSolution());
            boolean isSolved = false;
            EventEvaluationRecord eventEvaluationRecord = event.getEventEvaluationRecord();
            if(eventEvaluationRecord != null){
                if(eventEvaluationRecord.getState() == 1){
                    isSolved = true;
                }else if(eventEvaluationRecord.getState() == 0){
                    isSolved = false;
                }
            }
            myEventDetailOutputDTO.setIsSolved(isSolved);
            return myEventDetailOutputDTO;
        } else {
            return null;
        }
    }

    @Override
    public Event getEventById(Integer eventId) {
        return myEventDao.getEventById(eventId);
    }

    @Override
    public void updateEventEvaluationRecord(EventEvaluationRecord eventEvaluationRecord) {
        myEventDao.updateEventEvaluationRecord(eventEvaluationRecord);
    }

    @Override
    public void signoffEvent(AddEventEvaluationRecordInputDTO addEventEvaluationRecordInputDTO) {
        Integer eventId = addEventEvaluationRecordInputDTO.getEventId();
        Integer userId = addEventEvaluationRecordInputDTO.getUserId();
        myEventDao.updateEventStateByEventId(eventId, userId, 150);
        Event event = myEventDao.getEventById(eventId);
        EventEvaluationRecord eventEvaluationRecord = event.getEventEvaluationRecord();
        if(eventEvaluationRecord != null) {
            eventEvaluationRecord.setEvaluateGrade(addEventEvaluationRecordInputDTO.getGrade());
            eventEvaluationRecord.setEvaluateComment(addEventEvaluationRecordInputDTO.getComment());
            eventEvaluationRecord.setUser(userDao.getUserById(userId));
            eventEvaluationRecord.setState(addEventEvaluationRecordInputDTO.getIsSolved() == true ? 1 : 0);
            myEventDao.updateEventEvaluationRecord(eventEvaluationRecord);
        }else{
            eventEvaluationRecord = new EventEvaluationRecord();
            eventEvaluationRecord.setEvent(event);
            eventEvaluationRecord.setEvaluateGrade(addEventEvaluationRecordInputDTO.getGrade());
            eventEvaluationRecord.setEvaluateComment(addEventEvaluationRecordInputDTO.getComment());
            eventEvaluationRecord.setCreationDateTime(new Date());
            eventEvaluationRecord.setUser(userDao.getUserById(userId));
            eventEvaluationRecord.setState(addEventEvaluationRecordInputDTO.getIsSolved() == true ? 1 : 0);
            myEventDao.addEventEvaluationRecord(eventEvaluationRecord);
        }

    }


    @Override
    public Integer getEventStateByEventId(Integer eventId) {
        return myEventDao.getEventStateByEventId(eventId);
    }

    @Override
    public void closeEvent(Integer eventId) {
        myEventDao.closeEvent(eventId);
    }
}
