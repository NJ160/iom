package com.gxzygygs.iom.modules.sys.service;

import com.gxzygygs.iom.modules.sys.entity.Dto.PermissionDto;
import com.gxzygygs.iom.modules.sys.entity.Dto.RoleDto;
import com.gxzygygs.iom.modules.sys.entity.Po.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxzygygs.iom.modules.sys.entity.Po.Role;
import com.gxzygygs.iom.modules.sys.entity.Po.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author yulin
 * @since 2022-03-29
 */
public interface IPermissionService extends IService<Permission> {

    PermissionDto findPermissionById(PermissionDto permission);

    boolean checkIfPermissionExist(PermissionDto permission);

    List<Permission> listAllPermission();

    //选出对应角色的所有权限
    List<Permission> listPermissionsByRole(Role rolePo);

    //选出列表下角色的所有权限
    Map<Integer,List<Permission>> mapPermissionsByRoles(List<Role> rolePos);

    //选出列表下角色的所有权限
    List<Permission> listPermissionsByRoles(List<Role> rolePos);

    void updatePermissionsForRole(RoleDto roleDto);

    void insertPermissionsForRole(RoleDto roleDto);

    void deletePermissionsForRole(RoleDto roleDto);

    void RemovePermission(PermissionDto permission);
}
