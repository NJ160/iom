//package com.gxzygygs.iom;
//
//import com.gxzygygs.iom.modules.generator.CodeGenerator;
//import com.gxzygygs.iom.modules.monitor.controller.PromQueryController;
//import com.gxzygygs.iom.modules.monitor.entity.Prom.PromResultInfo;
//import com.gxzygygs.iom.modules.monitor.entity.Prom.PromSqlConstants.PromLinuxSqlConstants;
//import com.gxzygygs.iom.modules.monitor.entity.Prom.PromSqlConstants.PromWindowsSqlConstants;
//import com.gxzygygs.iom.modules.monitor.service.*;
//import com.gxzygygs.iom.modules.monitor.utils.PrometheusUtils;
//import com.gxzygygs.iom.utils.AesCipherUtil;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
//class IomApplicationTests {
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//    @Autowired
//    private IPromBaseService promBaseService;
//    @Autowired
//    private IPromCpuService promCpuService;
//    @Autowired
//    private IPromDiskService promDiskService;
//    @Autowired
//    private IPromMemoryService promMemoryService;
//    @Autowired
//    private IPromNetworkService promNetworkService;
//    @Autowired
//    private PromQueryController promQueryController;
//
//    //    private UserMapper userMapper;
//    @Autowired
//    CodeGenerator codeGenerator;
//    @Test
//    void contextLoads() {
////        List<User> users = userMapper.selectList(null);
//        //获取当前项目最外层的工作目录
//        String projectPath = System.getProperty("user.dir");
//        System.out.print(projectPath);
//
//    }
//    @Test
//    void generateCode() {
//
//        codeGenerator.codeGenerator();
//    }
//
//
//    @Test
//    void ASEUtilTest(){
//
//
//        System.out.print(AesCipherUtil.enCrypto("123456"));
//
//    }
//    @Test
//    void hset(){
//        HashOperations ops =redisTemplate.opsForHash();
//        ops.put("info","a","aa");
//    }
//
//    @Test
//    void hget(){
//        HashOperations ops =redisTemplate.opsForHash();
//        System.out.printf(ops.get("info","a").toString());
//    }
//
//    @Test
//    void prometheusTest(){
//
////        String res = PrometheusUtils.replaceWithInstance(PromLinuxSqlConstants.CPU_IRATE_USAGE_PRECENT,"192.168.241.17:9100");
//        //单位 秒
//        String now = String.valueOf(System.currentTimeMillis()/1000L);
//        String before =String.valueOf(System.currentTimeMillis()/1000L-30*60);
//
////        List<PromResultInfo> promResultInfos =  PrometheusUtils.promQueryRange(res,before,now,"1h",null);
////        List<PromResultInfo> promResnfos =  PrometheusUtils.promQuery(res,null,null);
////        System.out.printf(promResultInfos.toString());
//
//
//
////        List<PromResultInfo> promResultInfos = promService.getTargetStatus(null);
//
//
//        String instance ="192.168.241.17:9100";
////        String instance ="192.168.241.1:9182";
//
//        //cpu
//
//        List<PromResultInfo> AA = promCpuService.getCpuLogicalCores(instance,null,null);
//
//        List<PromResultInfo> BB = promCpuService.getCpuIRateUsages(instance,null,null);
//
//        List<PromResultInfo> CC = promCpuService.getCpuRateUsages(instance,before,now,"2m",null);
//
//
//
////        DISK
//        List<PromResultInfo> A = promDiskService.getDiskTotalUsage(instance,null,null);
//
//        List<PromResultInfo> B= promDiskService.getDiskPartitionTotal(instance,null,null);
//
//        List<PromResultInfo>C= promDiskService.getDiskPartitionFree(instance,null,null);
//
//        List<PromResultInfo>D= promDiskService.getDiskPartitionUsage(instance,null,null);
//
//        List<PromResultInfo>E= promDiskService.getDiskWriteIRate(instance,null,null);
//
//        List<PromResultInfo>F= promDiskService.getDiskReadIRate(instance,null,null);
//
//        List<PromResultInfo>G= promDiskService.getDiskWriteRate(instance,before,now,"2m",null);
//
//        List<PromResultInfo>H= promDiskService.getDiskReadRate(instance,before,now,"2m",null);
//
//        List<PromResultInfo>I= promDiskService.getDiskPartitionWriteRate(instance,before,now,"2m",null);
//
//        List<PromResultInfo>J= promDiskService.getDiskPartitionReadRate(instance,before,now,"2m",null);
//
//
//        //memory
//        List<PromResultInfo>K= promMemoryService.getMemoryTotal(instance,null,null);
//
//        List<PromResultInfo>L= promMemoryService.getMemoryUsagePercent(instance,null,null);
//
//
//        //network
//        List<PromResultInfo>M= promNetworkService.getNetworkSentIRate(instance,null,null);
//
//        List<PromResultInfo>N= promNetworkService.getNetworkReceiveIRate(instance,null,null);
//
//        List<PromResultInfo>O= promNetworkService.getNetworkSentRate(instance,before,now,"2m",null);
//
//        List<PromResultInfo>P= promNetworkService.getNetworkSentRate(instance,before,now,"2m",null);
//
//        List<PromResultInfo>Q= promBaseService.getUptime(null,null,null);
//
//
//        promQueryController.query();
//        System.out.printf("");
//    }
//
//}
//
