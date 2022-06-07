package com.gxzygygs.iom.modules.sys.service;

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

    Role findRoleById(Role role);

    Role findRoleByName(Role role);

    List<Role> listAllRoles();

    List<Role> listRolesByUser(User user);

    void updateRolesForUser(User user,List<Integer> roleIds);

    void insertRolesForUser(User user,List<Integer> roleIds);

    void deleteRolesForUser(User user,List<Integer> roleIds);

    void removeRole(Role role);

}
