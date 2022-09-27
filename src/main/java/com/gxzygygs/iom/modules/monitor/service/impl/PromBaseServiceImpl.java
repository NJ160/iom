package com.gxzygygs.iom.modules.monitor.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gxzygygs.iom.exceptions.customExceptions.PromException;
import com.gxzygygs.iom.modules.monitor.entity.Prom.PromConstants;
import com.gxzygygs.iom.modules.monitor.entity.Prom.PromResultInfo;
import com.gxzygygs.iom.modules.monitor.entity.Prom.PromSqlConstants.PromLinuxSqlConstants;
import com.gxzygygs.iom.modules.monitor.entity.Prom.PromSqlConstants.PromSqlConstants;
import com.gxzygygs.iom.modules.monitor.entity.Prom.PromSqlConstants.PromWindowsSqlConstants;
import com.gxzygygs.iom.modules.monitor.service.IPromBaseService;
import com.gxzygygs.iom.modules.monitor.utils.PrometheusUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PromBaseServiceImpl implements IPromBaseService {

    @Override
    public List<PromResultInfo> getUptime(String instance, String time, String timeout) {
        return promQueryOSInfo(instance,time,timeout, PromLinuxSqlConstants.UPTIME, PromWindowsSqlConstants.UPTIME);
    }

    @Override
    public List<PromResultInfo> getTargetStatus(String instance){
        String sql = PromSqlConstants.UP;
        if(StringUtils.isNotBlank(instance)){
            sql = PrometheusUtils.replaceWithInstance(PromSqlConstants.UP,instance);
        }
        return PrometheusUtils.promQuery(sql,null,null);
    }
    @Override
    public List<PromResultInfo> promQueryOSInfo(String instance, String time, String timeout, String linux, String windows) {
        //若instance不为空，则是查询单个实例
        if(StringUtils.isNotBlank(instance)){
            String query;
            String job = getTargetStatus(instance).get(0).getMetric().getJob();
            switch (job){
                case PromConstants.NODE_EXPORTER:
                    query = PrometheusUtils.replaceWithInstance(linux,instance);
                    break;
                case PromConstants.WINDOWS_EXPORTER:
                    query = PrometheusUtils.replaceWithInstance(windows,instance);
                    break;
                default:
                    throw new PromException("job(操作系统)匹配失败");
            }
            return PrometheusUtils.promQuery(query,time,timeout);
        }

        //若instance为空，则是查询所有实例的数据
        List<PromResultInfo> resultInfos = PrometheusUtils.promQuery(linux,time,timeout);
        resultInfos.addAll(PrometheusUtils.promQuery(windows,time,timeout));
        return resultInfos;
    }

    @Override
    public List<PromResultInfo> promQueryRangeOSInfo(String instance, String start, String end, String step, String timeout, String linux, String windows) {
        //若instance不为空，则是查询单个实例
        if(StringUtils.isNotBlank(instance)){
            String query;
            String job = getTargetStatus(instance).get(0).getMetric().getJob();
            switch (job){
                case PromConstants.NODE_EXPORTER:
                    query = PrometheusUtils.replaceWithInstance(linux,instance);
                    break;
                case PromConstants.WINDOWS_EXPORTER:
                    query = PrometheusUtils.replaceWithInstance(windows,instance);
                    break;
                default:
                    throw new PromException("job(操作系统)匹配失败");
            }
            return PrometheusUtils.promQueryRange(query,start,end,step,timeout);
        }else {
            throw new PromException("instance不能为空");
        }
    }
}
