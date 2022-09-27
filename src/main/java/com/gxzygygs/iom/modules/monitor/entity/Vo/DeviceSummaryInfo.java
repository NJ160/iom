package com.gxzygygs.iom.modules.monitor.entity.Vo;

import lombok.Data;

@Data
public class DeviceSummaryInfo {
    
    private String instance;

    private String uptime;

    private String cpuCores;

    private String cpuUsage;

    private String memoryTotal;

    private String memoryUsage;

    private String diskUsage;

    private String diskTotalRead;

    private String diskTotalWrite;

    private String networkSent;

    private String networkReceive;
}
