package com.framework;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {
	private static WebDriver driver;
	private static Properties properties = Settings.getInstance();
	private static long  pageLoadTimeout=Long.parseLong(properties.getProperty("PageLoad_Timeout","30").trim());
	private static long  objectLoadTimeout=Long.parseLong(properties.getProperty("ObjectLoad_Timeout","20").trim());
	
	private DriverFactory() {
		
	}
	
	public static WebDriver getWebDriver(Browser browser) {
		switch(browser) {
		case CHROME:
			System.setProperty("webdriver.chrome.driver", properties.getProperty("CHROME_PATH"));
			driver = new ChromeDriver();
			break;
		case FIREFOX:
			System.setProperty("webdriver.gecko.driver", properties.getProperty("FIREFOX_PATH"));
			driver = new FirefoxDriver();
			break;
		case INTERNET_EXPLORER:
			System.setProperty("webdriver.ie.driver", properties.getProperty("INTERNET_EXPLORER_PATH"));
			driver = new InternetExplorerDriver();
			break;
		case OPERA:
			System.setProperty("webdriver.opera.driver", properties.getProperty("OPERA_PATH"));
			driver =  new OperaDriver();
			break;
		case SAFARI:
			System.setProperty("webdriver.safari.driver", properties.getProperty("SAFARI_PATH"));
			driver = new SafariDriver();
			break;
		}
		
		driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(objectLoadTimeout, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		
		return driver;
	}
	

}
