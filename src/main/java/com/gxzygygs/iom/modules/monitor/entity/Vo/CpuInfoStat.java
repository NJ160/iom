package com.gxzygygs.iom.modules.monitor.entity.Vo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class CpuInfoStat {
    // CPU        int32    `json:"cpu"`       Cpu基础信息
    @JSONField(name = "cpu")
    private int cpu;
    // VendorID   string   `json:"vendorId"`   厂家标识
    @JSONField(name = "vendorId")
    private String vendorId;
    // Family     string   `json:"family"`     Cpu系列
    @JSONField(name = "family")
    private String family;
    // Model      string   `json:"model"`         Cpu代号
    @JSONField(name = "model")
    private String model;
    // Stepping   int32    `json:"stepping"`   更新版本
    @JSONField(name = "stepping")
    private int stepping;
    // PhysicalID string   `json:"physicalId"`  物理核编号
    @JSONField(name = "physicalId")
    private String physicalId;
    // CoreID     string   `json:"coreId"`        物理Cpu标号
    @JSONField(name = "coreId")
    private String coreId;
    // Cores      int32    `json:"cores"`         核心数
    @JSONField(name = "cores")
    private int cores;
    // ModelName  string   `json:"modelName"`   Cpu全称
    @JSONField(name = "modelName")
    private String modelName;
    // Mhz        float64  `json:"mhz"`       主频
    @JSONField(name = "mhz")
    private float mhz;
    // CacheSize  int32    `json:"cacheSize"`   二级缓存
    @JSONField(name = "cacheSize")
    private int cacheSize;
    // Flags      []string `json:"flags"`     支持
    @JSONField(name = "flags")
    private String[] flags;
    // Microcode  string   `json:"microcode"`  微指令
    @JSONField(name = "microcode")
    private String microcode;
}
