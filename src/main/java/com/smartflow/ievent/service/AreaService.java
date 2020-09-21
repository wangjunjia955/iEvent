package com.smartflow.ievent.service;

import com.smartflow.ievent.model.Area;

import java.util.List;
import java.util.Map;

public interface AreaService {
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
     * 获取工厂区域下拉框
     *
     * @return
     */
    public List<Map<String, Object>> getFactoryAreaInit();
}
