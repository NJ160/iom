package com.gxzygygs.iom.modules.monitor.service.impl;

import com.gxzygygs.iom.modules.monitor.entity.Prom.PromResultInfo;
import com.gxzygygs.iom.modules.monitor.entity.Prom.PromSqlConstants.PromLinuxSqlConstants;
import com.gxzygygs.iom.modules.monitor.entity.Prom.PromSqlConstants.PromWindowsSqlConstants;
import com.gxzygygs.iom.modules.monitor.service.IPromBaseService;
import com.gxzygygs.iom.modules.monitor.service.IPromDiskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PromDiskServiceImpl implements IPromDiskService {

    @Autowired
    IPromBaseService promBaseService;

    @Override
    public List<PromResultInfo> getDiskTotalUsage(String instance, String time, String timeout) {
        return promBaseService.promQueryOSInfo(instance,time,timeout, PromLinuxSqlConstants.DISK_TOTAL_USAGE_PERCENT, PromWindowsSqlConstants.DISK_TOTAL_USAGE_PERCENT);
    }

    @Override
    public List<PromResultInfo> getDiskPartitionTotal(String instance, String time, String timeout) {
        return promBaseService.promQueryOSInfo(instance,time,timeout, PromLinuxSqlConstants.DISK_PARTITION_TOTAL, PromWindowsSqlConstants.DISK_PARTITION_TOTAL);
    }

    @Override
    public List<PromResultInfo> getDiskPartitionFree(String instance, String time, String timeout) {
        return promBaseService.promQueryOSInfo(instance,time,timeout, PromLinuxSqlConstants.DISK_PARTITION_FREE, PromWindowsSqlConstants.DISK_PARTITION_FREE);
    }

    @Override
    public List<PromResultInfo> getDiskPartitionUsage(String instance, String time, String timeout) {
        return promBaseService.promQueryOSInfo(instance,time,timeout, PromLinuxSqlConstants.DISK_PARTITION_PERCENT, PromWindowsSqlConstants.DISK_PARTITION_PERCENT);
    }

    @Override
    public List<PromResultInfo> getDiskWriteIRate(String instance, String time, String timeout) {
        return promBaseService.promQueryOSInfo(instance,time,timeout, PromLinuxSqlConstants.DISK_WRITE_IRATE, PromWindowsSqlConstants.DISK_WRITE_IRATE);
    }

    @Override
    public List<PromResultInfo> getDiskReadIRate(String instance, String time, String timeout) {
        return promBaseService.promQueryOSInfo(instance,time,timeout, PromLinuxSqlConstants.DISK_READ_IRATE, PromWindowsSqlConstants.DISK_READ_IRATE);
    }

    @Override
    public List<PromResultInfo> getDiskWriteRate(String instance, String start, String end, String step, String timeout) {
        return promBaseService.promQueryRangeOSInfo(instance,start,end,step,timeout,PromLinuxSqlConstants.DISK_WRITE_RATE,PromWindowsSqlConstants.DISK_WRITE_RATE);
    }

    @Override
    public List<PromResultInfo> getDiskReadRate(String instance, String start, String end, String step, String timeout) {
        return promBaseService.promQueryRangeOSInfo(instance,start,end,step,timeout,PromLinuxSqlConstants.DISK_READ_RATE,PromWindowsSqlConstants.DISK_READ_RATE);
    }

    //TODO
    //有问题，不知道为啥linux会多出一倍的盘
    @Override
    public List<PromResultInfo> getDiskPartitionWriteRate(String instance, String start, String end, String step, String timeout) {
        return promBaseService.promQueryRangeOSInfo(instance,start,end,step,timeout,PromLinuxSqlConstants.DISK_PARTITION_WRITE_RATE,PromWindowsSqlConstants.DISK_PARTITION_WRITE_RATE);
    }
    //TODO
    //有问题，不知道为啥linux会多出一倍的盘
    @Override
    public List<PromResultInfo> getDiskPartitionReadRate(String instance, String start, String end, String step, String timeout) {
        return promBaseService.promQueryRangeOSInfo(instance,start,end,step,timeout,PromLinuxSqlConstants.DISK_PARTITION_READ_RATE,PromWindowsSqlConstants.DISK_PARTITION_READ_RATE);
    }
}
