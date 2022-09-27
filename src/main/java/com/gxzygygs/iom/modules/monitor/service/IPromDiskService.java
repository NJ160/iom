package com.gxzygygs.iom.modules.monitor.service;

import com.gxzygygs.iom.modules.monitor.entity.Prom.PromResultInfo;

import java.util.List;

public interface IPromDiskService {
    List<PromResultInfo> getDiskTotalUsage(String instance,  String time,  String timeout);

    List<PromResultInfo> getDiskPartitionTotal(String instance,  String time,  String timeout);

    List<PromResultInfo> getDiskPartitionFree(String instance,  String time,  String timeout);

    List<PromResultInfo> getDiskPartitionUsage(String instance,  String time,  String timeout);

    List<PromResultInfo> getDiskWriteIRate(String instance,  String time,  String timeout);

    List<PromResultInfo> getDiskReadIRate(String instance,  String time,  String timeout);

    List<PromResultInfo> getDiskWriteRate(String instance, String start, String end, String step, String timeout);

    List<PromResultInfo> getDiskReadRate(String instance, String start, String end, String step, String timeout);

    List<PromResultInfo> getDiskPartitionWriteRate(String instance, String start, String end, String step, String timeout);

    List<PromResultInfo> getDiskPartitionReadRate(String instance, String start, String end, String step, String timeout);
}
