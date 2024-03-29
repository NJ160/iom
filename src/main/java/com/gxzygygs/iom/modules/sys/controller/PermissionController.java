package com.gxzygygs.iom.modules.sys.controller;

import com.gxzygygs.iom.common.strategy.Delete;
import com.gxzygygs.iom.common.strategy.Insert;
import com.gxzygygs.iom.common.strategy.Select;
import com.gxzygygs.iom.common.strategy.Update;
import com.gxzygygs.iom.exceptions.customExceptions.AccountException;
import com.gxzygygs.iom.modules.sys.entity.Dto.PermissionDto;
import com.gxzygygs.iom.modules.sys.entity.Dto.RoleDto;
import com.gxzygygs.iom.modules.sys.entity.Dto.UserDto;
import com.gxzygygs.iom.modules.sys.entity.Po.Permission;
import com.gxzygygs.iom.modules.sys.entity.Po.Role;
import com.gxzygygs.iom.modules.sys.entity.Po.User;
import com.gxzygygs.iom.modules.sys.service.IPermissionService;
import com.gxzygygs.iom.modules.sys.service.IRoleService;
import com.gxzygygs.iom.modules.sys.service.IUserService;
import com.gxzygygs.iom.response.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author yulin
 * @since 2022-03-29
 */
@Controller
@RequestMapping("/sys/permission")
@ResponseBody
@Slf4j
public class PermissionController extends AbstractController{

    @Autowired
    IPermissionService permissionService;
    @Autowired
    IRoleService roleService;
    @Autowired
    IUserService userService;


    /**
     * 所有权限列表
     */
    @ApiOperation("获取权限信息接口")
    @GetMapping("/listAll")
    @RequiresPermissions("sys:permission:view")
    public Result listAll(){
        return Result.ok().put("permissions",permissionService.listAllPermission());
    }

    /**
     * 获取角色权限信息接口
     */
    @ApiOperation("获取角色权限信息接口")
    @PostMapping("/listByRole")
    @RequiresPermissions("sys:permission:view")
    public Result listByRole(@RequestBody @Validated(Select.class) RoleDto role){
        Role rolePo = roleService.findRoleById(role);
        return Result.ok().put("permissions",permissionService.listPermissionsByRole(role));
    }

    /**
     * 根据用户获得角色权限信息接口
     */
    @ApiOperation("根据用户获得角色权限信息接口")
    @PostMapping("/listByUser")
    @RequiresPermissions("sys:permission:view")
    public Result listByUser(@RequestBody @Validated(Select.class) UserDto user){
        user = userService.findUserByUsername(user);
        List roles = roleService.listRolesByUser(user);
        return Result.ok().put("rolePermissionMap",permissionService.mapPermissionsByRoles(roles));
    }

    /**
     * 更新权限信息接口
     */
    @ApiOperation("更新权限信息接口")
    @PostMapping("/update")
    @RequiresPermissions("sys:permission:update")
    public Result update(@RequestBody @Validated(Update.class) PermissionDto permission){
        if(!permissionService.updateById(permission)){
            throw new AccountException("权限信息更新失败");
        }
        return Result.ok("更新成功");
    }

    /**
     * 新增权限信息接口
     */
    @ApiOperation("新增权限信息接口")
    @PostMapping("/add")
    @RequiresPermissions("sys:permission:add")
    public Result add(@RequestBody @Validated(Insert.class) PermissionDto permission){
        //判断权限是否存在
        if(permissionService.checkIfPermissionExist(permission)){
            throw new AccountException("权限信息新增失败，"+permission.getPerCode()+"已存在。");
        }
        //存
        if(!permissionService.save(permission)){
            throw new AccountException("权限信息新增失败");
        }
        return Result.ok("新增成功");
    }

    /**
     * 删除权限信息接口
     */
    @ApiOperation("删除权限信息接口")
    @PostMapping("/remove")
    @RequiresPermissions("sys:permission:remove")
    public Result remove(@RequestBody @Validated(Delete.class) PermissionDto permission){
        permissionService.RemovePermission(permission);
        return Result.ok("删除成功");
    }

//    /**
//     * 更新用户角色信息接口
//     */
//    @ApiOperation("更新角色权限信息接口")
//    @PostMapping("/updateByRole")
//    @RequiresPermissions("sys:permission:update")
//    public Result updateByRole(@RequestBody @Validated(Update.class)RoleDto roleDto){
//        RoleDto role = roleService.findRoleById(roleDto);
//        roleDto.setPermissionIds(roleDto.getPermissionIds());
//        //获得数据库内的user对象
//        permissionService.updatePermissionsForRole(role);
//        return Result.ok("更新成功");
//    }
}
