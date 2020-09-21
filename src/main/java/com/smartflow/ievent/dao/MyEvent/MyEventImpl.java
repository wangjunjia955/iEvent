package com.smartflow.ievent.dao.MyEvent;

import com.smartflow.ievent.dto.MyEvent.MyEventInputForSearch;
import com.smartflow.ievent.model.Event;
import com.smartflow.ievent.model.EventEvaluationRecord;
import com.smartflow.ievent.util.StringUtil;
import org.hibernate.Session;
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
public class MyEventImpl implements MyEventDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Event> getMyEventListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception {
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
        if(myEventInputForSearch.getUserId() != null){
            //jpql += " and ActionOwnerId = "+myEventInputForSearch.getUserId();
            jpql += " and ReportUserId = " + myEventInputForSearch.getUserId();
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
    public Integer getTotalCountOfMyEventListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception {
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
        if(myEventInputForSearch.getUserId() != null){
            //jpql += " and ActionOwnerId = "+myEventInputForSearch.getUserId();
            jpql += " and ReportUserId = " + myEventInputForSearch.getUserId();
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
    public Event getEventById(Integer eventId) {
        return entityManager.find(Event.class, eventId);
    }

    @Override
    public void addEventEvaluationRecord(EventEvaluationRecord eventEvaluationRecord) {
        entityManager.persist(eventEvaluationRecord);
    }

    @Override
    public void updateEventEvaluationRecord(EventEvaluationRecord eventEvaluationRecord) {
        entityManager.merge(eventEvaluationRecord);
    }

    @Override
    public void updateEventStateByEventId(Integer eventId, Integer signerId, Integer state) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "update Event set SignedOffDateTime = GETDATE(), Signer.Id = :signerId, State = :state where Id = " + eventId;
        Query query = session.createQuery(hql);
        query.setParameter("signerId", signerId);
        query.setParameter("state", state);
        query.executeUpdate();
    }

    @Override
    public Integer getEventStateByEventId(Integer eventId) {
        Query query = entityManager.createQuery("select State from Event where Id = " + eventId);
        return query.getResultList().size() == 0 ? 0 : Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public void closeEvent(Integer eventId) {
        Query query = entityManager.createQuery("update Event set State = 160 where Id = "+eventId);
        query.executeUpdate();
    }
}
