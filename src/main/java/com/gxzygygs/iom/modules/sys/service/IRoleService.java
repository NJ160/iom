package com.gxzygygs.iom.modules.sys.service;

import com.gxzygygs.iom.modules.sys.entity.Dto.RoleDto;
import com.gxzygygs.iom.modules.sys.entity.Dto.UserDto;
import com.gxzygygs.iom.modules.sys.entity.Po.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxzygygs.iom.modules.sys.entity.Po.User;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author yulin
 * @since 2022-03-29
 */
public interface IRoleService extends IService<Role> {

    RoleDto findRoleById(RoleDto role);

    RoleDto findRoleByName(RoleDto role);

    boolean checkRoleExistByName(RoleDto role);

    List<Role> listAllRoles();

    List<Role> listRolesByUser(UserDto user);

    void updateRolesForUser(UserDto user);

    void insertRolesForUser(UserDto user);

    void deleteRolesForUser(UserDto user);

    void removeRole(RoleDto role);

}
