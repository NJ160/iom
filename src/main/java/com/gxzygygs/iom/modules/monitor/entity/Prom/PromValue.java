package com.gxzygygs.iom.modules.monitor.entity.Prom;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PromValue {
    private BigDecimal timeStamp;
    private String value;
}
