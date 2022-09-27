package com.gxzygygs.iom.modules.monitor.entity.Prom.PromSqlConstants;

public class PromSqlConstants {
    /**
     * 当前监控主机状态
     * 1 运行 0 下线
     */
    public static String UP = "up{instance=~\".*.*\"}";

}
