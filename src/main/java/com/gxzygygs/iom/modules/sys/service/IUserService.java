package com.gxzygygs.iom.modules.sys.service;

import com.gxzygygs.iom.modules.sys.entity.Po.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.security.auth.login.AccountException;
import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author yulin
 * @since 2022-03-25
 */
public interface IUserService extends IService<User> {

    User findUserByAccount(User user);

    boolean checkIfAccountExist(User user);

    User userRegister(User user, List<Integer> roleIds) throws AccountException;

    User userSummaryInfo(User user) throws AccountException;

    User userDetailInfo(User user) throws AccountException;

    boolean userInfoUpdate(User user) throws AccountException;

    boolean userDelete(User user) throws AccountException;
}
