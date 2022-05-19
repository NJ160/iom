package com.gxzygygs.iom.modules.sys.controller;

import com.gxzygygs.iom.common.strategy.Delete;
import com.gxzygygs.iom.common.strategy.Insert;
import com.gxzygygs.iom.common.strategy.Select;
import com.gxzygygs.iom.common.strategy.Update;
import com.gxzygygs.iom.exceptions.customExceptions.AccountException;
import com.gxzygygs.iom.modules.sys.entity.Po.Role;
import com.gxzygygs.iom.modules.sys.entity.Po.User;
import com.gxzygygs.iom.modules.sys.service.IRoleService;
import com.gxzygygs.iom.modules.sys.service.IUserService;
import com.gxzygygs.iom.response.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author yulin
 * @since 2022-03-29
 */
@Controller
@RequestMapping("/sys/role")
@Slf4j
public class RoleController extends AbstractController {

    @Autowired
    IRoleService roleService;
    @Autowired
    IUserService userService;

    /**
     * 所有角色列表
     */
    @ApiOperation("获取角色信息接口")
    @GetMapping("/listAll")
    @RequiresPermissions("sys:role:view")
    public Result listAll(){
        return Result.ok().put("roles",roleService.listAllRoles());
    }

    /**
     * 获取用户角色信息接口
     */
    @ApiOperation("获取用户角色信息接口")
    @PostMapping("/listByUser")
    @RequiresPermissions("sys:role:view")
    public Result listByUser(@RequestBody @Validated(Select.class) User user){
        //获得数据库内的user对象
        user = userService.findUserByAccount(user);
        return Result.ok().put("roles",roleService.listRolesByUser(user));
    }

    /**
     * 新增角色信息接口
     */
    @ApiOperation("新增角色信息接口")
    @PostMapping("/add")
    @RequiresPermissions("sys:role:add")
    public Result add(@RequestBody @Validated(Insert.class) Role role){
        if(!roleService.save(role)){
            throw new AccountException("角色信息新增失败");
        }
        return Result.ok("新增成功");
    }

    /**
     * 删除角色信息接口
     */
    @ApiOperation("删除角色信息接口")
    @PostMapping("/remove")
    @RequiresPermissions("sys:role:remove")
    public Result remove(@RequestBody @Validated(Delete.class) Role role){
        roleService.removeRole(role);
        return Result.ok("删除成功");
    }

    /**
     * 更新角色信息接口
     */
    @ApiOperation("更新角色信息接口")
    @PostMapping("/update")
    @RequiresPermissions("sys:role:update")
    public Result update(@RequestBody @Validated(Update.class) Role role){
        if(!roleService.updateById(role)){
            throw new AccountException("角色信息更新失败");
        }
        return Result.ok("更新成功");
    }

    /**
     * 更新用户角色信息接口
     */
    @ApiOperation("更新用户角色信息接口")
    @PostMapping("/updateByUser")
    @RequiresPermissions("sys:role:update")
    public Result updateByUser(@RequestBody @Validated(Select.class) User user, @RequestBody @NotBlank List<Integer> roleIds){
        //获得数据库内的user对象
        user = userService.findUserByAccount(user);
        roleService.updateRolesForUser(user,roleIds);
        return Result.ok();
    }


}
