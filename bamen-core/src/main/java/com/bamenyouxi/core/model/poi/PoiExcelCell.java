package com.bamenyouxi.core.model.poi;

import com.bamenyouxi.core.constant.TipMsgConstant;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;

/**
 * poi excel row cell
 * Created by 13477 on 2017/7/28.
 */
public class PoiExcelCell<T> {

	private static PoiExcelCell poiExcelCell;
	private PoiExcelRow poiExcelRow;
	private Row row;
	private T cellValue;
	private CellStyle cellStyle;
	private static int preCellNum = -1;     // 前一格单元格索引
	private int cellNum;                    // 当前单元格索引

	private PoiExcelCell() {
		reset();
	}

	private PoiExcelCell(PoiExcelRow poiExcelRow) {
		Assert.notNull(poiExcelRow, TipMsgConstant.PARAM_INVALID);
		if (poiExcelCell == null)
			poiExcelCell = new PoiExcelCell<>();
		poiExcelCell.poiExcelRow = poiExcelRow;
		poiExcelCell.row = poiExcelRow.getRow();
		poiExcelCell.cellValue = null;
		poiExcelCell.cellStyle = null;
		poiExcelCell.cellNum = ++preCellNum;
	}

	static <T> PoiExcelCell<T> getInstance(PoiExcelRow poiExcelRow) {
		return new PoiExcelCell<>(poiExcelRow);
	}

	public PoiExcelCell cellNum(int cellNum) {
		Assert.isTrue(cellNum >= 0, TipMsgConstant.PARAM_INVALID);
		preCellNum = cellNum;
		poiExcelCell.cellNum = cellNum;
		return poiExcelCell;
	}

	public PoiExcelCell cellValue(T cellValue) {
		poiExcelCell.cellValue = cellValue;
		return poiExcelCell;
	}

	public PoiExcelCell cellStyle(CellStyle cellStyle) {
		poiExcelCell.cellStyle = cellStyle;
		return poiExcelCell;
	}

	public PoiExcelRow createCell() {
		Cell cell = row.createCell(poiExcelCell.cellNum);

		Object cellValue = poiExcelCell.cellValue;
		if (cellValue != null) {

			if (cellValue instanceof String)
				cell.setCellValue((String) cellValue);
			else if (cellValue instanceof Integer)
				cell.setCellValue((Integer) cellValue);
			else if (cellValue instanceof Date)
				cell.setCellValue((Date) cellValue);
			else if (cellValue instanceof Boolean)
				cell.setCellValue((Boolean) cellValue);
			else if (cellValue instanceof Double)
				cell.setCellValue((Double) cellValue);
			else if (cellValue instanceof BigDecimal)
				cell.setCellValue(((BigDecimal) cellValue).doubleValue());
			else
				cell.setCellValue(cellValue.toString());
		}

		if (poiExcelCell.cellStyle != null)
			cell.setCellStyle(poiExcelCell.cellStyle);

		return poiExcelCell.poiExcelRow;
	}

	static void reset() {
		preCellNum = -1;
	}
}
