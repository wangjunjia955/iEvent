package com.smartflow.ievent.dao;

import com.smartflow.ievent.dto.MaintenanceAnalysisConditionInputDTO;
import com.smartflow.ievent.dto.PageConditionInputDTO;
import com.smartflow.ievent.dto.FaultDistributionConditionInputDTO;
import com.smartflow.ievent.model.EventCatetory;
import com.smartflow.ievent.model.EventSymptom;
import com.smartflow.ievent.util.ParseFieldToMapUtil;
import com.smartflow.ievent.util.StringUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.util.*;

@Repository
public class EventCategoryDaoImpl implements EventCategoryDao {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Map<String, Object>> getEventCategoryInit() {
        String hql = "select new EventCatetory(Id, CategoryCode, Name) from EventCatetory where State = 1 and CategoryId is NULL";
        List<EventCatetory> eventCategoryList = entityManager.createQuery(hql).getResultList();
        List<Map<String, Object>> mapList = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (!CollectionUtils.isEmpty(eventCategoryList)) {
            for (EventCatetory eventCatetory : eventCategoryList) {
                mapList = parseFieldToMapUtil.parseFiledToValueLabel(eventCatetory.getId(), eventCatetory.getCategoryCode(), eventCatetory.getName());
            }
        }
        return mapList;
    }

