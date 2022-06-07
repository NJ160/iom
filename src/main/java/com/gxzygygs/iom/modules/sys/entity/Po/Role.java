package com.gxzygygs.iom.modules.sys.entity.Po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.gxzygygs.iom.common.strategy.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author yulin
 * @since 2022-03-29
 */
@ApiModel(value = "Role对象", description = "角色表")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "角色Id不能为空",groups = {Select.class, Delete.class, Update.class})
    private Integer id;

    @ApiModelProperty("角色名称")
    @TableField("`name`")
    @NotBlank(message = "角色名称不能为空",groups = {Insert.class, Update.class})
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
            "id=" + id +
            ", name=" + name +
        "}";
    }
}
