package com.smartflow.ievent.dao;

import com.smartflow.ievent.model.Area;

import java.util.List;
import java.util.Map;

public interface AreaDao {
    /**
     * 获取区域下拉框
     *
     * @return
     */
    public List<Map<String, Object>> getAreaInit();

    /**
     * 根据区域id查询区域
     *
     * @param areaId
     * @return
     */
    public Area getAreaById(Integer areaId);

    /**
     * 根据工厂id查询区域
     *
     * @param factoryId
     * @return
     */
    public List<Map<String, Object>> getAreaListByFactoryId(Integer factoryId);

}
