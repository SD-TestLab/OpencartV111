package pageObjects;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testBase.BasePage;

public class MyAccountPage extends BasePage
{
	public MyAccountPage (WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy(xpath = "//h1[normalize-space()='My Account']")
	WebElement msgHeading;
	
	@FindBy(xpath="/html/body/div/main/div[2]/div/aside/div/a[14]")
	WebElement lnkLogout;
	
	public boolean isMyAccountPageExists()
	{
		try
		{
			return(msgHeading.isDisplayed());
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public void clickLogout(WebDriver driver)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lnkLogout);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.elementToBeClickable(lnkLogout));
	    lnkLogout.click();
     }
}

