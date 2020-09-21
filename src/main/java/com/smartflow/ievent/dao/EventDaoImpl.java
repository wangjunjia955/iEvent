package com.smartflow.ievent.dao;

import com.smartflow.ievent.dto.*;
import com.smartflow.ievent.dto.MyEvent.MyEventInputForSearch;
import com.smartflow.ievent.model.Event;
import com.smartflow.ievent.util.ParseFieldToMapUtil;
import com.smartflow.ievent.util.StringUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class EventDaoImpl implements EventDao {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Integer getTotalCountEventListByCondition(EventForConditionInputDTO eventForConditionInputDTO) throws ParseException {
        String hql = "select count(*) from Event where State != 160 ";
        String area = eventForConditionInputDTO.getArea();
        if (!StringUtils.isEmpty(area)) {
            hql += " and Area.AreaNumber like :AreaNumber ";
        }
        Date startDateTime = StringUtil.formatDate(eventForConditionInputDTO.getStartDateTime());
        if (startDateTime != null) {
            hql += " and IssueDateTime >= :StartDateTime ";
        }
        Date endDateTime = StringUtil.formatDate(eventForConditionInputDTO.getEndDateTime());
        endDateTime = StringUtil.getEndDateTime(endDateTime);
        if (endDateTime != null) {
            hql += " and IssueDateTime <= :EndDateTime";
        }
        if (eventForConditionInputDTO.isDisplayIsOver()) {
            hql += " and State = 140";//ActionFinished
        }
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if (!StringUtils.isEmpty(area)) {
            query.setParameter("AreaNumber", "%" + area + "%");
        }
        if (startDateTime != null) {
            query.setParameter("StartDateTime", startDateTime);
        }
        if (endDateTime != null) {
            query.setParameter("EndDateTime", endDateTime);
        }
        return query.uniqueResult() == null ? null : Integer.parseInt(query.uniqueResult().toString());
    }

    @Override
    public List<Event> getEventListByCondition(EventForConditionInputDTO eventForConditionInputDTO) throws ParseException {
        String hql = "from Event where State != 160 ";
        String area = eventForConditionInputDTO.getArea();
        if (!StringUtils.isEmpty(area)) {
            hql += " and Area.AreaNumber like :AreaNumber ";
        }
        Date startDateTime = StringUtil.formatDate(eventForConditionInputDTO.getStartDateTime());
        if (startDateTime != null) {
            hql += " and IssueDateTime >= :StartDateTime ";
        }
        Date endDateTime = StringUtil.formatDate(eventForConditionInputDTO.getEndDateTime());
        endDateTime = StringUtil.getEndDateTime(endDateTime);
        if (endDateTime != null) {
            hql += " and IssueDateTime <= :EndDateTime";
        }
        if (eventForConditionInputDTO.isDisplayIsOver()) {
            hql += " and State = 140";//ActionFinished
        }
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if (!StringUtils.isEmpty(area)) {
            query.setParameter("AreaNumber", "%" + area + "%");
        }
        if (startDateTime != null) {
            query.setParameter("StartDateTime", startDateTime);
        }
        if (endDateTime != null) {
            query.setParameter("EndDateTime", endDateTime);
        }
        query.setFirstResult((eventForConditionInputDTO.getPageIndex() - 1) * eventForConditionInputDTO.getPageSize());
        query.setMaxResults(eventForConditionInputDTO.getPageSize());
        return query.list();
    }

    @Override
    public void addEvent(Event event) {
        entityManager.persist(event);
    }

    @Override
    public List<Event> getEventListByEventIds(ExportEventInputDTO eventInputDTO) {
        String hql = "from Event where Id in :EventIdList";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameterList("EventIdList", eventInputDTO.getEventIdList());
        return query.list();
    }

    @Override
    public List<EventDownDTO> getEventBySymptomCondition(FaultDistributionConditionInputDTO faultDistributionConditionDTO, Integer symptomId) throws Exception {
        Date startDateTime = StringUtil.formatDate(faultDistributionConditionDTO.getStartDateTime());
        Date endDateTime = StringUtil.formatDate(faultDistributionConditionDTO.getEndDateTime());
        endDateTime = StringUtil.getEndDateTime(endDateTime);
        String sql = "select s.Id StationId,s.Name StationName,sum(datediff(MI,IssueDateTime,case when er.CreationDateTime is null then GETDATE() else er.CreationDateTime end)) DownTime " +
                "from ievent.Event e inner join core.Station s on e.StationId = s.Id " +
                "left join ievent.EventEvaluationRecord er on e.Id = er.EventId and er.State = 1 where EventSymptomId = " + symptomId;
       if(startDateTime != null){
            sql += " and IssueDateTime >= :StartDateTime ";
        }
        if(endDateTime != null) {
            sql += " and IssueDateTime <= :EndDateTime ";
        }
        if(faultDistributionConditionDTO.getAreaId() != null){
            sql += " and AreaId = " + faultDistributionConditionDTO.getAreaId();
        }
        sql += " group by s.Id,s.Name;";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        if(startDateTime != null){
            query.setParameter("StartDateTime", startDateTime);
        }
        if(endDateTime != null){
            query.setParameter("EndDateTime", endDateTime);
        }
        return query.setResultTransformer(Transformers.aliasToBean(EventDownDTO.class)).list();

    }

    /*@Override
    public List<EventDownDTO> getDownEvent(FaultDistributionConditionInputDTO faultDistributionConditionDTO) throws Exception{
//        select s.Name StationName,ec.Name,sum(datediff(MI,IssueDateTime,case when er.CreationDateTime is null then GETDATE() else er.CreationDateTime end)),EventCategoryId from ievent.Event e inner join core.Station s on e.StationId = s.Id inner join ievent.EventCatetory ec on e.EventCategoryId = ec.Id
//        left join ievent.EventEvaluationRecord er on e.Id = er.EventId and er.State = 1 where EventSymptomId = (select Id from ievent.EventSymptom where Name = '停机') group by EventCategoryId,s.Name,ec.Name;

//        select s.Id StationId,s.Name StationName,ec.Name,sum(datediff(MI,IssueDateTime,case when er.CreationDateTime is null then GETDATE() else er.CreationDateTime end)) DownTime,EventCategoryId from ievent.Event e inner join core.Station s on e.StationId = s.Id inner join ievent.EventCatetory ec on e.EventCategoryId = ec.Id
//        left join ievent.EventEvaluationRecord er on e.Id = er.EventId and er.State = 1 where EventSymptomId = (select Id from ievent.EventSymptom where Name = '停机') group by EventCategoryId,s.Id,s.Name,ec.Name
        Date startDateTime = StringUtil.formatDate(faultDistributionConditionDTO.getStartDateTime());
        Date endDateTime = StringUtil.formatDate(faultDistributionConditionDTO.getEndDateTime());
        endDateTime = StringUtil.getEndDateTime(endDateTime);
        String sql = "select s.Id StationId,s.Name StationName,ec.Name EventCategoryName,sum(datediff(MI,IssueDateTime,case when er.CreationDateTime is null then GETDATE() else er.CreationDateTime end)) DownTime,EventCategoryId from ievent.Event e inner join core.Station s on e.StationId = s.Id inner join ievent.EventCatetory ec on e.EventCategoryId = ec.Id " +
                "left join ievent.EventEvaluationRecord er on e.Id = er.EventId and er.State = 1 where EventSymptomId = (select Id from ievent.EventSymptom where Name = '停机') ";
//        String sql = "select s.Name StationName,sum(datediff(MI,IssueDateTime,case when er.CreationDateTime is null then GETDATE() else er.CreationDateTime end)) DownTime,EventCategoryId from ievent.Event e " +
//                "inner join core.Station s on e.StationId = s.Id left join ievent.EventEvaluationRecord er on e.Id = er.EventId and er.State = 1 " +
//                "where EventSymptomId = (select Id from ievent.EventSymptom where Name = '停机') ";
        if(startDateTime != null){
            sql += " and IssueDateTime >= :StartDateTime ";
        }
        if(endDateTime != null) {
            sql += " and IssueDateTime <= :EndDateTime ";
        }
        if(faultDistributionConditionDTO.getAreaId() != null){
            sql += " and AreaId = " + faultDistributionConditionDTO.getAreaId();
        }
        if(!CollectionUtils.isEmpty(faultDistributionConditionDTO.getStationIdList())){
            sql += " and StationId in :StationIdList";
        }
//        sql += " group by EventCategoryId,s.Name;";
        sql += " group by EventCategoryId,s.Id,s.Name,ec.Name";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        if(startDateTime != null){
            query.setParameter("StartDateTime", startDateTime);
        }
        if(endDateTime != null){
            query.setParameter("EndDateTime", endDateTime);
        }
        if(!CollectionUtils.isEmpty(faultDistributionConditionDTO.getStationIdList())){
            query.setParameterList("StationIdList", faultDistributionConditionDTO.getStationIdList());
        }
        return query.setResultTransformer(Transformers.aliasToBean(EventDownDTO.class)).list();
    }

    @Override
    public List<String> getDownStationName(List<Integer> stationIdList) {
        String hql = "select Name from Station where Id in(select Station.Id from Event where EventSymptom.Id = (select Id from EventSymptom where Name = '停机')";
        if(!CollectionUtils.isEmpty(stationIdList)){
            hql += "and StationId in :StationIdList";
        }
        hql += ")";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if(!CollectionUtils.isEmpty(stationIdList)){
            query.setParameterList("StationIdList", stationIdList);
        }
        return query.list();
    }*/

    @Override
    public List<EventSymptomIssueTimeDTO> getEventSymptomIssueTimeDTO(MaintenanceAnalysisConditionInputDTO maintenanceAnalysisConditionInputDTO, Integer stationId) throws Exception{
        String hql = "select e.Id as EventId,e.EventSymptom.Id as EventSymptomId,s.Name as StationName,e.IssueDateTime as IssueDateTime,e.ActionStartedDateTime as ActionStartedDateTime,e.ActionFinishedDateTime as ActionFinishedDateTime from Event e,Station s where e.Station.Id = s.Id and e.EventSymptom.Id = 3 ";
        if(maintenanceAnalysisConditionInputDTO.getAreaId() != null){
            hql += " and e.Area.Id = " + maintenanceAnalysisConditionInputDTO.getAreaId();
        }
        if(stationId != null){
            hql += " and e.Station.Id = " + stationId;
        }
        Date startDateTime = StringUtil.formatDate(maintenanceAnalysisConditionInputDTO.getStartDateTime());
        Date endDateTime = StringUtil.formatDate(maintenanceAnalysisConditionInputDTO.getEndDateTime());
        endDateTime = StringUtil.getEndDateTime(endDateTime);
        if(startDateTime != null){
            hql += " and e.IssueDateTime >= :StartDateTime ";
        }
        if(endDateTime != null) {
            hql += " and e.IssueDateTime <= :EndDateTime ";
        }
        hql += " order by e.IssueDateTime";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if(startDateTime != null){
            query.setParameter("StartDateTime", startDateTime);
        }
        if(endDateTime != null){
            query.setParameter("EndDateTime", endDateTime);
        }
        return query.setResultTransformer(Transformers.aliasToBean(EventSymptomIssueTimeDTO.class)).list();
    }

//    @Override
//    public Integer getTotalCountEventInitiatedListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception{
//        String hql = "select count(*) from Event where State != 160 ";
//        if (!StringUtils.isEmpty(myEventInputForSearch.getNo())) {
//            hql += " and EventCode like :EventCode ";
//        }
//        Date startDateTime = StringUtil.formatDate(myEventInputForSearch.getStartDateTime());
//        if (startDateTime != null) {
//            hql += " and IssueDateTime >= :StartDateTime ";
//        }
//        Date endDateTime = StringUtil.formatDate(myEventInputForSearch.getEndDateTime());
//        endDateTime = StringUtil.getEndDateTime(endDateTime);
//        if (endDateTime != null) {
//            hql += " and IssueDateTime <= :EndDateTime";
//        }
//        Integer areaId = myEventInputForSearch.getAreaId();
//        if (areaId != null) {
//            hql += " and Area.Id = "+areaId;
//        }
//        if (myEventInputForSearch.getStationId() != null) {
//            hql += " and StationId = " + myEventInputForSearch.getStationId();
//        }
//        if (myEventInputForSearch.getState() != null) {
//            hql += " and State = " + myEventInputForSearch.getState();
//        }
//        if(myEventInputForSearch.getUserId() != null){
//            hql += " and ReportUserId = " + myEventInputForSearch.getUserId();
//        }
//        Query query = sessionFactory.getCurrentSession().createQuery(hql);
//        if (!StringUtils.isEmpty(myEventInputForSearch.getNo())) {
//            query.setParameter("EventCode", "%" + myEventInputForSearch.getNo() + "%");
//        }
//        if (startDateTime != null) {
//            query.setParameter("StartDateTime", startDateTime);
//        }
//        if (endDateTime != null) {
//            query.setParameter("EndDateTime", endDateTime);
//        }
//        return query.uniqueResult() == null ? null : Integer.parseInt(query.uniqueResult().toString());
//    }
//
//    @Override
//    public List<Event> getEventInitiatedListByCondition(MyEventInputForSearch myEventInputForSearch) throws Exception{
//        String hql = "from Event where State != 160 ";
//        if (!StringUtils.isEmpty(myEventInputForSearch.getNo())) {
//            hql += " and EventCode like :EventCode ";
//        }
//        Date startDateTime = StringUtil.formatDate(myEventInputForSearch.getStartDateTime());
//        if (startDateTime != null) {
//            hql += " and IssueDateTime >= :StartDateTime ";
//        }
//        Date endDateTime = StringUtil.formatDate(myEventInputForSearch.getEndDateTime());
//        endDateTime = StringUtil.getEndDateTime(endDateTime);
//        if (endDateTime != null) {
//            hql += " and IssueDateTime <= :EndDateTime";
//        }
//        Integer areaId = myEventInputForSearch.getAreaId();
//        if (areaId != null) {
//            hql += " and Area.Id = " + areaId;
//        }
//        if (myEventInputForSearch.getStationId() != null) {
//            hql += " and StationId = " + myEventInputForSearch.getStationId();
//        }
//        if (myEventInputForSearch.getState() != null) {
//            hql += " and State = " + myEventInputForSearch.getState();
//        }
//        if(myEventInputForSearch.getUserId() != null){
//            hql += " and ReportUserId = " + myEventInputForSearch.getUserId();
//        }
//        Query query = sessionFactory.getCurrentSession().createQuery(hql);
//        if (!StringUtils.isEmpty(myEventInputForSearch.getNo())) {
//            query.setParameter("EventCode", "%" + myEventInputForSearch.getNo() + "%");
//        }
//        if (startDateTime != null) {
//            query.setParameter("StartDateTime", startDateTime);
//        }
//        if (endDateTime != null) {
//            query.setParameter("EndDateTime", endDateTime);
//        }
//        query.setFirstResult((myEventInputForSearch.getPageIndex() - 1) * myEventInputForSearch.getPageSize());
//        query.setMaxResults(myEventInputForSearch.getPageSize());
//        return query.list();
//    }


    @Override
    public Integer getTotalCountEventConcernedListByCondition(EventConcernedListInputDTO eventConcernedListInputDTO, boolean isMeFlag) {
        String hql = "select count(distinct e) from Event e,EventSubscription es where e.EventCategory.Id = es.EventCategory.Id and e.EventSymptom.Id = es.EventSymptom.Id and es.Role.Id in (select RoleId from Roles_Users where UserId = "+eventConcernedListInputDTO.getUserId()+")";
        //String hql = "select count(distinct e) from Event e,EventSubscription es where e.EventCategory.Id = es.EventCategory.Id and e.EventSymptom.Id = es.EventSymptom.Id and es.User.Id = "+eventConcernedListInputDTO.getUserId();
        if(isMeFlag){
            hql += " and es.User.Id = " + eventConcernedListInputDTO.getUserId();
        }
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.uniqueResult() == null ? null : Integer.parseInt(query.uniqueResult().toString());
    }

    @Override
    public List<Event> getEventConcernedListByCondition(EventConcernedListInputDTO eventConcernedListInputDTO, boolean isMeFlag) {
        String hql = "select distinct e from Event e,EventSubscription es where e.EventCategory.Id = es.EventCategory.Id and e.EventSymptom.Id = es.EventSymptom.Id and es.Role.Id in (select RoleId from Roles_Users where UserId = "+eventConcernedListInputDTO.getUserId()+")";
        if(isMeFlag){
            hql += " and es.User.Id = " + eventConcernedListInputDTO.getUserId();
        }
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setFirstResult((eventConcernedListInputDTO.getPageIndex() - 1) * eventConcernedListInputDTO.getPageSize());
        query.setMaxResults(eventConcernedListInputDTO.getPageSize());
        return query.list();
    }

    @Override
    public Integer getEventStateByEventId(Integer eventId) {
        javax.persistence.Query query = entityManager.createQuery("select State from Event where Id = " + eventId);
        return query.getResultList().size() == 0 ? 0 : Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public Event getEventById(Integer eventId) {
        return entityManager.find(Event.class, eventId);
    }

    @Override
    public void updateEventStateByEventId(Integer eventId, Integer signerId, Integer state) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "update Event set SignedOffDateTime = GETDATE(), Signer.Id = :signerId, State = :state where Id = " + eventId;
        javax.persistence.Query query = session.createQuery(hql);
        query.setParameter("signerId", signerId);
        query.setParameter("state", state);
        query.executeUpdate();
    }

    @Override
    public void updateEventActionOwnerByEventId(Integer userId, List<Integer> eventIdList) {
        String hql = "update Event set ActionOwner.Id = :userId, AssignedDateTime = GETDATE(), State = :state where Id in :eventIdList";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("userId", userId);
        query.setParameter("state", 120);
        query.setParameterList("eventIdList", eventIdList);
        query.executeUpdate();
    }

    @Override
    public List<Event> getEventListByEventIds(List<Integer> eventIdList) {
        String hql = "from Event where EventId in :eventIdList";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameterList("eventIdList", eventIdList);
        return query.list();
    }

    @Override
    public void updateEventAssigneeAndActionOwnerByEventId(AssignTaskInputDTO assignTaskInputDTO) {
        String hql = "update Event set Assignee.Id = :userId, AssignedDateTime = GETDATE(), ActionOwnerRole.Id = :roleId, ActionOwner.Id = :actionOwnerId, State = :state where Id = :eventId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("userId", assignTaskInputDTO.getUserId());
        query.setParameter("roleId", assignTaskInputDTO.getRoleId());
        query.setParameter("actionOwnerId", assignTaskInputDTO.getActionOwnerId());
        query.setParameter("state", 120);//已分配
        query.setParameter("eventId", assignTaskInputDTO.getEventId());
        query.executeUpdate();
    }

    @Override
    public List<Event> getEventListByEventSubscriptionRules() {
        String hql = "select e from Event e,EventSubscription es where e.EventSymptom.Id = es.EventSymptom.Id and e.EventCategory.Id = es.EventCategory.Id and e.Impact = es.Impact and e.Urgency = es.Urgency ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }
}
