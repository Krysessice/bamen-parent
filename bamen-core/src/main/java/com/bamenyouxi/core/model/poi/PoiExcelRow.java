package com.bamenyouxi.core.model.poi;

import com.bamenyouxi.core.constant.TipMsgConstant;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.util.Assert;

/**
 * poi excel sheet row
 * Created by 13477 on 2017/7/27.
 */
public class PoiExcelRow {

	private static PoiExcelRow poiExcelRow;
	private PoiExcelSheet poiExcelSheet;
	private Sheet sheet;
	private Row row;
	private static int preLineNum = -1; // 前一行索引
	private int lineNum;                // 当前行索引

	private PoiExcelRow() {
		reset();
	}

	private PoiExcelRow(PoiExcelSheet poiExcelSheet) {
		Assert.notNull(poiExcelSheet, TipMsgConstant.PARAM_INVALID);
		if (poiExcelRow == null)
			poiExcelRow = new PoiExcelRow();
		poiExcelRow.poiExcelSheet = poiExcelSheet;
		poiExcelRow.sheet = poiExcelSheet.getSheet();
		poiExcelRow.lineNum = ++preLineNum;
		PoiExcelCell.reset();
	}

	static PoiExcelRow getInstance(PoiExcelSheet poiExcelSheet) {
		return new PoiExcelRow(poiExcelSheet);
	}

	public PoiExcelRow lineNum(int lineNum) {
		Assert.isTrue(lineNum >= 0, TipMsgConstant.PARAM_INVALID);
		preLineNum = lineNum;
		poiExcelRow.lineNum = lineNum;
		return poiExcelRow;
	}

	public PoiExcelRow createRow() {
		poiExcelRow.row = poiExcelRow.sheet.createRow(poiExcelRow.lineNum);
		return poiExcelRow;
	}

	public <T> PoiExcelCell<T> cell() {
		return PoiExcelCell.getInstance(poiExcelRow);
	}

	Row getRow() {
		return poiExcelRow.row;
	}

	static void reset() {
		preLineNum = -1;
	}

	public PoiExcelSheet makeRow() {
		return poiExcelRow.poiExcelSheet;
	}
}
