package com.gxzygygs.iom.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gxzygygs.iom.exceptions.customExceptions.AccountException;
import com.gxzygygs.iom.modules.sys.entity.Dto.PermissionDto;
import com.gxzygygs.iom.modules.sys.entity.Dto.RoleDto;
import com.gxzygygs.iom.modules.sys.entity.Po.*;
import com.gxzygygs.iom.modules.sys.mapper.PermissionMapper;
import com.gxzygygs.iom.modules.sys.mapper.RolePermissionMapper;
import com.gxzygygs.iom.modules.sys.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author yulin
 * @since 2022-03-29
 */
@Service
@Slf4j
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    RolePermissionMapper rolePermissionMapper;


    @Override
    public PermissionDto findPermissionById(PermissionDto permission) {
        Permission permissionPo = getById(permission);
        if(permissionPo==null){
            throw new AccountException("权限不存在");
        }
        BeanUtils.copyProperties(permissionPo,permission);
        return permission;
    }

    @Override
    public boolean checkIfPermissionExist(PermissionDto permission) {
        return permissionMapper.selectOne(new LambdaQueryWrapper<Permission>().eq(Permission::getPerCode,permission.getPerCode()))!=null;
    }

    @Override
    public List<Permission> listAllPermission() {
        List<Permission> permissions = permissionMapper.selectList(new QueryWrapper<Permission>());
        return permissions;
    }

    @Override
    public List<Permission> listPermissionsByRole(Role rolePo) {
        //获得角色下所有用户权限对应关系
        List<RolePermission> rolePermissionPos = rolePermissionMapper.selectList(new LambdaQueryWrapper<RolePermission>().in(RolePermission::getRoleId,rolePo.getId()));
        if(rolePermissionPos.size()<=0){
            return new ArrayList<Permission>();
        }
        //获得角色下所有用户权限的id,set去重
        Set<Integer> permissionIds = rolePermissionPos.stream().map(RolePermission::getPermissionId).collect(Collectors.toSet());
        //根据权限Id获得所有权限
        List<Permission> permissionPos = permissionMapper.selectList(new LambdaQueryWrapper<Permission>().in(Permission::getId,permissionIds));
        return permissionPos;
    }

    @Override
    public Map<Integer, List<Permission>> mapPermissionsByRoles(List<Role> rolePos) {
        Map<Integer,List<Permission>> map = new HashMap<>();
        for(Role rolePo: rolePos){
            map.put(rolePo.getId(),listPermissionsByRole(rolePo));
        }
        return map;
    }

    @Override
    public List<Permission> listPermissionsByRoles(List<Role> rolePos) {
        List<Permission> list = new ArrayList<>();
        for(Role rolePo: rolePos){
            list.addAll(listPermissionsByRole(rolePo));
        }
        return list;
    }

    @Override
    public void updatePermissionsForRole(RoleDto role) {
        List<Integer> permissionIds = role.getPermissionIds();
        //选出所有和角色相关的权限关系
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(new LambdaQueryWrapper<RolePermission>().in(RolePermission::getRoleId,role.getId()));
        //获得所有的权限id
        List<Integer> dbPermissionIds = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        //获得两者的交集权限Id
        List<Integer> bothPermissionIds = dbPermissionIds.stream().filter(id -> permissionIds.contains(id)).collect(Collectors.toList());
        //获得新增的权限Id
        List<Integer> insertPermissionIds = permissionIds.stream().filter(id -> !bothPermissionIds.contains(id)).collect(Collectors.toList());
        //新增角色权限
        role.setPermissionIds(insertPermissionIds);
        insertPermissionsForRole(role);
        //获得删除的权限Id
        List<Integer> deletePermissionIds = dbPermissionIds.stream().filter(id -> !bothPermissionIds.contains(id)).collect(Collectors.toList());
        //删除角色权限
        role.setPermissionIds(deletePermissionIds);
        deletePermissionsForRole(role);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void insertPermissionsForRole(RoleDto role) {
        List<Integer> permissionIds = role.getPermissionIds();
        log.info("角色权限表新增{用户:"+role.getName()+",id:"+role.getId()+",新增权限IDs:"+permissionIds+"}");
        for (Integer permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(permissionId);
            int j = rolePermissionMapper.insert(rolePermission);
            if(j==0){
                throw new AccountException("角色权限表插入出错：角色"+role.getName()+",id:"+role.getId()+",新增权限ID:"+permissionId+"插入失败}");
            }
        }
        log.info("角色权限表成功新增记录{"+permissionIds.size()+"}条");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deletePermissionsForRole(RoleDto role) {
        List<Integer> permissionIds = role.getPermissionIds();
        log.info("角色权限表删除{用户:"+role.getName()+",id:"+role.getId()+",删除权限IDs:"+permissionIds+"}");
        for (Integer permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(permissionId);
            int j = rolePermissionMapper.delete(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId,role.getId())
                                                                                        .eq(RolePermission::getPermissionId,permissionId));
            if(j==0){
                throw new AccountException("角色权限表删除出错：角色"+role.getName()+",id:"+role.getId()+",删除权限ID:"+permissionId+"删除失败}");
            }
        }
        log.info("角色权限表成功删除记录{"+permissionIds.size()+"}条");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void RemovePermission(PermissionDto permission) {
        //获得权限相关所有角色权限对应信息
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getPermissionId,permission.getId()));
        if(rolePermissionList.size()>0){
            //删除所有相关角色权限对应信息
            int i = rolePermissionMapper.deleteBatchIds(rolePermissionList.stream().map(RolePermission::getId).collect(Collectors.toList()));
            if(i != rolePermissionList.size()){
                throw new AccountException("删除角色权限表失败,应删除"+rolePermissionList.size()+"条，实际删除"+i+"条");
            }
        }
        //删除权限信息
        if(!removeById(permission.getId())){
            throw new AccountException("删除权限表失败,PermissionId:"+permission.getId()+"PermissionPerCode:"+permission.getPerCode());
        }
    }

}
