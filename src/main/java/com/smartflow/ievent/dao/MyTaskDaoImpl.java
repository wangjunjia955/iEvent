package com.smartflow.ievent.dao;

import com.smartflow.ievent.dto.EventListOfMyTaskOutputDTO;
import com.smartflow.ievent.dto.MyEvent.MyEventInputForSearch;
import com.smartflow.ievent.model.Event;
import com.smartflow.ievent.model.EventEvaluationRecord;
import com.smartflow.ievent.util.StringUtil;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class MyTaskDaoImpl implements MyTaskDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Event> getEventListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception {
        String jpql = "from Event where State != 160 ";
        if (!StringUtils.isEmpty(myEventInputForSearch.getNo())) {
            jpql += " and EventCode like :EventCode ";
        }
        if (myEventInputForSearch.getAreaId() != null) {
            jpql += " and AreaId = " + myEventInputForSearch.getAreaId();
        }
        if (myEventInputForSearch.getStationId() != null) {
            jpql += " and StationId = " + myEventInputForSearch.getStationId();
        }
        if (myEventInputForSearch.getState() != null) {
            jpql += " and State = " + myEventInputForSearch.getState();
        }
        if (myEventInputForSearch.getUserId() != null) {
            //jpql += " and ReportUserId = " + myEventInputForSearch.getUserId();
            jpql += " and ActionOwnerId = " + myEventInputForSearch.getUserId();
        }
        Date startDateTime = StringUtil.formatDate(myEventInputForSearch.getStartDateTime());
        if (startDateTime != null) {
            jpql += " and IssueDateTime >= :StartDateTime ";
        }
        Date endDateTime = StringUtil.formatDate(myEventInputForSearch.getEndDateTime());
        endDateTime = StringUtil.getEndDateTime(endDateTime);
        if (endDateTime != null) {
            jpql += " and IssueDateTime <= :EndDateTime";
        }
        Query query = entityManager.createQuery(jpql);
        if (!StringUtils.isEmpty(myEventInputForSearch.getNo())) {
            query.setParameter("EventCode", "%" + myEventInputForSearch.getNo() + "%");
        }
        if (startDateTime != null) {
            query.setParameter("StartDateTime", startDateTime);
        }
        if (endDateTime != null) {
            query.setParameter("EndDateTime", endDateTime);
        }
        query.setFirstResult((myEventInputForSearch.getPageIndex() - 1) * myEventInputForSearch.getPageSize());
        query.setMaxResults(myEventInputForSearch.getPageSize());
        return query.getResultList();
    }

    @Override
    public Integer getTotalCountOfEventListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception {
        String jpql = "select count(*) from Event where State != 160 ";
        if (!StringUtils.isEmpty(myEventInputForSearch.getNo())) {
            jpql += " and EventCode like :EventCode ";
        }
        if (myEventInputForSearch.getAreaId() != null) {
            jpql += " and AreaId = " + myEventInputForSearch.getAreaId();
        }
        if (myEventInputForSearch.getStationId() != null) {
            jpql += " and StationId = " + myEventInputForSearch.getStationId();
        }
        if (myEventInputForSearch.getState() != null) {
            jpql += " and State = " + myEventInputForSearch.getState();
        }
        if (myEventInputForSearch.getUserId() != null) {
            //jpql += " and ReportUserId = " + myEventInputForSearch.getUserId();
            jpql += " and ActionOwnerId = " + myEventInputForSearch.getUserId();
        }
        Date startDateTime = StringUtil.formatDate(myEventInputForSearch.getStartDateTime());
        if (startDateTime != null) {
            jpql += " and IssueDateTime >= :StartDateTime ";
        }
        Date endDateTime = StringUtil.formatDate(myEventInputForSearch.getEndDateTime());
        endDateTime = StringUtil.getEndDateTime(endDateTime);
        if (endDateTime != null) {
            jpql += " and IssueDateTime <= :EndDateTime";
        }
        Query query = entityManager.createQuery(jpql);
        if (!StringUtils.isEmpty(myEventInputForSearch.getNo())) {
            query.setParameter("EventCode", "%" + myEventInputForSearch.getNo() + "%");
        }
        if (startDateTime != null) {
            query.setParameter("StartDateTime", startDateTime);
        }
        if (endDateTime != null) {
            query.setParameter("EndDateTime", endDateTime);
        }
        return query.getResultList().size() == 0 ? 0 : Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public void updateEvent(Event event) {
        entityManager.merge(event);
    }

    @Override
    public void addEventEvaluationRecord(EventEvaluationRecord eventEvaluationRecord) {
        entityManager.persist(eventEvaluationRecord);
    }
}
