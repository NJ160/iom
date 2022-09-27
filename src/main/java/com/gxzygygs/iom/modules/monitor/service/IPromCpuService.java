package com.gxzygygs.iom.modules.monitor.service;

import com.gxzygygs.iom.modules.monitor.entity.Prom.PromResultInfo;

import java.util.List;


public interface IPromCpuService {

    List<PromResultInfo> getCpuLogicalCores(String instance,  String time,  String timeout);

    List<PromResultInfo> getCpuIRateUsages(String instance,  String time,  String timeout);

    List<PromResultInfo> getCpuRateUsages(String instance, String start, String end, String step, String timeout);

}
