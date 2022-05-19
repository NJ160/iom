package com.gxzygygs.iom.modules.sys.entity.Vo;


import lombok.Data;

import java.util.List;

@Data
public class UserRoleVo {
    private String username;
    private List<String> roles;
    private List<String> permission;
}
