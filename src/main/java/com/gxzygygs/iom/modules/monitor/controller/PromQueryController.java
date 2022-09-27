package com.gxzygygs.iom.modules.monitor.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxzygygs.iom.modules.monitor.entity.Prom.PromResultInfo;
import com.gxzygygs.iom.modules.monitor.entity.Vo.DeviceSummaryInfo;
import com.gxzygygs.iom.modules.monitor.service.*;
import com.gxzygygs.iom.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/monitor/query")
@Api(tags = "Prometheus查询类")
public class PromQueryController {
    @Autowired
    private IPromBaseService promBaseService;
    @Autowired
    private IPromCpuService promCpuService;
    @Autowired
    private IPromDiskService promDiskService;
    @Autowired
    private IPromMemoryService promMemoryService;
    @Autowired
    private IPromNetworkService promNetworkService;

    @ApiOperation("设备信息页 查询接口")
//    Oracle、DB2、达梦、RDS、MySQL(一些)、Redis
    @GetMapping()
    public Result query(){

        Map<String,Object> map = new HashMap<String,Object>();

        List<PromResultInfo> uptimeRs = promBaseService.getUptime(null,null,null);
        for (PromResultInfo info : uptimeRs) {
            DeviceSummaryInfo dsi = new DeviceSummaryInfo();
            dsi.setInstance(info.getMetric().getInstance());
            dsi.setUptime(new BigDecimal(info.getValue()[1]).setScale(1, BigDecimal.ROUND_HALF_DOWN).toPlainString());
            map.put(info.getMetric().getInstance(),dsi);
        }

        List<PromResultInfo> cpuCoresRs = promCpuService.getCpuLogicalCores(null,null,null);
        cpuCoresRs.stream().forEach(info->{
            DeviceSummaryInfo dsi = (DeviceSummaryInfo)map.get(info.getMetric().getInstance());
            if (dsi!=null){
                dsi.setCpuCores(info.getValue()[1]);
            }
        });

        List<PromResultInfo> cpuUsageRs = promCpuService.getCpuIRateUsages(null,null,null);

        cpuUsageRs.stream().forEach(info->{
            DeviceSummaryInfo dsi = (DeviceSummaryInfo)map.get(info.getMetric().getInstance());
            if (dsi!=null){
                dsi.setCpuUsage(new BigDecimal(info.getValue()[1]).setScale(1, BigDecimal.ROUND_HALF_DOWN).toPlainString());
            }
        });

        List<PromResultInfo> memoryTotalRs = promMemoryService.getMemoryTotal(null,null,null);

        memoryTotalRs.stream().forEach(info->{
            DeviceSummaryInfo dsi = (DeviceSummaryInfo)map.get(info.getMetric().getInstance());
            if (dsi!=null){
                dsi.setMemoryTotal(new BigDecimal(info.getValue()[1]).setScale(1, BigDecimal.ROUND_HALF_DOWN).toPlainString());
            }
        });

        List<PromResultInfo> memoryUsageRs = promMemoryService.getMemoryUsagePercent(null,null,null);

        memoryUsageRs.stream().forEach(info->{
            DeviceSummaryInfo dsi = (DeviceSummaryInfo)map.get(info.getMetric().getInstance());
            if (dsi!=null){
                dsi.setMemoryUsage(new BigDecimal(info.getValue()[1]).setScale(1, BigDecimal.ROUND_HALF_DOWN).toPlainString());
            }
        });

        List<PromResultInfo> diskUsageRs = promDiskService.getDiskTotalUsage(null,null,null);

        diskUsageRs.stream().forEach(info->{
            DeviceSummaryInfo dsi = (DeviceSummaryInfo)map.get(info.getMetric().getInstance());
            if (dsi!=null){
                dsi.setDiskUsage(new BigDecimal(info.getValue()[1]).setScale(1, BigDecimal.ROUND_HALF_DOWN).toPlainString());
            }
        });

        List<PromResultInfo> diskTotalReadRs = promDiskService.getDiskReadIRate(null,null,null);

        diskTotalReadRs.stream().forEach(info->{
            DeviceSummaryInfo dsi = (DeviceSummaryInfo)map.get(info.getMetric().getInstance());
            if (dsi!=null){
                dsi.setDiskTotalRead(new BigDecimal(info.getValue()[1]).setScale(1, BigDecimal.ROUND_HALF_DOWN).toPlainString());
            }
        });

        List<PromResultInfo> diskTotalWriteRs = promDiskService.getDiskWriteIRate(null,null,null);

        diskTotalWriteRs.stream().forEach(info->{
            DeviceSummaryInfo dsi = (DeviceSummaryInfo)map.get(info.getMetric().getInstance());
            if (dsi!=null){
                dsi.setDiskTotalWrite(new BigDecimal(info.getValue()[1]).setScale(1, BigDecimal.ROUND_HALF_DOWN).toPlainString());
            }
        });

        List<PromResultInfo> networkSentRs = promNetworkService.getNetworkSentIRate(null,null,null);

        networkSentRs.stream().forEach(info->{
            DeviceSummaryInfo dsi = (DeviceSummaryInfo)map.get(info.getMetric().getInstance());
            if (dsi!=null){
                dsi.setNetworkSent(new BigDecimal(info.getValue()[1]).setScale(1, BigDecimal.ROUND_HALF_DOWN).toPlainString());
            }
        });

        List<PromResultInfo> networkReceiveRs = promNetworkService.getNetworkReceiveIRate(null,null,null);

        networkReceiveRs.stream().forEach(info->{
            DeviceSummaryInfo dsi = (DeviceSummaryInfo)map.get(info.getMetric().getInstance());
            if (dsi!=null){
                dsi.setNetworkReceive(new BigDecimal(info.getValue()[1]).setScale(1, BigDecimal.ROUND_HALF_DOWN).toPlainString());
            }
        });

        return Result.ok(map);
    }
}
