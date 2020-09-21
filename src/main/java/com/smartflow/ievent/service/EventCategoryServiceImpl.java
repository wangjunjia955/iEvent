package com.smartflow.ievent.service;

import com.smartflow.ievent.dao.EventCategoryDao;
import com.smartflow.ievent.dto.MaintenanceAnalysisConditionInputDTO;
import com.smartflow.ievent.dto.PageConditionInputDTO;
import com.smartflow.ievent.dto.EventCategoryListOutputDTO;
import com.smartflow.ievent.dto.FaultDistributionConditionInputDTO;
import com.smartflow.ievent.model.EventCatetory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EventCategoryServiceImpl implements EventCategoryService {

    @Autowired
    EventCategoryDao eventCategoryDao;

    @Override
    public List<Map<String, Object>> getEventCategoryInit() {
        List<Map<String, Object>> eventCategoryList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> eventCategoryParentList = eventCategoryDao.getEventCategoryInit();
        if (!CollectionUtils.isEmpty(eventCategoryParentList)) {
            for (Map<String, Object> eventCategoryParent : eventCategoryParentList) {
//                List<Map<String, Object>> eventCategorySonList = eventCategoryDao.getEventCategoryInitByParentEventCategoryId(Integer.parseInt(eventCategoryParent.get("value").toString()));
//                if (!CollectionUtils.isEmpty(eventCategorySonList)) {
//                    for (Map<String, Object> eventCategorySon : eventCategorySonList) {
//                        List<Map<String, Object>> eventCategoryGrandsonList = eventCategoryDao.getEventCategoryInitByParentEventCategoryId(Integer.parseInt(eventCategorySon.get("value").toString()));
//                        eventCategorySon.put("children", eventCategoryGrandsonList);
//                    }
//                }
                List<Map<String, Object>> eventCategorySonList = eventCategoryDao.getChildrenCategoryList(Integer.parseInt(eventCategoryParent.get("value").toString()));
                eventCategoryParent.put("children", eventCategorySonList);
                eventCategoryList.add(eventCategoryParent);
            }
            map.put("value", 0);
            map.put("label", "所有");
            map.put("children", eventCategoryDao.getAllEventCategoryInit());
            eventCategoryList.add(map);
        }
        return eventCategoryList;
    }

    @Override
    public List<Map<String, Object>> getEventCategoryNameInit() {
        return eventCategoryDao.getEventCategoryNameInit();
    }

    @Override
    public List<EventCatetory> getEventCategoryListByParentEventCategoryId(Integer catetoryId) {
        return eventCategoryDao.getEventCategoryListByParentEventCategoryId(catetoryId);
    }


    @Override
    public Integer getTotalCountEventCategoryList() {
        return eventCategoryDao.getTotalCountEventCategoryList();
    }

    @Override
    public List<EventCategoryListOutputDTO> getEventCategoryList(PageConditionInputDTO pageConditionDTO) {
        List<EventCatetory> eventCategoryList = eventCategoryDao.getEventCategoryList(pageConditionDTO);
        List<EventCategoryListOutputDTO> eventCategoryListDTOs = new ArrayList<>();
        if (!CollectionUtils.isEmpty(eventCategoryList)) {
            for (EventCatetory eventCategory : eventCategoryList) {
                EventCategoryListOutputDTO eventCategoryListDTO = new EventCategoryListOutputDTO();
                eventCategoryListDTO.setId(eventCategory.getId());
                eventCategoryListDTO.setCategoryCode(eventCategory.getCategoryCode());
                eventCategoryListDTO.setName(eventCategory.getName());
                eventCategoryListDTO.setDescription(eventCategory.getDescription());
                eventCategoryListDTO.setRoleName(eventCategory.getRole() == null ? null : eventCategory.getRole().getRoleName());
                eventCategoryListDTO.setParentCategory(eventCategory.getEventCategory() == null ? null : eventCategory.getEventCategory().getName());
                eventCategoryListDTOs.add(eventCategoryListDTO);
            }
        }
        return eventCategoryListDTOs;
    }

    @Override
    public boolean isExistCategoryCode(String categoryCode) {
        return eventCategoryDao.isExistCategoryCode(categoryCode);
    }

    @Override
    public void addEventCategory(EventCatetory eventCatetory) {
        eventCategoryDao.addEventCategory(eventCatetory);
    }

    @Override
    public EventCatetory getEventCategoryById(Integer catetoryId) {
        return eventCategoryDao.getEventCategoryById(catetoryId);
    }


    @Override
    public void updateEventCategory(EventCatetory eventCatetory) {
        eventCategoryDao.updateEventCategory(eventCatetory);
    }


    @Override
    public void deleteEventCategory(EventCatetory eventCatetory) {
        eventCategoryDao.deleteEventCategory(eventCatetory);
    }

    @Override
    public List<Map<String, Object>> getCategoryDistributionChartData(FaultDistributionConditionInputDTO faultDistributionConditionDTO) throws ParseException {
        return eventCategoryDao.getCategoryDistributionChartData(faultDistributionConditionDTO);
    }

    @Override
    public List<String> getCategoryNameFromEvent(MaintenanceAnalysisConditionInputDTO maintenanceAnalysisConditionInputDTO) throws Exception {
        return eventCategoryDao.getCategoryNameFromEvent(maintenanceAnalysisConditionInputDTO);
    }
}
