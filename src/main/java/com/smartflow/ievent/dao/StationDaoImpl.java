package com.smartflow.ievent.dao;

import com.smartflow.ievent.model.Station;
import com.smartflow.ievent.util.ParseFieldToMapUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class StationDaoImpl implements StationDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Map<String, Object>> getStationInit() {
        String hql = "select new Station(Id, StationNumber, Name) from Station where State = 1";
        List<Station> stationList = entityManager.createQuery(hql).getResultList();
        List<Map<String, Object>> mapList = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (stationList != null && !stationList.isEmpty()) {
            for (Station station : stationList) {
                mapList = parseFieldToMapUtil.parseFiledToMap(station.getId(), station.getStationNumber(), station.getName());
            }
        }
        return mapList;
    }

    @Override
    public Station getStationById(Integer stationId) {
        return entityManager.find(Station.class, stationId);
    }

    @Override
    public List<Map<String, Object>> getStationListByAreaId(Integer areaId) {
        String hql = "select new Station(Id, StationNumber) from Station where State = 1 and Id in(select StationId from Location_Station where LocationId in(select Id from Location where AreaId = :AreaId))";
        Query query = entityManager.createQuery(hql);
        query.setParameter("AreaId", areaId);
        List<Station> stationList = query.getResultList();
        List<Map<String, Object>> mapList = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (stationList != null && !stationList.isEmpty()) {
            for (Station station : stationList) {
                mapList = parseFieldToMapUtil.parseFiledToMap(station.getId(), station.getStationNumber(), null);
            }
        }
        return mapList;
    }
}
