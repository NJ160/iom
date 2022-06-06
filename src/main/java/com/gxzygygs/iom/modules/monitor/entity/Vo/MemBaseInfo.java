package com.gxzygygs.iom.modules.monitor.entity.Vo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MemBaseInfo {
    // Total amount of RAM on this system
    //    Total uint64 `json:"total"`
    @JSONField(name = "total")
    public BigDecimal total;
    // RAM available for programs to allocate
    //    Available uint64 `json:"available"`
    @JSONField(name = "available")
    public BigDecimal available;
    // RAM used by programs
    //    Used uint64 `json:"used"`
    @JSONField(name = "used")
    public BigDecimal used;
    // Percentage of RAM used by programs
    //    UsedPercent float64 `json:"usedPercent"`
    @JSONField(name = "usedPercent")
    public BigDecimal usedPercent;
}
