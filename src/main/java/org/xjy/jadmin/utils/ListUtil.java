package org.xjy.jadmin.utils;


import com.jfinal.plugin.activerecord.Record;
import com.xiaoleilu.hutool.util.StrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"rawtypes" })
public class ListUtil {
	private ListUtil(){
	}
	
	public static boolean isEmpty(List list){
		if(list == null || list.size() == 0){
			return true;
		}else{
			return false;
		}
	}
	public static boolean isHaveMoreThanOne(List list){
		if(list != null && list.size() > 1){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isNotEmpty(List list){
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isHaveOne(List list){
		if(list != null && list.size() == 1){
			return true;
		}else{
			return false;
		}
	}
	
	public static List<Map<String,Object>>  convertList(List<Record> dataList){
		List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
		if(isNotEmpty(dataList)){
			for(int i = 0; i <dataList.size(); i++){
				Record data = dataList.get(i);
				rsList.add(data.getColumns());
			}
		}
		return rsList;
	}
	
	/**
	 * 将list2string的字符串转换成list
	 * <p>示例： ["13600000000","15000000000","8025 1922","8025 1234"]</p>
	 * @param str
	 * @return
	 */
	public static List string2List(String str){
		if(StrUtil.isBlank(str))
			return null;
		
		try{
			// 去掉首尾的方括号
			str = str.substring(1, str.length() -1);
			
			String[] splitStr = str.split(",");
			
			List<String> list = new ArrayList<String>();
			
			for(String s : splitStr){
				// 去掉首尾的字符串引号
				list.add(s.substring(1, s.length() - 1));
			}
			
			return list;
			
		} catch (Exception e){
			return null;
		}
	}
}
