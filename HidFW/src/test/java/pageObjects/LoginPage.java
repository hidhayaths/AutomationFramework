package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
		
	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how=How.NAME,using="uid")
	private static WebElement username;
	
	@FindBy(how=How.NAME,using="password")
	private static WebElement password;
	
	@FindBy(how=How.NAME,using="btnLogin")
	private static WebElement btnLogin;
	
	
	public void Login(String userName,String pwd) {
		username.sendKeys(userName);
		password.sendKeys(pwd);
		btnLogin.click();
	}
	
	
}
