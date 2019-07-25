package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	@FindBy(how=How.XPATH,using="//td[starts-with(text(),'Manger Id')]")
	private static WebElement loggedInUser;
	
	@FindBy(how=How.CSS,using="ul.menusubnav li>a")
	private static List<WebElement> menus;
	
	@FindBy(how=How.CSS,using="ul.menusubnav li.orange>a")
	private static WebElement activeMenu;
	
	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public String getLoggedInUser() {
		return StringUtils.substringAfter(loggedInUser.getText(), ":").trim();
	}
	
	public List<String> getMenus(){
		List<String> allMenus = new ArrayList<String>();
		menus.forEach(el->allMenus.add(el.getText()));
		return allMenus;
	}
	
	public String getActiveMenu() {
		return activeMenu.getText();
	}
	
	public void clickMenu(String menu) {
		menus.stream().filter(el->el.getText().equalsIgnoreCase(menu)).findFirst().get().click();
	}
	
}
