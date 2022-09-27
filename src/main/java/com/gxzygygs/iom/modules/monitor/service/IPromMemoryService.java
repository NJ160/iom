package com.gxzygygs.iom.modules.monitor.service;

import com.gxzygygs.iom.modules.monitor.entity.Prom.PromResultInfo;

import java.util.List;

public interface IPromMemoryService {
    List<PromResultInfo> getMemoryTotal(String instance,  String time,  String timeout);

    List<PromResultInfo> getMemoryUsagePercent(String instance,  String time,  String timeout);
}
