package com.gxzygygs.iom.modules.sys.entity.Po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.gxzygygs.iom.common.strategy.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author yulin
 * @since 2022-03-25
 */
@ApiModel(value = "User对象", description = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("帐号")
    @TableField("`account`")
    @NotBlank(message = "用户名不能为空",groups = {Select.class,Delete.class,Update.class,Insert.class,Auth.class})
    private String account;

    @ApiModelProperty("密码")
    @TableField("`password`")
    @NotBlank(message = "密码不能为空",groups = {Auth.class,Insert.class})
    private String password;

    @ApiModelProperty("昵称")
    @NotBlank(message = "注册昵称不能为空",groups = Insert.class)
    private String username;

    @ApiModelProperty("注册时间")
    private Date regTime;

    @NotBlank(message = "注册邮箱不能为空",groups = Insert.class)
    @ApiModelProperty("邮箱")
    @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$",message = "邮箱格式不对",groups = {Insert.class})
    private String email;


    @NotBlank(message = "注册手机不能为空",groups = Insert.class)
    @Pattern(regexp = "1[3456789]\\d{9}",message = "手机号格式不对",groups = {Insert.class})
    @ApiModelProperty("手机")
    private String telephone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account=" + account +
                ", password=" + password +
                ", username=" + username +
                ", regTime=" + regTime +
                ", email=" + email +
                ", telephone=" + telephone +
                "}";
    }
}
