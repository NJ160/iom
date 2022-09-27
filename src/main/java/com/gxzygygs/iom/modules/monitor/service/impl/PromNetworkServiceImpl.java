package com.gxzygygs.iom.modules.monitor.service.impl;

import com.gxzygygs.iom.modules.monitor.entity.Prom.PromResultInfo;
import com.gxzygygs.iom.modules.monitor.entity.Prom.PromSqlConstants.PromLinuxSqlConstants;
import com.gxzygygs.iom.modules.monitor.entity.Prom.PromSqlConstants.PromWindowsSqlConstants;
import com.gxzygygs.iom.modules.monitor.service.IPromBaseService;
import com.gxzygygs.iom.modules.monitor.service.IPromNetworkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PromNetworkServiceImpl implements IPromNetworkService {

    @Autowired
    IPromBaseService promBaseService;

    @Override
    public List<PromResultInfo> getNetworkSentIRate(String instance, String time, String timeout) {
        return promBaseService.promQueryOSInfo(instance,time,timeout,PromLinuxSqlConstants.NETWORK_IRATE_SENT_RATE,PromWindowsSqlConstants.NETWORK_IRATE_SENT_RATE);
    }

    @Override
    public List<PromResultInfo> getNetworkSentRate(String instance, String start, String end, String step, String timeout) {
        return promBaseService.promQueryRangeOSInfo(instance,start,end,step,timeout,PromLinuxSqlConstants.NETWORK_RATE_SENT_RATE,PromWindowsSqlConstants.NETWORK_RATE_SENT_RATE);
    }

    @Override
    public List<PromResultInfo> getNetworkReceiveIRate(String instance, String time, String timeout) {
        return promBaseService.promQueryOSInfo(instance,time,timeout,PromLinuxSqlConstants.NETWORK_IRATE_RECEIVED_RATE,PromWindowsSqlConstants.NETWORK_IRATE_RECEIVED_RATE);
    }

    @Override
    public List<PromResultInfo> getNetworkReceiveRate(String instance, String start, String end, String step, String timeout) {
        return promBaseService.promQueryRangeOSInfo(instance,start,end,step,timeout,PromLinuxSqlConstants.NETWORK_RATE_RECEIVED_RATE,PromWindowsSqlConstants.NETWORK_RATE_RECEIVED_RATE);
    }
}
