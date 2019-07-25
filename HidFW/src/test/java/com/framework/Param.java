package com.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Param {
	private Hashtable<String, String> tblParams;

	public Param(String params) {
		tblParams = new Hashtable<>();
		List<String> parmList = Arrays.asList(StringUtils.split(params, "**"));
		parmList.forEach(str->tblParams.put(StringUtils.substringBefore(str, "="), StringUtils.substringAfter(str, "=")));
	}
	
	public String getParam(String key) {
		return tblParams.containsKey(key)?tblParams.get(key):"";
	}
	
	public String toString() {
		return tblParams.toString().replace("{", "").replace("}", "");
	}
	public boolean hasParam(String key) {
		return tblParams.containsKey(key);
	}
	public List<String> getParameterNames(){
		return new ArrayList<String>(tblParams.keySet());
		
	}
	public List<String> getAllValues(){
		return new ArrayList<String>(tblParams.values());
	}
	
}
