package org.xjy.jadmin.auth.controller;


import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.xiaoleilu.hutool.util.StrUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 主要用于扩充一些公共的方法，所有controller继承此类
 * @author jianyu
 *
 */
public class BaseController extends Controller {
	
	/**
	 * 获取当前页码
	 * @return
	 */
	protected int getPageNo(){
		String pageNo = getPara("pageNo");
		if(StrUtil.isBlank(pageNo)){
			pageNo = "1";
		}
		return Integer.parseInt(pageNo);
	}

	/**
	 * ajax回应信息 作者:肖建宇
	 * 
	 * @param jsonStr
	 */
	protected void returnJson(String jsonStr) {
		HttpServletResponse response = super.getResponse();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
	
	/**
	 * ajax回应jsonp信息 作者:肖建宇
	 * 
	 * @param callbackName
	 * @param jsonStr
	 */
	protected void returnJsonp(String callbackName, String jsonStr) {
		HttpServletResponse response = super.getResponse();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			String output = callbackName + "(" + jsonStr + ");";
			out = response.getWriter();
			out.write(output);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
	


    /**
	 * 返回json或者jsonp格式的数据
	 * @param result
	 */
	protected void renderJsonOrJsonp(Object result){
		
		String callback = getPara("callback");
		
		if(StrUtil.isEmpty(callback))
			renderJson(result);
		else
			renderText(callback + "(" + JsonKit.toJson(result, 6) + ")");
	}
	
	/**
	 * 校验必须参数是否有为空
	 * @param paramsMap 传递参数
	 * @param needParams 必须的参数数组
	 * @return true 有空  false 都不为空
	 */
	protected boolean checkParamsNull(Map paramsMap, String[] needParams){
		for (String param : needParams) {
			if (!paramsMap.containsKey(param) || StrUtil.isEmpty((String) paramsMap.get(param)))
				return true;
		}

		return false;
	}
	
	/**
	 * 重定向跳转到一个完整url
	 * 
	 * @param fullUrl
	 */
	protected void redirectUrl(String fullUrl){
		try {
			super.getResponse().sendRedirect(fullUrl);
			renderNull();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}


