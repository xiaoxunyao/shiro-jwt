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
@TableName("tbl_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 许可序号
     */
    @TableId(value = "permission_id",type = IdType.UUID)
    private String permissionId;

    /**
     * 许可名字
     */
    private String permissionName;


}
