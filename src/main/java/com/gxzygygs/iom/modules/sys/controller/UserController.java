package com.gxzygygs.iom.modules.sys.controller;

import com.gxzygygs.iom.common.strategy.Delete;
import com.gxzygygs.iom.common.strategy.Insert;
import com.gxzygygs.iom.common.strategy.Select;
import com.gxzygygs.iom.common.strategy.Update;
import com.gxzygygs.iom.modules.sys.entity.Po.User;
import com.gxzygygs.iom.modules.sys.entity.Vo.UserVo;
import com.gxzygygs.iom.response.Result;
import com.gxzygygs.iom.modules.sys.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.net.SocketFlow;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.security.auth.login.AccountException;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author yulin
 * @since 2022-03-29
 */
@RestController
@RequestMapping("/sys/user")
@Slf4j
@Api(tags = "用户控制类")
public class UserController extends AbstractController{

    @Autowired
    IUserService userService;
    @ApiOperation("用户注册接口")
    @PostMapping
    public Result register(@RequestBody @Validated(Insert.class) User user, @RequestBody @NotBlank List<Integer> roleIds) throws AccountException {
        userService.userRegister(user,roleIds);
        return Result.ok("注册成功");
    }

    @ApiOperation("用户详细信息接口")
    @GetMapping("/detail")
    @RequiresPermissions("sys:user:view")
    public Result infoDetail(@RequestBody @Validated(Select.class) User user){

        return Result.ok(new HashMap<>());
    }

    @ApiOperation("用户基础信息接口")
    @GetMapping
    @RequiresPermissions("sys:user:view")
    public Result infoSummary(){
        User user = getUser();
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);

        return new Result().put("userInfo",userVo);
    }

    @ApiOperation("用户更新接口")
    @PostMapping ("/update")
    @RequiresPermissions("sys:user:update")
    public Result update(@RequestBody @Validated(Update.class) User user) throws AccountException {
        userService.userInfoUpdate(user);
        return Result.ok();
    }

    @ApiOperation("用户删除接口")
    @PostMapping("/remove")
    @RequiresPermissions("sys:user:remove")
    public Result remove(@RequestBody @Validated(Delete.class) List<User> users) throws AccountException {
        for (User user : users) {
            userService.userDelete(user);
        }
      return Result.ok();
    };
}
