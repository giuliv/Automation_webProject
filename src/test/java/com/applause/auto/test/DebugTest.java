package com.applause.auto.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.pageframework.pages.LandingPage;
import com.applause.auto.pageframework.testdata.TestConstants.TestNGGroups;

/**
 * This is a sample test that verifies the project is setup correctly and can
 * execute a simple test.
 */
public class DebugTest extends BaseTest {

	@Test(groups = { TestNGGroups.DEBUG }, description = "3588")
	public void debugTest() {

		// Test Steps
		LandingPage landingPage = navigateToLandingPage();

		// Assertions
		Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");
	}
}
