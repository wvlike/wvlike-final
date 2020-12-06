package com.ismyself.testDmo.common.tk;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * package com.ismyself.testDmo.common.tk;
 *
 * @auther txw
 * @create 2020-09-22  21:56
 * @description：
 */
public interface CommonMapper<T> extends Mapper<T>, IdsMapper<T>, ConditionMapper<T> {
}
