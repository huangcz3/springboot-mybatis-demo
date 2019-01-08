package com.neo.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neo.param.ContractDetailParam;
import com.neo.service.ITestService;
import com.neo.util.response.Result;
import com.neo.util.response.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author User
 * @date 2018-08-04 15:07
 * @desc
 */
@RestController
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private ITestService testService;

    @GetMapping("/test")
    public Result testQueryPage(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Map> resultList = testService.getLogs();
        PageInfo<Map> pageInfo = new PageInfo<Map>(resultList);
        return ResultUtil.success(pageInfo);
    }

    @GetMapping("/test2")
    public Result test2(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                        @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Map> resultList = testService.getArea();
        PageInfo<Map> pageInfo = new PageInfo<Map>(resultList);
        return ResultUtil.success(pageInfo);
    }


    @GetMapping("/download")
    public void downloadExcel(HttpServletResponse response ,@RequestParam String version, @RequestParam(required = false, value = "reportId") Long reportId){
        ContractDetailParam contractDetailParam = new ContractDetailParam();
        contractDetailParam.setDataType("CONTRACT_DETAIL_REPORT");
        contractDetailParam.setVersion(version);
        if (reportId != null){
            contractDetailParam.setReportId(reportId);
        }
        testService.downloadContractDetail(response,contractDetailParam);
    }


}
