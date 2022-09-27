package com.gxzygygs.iom.modules.monitor.entity.Prom;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PromConstants {

    /**
     * 数据库链接
     */
    public static String Prometheus_URL;

    @Value("${prometheus.url}")
    public void setPrometheus_URL(String s){
        PromConstants.Prometheus_URL=s;
    }

    /**
     * prometheus-查询参数
     */
    public static final String SUCCESS = "success";

    /**
     * prometheus-查询PromQL表达式某个时间结果
     * query=: PromQL表达式。
     * time=: 指定查询的时间，可选参数，默认情况下为当前时刻。
     * timeout=: 超时设置。可选参数，默认情况下使用-query,timeout的全局设置。
     */
    public static final String QUERY = "query";

    /**
     * prometheus-查询PromQL表达式在一段时间返回内的计算结果
     * query=: PromQL表达式。
     * start=: 起始时间。
     * end=: 结束时间。
     * step=: 查询步长。
     * timeout=: 超时设置。可选参数，默认情况下使用-query,timeout的全局设置。
     */
    public static final String QUERY_RANGE = "query_range";


    /**
     * prometheus-url参数
     */
//    public static final String QUERY = "query";
    public static final String TIME = "time";
    public static final String TIMEOUT = "timeout";
    public static final String START = "start";
    public static final String END = "end";
    public static final String STEP = "step";

    /**
     * linux
     * node_exporter
     */
    public static final String NODE_EXPORTER = "node_exporter";

    /**
     * windows
     * windows_exporter
     */
    public static final String WINDOWS_EXPORTER = "windows_exporter";
}
