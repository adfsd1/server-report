package cn.report.utils;

import java.util.HashMap;

import net.sf.json.JSONObject;

/*
 * 将结果集生成JSON格式的工具类
 */
public class JsonUtil {
	public static String getJson(int code,String msg,Object data) {
		HashMap<String, Object> result = new HashMap<String,Object>();
		result.put("code", code);
		result.put("msg", msg);
		result.put("data", data);
		return JSONObject.fromObject(result).toString();
	}
}
