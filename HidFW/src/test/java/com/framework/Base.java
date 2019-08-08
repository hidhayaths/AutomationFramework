package com.framework;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class Base {
	
	protected static Properties properties;
	protected static WebDriver driver;
	
	protected static reportEvents log;
	
	private static ExtentReports extent;
	private static ExtentTest parentTest,IterationTest,test;
	
	private static int iteration=1;
	
	@BeforeSuite
	public void setup() {
		extent = ExtentManager.getInstance();
	}
	
	@BeforeClass
    public synchronized void beforeClass() {
		log = new reportEvents();
    	parentTest = extent.createTest(getClass().getName());
        properties = Settings.getInstance();
		String driver_pref = properties.getProperty("DEFAULT_BROWSER", "chrome");
		Browser browser = null;
		for(Browser br:Browser.values()) {
			if(br.getValue().equalsIgnoreCase(driver_pref)) browser = br;
			if(browser!=null) break;
		}
		driver = DriverFactory.getWebDriver(browser);
    }
	
	@DataProvider(name="testParams")
	public Object[][] getTestData(ITestContext context) {
		return DataUtils.getParams(context.getCurrentXmlTest().getParameter("scenario"),getClass().getName());
	}
	
	@BeforeMethod
	public void setupIteration() {
		IterationTest = parentTest.createNode("Iteration:"+iteration);
		iteration++;
	}
	
	@AfterMethod
	public void captureResut(ITestResult result) {
		if(result.getStatus()==ITestResult.FAILURE) IterationTest.fail(result.getThrowable());
    	if(result.getStatus()==ITestResult.SKIP) IterationTest.skip(result.getThrowable());
	}

    @AfterClass
    public void tearDown() {
    	if(extent.getStats().getChildCount()<1) IterationTest.warning("Test did not run");
    	extent.flush();
    	driver.quit();
    }
    
    @AfterSuite
    public void launchResult() throws IOException {
    	File htmlFile = new File(System.getProperty("report.dir")+"Report.html");
    	Desktop.getDesktop().browse(htmlFile.toURI());
    }
    
    protected class reportEvents{
    	
    	 //custom report event method
        public void verifyTrue(boolean condition,String stepName,String passMsg,String failMsg) {
        	test = IterationTest.createNode(stepName);
    			try {
    				if(condition)
    					test.pass(passMsg,MediaEntityBuilder.createScreenCaptureFromBase64String(Utils.takeScreenshot(driver)).build());
    				else
    					test.fail(failMsg,MediaEntityBuilder.createScreenCaptureFromBase64String(Utils.takeScreenshot(driver)).build());
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
        

        }
        
        public void verifyFalse(boolean condition,String stepName,String passMsg,String failMsg) {
        	verifyTrue(!condition, stepName, passMsg, failMsg);
        }
        
        public void assertTrue(boolean condition,String stepName,String passMsg,String failMsg) {
        	verifyTrue(condition, stepName, passMsg, failMsg);
        	Assert.assertTrue(condition, stepName);
        }
        
        public void assertFalse(boolean condition,String stepName,String passMsg,String failMsg) {
        	verifyTrue(!condition, stepName, passMsg, failMsg);
        	Assert.assertFalse(condition, stepName);
        }
        
        public void verifyEquals(String expected,String actual,String stepName,String passMsg,String failMsg) {
        	verifyTrue(expected.equalsIgnoreCase(actual), stepName, passMsg, failMsg);
        }
        
        public void verifyContains(String mainString,String subStr,String stepName,String passMsg,String failMsg) {
        	verifyTrue(mainString.contains(subStr), stepName, passMsg, failMsg);
        }
        
        public void verifyListContains(List<String> list,String subStr,String stepName,String passMsg,String failMsg) {
        	verifyTrue(list.stream().anyMatch(str->str.equalsIgnoreCase(subStr)), stepName, passMsg, failMsg);
        }
        
        public void assertEquals(String expected,String actual,String stepName,String passMsg,String failMsg) {
        	verifyTrue(expected.equalsIgnoreCase(actual), stepName, passMsg, failMsg);
        	Assert.assertEquals(actual, expected,stepName);
        }
        
        public void assertContains(String mainString,String subStr,String stepName,String passMsg,String failMsg) {
        	verifyTrue(mainString.contains(subStr), stepName, passMsg, failMsg);
        	Assert.assertTrue(mainString.contains(subStr), stepName);
        }
        
        public void assertListContains(List<String> list,String subStr,String stepName,String passMsg,String failMsg) {
        	boolean condition = list.stream().anyMatch(str->str.equalsIgnoreCase(subStr));
        	verifyTrue(condition, stepName, passMsg, failMsg);
        	Assert.assertTrue(condition, stepName);
        }
        
        public void addReportMessage(String stepName,String description,Status status) {
        	test = IterationTest.createNode(stepName);
        	test.log(status, description);
        }
    }
   }
