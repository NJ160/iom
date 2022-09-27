package com.gxzygygs.iom.modules.monitor.service;

import com.gxzygygs.iom.modules.monitor.entity.Prom.PromResultInfo;

import java.util.List;

public interface IPromBaseService {
    List<PromResultInfo> getUptime (String instance, String time, String timeout);

    List<PromResultInfo> getTargetStatus (String instance);

    List<PromResultInfo> promQueryOSInfo(String instance,  String time,  String timeout,String linux,String windows);

    List<PromResultInfo> promQueryRangeOSInfo(String instance, String start, String end, String step, String timeout,String linux,String windows);
}
