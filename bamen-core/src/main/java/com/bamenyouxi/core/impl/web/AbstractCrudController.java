package com.bamenyouxi.core.impl.web;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.model.result.WebResult;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * crud controller
 * Created by 13477 on 2017/7/10.
 */
public abstract class AbstractCrudController<T, ID extends Serializable> implements BaseController<T, ID> {

	@Override
	public abstract AbstractCrudService<T, ID> getService();

	@GetMapping("/{id}/")
	protected WebResult get(@PathVariable ID id) {
		T t = getService().get(new HashMap<String, Object>() {{
			put(FieldConstant.DBFieldConstant.F_ID.name(), id);
		}});
		return WebResult.of(t);
	}

	@GetMapping("/")
	protected WebResult get(@RequestParam Map<String, Object> params) {
		return WebResult.of(getService().get(params));
	}

	@GetMapping("/list/")
	protected WebResult list(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
	        @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
	        @RequestParam Map<String, Object> params) {
		return WebResult.of(getService().list(page, size, params));
	}

	@PostMapping("/")
	protected WebResult save(@RequestBody T t) {
		getService().save(t);
		return WebResult.of();
	}
}
