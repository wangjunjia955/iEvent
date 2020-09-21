package com.smartflow.ievent.service;

import com.smartflow.ievent.dao.EventSymptomDao;
import com.smartflow.ievent.dto.FaultDistributionConditionInputDTO;
import com.smartflow.ievent.dto.FaultDistributionConditionInputDTO;
import com.smartflow.ievent.dto.PageConditionInputDTO;
import com.smartflow.ievent.model.EventSymptom;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@Transactional
public class EventSymptomServiceImpl implements EventSymptomService {
    @Autowired
    EventSymptomDao eventSymptomDao;

    @Override
    public List<Map<String, Object>> getEventSymptomInit() {
        return eventSymptomDao.getEventSymptomInit();
    }

    @Override
    public List<Map<String, Object>> getEventSymptomNameInit() {
        return eventSymptomDao.getEventSymptomNameInit();
    }

    @Override
    public List<Map<String, Object>> getEventSymptomNameList() {
        return eventSymptomDao.getEventSymptomNameList();
    }

    @Override
    public List<Map<String, Object>> getSymptomDistributionChartData(FaultDistributionConditionInputDTO faultDistributionConditionDTO) throws ParseException {
        return eventSymptomDao.getSymptomDistributionChartData(faultDistributionConditionDTO);
    }

    @Override
    public Integer getTotalCountEventSymptomList() {
        return eventSymptomDao.getTotalCountEventSymptomList();
    }

    @Override
    public List<EventSymptom> getEventSymptomList(PageConditionInputDTO pageConditionDTO) {
        return eventSymptomDao.getEventSymptomList(pageConditionDTO);
    }

    @Override
    public boolean isExistSymptomCode(String symptomCode) {
        return eventSymptomDao.isExistSymptomCode(symptomCode);
    }

    @Override
    public void addEventSymptom(EventSymptom eventSymptom) {
        eventSymptomDao.addEventSymptom(eventSymptom);
    }

    @Override
    public EventSymptom getEventSymptomById(Integer eventSymptomId) {
        return eventSymptomDao.getEventSymptomById(eventSymptomId);
    }

    @Override
    public void updateEventSymptom(EventSymptom eventSymptom) {
        eventSymptomDao.updateEventSymptom(eventSymptom);
    }

    @Override
    public void deleteEventSymptom(EventSymptom eventSymptom) {
        eventSymptomDao.deleteEventSymptom(eventSymptom);
    }
}
