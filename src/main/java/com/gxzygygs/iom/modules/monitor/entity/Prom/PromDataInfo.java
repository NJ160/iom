package com.gxzygygs.iom.modules.monitor.entity.Prom;

import lombok.Data;

import java.util.List;

@Data
public class PromDataInfo {
    /**
     * prometheus结果类型
     * vector--瞬时向量
     * matrix--区间向量
     * scalar--标量
     * string--字符串
     */
    private String resultType;

    /**
     * prometheus指标属性和值
     */
    private List<PromResultInfo> result;

}
