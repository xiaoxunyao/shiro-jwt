package com.inet.codebase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

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
@TableName("tbl_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户序号
     */
    @TableId(value = "user_id",type = IdType.UUID)
    private String userId;

    /**
     * 用户名字
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户权限
     */
    private String userRole;

    /**
     * 用户权限名称
     * 非表中数据
     */
    @TableField(exist = false)
    private String roleName;

    @TableField(exist = false)
    private List<Resource> resourceList;


}
