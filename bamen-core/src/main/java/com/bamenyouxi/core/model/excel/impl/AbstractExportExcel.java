package com.bamenyouxi.core.model.excel.impl;

import java.util.List;

/**
 * abstract excel export layout
 * Created by 13477 on 2017/7/29.
 */
public abstract class AbstractExportExcel<T extends AbstractExportExcel.Field> {

	protected String parent;
	protected String child;
	protected String sheetName;

	protected List<String> titles;
	protected List<T> fields;

	public String getParent() {
		return parent;
	}

	public String getChild() {
		return child;
	}

	public String getSheetName() {
		return sheetName;
	}

	public List<String> getTitles() {
		return titles;
	}

	public List<T> getFields() {
		return fields;
	}

	public interface Field {

	}
}
