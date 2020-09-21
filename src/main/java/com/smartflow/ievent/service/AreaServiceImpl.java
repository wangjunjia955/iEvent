package com.smartflow.ievent.service;

import com.smartflow.ievent.dao.AreaDao;
import com.smartflow.ievent.dao.FactoryDao;
import com.smartflow.ievent.model.Area;
import com.smartflow.ievent.model.Factory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Cacheable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    @Autowired
    AreaDao areaDao;

    @Autowired
    FactoryDao factoryDao;

    @Override
    public List<Map<String, Object>> getAreaInit() {
        return areaDao.getAreaInit();
    }

    @Override
    public Area getAreaById(Integer areaId) {
        return areaDao.getAreaById(areaId);
    }


    @Override
    public List<Map<String, Object>> getFactoryAreaInit() {
        List<Map<String, Object>> factoryAreaList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> areaMap = new HashMap<>();
        List<Map<String, Object>> factoryList = factoryDao.getFactoryInit();
        if (!CollectionUtils.isEmpty(factoryList)) {
            for (Map<String, Object> factory : factoryList) {
                List<Map<String, Object>> areaList = areaDao.getAreaListByFactoryId(Integer.parseInt(factory.get("key").toString()));
                factory.put("children", areaList);
                factoryAreaList.add(factory);
            }
            map.put("key", 0);
            map.put("label", "所有");
            map.put("children", areaDao.getAreaInit());
            factoryAreaList.add(map);
        }
        return factoryAreaList;
    }
}
