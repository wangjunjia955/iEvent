package com.smartflow.ievent.service;

import com.smartflow.ievent.dto.MaintenanceAnalysisConditionInputDTO;
import com.smartflow.ievent.dto.PageConditionInputDTO;
import com.smartflow.ievent.dto.EventCategoryListOutputDTO;
import com.smartflow.ievent.dto.FaultDistributionConditionInputDTO;
import com.smartflow.ievent.model.EventCatetory;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface EventCategoryService {
    /**
     * 获取事件类别下拉框
     *
     * @return 事件类别
     */
    public List<Map<String, Object>> getEventCategoryInit();

    /**
     * 获取事件类别名称
     *
     * @return 事件父类别
     */
    public List<Map<String, Object>> getEventCategoryNameInit();

    /**
     * 查询事件类别总条数
     *
     * @return 总条数
     */
    public Integer getTotalCountEventCategoryList();

    /**
     * 获取事件类别列表
     *
     * @param pageConditionDTO 分页参数
     * @return 事件类别列表
     */
    public List<EventCategoryListOutputDTO> getEventCategoryList(PageConditionInputDTO pageConditionDTO);

    /**
     * 根据父类别获取事件子类别
     *
     * @param catetoryId
     * @return 事件类别
     */
    public List<EventCatetory> getEventCategoryListByParentEventCategoryId(Integer catetoryId);

    /**
     * 判断CategoryCode是否重复
     *
     * @param categoryCode
     * @return
     */
    public boolean isExistCategoryCode(String categoryCode);

    /**
     * 添加事件类别
     *
     * @param eventCatetory 事件类别
     */
    public void addEventCategory(EventCatetory eventCatetory);

    /**
     * 根据事件类别id查询事件类别
     *
     * @param catetoryId 事件类别id
     * @return 事件类别
     */
    public EventCatetory getEventCategoryById(Integer catetoryId);

    /**
     * 修改事件类别
     *
     * @param eventCatetory
     */
    public void updateEventCategory(EventCatetory eventCatetory);

    /**
     * 删除事件类别
     *
     * @param eventCatetory
     */
    public void deleteEventCategory(EventCatetory eventCatetory);

    /**
     * 获取症状分布图
     *
     * @param faultDistributionConditionDTO
     * @return
     */
    public List<Map<String, Object>> getCategoryDistributionChartData(FaultDistributionConditionInputDTO faultDistributionConditionDTO) throws ParseException;

    /**
     * 查询事件类别
     * @param maintenanceAnalysisConditionInputDTO
     * @return
     */
    public List<String> getCategoryNameFromEvent(MaintenanceAnalysisConditionInputDTO maintenanceAnalysisConditionInputDTO) throws Exception;

}
