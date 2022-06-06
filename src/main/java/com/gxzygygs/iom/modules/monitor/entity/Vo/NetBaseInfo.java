package com.gxzygygs.iom.modules.monitor.entity.Vo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class NetBaseInfo {
    @JSONField(name = "index")
    private int index;
    @JSONField(name = "mtu")
    private int mtu;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "hardwareaddr")
    private String hardWareAddr;
    @JSONField(name = "flags")
    private List<String> flags;
    @JSONField(name = "addrs")
    private List<String> addrs;


    //	Index        int             `json:"index"`        // 大概是个ID之类的东西
    //	MTU          int             `json:"mtu"`          // 最大传输单元   maximum transmission unit
    //	Name         string          `json:"name"`         // 接口名        e.g., "en0", "lo0", "eth0.100"
    //	HardwareAddr string          `json:"hardwareaddr"` // MAC地址       IEEE MAC-48, EUI-48 and EUI-64 form
    //	Flags        []string        `json:"flags"`        // 标识？大概是支持的功能     e.g., FlagUp, FlagLoopback, FlagMulticast
    //	Addrs        []InterfaceAddr `json:"addrs"`        // 其他地址
}
