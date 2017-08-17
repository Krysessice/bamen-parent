package com.bamenyouxi.core.util;

import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.model.excel.impl.AbstractExportExcel;
import com.bamenyouxi.core.model.poi.PoiExcel;
import com.bamenyouxi.core.model.poi.PoiExcelRow;
import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 *
 * Created by 13477 on 2017/7/29.
 */
public final class PoiExcelUtil<T extends AbstractExportExcel.Field> {

	private AbstractExportExcel<T> abstractExportExcel;

	private PoiExcelUtil(AbstractExportExcel<T> abstractExportExcel) {
		Assert.notNull(abstractExportExcel, TipMsgConstant.PARAM_INVALID);
		this.abstractExportExcel = abstractExportExcel;
	}

	public static <T extends AbstractExportExcel.Field> PoiExcelUtil getInstance(AbstractExportExcel<T> abstractExportExcel) {
		return new PoiExcelUtil<>(abstractExportExcel);
	}

	public void createExcel() throws IOException {
		PoiExcel poiExcel = PoiExcel.getInstance(abstractExportExcel.getChild());
		if (abstractExportExcel.getParent() != null)
			poiExcel.parent(abstractExportExcel.getParent());
		PoiExcelRow poiExcelRow = poiExcel.sheet().createSheet(abstractExportExcel.getSheetName()).row().createRow();
		abstractExportExcel.getTitles().forEach(title ->
			poiExcelRow.cell().cellValue(title).createCell()
		);
		List<T> fields = abstractExportExcel.getFields();
		if (fields != null)
			fields.forEach(field -> {
				try {
					poiExcelRow.makeRow().row().createRow();
					for (Field item : field.getClass().getDeclaredFields()) {
						item.setAccessible(true);
						poiExcelRow.cell().cellValue(item.get(field)).createCell();
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			});
		poiExcelRow.makeRow().makeSheet().makeExcel();
	}
}
