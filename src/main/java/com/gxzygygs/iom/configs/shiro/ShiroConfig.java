package com.gxzygygs.iom.configs.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {
    private static final transient Logger log = LoggerFactory.getLogger(ShiroConfig.class);
    @Autowired
    RedisSessionDAO redisSessionDAO;

    @Autowired
    RedisCacheManager redisCacheManager;


    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") SessionsSecurityManager securityManager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        //关联管理器
        bean.setSecurityManager(securityManager);

        //添加过滤器
        Map<String,String> filterChainMap = new LinkedHashMap<>();
        /**
         * 添加自己的过滤器，自定义url规则
         * Shiro自带拦截器配置规则
         * rest：表示限定请求的方式为post，get，delete等中的一种
         * port： 表示限定请求的url的端口是规定端口
         * perms：表示具有某些permission才能使用,perms参数可以写多个
         * roles： 表示具有某些特定角色才能使用，参数可以写多个
         * anon：表示可以匿名使用，无需任何权限
         * authc：表示需要认证才能使用，没有参数
         * authBasic：没有参数表示httpBasic认证
         * ssl：表示安全的url请求，协议为https
         * user：没有参数表示必须存在用户，当登入操作时不做检查
         * 详情见文档 http://shiro.apache.org/web.html#urls-
         */

        //登录页无需过滤
        filterChainMap.put("/sys/login","anon");
        filterChainMap.put("/sys/login/cpuInfo","anon");
        filterChainMap.put("/monitor/websockt","anon");
        filterChainMap.put("/swagger-ui/**","anon");
        filterChainMap.put("/webjars/***","anon");
        filterChainMap.put("/doc.html","anon");
        filterChainMap.put("/swagger-resources/**","anon");
        filterChainMap.put("/v3/**","anon");
        filterChainMap.put("/**","authc");

        bean.setLoginUrl("/sys/login");
        SecurityUtils.setSecurityManager(securityManager);
        bean.setFilterChainDefinitionMap(filterChainMap);
        return bean;
    }


    @Bean(name = "securityManager")
    public SessionsSecurityManager securityManager(@Qualifier("userRealm") UserRealm realm, @Qualifier("sessionManager") SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // inject UserRealm
        securityManager.setRealm(realm);
        // inject sessionManager
        securityManager.setSessionManager(sessionManager);
        // inject redisCacheManager
        securityManager.setCacheManager(redisCacheManager);
        // other stuff...
        return securityManager;
    }

    @Bean(name = "sessionManager")
    public SessionManager sessionManager(@Qualifier("sessionIdGenerator")JavaUuidSessionIdGenerator sessionIdGenerator) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator);
        // inject redisSessionDAO
        sessionManager.setSessionDAO(redisSessionDAO);
        // other stuff...
        return sessionManager;
    }

    @Bean(name = "userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }

    @Bean(name = "sessionIdGenerator")
    public JavaUuidSessionIdGenerator sessionIdGenerator(){
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SessionsSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
