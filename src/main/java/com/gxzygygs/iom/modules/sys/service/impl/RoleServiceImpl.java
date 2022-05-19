package com.gxzygygs.iom.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gxzygygs.iom.exceptions.customExceptions.AccountException;
import com.gxzygygs.iom.modules.sys.entity.Po.Role;
import com.gxzygygs.iom.modules.sys.entity.Po.RolePermission;
import com.gxzygygs.iom.modules.sys.entity.Po.User;
import com.gxzygygs.iom.modules.sys.entity.Po.UserRole;
import com.gxzygygs.iom.modules.sys.mapper.RoleMapper;
import com.gxzygygs.iom.modules.sys.mapper.RolePermissionMapper;
import com.gxzygygs.iom.modules.sys.mapper.UserRoleMapper;
import com.gxzygygs.iom.modules.sys.service.IPermissionService;
import com.gxzygygs.iom.modules.sys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxzygygs.iom.modules.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author yulin
 * @since 2022-03-29
 */
@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    RolePermissionMapper rolePermissionMapper;
    @Autowired
    IPermissionService iPermissionService;

    @Override
    public List<Role> listRolesByUser(User user) {
        //选出所有和用户相关的角色关系
        List<UserRole> userRolePos = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().in(UserRole::getUserId,user.getId()));
        //获得所有的角色id
        List<Integer> roleIds = userRolePos.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        //选出所有和用户相关的角色
        List<Role> roles = roleMapper.selectList(new LambdaQueryWrapper<Role>().in(Role::getId,roleIds));
        return roles;
    }

    @Override
    public Role findRoleById(Role role) {
        role = getById(role);
        if(role==null){
            throw new AccountException("角色不存在");
        }
        return role;
    }

    @Override
    public List<Role> listAllRoles() {
        List<Role> userRolePos = roleMapper.selectList(new QueryWrapper<Role>());
        return userRolePos;
    }

    @Override
    public void updateRolesForUser(User user, List<Integer> roleIds) {

        //选出所有和用户相关的角色关系
        List<UserRole> userRolePos = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().in(UserRole::getUserId,user.getId()));
        //获得所有的角色id
        List<Integer> dbRoleIds = userRolePos.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        //获得两者的交集角色Id
        List<Integer> bothRoleIds = dbRoleIds.stream().filter(id -> roleIds.contains(id)).collect(Collectors.toList());
        //获得新增的角色Id
        List<Integer> insertRoleIds = dbRoleIds.stream().filter(id -> bothRoleIds.contains(id)).collect(Collectors.toList());
        //新增用户角色
        insertRolesForUser(user,insertRoleIds);
        //获得删除的角色Id
        List<Integer> deleteRoleIds = roleIds.stream().filter(id -> bothRoleIds.contains(id)).collect(Collectors.toList());
        //删除用户角色
        deleteRolesForUser(user,deleteRoleIds);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertRolesForUser(User user, List<Integer> roleIds) {
        log.info("用户角色表新增{用户:"+user.getAccount()+",id:"+user.getId()+",新增角色IDs:"+roleIds+"}");
        for (Integer roleId : roleIds) {
            UserRole userRolePo = new UserRole();
            userRolePo.setRoleId(roleId);
            userRolePo.setUserId(user.getId());
            int j = userRoleMapper.insert(userRolePo);
            if(j==0){
                throw new AccountException("用户角色表插入出错：用户"+user.getAccount()+",id:"+user.getId()+",新增角色ID:"+roleId+"插入失败}");
            }
        }
        log.info("用户角色表成功新增记录{"+roleIds.size()+"}条");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRolesForUser(User user, List<Integer> roleIds) {
        log.info("用户角色表删除{用户:"+user.getAccount()+",id:"+user.getId()+",删除角色IDs:"+roleIds+"}");
        for (Integer roleId : roleIds) {
            UserRole userRolePo = new UserRole();
            userRolePo.setRoleId(roleId);
            userRolePo.setUserId(user.getId());
            int j = userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleId,roleId)
                                                                            .eq(UserRole::getUserId,user.getId()));
            if(j==0){
                throw new AccountException("用户角色表删除出错：用户"+user.getAccount()+",id:"+user.getId()+",新增角色ID:"+roleId+"删除失败}");
            }
        }
        log.info("用户角色表成功删除记录{"+roleIds.size()+"}条");
    }

    @Override
    public void removeRole(Role role) {
        //获得角色相关用户角色对应信息
        List<UserRole> userRoleList = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleId,role.getId()));

        //删除所有相关用户角色对应信息
        int i = userRoleMapper.deleteBatchIds(userRoleList.stream().map(UserRole::getId).collect(Collectors.toList()));
        if(i != userRoleList.size()){
            throw new AccountException("用户角色表失败,应删除"+userRoleList.size()+"条，实际删除"+i+"条");
        }

        //获得角色相关角色权限对应信息
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId,role.getId()));

        //删除角色相关角色权限对应信息
        iPermissionService.deletePermissionsForRole(role,rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList()));

        //删除角色信息
        if(!removeById(role.getId())){
            throw new AccountException("删除角色表失败,RoleId:"+role.getId()+"RoleName:"+role.getName());
        }
    }


}

