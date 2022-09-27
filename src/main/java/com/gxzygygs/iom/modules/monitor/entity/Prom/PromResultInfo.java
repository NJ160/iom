package com.gxzygygs.iom.modules.monitor.entity.Prom;

import lombok.Data;

import java.util.List;

@Data
public class PromResultInfo {
    /**
     * prometheus指标属性
     */
    private PromMetricInfo metric;

    /**
     * prometheus  query返回单个value时
     */
    private String[] value;

    /**
     * prometheus  query_range返回value时
     */
    private List<String[]> values;
}
