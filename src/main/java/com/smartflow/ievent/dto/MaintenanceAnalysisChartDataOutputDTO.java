package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.List;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class MaintenanceAnalysisChartDataOutputDTO {
    private List<String> XAxis;//工站名称
    private List<Integer> MTBF;//平均失效间隔（指系统两次故障发生时间之间的时间段的平均值）
    private List<Integer> MTTR;//平均修复时间（指系统从发生故障到维修结束之间的时间段的平均值）
    private List<Integer> MTTF;//平均无故障时间（指系统无故障运行的平均时间，取所以从系统开始正常运行到发生故障之间的时间段的平均值）
}
