package com.gxzygygs.iom.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gxzygygs.iom.exceptions.customExceptions.AccountException;
import com.gxzygygs.iom.modules.sys.entity.Po.Role;
import com.gxzygygs.iom.modules.sys.entity.Po.User;
import com.gxzygygs.iom.modules.sys.mapper.UserMapper;
import com.gxzygygs.iom.modules.sys.service.IRoleService;
import com.gxzygygs.iom.modules.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxzygygs.iom.utils.AesCipherUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

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
    public User findUserByAccount(User user){
        User userPo = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getAccount, user.getAccount()).last("LIMIT 1"));
        if(userPo==null){
            throw new AccountException("用户{"+ user.getAccount() +"}不存在");
        }
        return userPo;
    }

    @Override
    public boolean checkIfAccountExist(User user) {
        User userPo = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getAccount, user.getAccount()).last("LIMIT 1"));
        if(userPo == null){
            throw new AccountException("用户{"+ user.getAccount() +"}已存在");
        }
        return true;
    }

    @Override
    public User userRegister(User user, List<Integer> roleIds) throws AccountException {

        //判断用户账户是否已存在
        checkIfAccountExist(user);
        //初始化持久层用户对象
        User userPo = new User();
        BeanUtils.copyProperties(user,userPo);
        String password = AesCipherUtil.enCrypto(user.getPassword());
        user.setPassword(password);
        user.setRegTime(new Date());
        //插入用户表
        int i = userMapper.insert(user);
        if(i==0){
            throw new AccountException("用户{"+ user.getAccount() +"}注册失败,插入数据库失败");
        }
        //获得数据库内带ID的用户对象
        userPo = findUserByAccount(userPo);
        //插入用户角色表
        roleService.insertRolesForUser(userPo,roleIds);

        return user;
    }

    @Override
    public User userSummaryInfo(User user) throws AccountException {
        User userPo = findUserByAccount(user);
        return userPo;
    }

    @Override
    public User userDetailInfo(User user) throws AccountException {
        User userPo = findUserByAccount(user);
        return userPo;
    }

    @Override
    public boolean userInfoUpdate(User user) throws AccountException {
        if(StringUtils.isBlank(user.getPassword())){
            user.setPassword(null);
        }else{
            user.setPassword(AesCipherUtil.enCrypto(user.getAccount()));
        }
        //查询服务器内数据
        User userPo = userSummaryInfo(user);
        //通过ID修改信息
        int i = userMapper.update(null,new LambdaUpdateWrapper<User>().set(StringUtils.isNotBlank(user.getPassword()),User::getPassword,user.getPassword())
                                                            .set(StringUtils.isNotBlank(user.getEmail()),User::getEmail,user.getEmail())
                                                            .set(StringUtils.isNotBlank(user.getTelephone()),User::getTelephone,user.getTelephone())
                                                            .set(StringUtils.isNotBlank(user.getUsername()),User::getUsername,user.getUsername())
                                                            .eq(userPo.getId()!=null,User::getId,userPo.getId()));
        log.info("用户:"+user.getAccount()+"基础资料更新{"+i+"}条数据");
        if(i == 0){
            throw new AccountException("更新用户"+user.getAccount()+"数据失败");
        }
        return i!=0;
    }

    @Override
    public boolean userDelete(User user) throws AccountException {
        //查询服务器内数据
        User userPo = userSummaryInfo(user);
        int i = userMapper.deleteById(user);
        if(i == 0){
            throw new AccountException("删除用户"+user.getAccount()+"数据失败");
        }
        return i!=0;
    }
}
