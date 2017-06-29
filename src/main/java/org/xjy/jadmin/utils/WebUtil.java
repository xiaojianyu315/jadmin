package org.xjy.jadmin.utils;


import com.xiaoleilu.hutool.date.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings({"rawtypes" })
public class WebUtil {
	private static String encoding = "UTF-8";

	private WebUtil(){
	}

	public static Map getParameterMap(HttpServletRequest request) {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}

	public void renderText(String text,HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.setContentType("text/plain");
			response.setCharacterEncoding(encoding);
			writer = response.getWriter();
			writer.write(text);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException("renderText Exception",e);
		} finally {
			if(writer != null) {
				writer.close();
			}

		}
	}


}
