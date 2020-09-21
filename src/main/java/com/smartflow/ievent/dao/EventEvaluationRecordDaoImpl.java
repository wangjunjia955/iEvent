package com.smartflow.ievent.dao;

import com.smartflow.ievent.model.EventEvaluationRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class EventEvaluationRecordDaoImpl implements EventEvaluationRecordDao {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public void addEventEvaluationRecord(EventEvaluationRecord eventEvaluationRecord) {
        entityManager.persist(eventEvaluationRecord);
    }

    @Override
    public void updateEventEvaluationRecord(EventEvaluationRecord eventEvaluationRecord) {
        entityManager.merge(eventEvaluationRecord);
    }
}
