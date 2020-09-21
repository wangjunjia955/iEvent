package com.smartflow.ievent.dao;

import java.util.List;
import java.util.Map;

public interface FactoryDao {
    /**
     * 初始化工厂
     *
     * @return
     */
    public List<Map<String, Object>> getFactoryInit();
}
