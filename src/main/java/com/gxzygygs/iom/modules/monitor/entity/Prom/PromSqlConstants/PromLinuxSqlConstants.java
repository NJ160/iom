package com.gxzygygs.iom.modules.monitor.entity.Prom.PromSqlConstants;


public class PromLinuxSqlConstants {
    /**
     * 开机时间
     * by instance
     * 单位 天
     */
    public static String UPTIME = "(time() - node_boot_time_seconds{instance=~\".*.*\"})/86400";

    /*************************************************DISK磁盘**********************************************************/

    /**
     * DISK 总使用率
     * by instance
     * 单位 percent
     */
    public static String DISK_TOTAL_USAGE_PERCENT = "1-(sum(node_filesystem_free_bytes {instance=~\".*.*\",fstype=~\"ext.?|xfs\"})by (instance)/sum((node_filesystem_size_bytes{instance=~\".*.*\",fstype=~\"ext.?|xfs\"}))by (instance))";

    /**
     * DISK WRITE速率
     * 1分钟内平均速率  irate用于取最后两个数据点求平均值 适用于短时间求精确值
     * by instance
     * 单位 byte
     */
    public static String DISK_WRITE_IRATE = "sum(irate(node_disk_written_bytes_total{instance=~\".*.*\",device !~\"dm.*\"}[1m])) by (instance)";

    /**
     * DISK WRITE速率
     * by instance
     * 单位 byte
     */
    public static String DISK_WRITE_RATE = "sum(rate(node_disk_written_bytes_total{instance=~\".*.*\",device !~\"dm.*\"}[1m])) by (instance)";

    /**
     * DISK READ速率
     * 1分钟内平均速率  irate用于取最后两个数据点求平均值 适用于短时间求精确值
     * by instance
     * 单位 byte
     */
    public static String DISK_READ_IRATE = "sum(irate(node_disk_read_bytes_total{instance=~\".*.*\",device !~\"dm.*\"}[1m])) by (instance)";

    /**
     * DISK READ速率
     * by instance
     * 单位 byte
     */
    public static String DISK_READ_RATE = "sum(rate(node_disk_read_bytes_total{instance=~\".*.*\",device !~\"dm.*\"}[1m])) by (instance)";

    /**
     * 磁盘分区写速率
     * by instance
     * 单位 byte
     */
    public static String DISK_PARTITION_WRITE_RATE="rate(node_disk_written_bytes_total{instance=~\".*.*\"}[1m])";

    /**
     * 磁盘分区读取速率
     * by instance
     * 单位 byte
     */
    public static String DISK_PARTITION_READ_RATE="rate(node_disk_read_bytes_total{instance=~\".*.*\"}[1m])";

    /**
     * 磁盘分区总空间
     * by instance
     * 单位 byte
     */
    public static String DISK_PARTITION_TOTAL="node_filesystem_size_bytes{instance=~\".*.*\",fstype=~\"ext.*|xfs|nfs\",mountpoint !~\".*pod.*\"}-0";

    /**
     * 磁盘分区剩余空间
     * by instance
     * 单位 byte
     */
    public static String DISK_PARTITION_FREE="node_filesystem_free_bytes {instance=~\".*.*\",fstype=~\"ext.*|xfs|nfs\",mountpoint !~\".*pod.*\"}-0";

    /**
     * 磁盘分区使用百分比
     * by instance
     * 单位 percent
     */
    public static String DISK_PARTITION_PERCENT="1-node_filesystem_free_bytes{instance=~\".*.*\",fstype=~\"ext.*|xfs|nfs\",mountpoint !~\".*pod.*\"}/node_filesystem_size_bytes{instance=~\".*.*\",fstype=~\"ext.*|xfs|nfs\",mountpoint !~\".*pod.*\"}";

    /*************************************************CPU处理器*********************************************************/


    /**
     CPU逻辑?核数
     by instance
     */
    public static String CPU_LOGICAL_CORES = "count(node_cpu_seconds_total{mode=~'system',instance=~\".*.*\"}) by (instance)";

    /**
     * CPU使用率/
     * 1分钟内平均速率  irate用于取最后两个数据点求平均值 适用于短时间求精确值
     * by instance
     */
    public static String CPU_IRATE_USAGE_PRECENT = "100 - (avg by (instance) (irate(node_cpu_seconds_total{mode=\"idle\",instance=~\".*.*\"}[1m])) * 100)";

    /**
     * CPU使用率/
     * 1分钟内平均速率  rate取时间范围内所有数据点的平均值，适用于求长时间画图，图像会更加平滑
     * by instance
     */
    public static String CPU_RATE_USAGE_PRECENT = "(1 - avg(rate(node_cpu_seconds_total{mode=\"idle\",instance=~\".*.*\"}[1m])) by (instance)) * 100";

    /*************************************************MEMORY内存********************************************************/

    /**
     总内存
     by instance
     单位 byte
     */
    public static String MEMORY_TOTAL = "node_memory_MemTotal_bytes{instance=~\".*.*\"}";


    /**
     MEMORY使用率
     by instance
     */
    public static String MEMORY_USAGE_PRECENT = "(1 - (node_memory_MemAvailable_bytes{instance=~\".*.*\"} / (node_memory_MemTotal_bytes{instance=~\".*.*\"})))";

    /*************************************************NETWORK网络*******************************************************/

    /**
     * NETWORK SENT速率
     * 1分钟内平均速率  irate用于取最后两个数据点求平均值 适用于短时间求精确值
     * by instance
     * 单位 byte
     */
    public static String NETWORK_IRATE_SENT_RATE = "max(irate(node_network_transmit_bytes_total{instance=~\".*.*\"}[1m])) by (instance)";

    /**
     * NETWORK SENT速率
     * by instance
     * 单位 byte
     */
    public static String NETWORK_RATE_SENT_RATE = "max(rate(node_network_transmit_bytes_total{instance=~\".*.*\"}[1m])) by (instance)";

    /**
     * NETWORK RECEIVED速率
     * 1分钟内平均速率  irate用于取最后两个数据点求平均值 适用于短时间求精确值
     * by instance
     * 单位 byte
     */
    public static String NETWORK_IRATE_RECEIVED_RATE = "max(irate(node_network_receive_bytes_total{instance=~\".*.*\"}[1m])) by (instance)";

    /**
     * NETWORK RECEIVED速率
     * by instance
     * 单位 byte
     */
    public static String NETWORK_RATE_RECEIVED_RATE = "max(rate(node_network_receive_bytes_total{instance=~\".*.*\"}[1m])) by (instance)";


}
