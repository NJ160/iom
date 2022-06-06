package com.gxzygygs.iom.modules.monitor.entity.Vo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class HostBaseInfo {
//    Hostname             string `json:"hostname"`
    @JSONField(name = "hostname")
    public String hostName;
//    Uptime               uint64 `json:"uptime"`
    @JSONField(name = "uptime")
    public BigDecimal uptime;
//    BootTime             uint64 `json:"bootTime"`
    @JSONField(name = "bootTime")
    public BigDecimal bootTime;
//    Procs                uint64 `json:"procs"`           // number of processes
    @JSONField(name = "procs")
    public BigDecimal procs;
//    OS                   string `json:"os"`              // ex: freebsd, linux
    @JSONField(name = "os")
    public String os;
//    Platform             string `json:"platform"`        // ex: ubuntu, linuxmint
    @JSONField(name = "platform")
    public String platform;
//    PlatformFamily       string `json:"platformFamily"`  // ex: debian, rhel
    @JSONField(name = "platformFamily")
    public String platformFamily;
//    PlatformVersion      string `json:"platformVersion"` // version of the complete OS
    @JSONField(name = "platformVersion")
    public String platformVersion;
//    KernelVersion        string `json:"kernelVersion"`   // version of the OS kernel (if available)
    @JSONField(name = "kernelVersion")
    public String kernelVersion;
//    KernelArch           string `json:"kernelArch"`      // native cpu architecture queried at runtime, as returned by `uname -m` or empty string in case of error
    @JSONField(name = "kernelArch")
    public String kernelArch;
//    VirtualizationSystem string `json:"virtualizationSystem"`
    @JSONField(name = "virtualizationSystem")
    public String virtualizationSystem;
//    VirtualizationRole   string `json:"virtualizationRole"` // guest or host
    @JSONField(name = "virtualizationRole")
    public String virtualizationRole;
//    HostID               string `json:"hostId"`             // ex: uuid
    @JSONField(name = "hostId")
    public String hostId;
}
