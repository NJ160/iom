package com.gxzygygs.iom.modules.sys.entity.Dto;

import com.gxzygygs.iom.modules.sys.entity.Po.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class JwtUser {

    String username;

    private List<String> roles;

    private List<String> permissions;

    public JwtUser() {
    }
}
