package com.gxzygygs.iom.modules.sys.entity.Vo;
import lombok.Data;
import java.util.Date;

@Data
public class UserVo {
    private String account;
    private String username;
    private Date regTime;
    private String email;
    private String telephone;
}
