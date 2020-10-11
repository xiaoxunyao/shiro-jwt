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
 * @since 2020-10-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_resource")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源序号
     */
    @TableId(value = "resource_id",type = IdType.UUID)
    private String resourceId;

    /**
     * 资源的用户序号
     */
    private String resourceUserId;

    /**
     * 资源的许可序号
     */
    private String resourcePermissionId;
    /**
     * 资源的名字
     */
    private String PermissionName;


}
