package com.bamenyouxi.core.impl.service;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.model.mysql.BaseEntity;
import com.bamenyouxi.core.util.CommonUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.Put;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * crud service
 * Created by 13477 on 2017/6/29.
 */
public abstract class AbstractCrudService<T, ID extends Serializable> implements BaseService<T, ID> {

	@Override
	public abstract CrudMapper<T, ID> getMapper();

	protected String sort() {
		return FieldConstant.SortConstant.CREATE_TIME_DESC;
	}

	protected void checkParamsType(Map<Object, Class<?>> params) {
		params.forEach((k, v) -> {
			if (v.isInstance(k)) return;
			if (Integer.class.equals(v) && CommonUtil.isNumeric(String.valueOf(k)))
				return;
			throw new IllegalArgumentException(TipMsgConstant.PARAM_INVALID);
		});
	}

	protected void getBefore(Map<String, Object> params) {}
	protected void saveBefore(T t) {}
	protected void updateBefore(T t) {}
	protected  void DeleteBefore(T t){}

	protected void listBefore(Map<String, Object> params) {
		params.put(SysConstant.PageConstant.DEFAULT_PAGE_NAME, null);
		params.put(SysConstant.PageConstant.DEFAULT_SIZE_NAME, null);
	}

	public T get(Map<String, Object> params) {
		this.getBefore(params);
		return CommonUtil.isEmpty(getMapper().get(params)).get(0);
	}

	public T save(T t) {
		this.saveBefore(t);
		getMapper().save(t);
		return t;
	}

	@Transactional
	public T update(T t) {
		this.updateBefore(t);
		Assert.isTrue(getMapper().update(t) > 0, TipMsgConstant.OPERATION_FAILED);
		return (t instanceof BaseEntity) ?
				CommonUtil.isEmpty(getMapper().get(new HashMap<String, Object>() {{
					put(FieldConstant.DBFieldConstant.F_ID.name(), ((BaseEntity) t).getId());
				}})).get(0) : null;
	}

	public List<T> list(Map<String, Object> params) {
		this.listBefore(params);
		return getMapper().get(params);
	}

	public PageInfo<T> list(int page, int size, Map<String, Object> params) {
		this.listBefore(params);
		PageHelper.startPage(page, size, sort());
		return new PageInfo<>(getMapper().get(params));
	}

	public void delete(T t){
		this.DeleteBefore(t);
		Assert.isTrue(getMapper().delete(t)>0,TipMsgConstant.OPERATION_FAILED);
	}

}
