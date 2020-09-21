package com.smartflow.ievent.service;

import com.smartflow.ievent.model.Station;

import java.util.List;
import java.util.Map;

public interface StationService {
    /**
     * 获取站点下拉框
     *
     * @return
     */
    public List<Map<String, Object>> getStationInit();

    /**
     * 根据工站id获取工站
     *
     * @param stationId
     * @return
     */
    public Station getStationById(Integer stationId);

    /**
     * 根据区域id查询工站列表
     * @param areaId
     * @return
     */
    public List<Map<String,Object>> getStationListByAreaId(Integer areaId);
}
