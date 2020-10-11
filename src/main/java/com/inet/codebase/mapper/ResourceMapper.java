package com.inet.codebase.mapper;

import com.inet.codebase.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author HCY
 * @since 2020-10-11
 */
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {
    /**
     * 获取 userID 的所有的所有小功能
     * @author HCY
     * @since 2020-10-11
     * @param userID
     * @return
     */
    List<Resource> getListAndResource(String userID);
}
