package com.smartflow.ievent.controller;

import com.smartflow.ievent.dto.EventListOfMyTaskOutputDTO;
import com.smartflow.ievent.dto.MyEvent.MyEventInputForSearch;
import com.smartflow.ievent.service.*;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Api(value = "API - MyTask", description = "我的任务接口详情")
@RestController
@RequestMapping("/MyTask")
public class MyTaskController extends BaseController {

    @Autowired
    MyTaskService myTaskService;

    /**
     * 获取事件列表
     *
     * @param myEventInputForSearch
     * @return
     */
    @ApiOperation(value = "获取事件列表", httpMethod = "POST")
//    @ApiImplicitParam(dataType = "MyEventInputForSearch", required = true, paramType = "query")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/GetEventListByCondition", method = RequestMethod.POST)
    public Map<String, Object> getEventListByCondition(@Valid @RequestBody MyEventInputForSearch myEventInputForSearch) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            Integer rowCount = myTaskService.getTotalCountOfEventListByCondition(myEventInputForSearch);
            List<EventListOfMyTaskOutputDTO> eventList = myTaskService.getEventListByCondition(myEventInputForSearch);
            map.put("RowCount", rowCount);
            map.put("Tdto", eventList);
            json = this.setJson(200, "查询成功！", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            json = this.setJson(0, "查询失败：" + e.getMessage(), 1);
        }
        return json;
    }

}
