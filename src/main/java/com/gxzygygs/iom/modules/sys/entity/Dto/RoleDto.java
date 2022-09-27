package com.gxzygygs.iom.modules.sys.entity.Dto;

import com.gxzygygs.iom.common.strategy.Insert;
import com.gxzygygs.iom.common.strategy.Update;
import com.gxzygygs.iom.modules.sys.entity.Po.Role;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RoleDto extends Role{

    @Valid
    @NotNull(message = "权限不能为空",groups = {Insert.class, Update.class})
    @Size(min = 1, message = "注册用户至少得拥有一个权限",groups = {Insert.class, Update.class})
    private List<Integer> permissionIds;
}

