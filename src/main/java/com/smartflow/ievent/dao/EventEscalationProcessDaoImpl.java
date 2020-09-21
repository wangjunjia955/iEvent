package com.smartflow.ievent.dao;

import com.smartflow.ievent.model.EventEscalationProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class EventEscalationProcessDaoImpl implements EventEscalationProcessDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public EventEscalationProcess getEventEscalationProcessById(Integer escalationProcessId) {
        return entityManager.find(EventEscalationProcess.class, escalationProcessId);
    }
}
