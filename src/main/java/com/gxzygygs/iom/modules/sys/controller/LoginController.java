package com.gxzygygs.iom.modules.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.gxzygygs.iom.common.strategy.Auth;
import com.gxzygygs.iom.modules.monitor.websocket.SimpleServer;
import com.gxzygygs.iom.modules.sys.entity.Po.User;
import com.gxzygygs.iom.modules.sys.service.IUserService;
import com.gxzygygs.iom.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.java_websocket.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/sys/login")
@Api(tags = "用户登录类")
@Slf4j
public class LoginController extends AbstractController{

    @Autowired
    IUserService userService;

    @ApiOperation("用户登录页跳转")
    @GetMapping
    public Result loginPage(){
        return Result.error(HttpStatus.PERMANENT_REDIRECT.value(),"重定向登录页");
    }

    @ApiOperation("用户登录接口")
    @PostMapping
    public Result login(@RequestBody @Validated(Auth.class) User user,HttpServletRequest request,HttpServletResponse response){
        Subject currentUser = SecurityUtils.getSubject();
        String account = user.getAccount();
        char[] password = user.getPassword().toCharArray();

        Map<String, String> headerMap = new HashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name	= enumeration.nextElement();
            String value = request.getHeader(name);
            headerMap.put(name, value);
        }

        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(account, password);
            try {
                currentUser.login(token);
                log.info("用户： " + token.getPrincipal() + "登录成功。   "+"sessionId:"+currentUser.getSession().getId());
            } catch (UnknownAccountException e) {
                log.info("该用户名不存在： " + token.getPrincipal());
                return Result.error(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            } catch (IncorrectCredentialsException e) {
                log.info("用户： " + token.getPrincipal() + "的密码不正确!");
                return Result.error(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            } catch (AuthenticationException e) {
                log.info("用户： " + token.getPrincipal() + "登录出错。 " + e.getMessage());
                return Result.error(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            }
        }
        return Result.ok("登录成功");
    }


    @ApiOperation("用户注销接口")
    @GetMapping("/logout")
    public Result logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.ok();
    }

    @PostMapping("/cpuInfo")
    public Result cpuInfo(@RequestBody JSONObject jsonObject){
        log.info(jsonObject.toJSONString());
        return Result.ok();
    }


}

