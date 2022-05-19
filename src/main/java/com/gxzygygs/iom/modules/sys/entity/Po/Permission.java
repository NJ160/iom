package com.gxzygygs.iom.modules.sys.entity.Po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.gxzygygs.iom.common.strategy.Delete;
import com.gxzygygs.iom.common.strategy.Insert;
import com.gxzygygs.iom.common.strategy.Select;
import com.gxzygygs.iom.common.strategy.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author yulin
 * @since 2022-03-29
 */
@ApiModel(value = "Permission对象", description = "资源表")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @NotBlank(message = "权限Id不能为空",groups = {Select.class, Delete.class, Update.class})
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("资源名称")
    @NotBlank(message = "权限名称不能为空",groups = {Insert.class})
    @TableField("`name`")
    private String name;

    @ApiModelProperty("权限代码字符串")
    @NotBlank(message = "权限代码不能为空",groups = {Insert.class})
    private String perCode;

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
    public String getPerCode() {
        return perCode;
    }

    public void setPerCode(String perCode) {
        this.perCode = perCode;
    }

    @Override
    public String toString() {
        return "Permission{" +
            "id=" + id +
            ", name=" + name +
            ", perCode=" + perCode +
        "}";
    }
}
