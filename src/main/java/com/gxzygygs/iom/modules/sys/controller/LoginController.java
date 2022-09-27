package com.gxzygygs.iom.modules.sys.controller;

import com.gxzygygs.iom.common.strategy.Auth;
import com.gxzygygs.iom.constant.Constant;
import com.gxzygygs.iom.exceptions.customExceptions.AccountException;
import com.gxzygygs.iom.modules.sys.entity.Dto.UserDto;
import com.gxzygygs.iom.modules.sys.service.IUserService;
import com.gxzygygs.iom.response.Result;
import com.gxzygygs.iom.utils.AesCipherUtil;
import com.gxzygygs.iom.utils.JedisUtil;
import com.gxzygygs.iom.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/sys/login")
@Api(tags = "用户登录类")
@ResponseBody
@Slf4j
public class LoginController extends AbstractController{

    @Autowired
    IUserService userService;
    /**
     * RefreshToken过期时间
     */
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @ApiOperation("用户登录页跳转")
    @GetMapping
    public Result loginPage(){
        return Result.error(HttpStatus.PERMANENT_REDIRECT.value(),"重定向(Redirect.)");
    }

    @ApiOperation("用户登录接口")
    @PostMapping
    public Result login(@RequestBody @Validated(Auth.class) UserDto user, HttpServletRequest request, HttpServletResponse response){
        // 查询数据库中的帐号信息
        UserDto userDtoTemp = new UserDto();
        userDtoTemp.setUsername(user.getUsername());
        userDtoTemp = userService.findUserByUsername(userDtoTemp);
        if (userDtoTemp == null) {
            throw new AccountException("该帐号不存在(The account does not exist.)");
        }
        String asd = AesCipherUtil.enCrypto(user.getUsername()+user.getPassword());
        // 密码进行AES解密
        String key = AesCipherUtil.deCrypto(userDtoTemp.getPassword());
        // 因为密码加密是以帐号+密码的形式进行加密的，所以解密后的对比是帐号+密码
        if (key.equals(user.getUsername() + user.getPassword())) {
            // 清除可能存在的Shiro权限信息缓存
            if (JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + user.getUsername())) {
                JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + user.getUsername());
            }
            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + user.getUsername(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtil.sign(user.getUsername(), currentTimeMillis);
            response.setHeader("Authorization", token);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            return Result.ok("登录成功(Login Success.)");
        } else {
            throw new AccountException("帐号或密码错误(Account or Password Error.)");
        }

    }


    @ApiOperation("用户注销接口")
    @GetMapping("/logout")
    public Result logout(){
        Subject subject = SecurityUtils.getSubject();
        String token = (String) subject.getPrincipal();
        String username = JwtUtil.getClaim(token,Constant.USERNAME);
        if (JedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN + username)) {
            if (JedisUtil.delKey(Constant.PREFIX_SHIRO_REFRESH_TOKEN + username) > 0) {
                return Result.ok("注销成功(Logout Success)");
            }
        }
        throw new AccountException("注销失败，Username不存在(Deletion Failed. Username does not exist.)");
    }

}

