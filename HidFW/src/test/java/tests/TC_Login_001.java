package tests;

import org.openqa.selenium.Alert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.framework.Base;
import com.framework.Param;

import pageObjects.HomePage;
import pageObjects.LoginPage;

public class TC_Login_001 extends Base {
		
	@Test(dataProvider="testParams")
	public void loginTest(Param param) {
		System.out.println(param);
		LoginPage login= new LoginPage(driver);
		HomePage home = new HomePage(driver);
		driver.get(properties.getProperty("Application_URL"));
		String username = param.getParam("strUserName");
		login.Login(username, param.getParam("strPassword"));
		log.assertTrue(username.equalsIgnoreCase(home.getLoggedInUser()), "Verify Login", "User logged In",
				"User Not Logged In");
		home.clickMenu("Log out");
		try {
			Thread.sleep(3000);
			Alert alert = driver.switchTo().alert();
			alert.accept();
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		log.addReportMessage("Logout", "user Logged Out", Status.INFO);
	}
}
