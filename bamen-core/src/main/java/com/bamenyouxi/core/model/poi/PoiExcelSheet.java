package com.bamenyouxi.core.model.poi;

import com.bamenyouxi.core.constant.TipMsgConstant;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.springframework.util.Assert;

/**
 * poi excel sheet
 * Created by 13477 on 2017/7/27.
 */
public class PoiExcelSheet {

	private PoiExcel poiExcel;
	private Workbook wb;
	private Sheet sheet;

	private PoiExcelSheet(PoiExcel poiExcel) {
		Assert.notNull(poiExcel, TipMsgConstant.PARAM_INVALID);
		this.poiExcel = poiExcel;
		this.wb = poiExcel.getWb();
		PoiExcelRow.reset();
	}

	static PoiExcelSheet getInstance(PoiExcel poiExcel) {
		return new PoiExcelSheet(poiExcel);
	}

	public PoiExcelSheet createSheet(String sheetName) {
		this.sheet = this.wb.createSheet(WorkbookUtil.createSafeSheetName(sheetName));
		return this;
	}

	public PoiExcelRow row() {
		return PoiExcelRow.getInstance(this);
	}

	Sheet getSheet() {
		return this.sheet;
	}

	public PoiExcel makeSheet() {
		return this.poiExcel;
	}
}
