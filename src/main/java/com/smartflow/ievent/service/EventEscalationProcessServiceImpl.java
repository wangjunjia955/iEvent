package com.smartflow.ievent.service;

import com.smartflow.ievent.dao.EventEscalationProcessDao;
import com.smartflow.ievent.model.EventEscalationProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventEscalationProcessServiceImpl implements EventEscalationProcessService {
    @Autowired
    EventEscalationProcessDao eventEscalationProcessDao;

    @Override
    public EventEscalationProcess getEventEscalationProcessById(Integer escalationProcessId) {
        return eventEscalationProcessDao.getEventEscalationProcessById(escalationProcessId);
    }
}
