package com.smartflow.ievent.controller;

import com.smartflow.ievent.dto.EventForAddInputDTO;
import com.smartflow.ievent.dto.EventForConditionInputDTO;
import com.smartflow.ievent.dto.EventForListOutputDTO;
import com.smartflow.ievent.dto.ExportEventInputDTO;
import com.smartflow.ievent.service.*;
import com.smartflow.ievent.util.ExportCsvUtil;
import com.smartflow.ievent.util.PropertyUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 事件概览控制层
 */
@Log4j2
@RestController
@RequestMapping("/EventFor")
public class EventForController extends BaseController {

    @Autowired
    EventService eventService;
    @Autowired
    PropertyUtil propertyUtil;


//    @RequestMapping(value = "/GetEventForListByConditionInit",method = RequestMethod.GET)
//    public Map<String,Object> getEventForListByConditionInit(){
//        Map<String,Object> json = new HashMap<>();
//        Map<String,Object> map = new HashMap<>();
//        try{
//            //区域
//            List<Map<String, Object>> AreaList = areaService.getAreaInit();
//            map.put("AreaList", AreaList);
//            json = this.setJson(200,"初始化成功！", map);
//        }catch(Exception e){
//            e.printStackTrace();
//            log.error(e);
//            json = this.setJson(0,"初始化失败："+e.getMessage(), 1);
//        }
//        return json;
//    }

    /**
     * 查询事件概览列表
     *
     * @param eventForConditionInputDTO
     * @return
     */
    @RequestMapping(value = "/GetEventForListByCondition", method = RequestMethod.POST)
    public Map<String, Object> getEventForListByCondition(@Valid @RequestBody EventForConditionInputDTO eventForConditionInputDTO) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            Integer totalCount = eventService.getTotalCountEventListByCondition(eventForConditionInputDTO);
            List<EventForListOutputDTO> eventList = eventService.getEventListByCondition(eventForConditionInputDTO);
            map.put("RowCount", totalCount);
            map.put("Tdto", eventList);
            json = this.setJson(200, "列表查询成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "列表查询失败：" + e.getMessage(), 1);
        }
        return json;
    }

    /**
     * 导出事件列表
     *
     * @param exportEventInputDTO
     * @return
     */
    @RequestMapping(value = "/ExportEvent", method = RequestMethod.POST)
    public Map<String, Object> exportEvent(@Valid @RequestBody ExportEventInputDTO exportEventInputDTO, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> json = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            List<String[]> eventDataList = eventService.getEventListByEventIds(exportEventInputDTO);
            //构造导出数据结构
            //设置表头
            String headers[] = {"No", "区域", "站点", "症状", "影响度", "紧急度", "标题", "报告人", "处理人", "发生时间", "已流逝时间", "报告时间", "工作分配时间", "工作开始时间", "工作结束时间", "签收确认时间", "状态"};
            //设置每列字段
            String columnFields[] = {"No", "AreaNumber", "StationNumber", "Symptom", "Impact", "Urgency", "Title", "ReportUser", "Assignee", "IssueDateTime", "PassedTime", "ReportDateTime", "AssignedDateTime", "ActionStartedDateTime", "ActionFinishedDateTime", "SignedOffDateTime", "State"};
            Date date = new Date();
            String fileName = "事件概览列表_";
            String webappPath = request.getServletContext().getRealPath("/temp");
            String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/temp/";

            OutputStream os = response.getOutputStream();
            String file_Name = ExportCsvUtil.exportCsv(request, response, eventDataList, headers, webappPath, fileName);
            if (file_Name != null) {
                json = this.setJson(200, "导出成功！", filePath + file_Name);
            } else {
                json = this.setJson(0, "导出失败", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "导出失败：" + e.getMessage(), 1);
        }
        return json;
    }

  /*  @RequestMapping(value = "/exportEvent", method = RequestMethod.POST)
    public Map<String,Object> exportEvent1(@Valid @RequestBody ExportEventInputDTO exportEventInputDTO, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> json = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try{
            List<String[]> eventDataList = eventService.getEventListByEventIds(exportEventInputDTO);
            //构造导出数据结构
            //设置表头
            String headers[] = {"No","区域","站点","症状","影响度","紧急度","标题","报告人","处理人","发生时间","已流逝时间","报告时间","工作分配时间","工作开始时间","工作结束时间","签收确认时间","状态"};
            //设置每列字段
            String columnFields[] = {"Id","No","AreaNumber","StationNumber","Symptom","Impact","Urgency","Title","ReportUser","Assignee","IssueDateTime","PassedTime","ReportDateTime","AssignedDateTime","ActionStartedDateTime","ActionFinishedDateTime","SignedOffDateTime","State"};
            Date date = new Date();
            String fileName = "事件概览列表_"+dateFormat.format(date);
            OutputStream os = response.getOutputStream();
            ExportCsvUtil.exportCsv(request, response, eventDataList, headers, null, fileName);
            json = this.setJson(200, "导出成功！", null);
            os.close();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "导出失败："+e.getMessage(), 1);
        }
        return json;
    }*/

}
