package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass
{
	@Test(groups = {"Regression","Master"})
	public void verify_account_registration() throws InterruptedException
	{
		logger.info("***** Starting TC001_AccountRegistrationTest *****");
		
		try
		{
			// Navigate to Registration Page
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			logger.info("Clicked on MyAccount Link");
			
			hp.clickRegister();
			logger.info("Clicked on Register Link");
			
			AccountRegistrationPage regPage=new AccountRegistrationPage(driver);
			
			//Provide Customer Details
			logger.info("Providing customer details...");
			String firstName = randomString().toUpperCase();
			String lastName = randomString().toUpperCase();
			String email = randomString() + "@gmail.com";
			String password = randomAlphaNumeric();
			
			regPage.setFirstName(firstName);
			regPage.setLastName(lastName);
			regPage.setEmail(email);
			regPage.setPassword(password);
			
			regPage.setPrivacyPolicy(driver);
			regPage.clickContinue();
			
			//Validate Confirmation Message
			logger.info("Validating expected confirmation message...");
			String confMsg = regPage.getConfirmationMsg();
			
			Assert.assertEquals(confMsg, "Your Account Has Been Created!",
	                    "Account registration failed. Expected confirmation message not found.");

	        logger.info("Account successfully registered with email: " + email);

		} catch(Exception e) {
			logger.error("Exception occurred during account registration: " + e.getMessage());
            //ReportingUtils.captureScreenshot(driver, "TC001_AccountRegistrationTest_Failure");
            Assert.fail("Test case failed due to exception: " + e.getMessage());
		}
		
		logger.info("***** Finished TC001_AccountRegistrationTest *****");
	}
}
