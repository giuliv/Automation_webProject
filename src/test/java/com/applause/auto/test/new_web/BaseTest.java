package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.integrations.base.BaseSeleniumTest;
import com.applause.auto.integrations.helpers.SdkHelper;
import com.applause.auto.new_web.views.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class BaseTest extends BaseSeleniumTest {

  public static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  /** Get a new WebDriver at the start of each test. */
  @BeforeMethod(alwaysRun = true)
  public void beforeMethod(Method method) {
    String runId = String.format("%s:%s", method.getName(), System.currentTimeMillis());
    logger.debug(String.format("Setting runId to %s", runId));
    System.setProperty("runId", runId);

    // Set the default wait time on elements to 20 seconds
    setTimeout(20);

    // Maximize the browser for desktop web platforms
    if (SdkHelper.getEnvironmentHelper().isChrome()
        || SdkHelper.getEnvironmentHelper().isFirefox()
        || SdkHelper.getEnvironmentHelper().isSafari()
        || SdkHelper.getEnvironmentHelper().isIE()
        || SdkHelper.getEnvironmentHelper().isEdge()) {
      getDriver().manage().window().maximize();
    }

    logger.info("Test case setup complete.");
  }

  /*
   * Platform Agnostic Test Helpers
   */

  HomePage navigateToHome() {
    logger.info(String.format("Navigating to the home page '%s'", TestData.LANDING_PAGE_URL));
    getDriver().navigate().to(TestData.LANDING_PAGE_URL);
    return this.create(HomePage.class);
  }
}
