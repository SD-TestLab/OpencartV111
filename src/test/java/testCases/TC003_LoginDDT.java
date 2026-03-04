package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass
{
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "Datadriven")	//getting data provider from different class
	public void verify_loginDDT(String email, String pwd, String exp)
	{
		logger.info("****Starting TC003_LoginDDT****");
		
		try
		{
			//Navigate to Login Page
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			logger.info("Navigated to Login Page");
			
			//Perform Login
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();
			logger.info("Login attempted with email: " + email);
			    
			//Verify My Account Page
			MyAccountPage macc = new MyAccountPage(driver);
			boolean targetPage = macc.isMyAccountPageExists();
		
			if(exp.equalsIgnoreCase("valid"))
			{
				if(targetPage==true)
				{
					logger.info("Login successful for VALID credentials: " + email + " / " + pwd);
			        macc.clickLogout(driver);
			        Assert.assertTrue(true, "PASS: Valid login passed for --> " + email);
			    } else {
			        logger.error("Login failed for VALID credentials: " + email + " / " + pwd);
			        Assert.fail("FAIL: Valid login failed for -->" + email);
				}
			}
			
			if(exp.equalsIgnoreCase("Invalid"))
			{
				if(targetPage==true)
				{
					logger.error("Login succeeded unexpectedly for INVALID credentials: " + email + " / " + pwd);
			        macc.clickLogout(driver);
			        Assert.fail("FAI  L: Invalid login succeeded for -> " + email);
			    } else {
			        logger.info("Login failed as expected for INVALID credentials: " + email + " / " + pwd);
			        Assert.assertTrue(true, "PASS: Invalid login failed correctly for -> " + email);
				}
			}
		}
		catch(Exception e)
		{
			logger.error("Exception occurred during DDT login test: " + e.getMessage());
            //ReportingUtils.captureScreenshot(driver, "TC003_LoginDDT_Failure");
            Assert.fail("Test case failed due to exception: " + e.getMessage());

		}
		logger.info("****Finished TC003_LoginDDT****");

	}
}
