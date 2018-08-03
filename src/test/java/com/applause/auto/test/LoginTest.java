package com.applause.auto.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.pageframework.Chunks.AccountChunk;
import com.applause.auto.pageframework.pages.LandingPage;
import com.applause.auto.pageframework.pages.SchedulePage;
import com.applause.auto.pageframework.testdata.TestConstants.TestData;
import com.applause.auto.pageframework.testdata.TestConstants.TestNGGroups;

public class LoginTest extends BaseTest {

	private LandingPage landingPage;
	private SchedulePage schedule;
	private AccountChunk menuchunk;

	@Test(groups = { TestNGGroups.LOGIN }, description = "3589")
	public void loginTest() {

		LOGGER.info("1. Navigate to landing page");
		landingPage = navigateToLandingPage();

		// Assertions
		Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");

		LOGGER.info("2. Click on Skip");
		schedule = landingPage.tapSkipButton();

		LOGGER.info("3. Click on Login and enter test data");
		schedule = templateTestHelper.logIntoAccount(TestData.USERNAME, TestData.PASSWORD, schedule);

		LOGGER.info("4. Click on Account Menu");
		menuchunk = schedule.clickonAccountMenu();

		LOGGER.info("5. Verify the login user name with test data");
		Assert.assertEquals(menuchunk.getUsernameText(), TestData.USERNAME);
		LOGGER.info("Login test Pass");

	}
}
