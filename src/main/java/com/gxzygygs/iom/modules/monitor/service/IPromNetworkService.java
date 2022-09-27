package com.gxzygygs.iom.modules.monitor.service;

import com.gxzygygs.iom.modules.monitor.entity.Prom.PromResultInfo;

import java.util.List;

public interface IPromNetworkService {
    List<PromResultInfo> getNetworkSentIRate(String instance,  String time,  String timeout);

    List<PromResultInfo> getNetworkSentRate(String instance, String start, String end, String step, String timeout);

    List<PromResultInfo> getNetworkReceiveIRate(String instance,  String time,  String timeout);

    List<PromResultInfo> getNetworkReceiveRate(String instance, String start, String end, String step, String timeout);

}
