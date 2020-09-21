package com.smartflow.ievent.service;

import com.smartflow.ievent.dto.FaultDistributionConditionInputDTO;
import com.smartflow.ievent.dto.FaultDistributionConditionInputDTO;
import com.smartflow.ievent.dto.PageConditionInputDTO;
import com.smartflow.ievent.model.EventSymptom;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface EventSymptomService {
    /**
     * 初始化症状下拉框
     *
     * @return
     */
    public List<Map<String, Object>> getEventSymptomInit();

    /**
     * 初始化症状名称下拉框
     *
     * @return
     */
    public List<Map<String, Object>> getEventSymptomNameInit();

    /**
     * 获取症状名称
     * @return
     */
    public List<Map<String, Object>> getEventSymptomNameList();

    /**
     * 获取症状分布图数据
     *
     * @param faultDistributionConditionDTO
     * @return
     */
    public List<Map<String, Object>> getSymptomDistributionChartData(FaultDistributionConditionInputDTO faultDistributionConditionDTO) throws ParseException;

    /**
     * 查询症状列表总条数
     *
     * @return
     */
    public Integer getTotalCountEventSymptomList();

    /**
     * 查询症状列表
     *
     * @param pageConditionDTO 分页条件
     * @return
     */
    public List<EventSymptom> getEventSymptomList(PageConditionInputDTO pageConditionDTO);

    /**
     * 判断SymptomCode是否重复
     *
     * @param symptomCode
     * @return
     */
    public boolean isExistSymptomCode(String symptomCode);

    /**
     * 添加症状
     *
     * @param eventSymptom
     */
    public void addEventSymptom(EventSymptom eventSymptom);

    /**
     * 根据症状id查询症状
     *
     * @param eventSymptomId
     * @return
     */
    public EventSymptom getEventSymptomById(Integer eventSymptomId);

    /**
     * 修改症状
     *
     * @param eventSymptom
     */
    public void updateEventSymptom(EventSymptom eventSymptom);

    /**
     * 删除症状
     *
     * @param eventSymptom
     */
    public void deleteEventSymptom(EventSymptom eventSymptom);
}
