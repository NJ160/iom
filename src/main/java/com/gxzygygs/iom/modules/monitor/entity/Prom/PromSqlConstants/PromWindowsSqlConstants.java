package com.gxzygygs.iom.modules.monitor.entity.Prom.PromSqlConstants;

public class PromWindowsSqlConstants {
    /**
     开机时间
     by instance
     单位 天
     */
    public static String UPTIME = "(time() - windows_system_system_up_time{instance=~\".*.*\"})/86400";


    /*************************************************DISK磁盘**********************************************************/

    /**
     * DISK 总使用率
     * by instance
     * 单位 percent
     */
    public static String DISK_TOTAL_USAGE_PERCENT = "(sum(windows_logical_disk_size_bytes{volume!~\"Harddisk.*\", instance=~\".*.*\"}) by (instance) - sum(windows_logical_disk_free_bytes{volume!~\"Harddisk.*\", instance=~\".*.*\"}) by (instance)) / sum(windows_logical_disk_size_bytes{volume!~\"Harddisk.*\", instance=~\".*.*\"}) by (instance) ";

    /**
     * DISK WRITE速率
     * 1分钟内平均速率  irate用于取最后两个数据点求平均值 适用于短时间求精确值
     * by instance
     * 单位 byte
     */
    public static String DISK_WRITE_IRATE = "sum(irate(windows_logical_disk_write_bytes_total{instance=~\".*.*\"}[1m]))by (instance)";

    /**
     * DISK WRITE速率
     * by instance
     * 单位 byte
     */
    public static String DISK_WRITE_RATE = "sum(rate(windows_logical_disk_write_bytes_total{instance=~\".*.*\"}[1m]))by (instance)";

    /**
     * DISK READ速率
     * 1分钟内平均速率  irate用于取最后两个数据点求平均值 适用于短时间求精确值
     * by instance
     * 单位 byte
     */
    public static String DISK_READ_IRATE = "sum(irate(windows_logical_disk_read_bytes_total{instance=~\".*.*\"}[1m]))by (instance)";

    /**
     * DISK READ速率
     * by instance
     * 单位 byte
     */
    public static String DISK_READ_RATE = "sum(rate(windows_logical_disk_read_bytes_total{instance=~\".*.*\"}[1m]))by (instance)";

    /**
     * 磁盘分区写速率
     * by instance
     * 单位 byte
     */
    public static String DISK_PARTITION_WRITE_RATE="rate(windows_logical_disk_write_bytes_total{instance=~\".*.*\", volume !~\"HarddiskVolume.+\"}[1m])";

    /**
     * 磁盘分区读取速率
     * by instance
     * 单位 byte
     */
    public static String DISK_PARTITION_READ_RATE="rate(windows_logical_disk_read_bytes_total{instance=~\".*.*\", volume !~\"HarddiskVolume.+\"}[1m])";

    /**
     * 磁盘分区总空间
     * by instance
     * 单位 byte
     */
    public static String DISK_PARTITION_TOTAL="windows_logical_disk_size_bytes{instance=~\".*.*\", volume !~\"HarddiskVolume.+\"}";

    /**
     * 磁盘分区剩余空间
     * by instance
     * 单位 byte
     */
    public static String DISK_PARTITION_FREE="windows_logical_disk_free_bytes{instance=~\".*.*\", volume !~\"HarddiskVolume.+\"}";

    /**
     * 磁盘分区使用百分比
     * by instance
     * 单位 percent
     */
    public static String DISK_PARTITION_PERCENT="1 - (windows_logical_disk_free_bytes{instance=~\".*.*\", volume !~\"HarddiskVolume.+\"} / windows_logical_disk_size_bytes{instance=~\".*.*\", volume !~\"HarddiskVolume.+\"})";

    /*************************************************CPU处理器*********************************************************/

    /**
     * CPU逻辑核数
     * by instance
     */
    public static String CPU_LOGICAL_CORES = "windows_cs_logical_processors{instance=~\".*.*\"}";

    /**
     CPU使用率/
     1分钟内平均速率  irate用于取最后两个数据点求平均值 适用于短时间求精确值
     by instance
     */
    public static String CPU_IRATE_USAGE_PRECENT = "(100 - (avg by (instance) (irate(windows_cpu_time_total{mode=\"idle\",instance=~\".*.*\"}[1m])) * 100))/100";

    /**
     CPU使用率/
     1分钟内平均速率  rate取时间范围内所有数据点的平均值，适用于求长时间画图，图像会更加平滑
     by instance
     */
    public static String CPU_RATE_USAGE_PRECENT = "1 - avg(rate(windows_cpu_time_total{mode=\"idle\",instance=~\".*.*\"}[1m])) by (instance)";

    /*************************************************MEMORY内存********************************************************/


    /**
     * 总内存
     * by instance
     * 单位 byte
     */
    public static String MEMORY_TOTAL = "windows_cs_physical_memory_bytes{instance=~\".*.*\"}";

    /**
     * MEMORY使用率
     * by instance
     */
    public static String MEMORY_USAGE_PRECENT = "(windows_cs_physical_memory_bytes{instance=~\".*.*\"} - windows_os_physical_memory_free_bytes{instance=~\".*.*\"}) / windows_cs_physical_memory_bytes{instance=~\".*.*\"}";

    /*************************************************NETWORK网络*******************************************************/

    /**
     * NETWORK SENT速率
     * 1分钟内平均速率  irate用于取最后两个数据点求平均值 适用于短时间求精确值
     * by instance
     * 单位 byte
     */
    public static String NETWORK_IRATE_SENT_RATE = "irate(windows_net_bytes_sent_total{instance=~\".*.*\"}[1m]) >0";

    /**
     * NETWORK SENT速率
     * by instance
     * 单位 byte
     */
    public static String NETWORK_RATE_SENT_RATE = "rate(windows_net_bytes_sent_total{instance=~\".*.*\"}[1m]) >0";

    /**
     * NETWORK RECEIVED速率
     * 1分钟内平均速率  irate用于取最后两个数据点求平均值 适用于短时间求精确值
     * by instance
     * 单位 byte
     */
    public static String NETWORK_IRATE_RECEIVED_RATE = "irate(windows_net_bytes_received_total{instance=~\".*.*\"}[1m]) >0";

    /**
     * NETWORK RECEIVED速率
     * by instance
     * 单位 byte
     */
    public static String NETWORK_RATE_RECEIVED_RATE = "rate(windows_net_bytes_received_total{instance=~\".*.*\"}[1m]) >0";



}
