package com.smartflow.ievent.controller;

import com.smartflow.ievent.dto.EventSymptomIssueTimeDTO;
import com.smartflow.ievent.dto.*;
import com.smartflow.ievent.model.Station;
import com.smartflow.ievent.service.*;
import com.smartflow.ievent.util.ParseFieldToMapUtil;
import com.smartflow.ievent.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Log4j2
@RestController
@RequestMapping("/AnalysisReport")
public class AnalysisReportController extends BaseController {

    @Autowired
    AreaService areaService;
    @Autowired
    StationService stationService;
    @Autowired
    EventCategoryService eventCatetoryService;
    @Autowired
    EventSymptomService eventSymptomService;
    @Autowired
    EventService eventService;

    /**
     * 初始化区域、站点下拉框
     *
     * @return 下拉框
     */
    @RequestMapping(value = "/GetAreaAndStationInit", method = RequestMethod.GET)
    public Map<String, Object> getAreaAndStationInit() {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            //区域
            List<Map<String, Object>> AreaList = areaService.getAreaInit();
            //站点
            List<Map<String, Object>> StationList = stationService.getStationInit();
            map.put("AreaList", AreaList);
            map.put("StationList", StationList);
            json = this.setJson(200, "初始化成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "初始化失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 根据区域查询站点
     * @param areaId
     * @return
     */
    @RequestMapping(value = "/GetStationListInitByAreaId/{areaId}", method = RequestMethod.GET)
    public Map<String,Object> getStationListInitByAreaId(@PathVariable Integer areaId){
        Map<String,Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try{
            List<Map<String,Object>> stationList = stationService.getStationListByAreaId(areaId);
            map.put("StationList", stationList);
            json = this.setJson(200, "查询成功！",map);
        }catch(Exception e){
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "初始化站点失败："+e.getMessage(), 1);
        }
        return json;
    }


    /**
     * 获取症状分布柱状图
     * @param faultDistributionConditionDTO
     * @return
     */
    @RequestMapping(value="/GetSymptomDistributionBarChart",method = RequestMethod.POST)
    public Map<String,Object> getSymptomDistributionBarChart(@RequestBody FaultDistributionConditionInputDTO faultDistributionConditionDTO){
        Map<String,Object> json = new HashMap<>();
        try {
            SymptomDistributionBarChartDTO symptomDistributionBarChartDTO = new SymptomDistributionBarChartDTO();
            List<SymptomDistributionBarChartDTO.Legend> legendList = new ArrayList<>();
            List<Map<String, Object>> stationList = stationService.getStationListByAreaId(faultDistributionConditionDTO.getAreaId());
            List<String> stationNameList = new ArrayList<>();
            for (Map<String, Object> station : stationList) {
                stationNameList.add(station.get("label").toString());
            }
            List<Map<String, Object>> symptomList = eventSymptomService.getEventSymptomNameInit();
            for (Map<String, Object> symptom : symptomList) {
                SymptomDistributionBarChartDTO.Legend legend = null;
                List<EventDownDTO> eventList = eventService.getEventBySymptomCondition(faultDistributionConditionDTO, Integer.parseInt(symptom.get("key").toString()));
                List<Integer> downTimeList = new ArrayList<>();
                for (Map<String, Object> station : stationList) {
                    Integer downTime = 0;
                    for (EventDownDTO event : eventList) {
                        if(Integer.parseInt(station.get("key").toString()) == event.getStationId()){
                            downTime = event.getDownTime();
                            break;
                        }
                    }
                    downTimeList.add(downTime);
                }
                legend = new SymptomDistributionBarChartDTO.Legend(symptom.get("label").toString(), "bar", downTimeList);
                legendList.add(legend);
            }
            symptomDistributionBarChartDTO.setXAxis(stationNameList);
            symptomDistributionBarChartDTO.setLegend(legendList);
            json = this.setJson(200, "查询成功", symptomDistributionBarChartDTO);
        }catch(Exception e){
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "查询失败："+e.getMessage(), 1);
        }
        return json;
    }


