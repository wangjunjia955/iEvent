package com.smartflow.ievent.dao;

import com.smartflow.ievent.model.Area;
import com.smartflow.ievent.util.ParseFieldToMapUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Repository;

import javax.persistence.Cacheable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AreaDaoImpl implements AreaDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Map<String, Object>> getAreaInit() {
        String jpql = "select new Area(Id, AreaNumber, Description) from Area where State = 1";
        List<Area> areaList = entityManager.createQuery(jpql).getResultList();
        List<Map<String, Object>> mapList = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (areaList != null && !areaList.isEmpty()) {
            for (Area area : areaList) {
                mapList = parseFieldToMapUtil.parseFiledToMap(area.getId(), area.getAreaNumber(), area.getDescription());
            }
        }
        return mapList;
    }

    @Override
    public Area getAreaById(Integer areaId) {
        return entityManager.find(Area.class, areaId);
    }

    @Override
    public List<Map<String, Object>> getAreaListByFactoryId(Integer factoryId) {
        String jpql = "select new Area(Id, AreaNumber, Description) from Area where State = 1 and FactoryId = " + factoryId;
        List<Area> areaList = entityManager.createQuery(jpql).getResultList();
        List<Map<String, Object>> mapList = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (areaList != null && !areaList.isEmpty()) {
            for (Area area : areaList) {
                mapList = parseFieldToMapUtil.parseFiledToMap(area.getId(), area.getAreaNumber(), area.getDescription());
            }
        }
        return mapList;
    }
}
