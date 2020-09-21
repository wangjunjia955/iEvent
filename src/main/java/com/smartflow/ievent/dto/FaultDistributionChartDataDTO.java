package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 故障分布图数据实体
 */
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class FaultDistributionChartDataDTO {
    //症状分布图
    private List<Map<String, Object>> SymptomDistributionPieChart;
    //类别分布图
    private List<Map<String, Object>> CategoryDistributionPieChart;
    //设备停机排序
    //private FacilityDownTimeSequenceBarChartOutputDTO FacilityDownTimeSequenceBarChartDTO;
    private Map<String, Object> FacilityDownTimeSequenceBarChartDTO;
}