    /**
     * 获取症状分布饼图
     * @param faultDistributionConditionDTO
     * @return
     */
    @RequestMapping(value = "/GetSymptomDistributionPieChart", method = RequestMethod.POST)
    public Map<String, Object> getSymptomDistributionPieChart(@RequestBody FaultDistributionConditionInputDTO faultDistributionConditionDTO) {
        Map<String, Object> json = new HashMap<>();
        //FaultDistributionChartDataDTO faultDistributionChartDataDTO = new FaultDistributionChartDataDTO();
        try {
            List<Map<String,Object>> symptomNameList = eventSymptomService.getEventSymptomNameList();
            List<Map<String, Object>> symptomChartData = eventSymptomService.getSymptomDistributionChartData(faultDistributionConditionDTO);
            List<Map<String,Object>> symptomMapList = new ArrayList<>();
            /*Integer totalTime = 0;
            for (Map<String,Object> chartData : symptomChartData) {
                totalTime += Integer.parseInt(chartData.get("value").toString());
            }*/
            ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
            symptomMapList = parseFieldToMapUtil.merge(symptomNameList, symptomChartData);
            json = this.setJson(200, "查询成功！", symptomMapList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "查询失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 获取类别分布饼图
     * @param faultDistributionConditionDTO
     * @return
     */
    @RequestMapping(value = "/GetCategoryDistributionPieChart", method = RequestMethod.POST)
    public Map<String, Object> getCategoryDistributionPieChart(@RequestBody FaultDistributionConditionInputDTO faultDistributionConditionDTO) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> categoryDownTimeChartData = new HashMap<>();
        try {
            List<Map<String, Object>> categoryList = eventCatetoryService.getCategoryDistributionChartData(faultDistributionConditionDTO);
            List<Map<String,Object>> categoryNameList = eventCatetoryService.getEventCategoryNameInit();
            List<Map<String,Object>> categoryMapList = new ArrayList<>();
            /*for (Map<String,Object> chartData : categoryList) {
                categoryMapList.add(chartData);
            }
            for (Map<String,Object> categoryName : categoryNameList) {
                for (Map<String,Object> category : categoryList) {
                    Map<String, Object> categoryMap = new HashMap<>();
                    if (!categoryName.get("label").toString().equals(category.get("name").toString())) {
                        categoryMap.put("name", categoryName.get("label").toString());
                        categoryMap.put("value", 0);
                        categoryMapList.add(categoryMap);
                    }
                    break;
                }
            }*/
            ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
            categoryMapList = parseFieldToMapUtil.merge(categoryNameList, categoryList);
            json = this.setJson(200, "查询成功！", categoryMapList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "查询失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /*@RequestMapping(value = "/GetFaultDistributionChartData", method = RequestMethod.POST)
    public Map<String, Object> getFaultDistributionChartData(@RequestBody FaultDistributionConditionInputDTO faultDistributionConditionDTO) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        FaultDistributionChartDataDTO faultDistributionChartDataDTO = new FaultDistributionChartDataDTO();
        Map<String, Object> categoryDownTimeChartData = new HashMap<>();
        try {
            List<Map<String, Object>> categoryList = eventCatetoryService.getCategoryDistributionChartData(faultDistributionConditionDTO);
            List<Map<String, Object>> symptomList = eventSymptomService.getSymptomDistributionChartData(faultDistributionConditionDTO);
            faultDistributionChartDataDTO.setCategoryDistributionPieChart(categoryList);
            faultDistributionChartDataDTO.setSymptomDistributionPieChart(symptomList);
            FacilityDownTimeSequenceBarChartOutputDTO facilityDownTimeSequenceBarChartOutputDTO = new FacilityDownTimeSequenceBarChartOutputDTO();
            List<String> categoryNameList = eventCatetoryService.getDownCategoryNameFromEvent(faultDistributionConditionDTO);
            List<String> stationNameList = eventService.getDownStationName(faultDistributionConditionDTO.getStationIdList());
            //将前端选的所有工站都显示，没有故障的给0占位
            //facilityDownTimeSequenceBarChartOutputDTO.setCategoryLegend(categoryNameList);
            List<EventDownDTO> eventList = eventService.getDownEvent(faultDistributionConditionDTO);
            //List<List<Integer>> downTimeLists = new ArrayList<>();
            if(!CollectionUtils.isEmpty(eventList)){
                for (String categoryName : categoryNameList) {
                    List<Integer> downTimeList = new ArrayList<>();
                    Map<String, List<Integer>> categoryDownTimeMap = new HashMap<>();
                    for(String stationName : stationNameList) {
                        Integer downTime = 0;
                        for (EventDownDTO event : eventList) {
                            if (stationName.equals(event.getStationName())){
                                if (categoryName.equals(event.getEventCategoryName())) {
                                    downTime = event.getDownTime();
                                    break;
                                }
                             }
                        }
                        downTimeList.add(downTime);
                    }
                    categoryDownTimeChartData.put(categoryName, downTimeList);
                }
            }
            //facilityDownTimeSequenceBarChartOutputDTO.setYAxisData(stationNameList);
            categoryDownTimeChartData.put("YAxis", stationNameList);
            //facilityDownTimeSequenceBarChartOutputDTO.setDownTime(downTimeLists);
            //facilityDownTimeSequenceBarChartOutputDTO.setCategoryDownTimeList(categoryDownTimeMapList);
            //facilityDownTimeSequenceBarChartOutputDTO.setCategoryDownTimeChartData(categoryDownTimeChartData);
            //faultDistributionChartDataDTO.setFacilityDownTimeSequenceBarChartDTO(facilityDownTimeSequenceBarChartOutputDTO);
            faultDistributionChartDataDTO.setFacilityDownTimeSequenceBarChartDTO(categoryDownTimeChartData);
            List<String> categoryLegendList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(categoryList)) {
                for (Map<String, Object> category : categoryList) {
                    String categoryName = (String) category.get("name");
                    categoryLegendList.add(categoryName);
                }
            }
            json = this.setJson(200, "查询成功！", faultDistributionChartDataDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "查询失败：" + e.getMessage(), 1);
        }
        return json;
    }
*/
    /**
     * 获取维护分析图表
     * @param maintenanceAnalysisConditionInputDTO
     * @return
     */
    @RequestMapping(value = "/GetMaintenanceAnalysisChartData", method = RequestMethod.POST)
    public Map<String,Object> getMaintenanceAnalysis(@RequestBody MaintenanceAnalysisConditionInputDTO maintenanceAnalysisConditionInputDTO) {
        Map<String, Object> json = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        try {
            MaintenanceAnalysisChartDataOutputDTO maintenanceAnalysisChartDataOutputDTO = new MaintenanceAnalysisChartDataOutputDTO();

            //List<String> categoryNameList = eventCatetoryService.getCategoryNameFromEvent(maintenanceAnalysisConditionInputDTO);
            Date startDateTime = StringUtil.formatDate(maintenanceAnalysisConditionInputDTO.getStartDateTime());//开始时间
            Date endDateTime = StringUtil.getEndDateTime(StringUtil.formatDate(maintenanceAnalysisConditionInputDTO.getEndDateTime()));
            if(endDateTime == null){
                endDateTime = new Date();
            }
            List<String> stationList = new ArrayList<>();
            List<Integer> MTBFList = new ArrayList<>();
            List<Integer> MTTRList = new ArrayList<>();
            List<Integer> MTTFList = new ArrayList<>();
            for (Integer stationId:maintenanceAnalysisConditionInputDTO.getStationIdList()) {
                Integer runningTime = 0;
                Integer faultTime = 0;
                Integer faultCount = 0;
                Station station = stationService.getStationById(stationId);
                String stationName = station == null ? null : station.getName();
                List<EventSymptomIssueTimeDTO> eventSymptomIssueTimeDTOList = eventService.getEventSymptomIssueTimeDTO(maintenanceAnalysisConditionInputDTO, stationId);
                if (!CollectionUtils.isEmpty(eventSymptomIssueTimeDTOList)) {
                    for (int i = 0; i < eventSymptomIssueTimeDTOList.size(); i++){
                        EventSymptomIssueTimeDTO eventSymptomIssueTimeDTO = eventSymptomIssueTimeDTOList.get(i);
                        Date lastFinishedDateTime = null;//上一次维修结束时间（开始使用时间）
                        Date finishedDateTime = null;//当前维修结束时间
                        Date nextIssueDateTime = null;//下一次故障发生时间
                        Date issueDateTime = eventSymptomIssueTimeDTO.getIssueDateTime();//发生时间
                        Date actionFinishedDateTime = eventSymptomIssueTimeDTO.getActionFinishedDateTime();//维修结束时间
                        if(startDateTime == null){
                            startDateTime = issueDateTime;
                        }
                        //判断上一次的类别和当前循环的类别是否相等
                        lastFinishedDateTime = startDateTime;
                        if(i != 0){
                            EventSymptomIssueTimeDTO lastEventSymptomIssueTimeDTO = eventSymptomIssueTimeDTOList.get(i - 1);
                            lastFinishedDateTime = lastEventSymptomIssueTimeDTO.getActionFinishedDateTime() == null ? endDateTime : lastEventSymptomIssueTimeDTO.getActionFinishedDateTime();
                        }
                        //开始时间-故障发生时间（运行时间）
                        runningTime += StringUtil.getStateApartMinites(lastFinishedDateTime, issueDateTime);

                        if(actionFinishedDateTime == null){
                            actionFinishedDateTime = endDateTime;
                        }
//                            if (eventSymptomIssueTimeDTO.getEventSymptomId() == 4) {//无状态
//                                runningTime += StringUtil.getStateApartMinites(issueDateTime, actionFinishedDateTime == null ? new Date() : nextIssueDateTime);
//                            }
                        if(eventSymptomIssueTimeDTO.getEventSymptomId() == 3) {//报警
                            faultTime += StringUtil.getStateApartMinites(issueDateTime, actionFinishedDateTime == null ? endDateTime : actionFinishedDateTime);
                            faultCount ++;//出现故障总次数
                        }

                        //判断下一次的类别和当前循环的类别是否相等
                        if(i == eventSymptomIssueTimeDTOList.size()-1){
                            nextIssueDateTime = endDateTime;
                            finishedDateTime = eventSymptomIssueTimeDTO.getActionFinishedDateTime() == null ? endDateTime : eventSymptomIssueTimeDTO.getActionFinishedDateTime();
                            runningTime += StringUtil.getStateApartMinites(finishedDateTime, nextIssueDateTime);
                        }

                    }
                }
                stationList.add(stationName);
                MTBFList.add((runningTime+faultTime)/(faultCount==0 ? 1 : faultCount));
                MTTRList.add(faultTime/(faultCount==0 ? 1 : faultCount));
                MTTFList.add(runningTime/(faultCount==0 ? 1 : faultCount));
            }
            maintenanceAnalysisChartDataOutputDTO.setXAxis(stationList);
            maintenanceAnalysisChartDataOutputDTO.setMTBF(MTBFList);
            maintenanceAnalysisChartDataOutputDTO.setMTTR(MTTRList);
            maintenanceAnalysisChartDataOutputDTO.setMTTF(MTTFList);
            /*
            List<String> dimensions = new ArrayList<>();
            dimensions.add("Category");
            dimensions.add("MTBF");
            dimensions.add("MTTR");
            dimensions.add("MTTF");
            maintenanceAnalysisChartDataOutputDTO.setDimensions(dimensions);
            List<Source> sourceList = new ArrayList<>();
            for (String categoryName : categoryNameList) {
                Integer runningTime = 0;
                Integer faultTime = 0;
                Integer faultCount = 0;
                Source source = new Source();
                source.setCategory(categoryName);
                List<EventSymptomIssueTimeDTO> eventSymptomIssueTimeDTOList = eventService.getEventSymptomIssueTimeDTO(maintenanceAnalysisConditionInputDTO);
                if (!CollectionUtils.isEmpty(eventSymptomIssueTimeDTOList)) {
                    for (int i = 0; i < eventSymptomIssueTimeDTOList.size(); i++){
                        EventSymptomIssueTimeDTO eventSymptomIssueTimeDTO = eventSymptomIssueTimeDTOList.get(i);

                        if (categoryName.equals(eventSymptomIssueTimeDTO.getCategoryName())) {
                            Date lastFinishedDateTime = null;//上一次维修结束时间（开始使用时间）
                            Date finshedDateTime = null;//当前维修结束时间
                            Date nextIssueDateTime = null;//下一次故障发生时间
                            Date issueDateTime = eventSymptomIssueTimeDTO.getIssueDateTime();//发生时间
                            Date actionFinishedDateTime = eventSymptomIssueTimeDTO.getActionFinishedDateTime();//维修结束时间
                            if(startDateTime == null){
                                startDateTime = issueDateTime;
                            }
                            //判断上一次的类别和当前循环的类别是否相等
                            lastFinishedDateTime = startDateTime;
                            if(i != 0){
                                EventSymptomIssueTimeDTO lastEventSymptomIssueTimeDTO = eventSymptomIssueTimeDTOList.get(i - 1);
                                if(categoryName.equals(lastEventSymptomIssueTimeDTO.getCategoryName())){
                                    lastFinishedDateTime = lastEventSymptomIssueTimeDTO.getActionFinishedDateTime() == null ? endDateTime : lastEventSymptomIssueTimeDTO.getActionFinishedDateTime();
                                }
                            }
                            //开始时间-故障发生时间（运行时间）
                            runningTime += StringUtil.getStateApartMinites(lastFinishedDateTime, issueDateTime);

                            if(actionFinishedDateTime == null){
                                actionFinishedDateTime = endDateTime;
                            }
//                            if (eventSymptomIssueTimeDTO.getEventSymptomId() == 4) {//无状态
//                                runningTime += StringUtil.getStateApartMinites(issueDateTime, actionFinishedDateTime == null ? new Date() : nextIssueDateTime);
//                            }
                            if(eventSymptomIssueTimeDTO.getEventSymptomId() == 3) {//报警
                                faultTime += StringUtil.getStateApartMinites(issueDateTime, actionFinishedDateTime == null ? endDateTime : actionFinishedDateTime);
                                faultCount ++;//出现故障总次数
                            }

                            //判断下一次的类别和当前循环的类别是否相等
                            nextIssueDateTime = endDateTime;
                            if(i < eventSymptomIssueTimeDTOList.size()-1) {
                                EventSymptomIssueTimeDTO nextEventSymptomIssueTimeDTO = eventSymptomIssueTimeDTOList.get(i + 1);
                                if(!categoryName.equals(nextEventSymptomIssueTimeDTO.getCategoryName())){
                                    finshedDateTime = eventSymptomIssueTimeDTO.getActionFinishedDateTime() == null ? endDateTime : eventSymptomIssueTimeDTO.getActionFinishedDateTime();
                                    runningTime += StringUtil.getStateApartMinites(finshedDateTime, nextIssueDateTime);
                                }
                            }
                        }
                    }
                }
                source.setMTBF((runningTime+faultTime)/(faultCount==0 ? 1 : faultCount));
                source.setMTTR(faultTime/(faultCount==0 ? 1 : faultCount));
                source.setMTTF(runningTime/(faultCount==0 ? 1 : faultCount));
                sourceList.add(source);
            }
            maintenanceAnalysisChartDataOutputDTO.setSource(sourceList);
            */
            json = this.setJson(200, "查询成功", maintenanceAnalysisChartDataOutputDTO);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "查询失败："+e.getMessage(),1);
        }
        return json;
    }
}
