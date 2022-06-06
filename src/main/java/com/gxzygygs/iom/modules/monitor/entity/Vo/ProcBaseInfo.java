package com.gxzygygs.iom.modules.monitor.entity.Vo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProcBaseInfo {

//    Pid         *process.Process        `json:"pid"`         //pid
    @JSONField(name = "pid")
    private String pid;
//    Name        string                  `json:"name"`        //进程名
    @JSONField(name = "name")
    private String name;
//    IsRunning   bool                    `json:"isRunning"`   //进程是否还在运行
    @JSONField(name = "isRunning")
    private boolean isRunning;
//    Username    string                  `json:"username"`    //进程的用户名
    @JSONField(name = "username")
    private String username;
//    CreateTime  int64                   `json:"createTime"`  //进程创建时间
    @JSONField(name = "createTime")
    private BigDecimal createTime;
//    CPUPercent  string                  `json:"cpuPercent"`  //Cpu使用率
    @JSONField(name = "cpuPercent")
    private String cpuPercent;
//    MemoryUsage *process.MemoryInfoStat `json:"memoryUsage"` //内存使用信息
    @JSONField(name = "memoryUsage")
    private ProcMemInfo memoryUsage;
//    Nice        int32                   `json:"nice"`        //进程优先级
    @JSONField(name = "nice")
    private BigDecimal nice;
//    Cwd         string                  `json:"cwd"`         //工作路径
    @JSONField(name = "cwd")
    private String cwd;
//    CmdLine     string                  `json:"cmdLine"`     //执行文件命令
    @JSONField(name = "cmdLine")
    private String cmdLine;
//    Exe         string                  `json:"exe"`         //执行文件路径
    @JSONField(name = "exe")
    private String exe;
//    Parent      *process.Process        `json:"parent"`      //父进程
    @JSONField(name = "parent")
    private String parent;
//    Children    []*process.Process      `json:"children"`    //子进程
    @JSONField(name = "children")
    private List<String> children;
}
