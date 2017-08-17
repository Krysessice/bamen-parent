package com.bamenyouxi.core.model.poi;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * poi excel model
 * Created by 13477 on 2017/7/27.
 */
public class PoiExcel {

	private Workbook wb;
	private String parent = "/excel/";
	private final String child;

	private PoiExcel(String child) {
		this.child = child;
		this.wb = new XSSFWorkbook();
	}

	public static PoiExcel getInstance(String child) {
		return new PoiExcel(child);
	}

	public PoiExcel parent(String parent) {
		this.parent = parent;
		return this;
	}

	public PoiExcelSheet sheet() {
		return PoiExcelSheet.getInstance(this);
	}

	Workbook getWb() {
		return this.wb;
	}

	public void makeExcel() throws IOException {
		File parentFile = new File(parent);
		if (!parentFile.exists())
			parentFile.mkdirs();
		FileOutputStream fileOut = new FileOutputStream(new File(parentFile, child));
		this.wb.write(fileOut);
		fileOut.close();
	}
}
