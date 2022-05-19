package com.gxzygygs.iom.configs.shiro;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gxzygygs.iom.modules.sys.entity.Po.Permission;
import com.gxzygygs.iom.modules.sys.entity.Po.Role;
import com.gxzygygs.iom.modules.sys.entity.Po.User;
import com.gxzygygs.iom.modules.sys.service.IPermissionService;
import com.gxzygygs.iom.modules.sys.service.IRoleService;
import com.gxzygygs.iom.modules.sys.service.IUserService;
import com.gxzygygs.iom.utils.AesCipherUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class UserRealm extends AuthorizingRealm {

    @Autowired
    IUserService userService;
    @Autowired
    IRoleService roleService;
    @Autowired
    IPermissionService permissionService;

    private static final transient Logger log = LoggerFactory.getLogger(UserRealm.class);

    /**
     * 获取角色与权限
     *  doGetAuthorizationInfo执行时机有三个，如下：
     *  1、subject.hasRole(“admin”) 或 subject.isPermitted(“admin”)：自己去调用这个是否有什么角色或者是否有什么权限的时候；
     *  2、@RequiresRoles("admin") ：在方法上加注解的时候；
     *  3、@shiro.hasPermission name = "admin"/@shiro.hasPermission："dustin:test"在页面上加shiro标签的时候，即进这个页面的时候扫描到有这个标签的时候。
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();
        log.info("用户"+ user.getAccount() +"进入"+getName()+"域进行权限认证");
        List<Role> rolePos = roleService.listRolesByUser(user);
        for (Role rolePo : rolePos) {
            if (rolePo != null) {
                // 添加角色
                simpleAuthorizationInfo.addRole(rolePo.getName());
               // 根据用户角色查询权限
                List<Permission> PermissionPos = permissionService.listPermissionsByRole(rolePo);
                for (Permission PermissionPo : PermissionPos) {
                    if (PermissionPo != null) {
                        // 添加权限
                        simpleAuthorizationInfo.addStringPermission(PermissionPo.getPerCode());
                    }
                }
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 登录信息验证
     * 1.doGetAuthenticationInfo执行时机如下
     * 当调用Subject currentUser = SecurityUtils.getSubject();
     * currentUser.login(token);
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("进入"+getName()+"域进行登录认证");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String account = token.getUsername();

        log.info("用户授权验证:"+account);
        String password = new String(token.getPassword());
        if (StringUtils.isEmpty(account)) {
            throw new AuthenticationException("用户名不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            throw new AuthenticationException("密码不能为空");
        }
        User user = new User();
        user.setAccount(account);
        user = userService.findUserByAccount(user);

        if (!user.getPassword().equals(AesCipherUtil.enCrypto(password))){
            throw new IncorrectCredentialsException("密码错误");
        }
        return new SimpleAuthenticationInfo(user //服务器内的数据
                , password //用户传来的凭证（密码）
                , getName()); //当前realm的名字
    }
}