package com.gxzygygs.iom.modules.sys.service;

import com.gxzygygs.iom.modules.sys.entity.Dto.UserDto;
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

    UserDto findUserByUsername(UserDto user);

    boolean checkIfUserNameExist(UserDto user);

    UserDto userRegister(UserDto user) throws AccountException;

    UserDto userSummaryInfo(UserDto user) throws AccountException;

    UserDto userDetailInfo(UserDto user) throws AccountException;

    boolean userInfoUpdate(UserDto user) throws AccountException;

    boolean userDelete(UserDto user) throws AccountException;
}
