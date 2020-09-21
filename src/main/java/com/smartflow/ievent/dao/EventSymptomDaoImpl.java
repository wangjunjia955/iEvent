package com.smartflow.ievent.dao;

import com.smartflow.ievent.dto.FaultDistributionConditionInputDTO;
import com.smartflow.ievent.dto.PageConditionInputDTO;
import com.smartflow.ievent.model.EventSymptom;
import com.smartflow.ievent.util.ParseFieldToMapUtil;
import com.smartflow.ievent.util.StringUtil;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
@Repository
public class EventSymptomDaoImpl implements EventSymptomDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Map<String, Object>> getEventSymptomInit() {
        String hql = "select new EventSymptom(Id, SymptomCode, Name) from EventSymptom where State = 1";
        /*Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();*/
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(hql);
        List<EventSymptom> eventSymptomList = query.list();
        List<Map<String, Object>> mapList = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (!CollectionUtils.isEmpty(eventSymptomList)) {
            for (EventSymptom eventSymptom : eventSymptomList) {
                mapList = parseFieldToMapUtil.parseFiledToMap(eventSymptom.getId(), eventSymptom.getSymptomCode(), eventSymptom.getName());
            }
        }
        return mapList;
    }

    @Override
    public List<Map<String, Object>> getEventSymptomNameInit() {
        String hql = "select new EventSymptom(Id, Name) from EventSymptom where State = 1";
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(hql);
        List<EventSymptom> eventSymptomList = query.list();
        List<Map<String, Object>> mapList = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (!CollectionUtils.isEmpty(eventSymptomList)) {
            for (EventSymptom eventSymptom : eventSymptomList) {
                mapList = parseFieldToMapUtil.parseFiledToMap(eventSymptom.getId(), eventSymptom.getName(), null);
            }
        }
        return mapList;
    }

    @Override
    public List<Map<String, Object>> getEventSymptomNameList() {
        String hql = "select Name from EventSymptom where State = 1";
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(hql);
        List<String> symptomNameList = query.list();
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(symptomNameList)) {
            for (String symptomName : symptomNameList) {
                Map<String,Object> map = new HashMap<>();
                map.put("name", symptomName);
                mapList.add(map);
            }
        }
        return mapList;
    }

    @Override
    public List<Map<String, Object>> getSymptomDistributionChartData(FaultDistributionConditionInputDTO faultDistributionConditionDTO) throws ParseException {
        String sql = "select es.Name name,sum(datediff(MI,IssueDateTime,case when er.CreationDateTime is null then GETDATE() else er.CreationDateTime end)) value " +
                "from ievent.Event e inner join core.Station s on e.StationId = s.Id inner join ievent.EventSymptom es on e.EventSymptomId = es.Id " +
                "left join ievent.EventEvaluationRecord er on e.Id = er.EventId and er.State = 1 where s.StationNumber = :stationNumber and es.State != -1 ";
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
        sql += " group by es.Name";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("stationNumber", faultDistributionConditionDTO.getStationNumber());
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
    public List<EventSymptom> getEventSymptomList(PageConditionInputDTO pageConditionDTO) {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventSymptom where State = 1");
        if (!StringUtils.isEmpty(pageConditionDTO.getPageIndex()) && !StringUtils.isEmpty(pageConditionDTO.getPageSize())) {
            query.setFirstResult((pageConditionDTO.getPageIndex() - 1) * pageConditionDTO.getPageSize());
            query.setMaxResults(pageConditionDTO.getPageSize());
        }
        return query.list();
    }

    @Override
    public Integer getTotalCountEventSymptomList() {
        Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from EventSymptom where State = 1");
        return query.uniqueResult() == null ? 0 : Integer.parseInt(query.uniqueResult().toString());
    }

    @Override
    public boolean isExistSymptomCode(String symptomCode) {
        String hql = "select count(*) from EventSymptom where State != -1 and SymptomCode = :SymptomCode";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("SymptomCode", symptomCode);
        return query.uniqueResult() == null ? null : (Integer.parseInt(query.uniqueResult().toString()) <= 1 ? false : true);
    }

    @Override
    public void addEventSymptom(EventSymptom eventSymptom) {
        entityManager.persist(eventSymptom);
    }

    @Override
    public EventSymptom getEventSymptomById(Integer eventSymptomId) {
        return entityManager.find(EventSymptom.class, eventSymptomId);
    }

    @Override
    public void updateEventSymptom(EventSymptom eventSymptom) {
        entityManager.merge(eventSymptom);
    }

    @Override
    public void deleteEventSymptom(EventSymptom eventSymptom) {
        eventSymptom.setState(-1);
        entityManager.merge(eventSymptom);
    }
}
