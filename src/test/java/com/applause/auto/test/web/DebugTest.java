package com.applause.auto.test.web;

import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.web.views.Landing;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * This is a sample test that verifies the project is setup correctly and can execute a simple test.
 */
public class DebugTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.DEBUG},
      description = "3588")
  public void debugTest() {

    logger.info("Test Step - Navigate to landing page");
    Landing landing = navigateToLanding();

    logger.info("Assertion - Verify navigation to the landing page");
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");
  }
}
