package com.smartflow.ievent.dao;

import com.smartflow.ievent.model.Factory;
import com.smartflow.ievent.util.ParseFieldToMapUtil;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FactoryDaoImpl implements FactoryDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Map<String, Object>> getFactoryInit() {
        String jpql = "select new Factory(Id, FactoryCode, Name) from Factory";
        List<Factory> factoryList = entityManager.createQuery(jpql).getResultList();
        List<Map<String, Object>> mapList = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (!CollectionUtils.isEmpty(factoryList)) {
            for (Factory factory : factoryList) {
                mapList = parseFieldToMapUtil.parseFiledToMap(factory.getId(), factory.getFactoryCode(), factory.getName());
            }
        }
        return mapList;
    }
}
