package com.bamenyouxi.core.util;

import com.bamenyouxi.core.constant.TipMsgConstant;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 文件上传下载工具类
 * Created by 13477 on 2017/7/31.
 */
public final class FileUtil {

	public static void read(HttpServletResponse response, String filePath) throws Exception {
		InputStream input = null;
		OutputStream output = null;

		try {
			File file = new File(filePath);
			String fileName = URLEncoder.encode(file.getName(), "UTF-8");
			if (fileName.length() > 150)
				fileName = new String(fileName.getBytes("gbk"), "ISO8859-1");

			input = new BufferedInputStream(new FileInputStream(filePath));
			byte[] buffer = new byte[input.available()];
			input.read(buffer);

			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			response.setContentType("application/octet-stream");
			output = new BufferedOutputStream(response.getOutputStream());
			output.write(buffer);
		} catch (Exception e) {
			throw new Exception(TipMsgConstant.EXCEPTION_FILE_DOWNLOAD);
		} finally {
			try {
				if (input != null) input.close();
				if (output != null) {
					output.flush();
					output.close();
				}
			} catch (IOException e) {
				throw new IOException(TipMsgConstant.EXCEPTION_FILE_DOWNLOAD);
			}
		}
	}
}
