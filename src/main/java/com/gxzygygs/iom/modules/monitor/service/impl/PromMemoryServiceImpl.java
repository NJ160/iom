package com.gxzygygs.iom.modules.monitor.service.impl;

import com.gxzygygs.iom.modules.monitor.entity.Prom.PromResultInfo;
import com.gxzygygs.iom.modules.monitor.entity.Prom.PromSqlConstants.PromLinuxSqlConstants;
import com.gxzygygs.iom.modules.monitor.entity.Prom.PromSqlConstants.PromWindowsSqlConstants;
import com.gxzygygs.iom.modules.monitor.service.IPromBaseService;
import com.gxzygygs.iom.modules.monitor.service.IPromMemoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PromMemoryServiceImpl implements IPromMemoryService {

    @Autowired
    IPromBaseService promBaseService;

    @Override
    public List<PromResultInfo> getMemoryTotal(String instance, String time, String timeout) {
        return promBaseService.promQueryOSInfo(instance,time,timeout, PromLinuxSqlConstants.MEMORY_TOTAL, PromWindowsSqlConstants.MEMORY_TOTAL);
    }

    @Override
    public List<PromResultInfo> getMemoryUsagePercent(String instance, String time, String timeout) {
        return promBaseService.promQueryOSInfo(instance,time,timeout, PromLinuxSqlConstants.MEMORY_USAGE_PRECENT, PromWindowsSqlConstants.MEMORY_USAGE_PRECENT);
    }
}
