package com.smartflow.ievent.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;


public class SymptomDistributionBarChartDTO {

    private List<String> XAxis;//站点名称
    private List<Legend> legend;
    @JsonProperty("XAxis")
    public List<String> getXAxis() {
        return XAxis;
    }

    public void setXAxis(List<String> XAxis) {
        this.XAxis = XAxis;
    }

    public List<Legend> getLegend() {
        return legend;
    }

    public void setLegend(List<Legend> legend) {
        this.legend = legend;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Legend{
        private String name;//症状名称
        private String type;//图表类型(bar柱状图)
        private List<Integer> data;//故障持续时间
    }
}
