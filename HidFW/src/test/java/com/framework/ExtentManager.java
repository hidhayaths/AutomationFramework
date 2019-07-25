package com.framework;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentManager {
	
	private static String reportPath;
	private static String screenShotPath;
	 private static ExtentReports extent;
	    
	    public static ExtentReports getInstance() {
	    	if (extent != null) return extent;
	    	
	    	reportPath = String.format("Results//Test_%s//", Utils.getCurrentDateTimeAsString());
	    	screenShotPath= reportPath+"Screenshots";
	    	System.setProperty("report.dir", reportPath);
	    	System.setProperty("screenshot.dir", screenShotPath);
    		new File(reportPath).mkdirs();
    		new File(screenShotPath).mkdir();
    		return createInstance();
	    }
	    
	    private static ExtentReports createInstance() {
	        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath+"Report.html");
	        htmlReporter.loadXMLConfig("extent-config.xml");
	        extent = new ExtentReports();
	        extent.attachReporter(htmlReporter);
	        extent.setSystemInfo("User", System.getProperty("user.name"));
	        extent.setSystemInfo("OS", System.getProperty("os.name"));
	        extent.setSystemInfo("OS Version", System.getProperty("os.version"));
	        extent.setSystemInfo("Computer Name", System.getenv("COMPUTERNAME"));
	        extent.setSystemInfo("Time Zone",System.getProperty("user.timezone"));
	        return extent;
	    }
}