    @Override
    public List<Map<String, Object>> getEventCategoryNameInit() {
        String hql = "select Name from EventCatetory where State = 1";// and CategoryId is NULL
        List<String> categoryNameList = entityManager.createQuery(hql).getResultList();
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(categoryNameList)) {
            for (String categoryName : categoryNameList) {
                Map<String,Object> map = new HashMap<>();
                map.put("name", categoryName);
                mapList.add(map);
            }
        }
        return mapList;
    }

    @Override
    public List<Map<String, Object>> getAllEventCategoryInit() {
        String hql = "select new EventCatetory(Id, CategoryCode, Name) from EventCatetory where State = 1 ";
        List<EventCatetory> eventCategoryList = entityManager.createQuery(hql).getResultList();
        List<Map<String, Object>> mapList = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (!CollectionUtils.isEmpty(eventCategoryList)) {
            for (EventCatetory eventCatetory : eventCategoryList) {
                mapList = parseFieldToMapUtil.parseFiledToValueLabel(eventCatetory.getId(), eventCatetory.getCategoryCode(), eventCatetory.getName());
            }
        }
        return mapList;
    }

    @Override
    public List<Map<String, Object>> getEventCategoryInitByParentEventCategoryId(Integer categoryId) {
        String hql = "select new EventCatetory(Id, CategoryCode, Name) from EventCatetory where State = 1 and CategoryId = " + categoryId;
        List<EventCatetory> eventCategoryList = entityManager.createQuery(hql).getResultList();
        List<Map<String, Object>> mapList = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (!CollectionUtils.isEmpty(eventCategoryList)) {
            for (EventCatetory eventCatetory : eventCategoryList) {
                mapList = parseFieldToMapUtil.parseFiledToValueLabel(eventCatetory.getId(), eventCatetory.getCategoryCode(), eventCatetory.getName());
            }
        }
        return mapList;
    }

    @Override
    public List<EventCatetory> getEventCategoryListByParentEventCategoryId(Integer categoryId) {
        String hql = "from EventCatetory where CategoryId = " + categoryId;
        return entityManager.createQuery(hql).getResultList();
    }

    @Override
    public Integer getTotalCountEventCategoryList() {
        Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from EventCatetory where State = 1");
        return query.uniqueResult() == null ? 0 : Integer.parseInt(query.uniqueResult().toString());
    }

    @Override
    public List<EventCatetory> getEventCategoryList(PageConditionInputDTO pageConditionDTO) {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventCatetory where State = 1");
        if (!StringUtils.isEmpty(pageConditionDTO.getPageIndex()) && !StringUtils.isEmpty(pageConditionDTO.getPageSize())) {
            query.setFirstResult((pageConditionDTO.getPageIndex() - 1) * pageConditionDTO.getPageSize());
            query.setMaxResults(pageConditionDTO.getPageSize());
        }
        return query.list();
    }

    @Override
    public boolean isExistCategoryCode(String categoryCode) {
        String hql = "select count(*) from EventCatetory where State != -1 and CategoryCode = :CategoryCode";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("CategoryCode", categoryCode);
        return query.uniqueResult() == null ? null : (Integer.parseInt(query.uniqueResult().toString()) <=
                1 ? false : true);
    }

    @Override
    public void addEventCategory(EventCatetory eventCatetory) {
        sessionFactory.getCurrentSession().save(eventCatetory);
    }

    @Override
    public EventCatetory getEventCategoryById(Integer categoryId) {
        return sessionFactory.getCurrentSession().get(EventCatetory.class, categoryId);
    }

    @Override
    public void updateEventCategory(EventCatetory eventCatetory) {
        sessionFactory.getCurrentSession().update(eventCatetory);
    }

    @Override
    public void deleteEventCategory(EventCatetory eventCatetory) {
        eventCatetory.setState(-1);
        sessionFactory.getCurrentSession().merge(eventCatetory);
    }

    @Override
    public List<Map<String, Object>> getCategoryDistributionChartData(FaultDistributionConditionInputDTO faultDistributionConditionDTO) throws ParseException {
        String sql = "select c.Name,sum(datediff(MI,IssueDateTime,case when er.CreationDateTime is null then GETDATE() else er.CreationDateTime end)) DownTime " +
                "from ievent.Event e inner join core.Station s on e.StationId = s.Id inner join ievent.EventCatetory c on e.EventCategoryId = c.Id\n" +
                "inner join ievent.EventSymptom es on e.EventSymptomId = es.Id left join ievent.EventEvaluationRecord er on e.Id = er.EventId and er.State = 1 " +
                "where  c.State != -1 and s.StationNumber = :stationName and es.Name = :symptomName ";
        Date startDateTime = StringUtil.formatDate(faultDistributionConditionDTO.getStartDateTime());
        Date endDateTime = StringUtil.formatDate(faultDistributionConditionDTO.getEndDateTime());
        endDateTime = StringUtil.getEndDateTime(endDateTime);
        if (startDateTime != null) {
            sql += " and IssueDateTime >= :StartDateTime ";
        }
        if (endDateTime != null) {
            sql += " and IssueDateTime <= :EndDateTime ";
        }
        if (faultDistributionConditionDTO.getAreaId() != null) {
            sql += " and AreaId = " + faultDistributionConditionDTO.getAreaId();
        }
        sql += " group by c.Name";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("stationName", faultDistributionConditionDTO.getStationNumber());
        query.setParameter("symptomName", faultDistributionConditionDTO.getSymptomName());
        if (startDateTime != null) {
            query.setParameter("StartDateTime", startDateTime);
        }
        if (endDateTime != null) {
            query.setParameter("EndDateTime", endDateTime);
        }
        List<Object[]> list = query.list();
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Object[] obj : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", obj[0]);
                map.put("value", obj[1]);
                mapList.add(map);
            }
        }
        return mapList;
    }

    @Override
    public List<String> getCategoryNameFromEvent(MaintenanceAnalysisConditionInputDTO maintenanceAnalysisConditionInputDTO) throws Exception {
        Date startDateTime = StringUtil.formatDate(maintenanceAnalysisConditionInputDTO.getStartDateTime());
        Date endDateTime = StringUtil.formatDate(maintenanceAnalysisConditionInputDTO.getEndDateTime());
        endDateTime = StringUtil.getEndDateTime(endDateTime);
        String hql = "select Name from EventCatetory where Id in(select EventCategory.Id from Event where 1=1 ";
        if(startDateTime != null){
            hql += " and IssueDateTime >= :StartDateTime ";
        }
        if(endDateTime != null) {
            hql += " and IssueDateTime <= :EndDateTime ";
        }
        if(maintenanceAnalysisConditionInputDTO.getAreaId() != null){
            hql += " and Area.Id = " + maintenanceAnalysisConditionInputDTO.getAreaId();
        }
//        if(maintenanceAnalysisConditionInputDTO.getStationId() != null){
//            hql += " and Station.Id = " + maintenanceAnalysisConditionInputDTO.getStationId();
//        }
        hql += ")";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if(startDateTime != null){
            query.setParameter("StartDateTime", startDateTime);
        }
        if(endDateTime != null){
            query.setParameter("EndDateTime", endDateTime);
        }
        return query.list();
    }

    @Override
    public List<Map<String, Object>> getChildrenCategoryList(Integer parentCategoryId) {
        List<Map<String,Object>> childrenCategoryList = getEventCategoryInitByParentEventCategoryId(parentCategoryId);
        for (Map<String,Object> children:childrenCategoryList) {
            children.put("children", getChildrenCategoryList(Integer.parseInt(children.get("value").toString())));
        }
        return childrenCategoryList;
    }
}
