package com.applause.auto.test.web;

import com.applause.auto.common.data.TestConstants.TestNGGroups;
import com.applause.auto.web.views.Landing;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * This is a sample test that verifies the project is setup correctly and can execute a simple test.
 */
public class DebugTest extends BaseTest {

  @Test(
      groups = {TestNGGroups.DEBUG},
      description = "3588")
  public void debugTest() {

    // Test Steps
    Landing landingPage = navigateToLandingPage();

    // Assertions
    Assert.assertNotNull(landingPage, "Failed to navigate to the landing page.");
  }
}
