package com.gxzygygs.iom.modules.sys.controller;

import com.gxzygygs.iom.modules.sys.entity.Po.User;
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

    protected User getUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    protected String getUserAccount() {
        return getUser().getAccount();
    }
}

