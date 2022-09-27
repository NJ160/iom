package com.gxzygygs.iom.modules.sys.controller;

import com.gxzygygs.iom.common.strategy.Delete;
import com.gxzygygs.iom.common.strategy.Insert;
import com.gxzygygs.iom.common.strategy.Select;
import com.gxzygygs.iom.common.strategy.Update;
import com.gxzygygs.iom.constant.Constant;
import com.gxzygygs.iom.jwt.JwtToken;
import com.gxzygygs.iom.modules.sys.entity.Dto.JwtUser;
import com.gxzygygs.iom.modules.sys.entity.Dto.UserDto;
import com.gxzygygs.iom.modules.sys.entity.Po.Role;
import com.gxzygygs.iom.modules.sys.entity.Po.User;
import com.gxzygygs.iom.modules.sys.entity.Vo.UserVo;
import com.gxzygygs.iom.modules.sys.service.IPermissionService;
import com.gxzygygs.iom.modules.sys.service.IRoleService;
import com.gxzygygs.iom.response.Result;
import com.gxzygygs.iom.modules.sys.service.IUserService;
import com.gxzygygs.iom.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
@ResponseBody
@Api(tags = "用户控制类")
public class UserController extends AbstractController{

    @Autowired
    IUserService userService;
    @Autowired
    IRoleService roleService;
    @Autowired
    IPermissionService permissionService;

    @ApiOperation("用户注册接口")
    @PostMapping
    public Result register(@RequestBody @Validated(Insert.class) UserDto user) throws AccountException {
        userService.userRegister(user);
        return Result.ok("注册成功");
    }

    @ApiOperation("用户详细信息接口")
    @GetMapping("/detail")
    @RequiresPermissions("sys:user:view")
    public Result infoDetail() throws AccountException {

        UserDto userDto = new UserDto();
        userDto.setUsername(getUsername());
        userDto = userService.userSummaryInfo(userDto);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDto,userVo);

        return Result.ok().put("user",userVo);
    }

    @ApiOperation("用户基础信息接口")
    @GetMapping("/info")
    @RequiresPermissions("sys:user:view")
    public Result infoSummary(){
        return Result.ok().put("name",getUsername());
    }

    @ApiOperation("用户更新接口")
    @PostMapping ("/update")
    @RequiresPermissions("sys:user:update")
    public Result update(@RequestBody @Validated(Update.class) UserDto user) throws AccountException {
        userService.userInfoUpdate(user);
        return Result.ok();
    }

    @ApiOperation("用户删除接口")
    @PostMapping("/remove")
    @RequiresPermissions("sys:user:remove")
    public Result remove(@RequestBody @Validated(Delete.class) UserDto user) throws AccountException {
        userService.userDelete(user);
        return Result.ok();
    };
}
