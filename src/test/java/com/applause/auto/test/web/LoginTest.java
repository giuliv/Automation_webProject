package com.applause.auto.test.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.web.views.Landing;
import com.applause.auto.web.views.MyAccountPage;
import com.applause.auto.web.views.SignInPage;
import com.applause.auto.common.data.TestConstants;
import com.applause.auto.common.data.TestConstants.TestNGGroups;

import java.lang.invoke.MethodHandles;

public class LoginTest extends BaseTest {

	private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

	@Test(groups = { TestNGGroups.LOGIN }, description = "10351")
	public void loginTest() {

		logger.info("1. Navigate to landing page");
		Landing landing = navigateToLanding();
		Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

		logger.info("2. Click on Sign In");
		SignInPage signInPage = landing.clickSignInButton();

		logger.info("3. Enter username and password");
		signInPage.enterEmail(TestConstants.TestData.USERNAME);
		signInPage.enterPassword(TestConstants.TestData.PASSWORD);
		MyAccountPage myAccountPage = signInPage.clickonSignInButton();

		logger.info("Verify user is signed in");
		Assert.assertTrue(myAccountPage.getWelcomeMessage().contains("Applause"),
				"User is not signed in or welcome name is wrong");

	}
}
