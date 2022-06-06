package com.gxzygygs.iom.modules.monitor.entity.Vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CpuBaseInfo {
    private int physicalCores;
    private int logicalCores;
    private List<CpuInfoStat> cpuInfoStats = new ArrayList<>();
}
