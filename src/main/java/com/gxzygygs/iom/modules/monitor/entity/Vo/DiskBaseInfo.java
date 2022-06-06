package com.gxzygygs.iom.modules.monitor.entity.Vo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DiskBaseInfo {
    //    Total       uint64 `json:"total"`   磁盘大小
    @JSONField(name = "total")
    private BigDecimal total;
    //    Used        uint64 `json:"used"`     已用储存
    @JSONField(name = "used")
    private BigDecimal used;
    //    Free        uint64 `json:"free"`     可用储存
    @JSONField(name = "free")
    private BigDecimal free;
    //    UsedPercent string `json:"usedPercent"`    使用率
    @JSONField(name = "usedPercent")
    private String usedPercent;
    //    Mountpoint  string `json:"mountpoint"`      挂载点//只有Linux有
    @JSONField(name = "mountpoint")
    private String mountPoint;

//TODO 添加fstype   文件系统
//TODO 添加opts     可写可读之类的

}
