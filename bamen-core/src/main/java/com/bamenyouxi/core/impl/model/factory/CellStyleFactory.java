package com.bamenyouxi.core.impl.model.factory;

import com.bamenyouxi.core.constant.TipMsgConstant;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.Assert;

/**
 * 单元格样式工厂类
 * Created by 13477 on 2017/7/29.
 */
public class CellStyleFactory {
	private static Workbook wb;
	private static CreationHelper creationHelper;

	private CellStyleFactory(Workbook wb) {
		Assert.notNull(wb, TipMsgConstant.PARAM_INVALID);
		CellStyleFactory.wb = wb;
		if (creationHelper == null)
			creationHelper = wb.getCreationHelper();
	}

	public static CellStyleFactory getInstance(Workbook wb) {
		return new CellStyleFactory(wb);
	}

	private CellStyle defaultCellStyle() {
		return wb.createCellStyle();
	}

	public CellStyle defaultDateCellStyle() {
		CellStyle cellStyle = defaultCellStyle();
		cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("m/d/yy"));
		return cellStyle;
	}

	public CellStyle dateCellStyle(String format) {
		Assert.notNull(format, TipMsgConstant.PARAM_INVALID);
		CellStyle cellStyle = defaultCellStyle();
		cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(format));
		return cellStyle;
	}
}
