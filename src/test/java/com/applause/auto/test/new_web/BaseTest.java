package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.integrations.base.ApplauseSeleniumTest;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.*;
import com.applause.auto.web.helpers.TestHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class BaseTest extends ApplauseSeleniumTest {

  public static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());
  public TestHelper testHelper = new TestHelper();

  /** Get a new WebDriver at the start of each test. */
  @BeforeMethod(alwaysRun = true)
  public void beforeMethod(Method method) {
    String runId = String.format("%s:%s", method.getName(), System.currentTimeMillis());
    logger.debug(String.format("Setting runId to %s", runId));
    System.setProperty("runId", runId);

    // Set the default wait time on elements to 20 seconds
    SdkHelper.setTimeout(20);

    // Maximize the browser for desktop web platforms
    if (SdkHelper.getEnvironmentHelper().isChrome()
        || SdkHelper.getEnvironmentHelper().isFirefox()
        || SdkHelper.getEnvironmentHelper().isSafari()
        || SdkHelper.getEnvironmentHelper().isIE()
        || SdkHelper.getEnvironmentHelper().isEdge()) {
      SdkHelper.getDriver().manage().window().maximize();
    }

    logger.info("Test case setup complete.");
  }

  /*
   * Platform Agnostic Test Helpers
   */

  public HomePage navigateToHome() {
    logger.info(String.format("Navigating to the home page '%s'", TestData.LANDING_PAGE_URL));
    SdkHelper.getDriver().navigate().to(TestData.LANDING_PAGE_URL);
    return SdkHelper.create(HomePage.class);
  }
}
