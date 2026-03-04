package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass
{
	@Test(groups = {"Sanity","Master"})
	public void verify_login()
	{
		logger.info("***** Starting login test *****");
		
		try
		{
			//Navigate to HomePage
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			logger.info("Navigated to Login Page");

			
			//Perform Login
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(p.getProperty("email"));
			lp.setPassword(p.getProperty("password"));
			lp.clickLogin();
			logger.info("Login attempted with configured credentials");

			
			//Verify My Account Page
			MyAccountPage macc = new MyAccountPage(driver);
			boolean targetPage = macc.isMyAccountPageExists();
			
			Assert.assertTrue(targetPage,
					"Login failed: My Account page was not displayed.");
            logger.info("Login successful, My Account page verified");

		}
		catch(Exception e)
		{
			logger.error("Exception occurred during login test: " + e.getMessage());
            //ReportingUtils.captureScreenshot(driver, "TC002_LoginTest_Failure");
            Assert.fail("Test case failed due to exception: " + e.getMessage());

		}
			logger.info("***** Finish TC002_LoginTest*****");
		
	}
}
