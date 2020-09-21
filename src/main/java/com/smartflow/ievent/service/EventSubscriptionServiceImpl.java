package com.smartflow.ievent.service;

import com.smartflow.ievent.dao.*;
import com.smartflow.ievent.dto.*;
import com.smartflow.ievent.model.EventSubscription;
import com.smartflow.ievent.model.EventSymptom;
import com.smartflow.ievent.util.GetPropertyNameByIdUtil;
import com.smartflow.ievent.util.ParseFieldToMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class EventSubscriptionServiceImpl implements EventSubscriptionService {
    @Autowired
    EventSubscriptionDao eventSubscriptionDao;
    @Autowired
    EventCategoryDao eventCategoryDao;
    @Autowired
    EventSymptomDao eventSymptomDao;
    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    GetPropertyNameByIdUtil getPropertyNameByIdUtil = new GetPropertyNameByIdUtil();

    @Override
    public List<MySubscriptionListConditionOutputDTO> getEventSubscriptionList(MySubscriptionListConditionInputDTO mySubscriptionListConditionInputDTO) {
        List<EventSubscription> eventSubscriptionList = eventSubscriptionDao.getEventSubscriptionList(mySubscriptionListConditionInputDTO);
        List<MySubscriptionListConditionOutputDTO> mySubscriptionListConditionOutputDTOList = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (!CollectionUtils.isEmpty(eventSubscriptionList)) {
            for (EventSubscription eventSubscription : eventSubscriptionList) {
                MySubscriptionListConditionOutputDTO mySubscriptionListConditionOutputDTO = new MySubscriptionListConditionOutputDTO();
                mySubscriptionListConditionOutputDTO.setId(eventSubscription.getId());
                mySubscriptionListConditionOutputDTO.setEventCategory(eventSubscription.getEventCategory().getName());
                mySubscriptionListConditionOutputDTO.setEventSymptom(eventSubscription.getEventSymptom() == null ? null : parseFieldToMapUtil.parseFiledToString(eventSubscription.getEventSymptom().getSymptomCode(), eventSubscription.getEventSymptom().getName()));
                mySubscriptionListConditionOutputDTO.setImpactDegree(getPropertyNameByIdUtil.getImpactDegreeValueByKey(eventSubscription.getImpact()));
                mySubscriptionListConditionOutputDTO.setUrgencyDegree(getPropertyNameByIdUtil.getUrgencyDegreeValueByKey(eventSubscription.getUrgency()));
                mySubscriptionListConditionOutputDTO.setSubscriber(eventSubscription.getUser().getUserName());
                mySubscriptionListConditionOutputDTO.setSubscribeRole(eventSubscription.getRole() == null ? null : eventSubscription.getRole().getRoleName());
                mySubscriptionListConditionOutputDTOList.add(mySubscriptionListConditionOutputDTO);
            }
        }
        return mySubscriptionListConditionOutputDTOList;
    }

    @Override
    public Integer getTotalCountEventSubscriptionList(MySubscriptionListConditionInputDTO mySubscriptionListConditionInputDTO) {
        return eventSubscriptionDao.getTotalCountEventSubscriptionList(mySubscriptionListConditionInputDTO);
    }

    @Override
    public void addEventSubscription(AddEventSubscriptionInputDTO addEventSubscriptionInputDTO) {
        EventSubscription eventSubscription = new EventSubscription();
        List<Integer> eventCategoryList = addEventSubscriptionInputDTO.getEventCategoryId();
        if(!CollectionUtils.isEmpty(eventCategoryList)){
            eventSubscription.setEventCategory(eventCategoryDao.getEventCategoryById(eventCategoryList.get(eventCategoryList.size()-1)));
        }
        eventSubscription.setImpact(addEventSubscriptionInputDTO.getImpactDegree());
        eventSubscription.setUrgency(addEventSubscriptionInputDTO.getUrgencyDegree());
        eventSubscription.setEventSymptom(StringUtils.isEmpty(addEventSubscriptionInputDTO.getEventSymptomId()) ? null : eventSymptomDao.getEventSymptomById(addEventSubscriptionInputDTO.getEventSymptomId()));
        eventSubscription.setUser(userDao.getUserById(addEventSubscriptionInputDTO.getUserId()));
        eventSubscription.setRole(StringUtils.isEmpty(addEventSubscriptionInputDTO.getSubscribeRoleId()) ? null : roleDao.getRoleById(addEventSubscriptionInputDTO.getSubscribeRoleId()));
        eventSubscription.setCreationDateTime(new Date());
        eventSubscription.setState(1);
        eventSubscriptionDao.addEventSubscription(eventSubscription);
    }

    @Override
    public EditEventSubscriptionInitOutputDTO getEventSubscriptionById(Integer eventSubscriptionId) {
        EventSubscription eventSubscription = eventSubscriptionDao.getEventSubscriptionById(eventSubscriptionId);
        EditEventSubscriptionInitOutputDTO editEventSubscriptionInitOutputDTO = new EditEventSubscriptionInitOutputDTO();
        if (eventSubscription != null) {
            editEventSubscriptionInitOutputDTO.setId(eventSubscription.getId());
            List<String> eventCategoryIdList = Arrays.asList(eventSubscription.getEventCategory().getParentCategory().split(","));
            editEventSubscriptionInitOutputDTO.setEventCategoryId(eventCategoryIdList.stream().map(Integer::parseInt).collect(Collectors.toList()));
            editEventSubscriptionInitOutputDTO.setImpactDegree(eventSubscription.getImpact());
            editEventSubscriptionInitOutputDTO.setUrgencyDegree(eventSubscription.getUrgency());
            editEventSubscriptionInitOutputDTO.setEventSymptomId(eventSubscription.getEventSymptom() == null ? null : eventSubscription.getEventSymptom().getId());
            editEventSubscriptionInitOutputDTO.setSubscribeRoleId(eventSubscription.getRole() == null ? null : eventSubscription.getRole().getId());
        }
        return editEventSubscriptionInitOutputDTO;
    }

    @Override
    public void updateEventSubscription(EditEventSubscriptionInputDTO editEventSubscriptionInputDTO) {
        EventSubscription eventSubscription = eventSubscriptionDao.getEventSubscriptionById(editEventSubscriptionInputDTO.getId());
        if (eventSubscription != null) {
            List<Integer> eventCategoryIdList = editEventSubscriptionInputDTO.getEventCategoryId();
            eventSubscription.setEventCategory(eventCategoryDao.getEventCategoryById(eventCategoryIdList.get(eventCategoryIdList.size()-1)));
            eventSubscription.setImpact(editEventSubscriptionInputDTO.getImpactDegree());
            eventSubscription.setUrgency(editEventSubscriptionInputDTO.getUrgencyDegree());
            eventSubscription.setEventSymptom(StringUtils.isEmpty(editEventSubscriptionInputDTO.getEventSymptomId()) ? null : eventSymptomDao.getEventSymptomById(editEventSubscriptionInputDTO.getEventSymptomId()));
            eventSubscription.setRole(StringUtils.isEmpty(editEventSubscriptionInputDTO.getSubscribeRoleId()) ? null : roleDao.getRoleById(editEventSubscriptionInputDTO.getSubscribeRoleId()));
            eventSubscriptionDao.updateEventSubscription(eventSubscription);
        }
    }

    @Override
    public void cancelSubscription(Integer eventSubscriptionId) {
        eventSubscriptionDao.cancelSubscription(eventSubscriptionId);
    }
}
