package com.gxzygygs.iom.modules.monitor.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gxzygygs.iom.modules.monitor.entity.Prom.PromConstants;
import com.gxzygygs.iom.modules.monitor.entity.Prom.PromResponceInfo;
import com.gxzygygs.iom.modules.monitor.entity.Prom.PromResultInfo;
import com.alibaba.fastjson2.JSON;
import com.gxzygygs.iom.utils.RestTemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import reactor.util.annotation.Nullable;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title: prometheus工具类
 */
@Slf4j
public class PrometheusUtils {

        /**
         * @Title: 拼接url工具类
         * @MethodName: urlBuilder
         * @param map
         */
        public static String urlBuilder(String url,String func,Map<String,Object> map){
            StringBuilder sb=new StringBuilder();
            sb.append(url+func+"?");
            map.keySet().stream().forEach(key->{
                sb.append("&"+key);
                sb.append("={");
                sb.append(key);
                sb.append("}");
            });
            return sb.toString();
        }



        /**
         * @Title: 调用QUERY接口
         * @MethodName: promQuery
         * @param query
         * @param time
         * @param timeout
         */
        public static List<PromResultInfo> promQuery(@NotNull String query, @Nullable String time, @Nullable String timeout){
            Map<String,Object> params = new HashMap<>();
            params.put(PromConstants.QUERY,query);

            if(time!=null){
                params.put(PromConstants.TIME,time);
            }

            if(timeout!=null){
                params.put(PromConstants.TIMEOUT,timeout);
            }

            String url = urlBuilder(PromConstants.Prometheus_URL,PromConstants.QUERY,params);

            return getPromInfo(url,params);
        }


        /**
         * @Title: 调用QUERY_RANGE接口
         * @MethodName: promQueryRange
         * @param query
         * @param start
         * @param end
         * @param step
         * @param timeout
         */
        public static List<PromResultInfo> promQueryRange(@NotNull String query, @NotNull String start, @NotNull String end,@NotNull String step, @Nullable String timeout){
            Map<String,Object> params = new HashMap<>();

            params.put(PromConstants.QUERY,query);
            params.put(PromConstants.START,start);
            params.put(PromConstants.END,end);
            params.put(PromConstants.STEP,step);

            if(timeout!=null){
                params.put(PromConstants.TIMEOUT,timeout);
            }

            String url = urlBuilder(PromConstants.Prometheus_URL,PromConstants.QUERY_RANGE,params);


            return getPromInfo(url,params);
        }



        /**
         * @Title: 执行Prom命令
         * @MethodName: getPromInfo
         * @param url
         * @param params
         */
        public static List<PromResultInfo> getPromInfo(String url,Map<String,Object> params) {

            log.info("【主机信息】，请求地址：{}，请求QL：{}",url,params.values());

            ResponseEntity<String> http = null;
            try {
                http = RestTemplateUtils.get(url,String.class,params);
            } catch (Exception e) {
                log.error("【主机信息】异常，请求地址：{}，请求QL：{}，异常信息：{}", url, params.values(), e);
            }

            PromResponceInfo responceInfo = JSON.parseObject(http.getBody(), PromResponceInfo.class);
            log.info("【主机信息】，请求地址：{}，请求QL：{}，返回信息：{}", url, params.values(), responceInfo);
            if (Objects.isNull(responceInfo)) {
                return null;
            }

            String status = responceInfo.getStatus();
            if (StringUtils.isBlank(status) || !PromConstants.SUCCESS.equals(status)) {
                log.info("【主机信息】，请求地址：{}，请求QL：{}，错误类型：{}，错误信息：{}", url, params.values(),responceInfo.getErrorType(), responceInfo.getError());
                return null;
            }
            List<PromResultInfo> result = responceInfo.getData().getResult();
            return result;
        }


    /**
     * @param sourceString 需要匹配的字符串
     * @param instance  需要替换的instance
     * @return
     */

    public static String replaceWithInstance(@NotBlank String sourceString, @NotBlank String instance) {
        Pattern pattern = Pattern.compile("\\.\\*\\.\\*");
        String targetString = sourceString;
        Matcher matcher = pattern.matcher(sourceString);
        if (matcher.find()) {
            try {
                targetString = matcher.replaceFirst(instance);

            } catch (Exception e) {
                throw new RuntimeException("String formatter failed", e);

            }
        }
        return targetString;
    }



}
