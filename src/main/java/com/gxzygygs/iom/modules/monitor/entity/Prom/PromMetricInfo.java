package com.gxzygygs.iom.modules.monitor.entity.Prom;

import lombok.Data;

@Data
public class PromMetricInfo {
    /**
     * prometheus指标名称
     */
    private String __name__;

    /**
     * prometheus实例名称
     */
    private String instance;

    /**
     * prometheus任务名称
     */
    private String job;

    /**
     * LINUX 硬盘实例设备名称
     */
    private String device;
    /**
     * WINDOWS 硬盘盘符设备名称
     */
    private String volume;

}
