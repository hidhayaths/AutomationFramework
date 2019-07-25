package com.framework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Settings {
	
	private static Properties properties; 
	private static final String fileName ="Settings.properties";
	
	private Settings() {
		
	}
	
	public static Properties getInstance() {
		if(properties==null) 
			properties = loadProperties();
			
		return properties;
	}
	
	private static Properties loadProperties() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(fileName));
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
			//throw new FrameworkException(String.format("File Not found exception while loading property file with name '%s'", fileName));
			throw new RuntimeException(String.format("File Not found exception while loading property file with name '%s'", fileName));
		}catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException(String.format("IO Exception while loadding property file '%s'", fileName));
		}
		return properties;
	}
	@Override
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException();
	}
}
