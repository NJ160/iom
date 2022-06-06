package com.gxzygygs.iom.modules.monitor.entity.Vo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProcMemInfo {
//    RSS    uint64 `json:"rss"`    // bytes
    @JSONField(name = "rss")
    private BigDecimal rss;
//    VMS    uint64 `json:"vms"`    // bytes
    @JSONField(name = "vms")
    private BigDecimal vms;
//    HWM    uint64 `json:"hwm"`    // bytes
    @JSONField(name = "hwm")
    private BigDecimal hwm;
//    Data   uint64 `json:"data"`   // bytes
    @JSONField(name = "data")
    private BigDecimal data;
//    Stack  uint64 `json:"stack"`  // bytes
    @JSONField(name = "stack")
    private BigDecimal stack;
//    Locked uint64 `json:"locked"` // bytes
    @JSONField(name = "locked")
    private BigDecimal locked;
//    Swap   uint64 `json:"swap"`   // bytes
    @JSONField(name = "swap")
    private BigDecimal swap;
}
