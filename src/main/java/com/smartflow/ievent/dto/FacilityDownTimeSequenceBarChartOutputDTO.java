package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class FacilityDownTimeSequenceBarChartOutputDTO {

    private Map<String, List<Object>> CategoryDownTimeChartData;
   /* //图列：类别分类
    private List<String> CategoryLegend;
    //设备排名
    private List<String> yAxisData;
    //停机时间(秒)
    //private List<List<Integer>> DownTime;
    private List<Map<String, List<Integer>>> CategoryDownTimeList;*/
}
