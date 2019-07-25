package com.framework;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Utils {
	public static String takeScreenshot(WebDriver driver) {
		TakesScreenshot ts= (TakesScreenshot) driver;
		String screenshot = ts.getScreenshotAs(OutputType.BASE64);
		File img = ts.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(img, new File(System.getProperty("screenshot.dir")+"//"+getCurrentDateTimeAsString()+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return screenshot;
	}
	
	public static String getCurrentDateTimeAsString() {
		return new SimpleDateFormat("ddMMYYYY_HHmmss").format(new Date());
	}
}
