package com.framework;

public enum Browser {
	CHROME("chrome"),
	FIREFOX("firefox"),
	INTERNET_EXPLORER("internet_explorer"),
	OPERA("opera"),
	SAFARI("safari");
	
	private String value;
	
	Browser(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
