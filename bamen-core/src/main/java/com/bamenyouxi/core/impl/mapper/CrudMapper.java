package com.bamenyouxi.core.impl.mapper;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * mapper for crud
 * Created by hc on 2017/7/9.
 */
public interface CrudMapper<T, ID extends Serializable> extends BaseMapper<T, ID> {

    List<T> get(@Param("params") Map<String, Object> params);

    List<T> sum(Map<String, Object> params);

    int save(T t);

    int saveMul(List<T> list);

    int update(T t);

    int delete(T t);
}
