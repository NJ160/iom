package com.gxzygygs.iom.modules.sys.entity.Vo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gxzygygs.iom.common.strategy.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class UserVo {
    private String account;
    private String username;
    private Date regTime;
    private String email;
    private String telephone;
    private String department;
}
