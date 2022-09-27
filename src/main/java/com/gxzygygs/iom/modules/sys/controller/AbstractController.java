package com.gxzygygs.iom.modules.sys.controller;

import com.gxzygygs.iom.constant.Constant;
import com.gxzygygs.iom.jwt.JwtToken;
import com.gxzygygs.iom.modules.sys.entity.Dto.JwtUser;
import com.gxzygygs.iom.modules.sys.entity.Po.User;
import com.gxzygygs.iom.utils.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

    /**
     * Controller公共组件
     *
     * @author Mark sunlightcs@gmail.com
     */
public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected String getToken() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }
    protected String getUsername() {
        String token = getToken();
        String username = JwtUtil.getClaim(token, Constant.USERNAME);
        return username;
    }


//    protected String getUserUsername() {
//        return getUser().getUsername();
//    }
}

