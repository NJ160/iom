package com.gxzygygs.iom.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gxzygygs.iom.exceptions.customExceptions.AccountException;
import com.gxzygygs.iom.modules.sys.entity.Dto.UserDto;
import com.gxzygygs.iom.modules.sys.entity.Po.Role;
import com.gxzygygs.iom.modules.sys.entity.Po.User;
import com.gxzygygs.iom.modules.sys.mapper.UserMapper;
import com.gxzygygs.iom.modules.sys.service.IRoleService;
import com.gxzygygs.iom.modules.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxzygygs.iom.utils.AesCipherUtil;
import com.gxzygygs.iom.utils.common.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author yulin
 * @since 2022-03-25
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    IRoleService roleService;

    @Override
    public UserDto findUserByUsername(UserDto user){
        User userPo = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername()).last("LIMIT 1"));
        if(userPo==null){
            throw new AccountException("用户{"+ user.getUsername() +"}不存在");
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userPo,userDto);
        return userDto;
    }

    @Override
    public boolean checkIfUserNameExist(UserDto user) {
        User userPo = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername()).last("LIMIT 1"));
        if(userPo != null){
            throw new AccountException("用户{"+ user.getUsername() +"}已存在");
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public UserDto userRegister(UserDto user) throws AccountException {

        //判断用户账户是否已存在
        checkIfUserNameExist(user);
        String password = AesCipherUtil.enCrypto(user.getUsername()+user.getPassword());
        user.setPassword(password);
        user.setRegTime(new Date());
        //插入用户表
        int i = userMapper.insert(user);
        if(i==0){
            throw new AccountException("用户{"+ user.getUsername() +"}注册失败,插入数据库失败");
        }
        //获得数据库内带ID的用户对象
        User userPo = new User();
        userPo = findUserByUsername(user);
        BeanUtils.copyProperties(user,userPo);
        //插入用户角色表
        roleService.insertRolesForUser(user);

        return user;
    }

    @Override
    public UserDto userSummaryInfo(UserDto user) throws AccountException {
        UserDto userDto = findUserByUsername(user);
        return userDto;
    }

    @Override
    public UserDto userDetailInfo(UserDto user) throws AccountException {
        UserDto userDto = findUserByUsername(user);
        return userDto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public boolean userInfoUpdate(UserDto user) throws AccountException {
        //查询服务器内数据
        User userPo = userSummaryInfo(user);
        if(userPo==null){
            throw new AccountException("更新用户"+user.getUsername()+"失败,用户不存在");
        }

        if(StringUtils.isBlank(user.getPassword())){
            user.setPassword(null);
        }else{
            user.setPassword(AesCipherUtil.enCrypto(user.getUsername()+user.getPassword()));
        }

        //通过ID修改信息
        int i = userMapper.update(null,new LambdaUpdateWrapper<User>().set(StringUtils.isNotBlank(user.getPassword()),User::getPassword,user.getPassword())
                                                            .set(StringUtils.isNotBlank(user.getEmail()),User::getEmail,user.getEmail())
                                                            .set(StringUtils.isNotBlank(user.getTelephone()),User::getTelephone,user.getTelephone())
                                                            .set(StringUtils.isNotBlank(user.getAccount()),User::getAccount,user.getAccount())
                                                            .set(StringUtils.isNotBlank(user.getDepartment()),User::getDepartment,user.getDepartment())
                                                            .set(user.isDisabled(),User::isDisabled,user.isDisabled()?1:0)
                                                            //设置Username防止用户传了一个空的东西导致sql没有set
                                                            .set(StringUtils.isNotBlank(user.getUsername()),User::getUsername,user.getUsername())
                                                            .eq(userPo.getId()!=null,User::getId,userPo.getId()));
        log.info("用户:"+user.getUsername()+"基础资料更新{"+i+"}条数据");
        if(i == 0){
            throw new AccountException("更新用户"+user.getUsername()+"数据失败");
        }
        return i!=0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public boolean userDelete(UserDto user) throws AccountException {
        //查询服务器内数据
        User userPo = userSummaryInfo(user);
        if(userPo==null){
            throw new AccountException("删除用户"+user.getUsername()+"失败,用户不存在");
        }
        BeanUtils.copyProperties(userPo,user);
        //获得删除用户下的所有角色
        List<Integer> roleIds = roleService.listRolesByUser(user).stream().map(Role::getId).collect(Collectors.toList());
        user.setRoleIds(roleIds);
        //在用户角色表下删除所有相关关系
        roleService.deleteRolesForUser(user);

        int i = userMapper.deleteById(userPo);
        if(i == 0){
            throw new AccountException("删除用户"+user.getUsername()+"数据失败");
        }
        return i!=0;
    }
}
