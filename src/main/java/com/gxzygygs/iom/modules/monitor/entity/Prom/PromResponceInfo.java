package com.gxzygygs.iom.modules.monitor.entity.Prom;

import lombok.Data;

@Data
public class PromResponceInfo {
    //status": "success" | "error"
    private String status;

    private PromDataInfo data;

    // Only set if status is "error". The data field may still hold
    // additional data.
    private String errorType;

    private String error;
}
