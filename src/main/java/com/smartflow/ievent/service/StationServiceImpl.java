package com.smartflow.ievent.service;

import com.smartflow.ievent.dao.StationDao;
import com.smartflow.ievent.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StationServiceImpl implements StationService {
    @Autowired
    StationDao stationDao;

    @Override
    public List<Map<String, Object>> getStationInit() {
        return stationDao.getStationInit();
    }

    @Override
    public Station getStationById(Integer stationId) {
        return stationDao.getStationById(stationId);
    }

    @Override
    public List<Map<String, Object>> getStationListByAreaId(Integer areaId) {
        return stationDao.getStationListByAreaId(areaId);
    }
}
