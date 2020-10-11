package com.inet.codebase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author HCY
 * @since 2020-10-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限序号
     */
    @TableId(value = "role_id",type = IdType.UUID)
    private String roleId;

    /**
     * 权限名称
     */
    private String roleName;


}
