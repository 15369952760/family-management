package com.family.config.base;

/**
 * @Author: yuandh
 * @Description: 通用dao
 * @Date: Created in 14:49 2019/3/21
 * @Modified By:
 */

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
public interface BaseMapper<T> extends Mapper<T>,MySqlMapper<T>{
}
