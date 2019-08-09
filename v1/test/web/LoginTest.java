package com.applause.auto.test.web;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.pageframework.pages.LandingPage;
import com.applause.auto.pageframework.pages.MyAccountPage;
import com.applause.auto.pageframework.pages.SignInPage;
import com.applause.auto.pageframework.testdata.TestConstants;
import com.applause.auto.pageframework.testdata.TestConstants.TestNGGroups;

public class LoginTest extends BaseTest {

	private LandingPage landingPage;
	private MyAccountPage myAccountPage;
	private SignInPage signInPage;

	@Test(groups = { TestNGGroups.LOGIN }, description = "10351")
	public void loginTest() {

		LOGGER.info("1. Navigate to landing page");
		landingPage = navigateToLandingPage();

		// Assertions
		Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");

		LOGGER.info("2. Click on Sign In");
		signInPage = landingPage.clickSignInButton();

		LOGGER.info("3. Enter username and password");
		signInPage.enterEmail(TestConstants.TestData.USERNAME);
		signInPage.enterPassword(TestConstants.TestData.PASSWORD);
		myAccountPage = signInPage.clickonSignInButton();

		LOGGER.info("Verify user is signed in");
		Assert.assertTrue(myAccountPage.getWelcomeMessage().contains("Applause"),
				"User is not signed in or welcome name is wrong");

	}
}
