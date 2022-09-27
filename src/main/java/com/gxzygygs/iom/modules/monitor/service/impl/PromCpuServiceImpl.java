package com.gxzygygs.iom.modules.monitor.service.impl;

import com.gxzygygs.iom.modules.monitor.entity.Prom.PromResultInfo;
import com.gxzygygs.iom.modules.monitor.entity.Prom.PromSqlConstants.PromLinuxSqlConstants;
import com.gxzygygs.iom.modules.monitor.entity.Prom.PromSqlConstants.PromWindowsSqlConstants;
import com.gxzygygs.iom.modules.monitor.service.IPromBaseService;
import com.gxzygygs.iom.modules.monitor.service.IPromCpuService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PromCpuServiceImpl implements IPromCpuService {

    @Autowired
    IPromBaseService promBaseService;

    @Override
    public List<PromResultInfo> getCpuLogicalCores( String instance,  String time,  String timeout) {
        return promBaseService.promQueryOSInfo(instance,time,timeout,PromLinuxSqlConstants.CPU_LOGICAL_CORES,PromWindowsSqlConstants.CPU_LOGICAL_CORES);
    }

    @Override
    public List<PromResultInfo> getCpuIRateUsages(String instance,  String time,  String timeout) {
        return promBaseService.promQueryOSInfo(instance,time,timeout,PromLinuxSqlConstants.CPU_IRATE_USAGE_PRECENT,PromWindowsSqlConstants.CPU_IRATE_USAGE_PRECENT);
    }

    @Override
    public List<PromResultInfo> getCpuRateUsages(String instance, String start, String end, String step, String timeout) {
        return promBaseService.promQueryRangeOSInfo(instance,start,end,step,timeout,PromLinuxSqlConstants.CPU_RATE_USAGE_PRECENT,PromWindowsSqlConstants.CPU_RATE_USAGE_PRECENT);
    }
}
