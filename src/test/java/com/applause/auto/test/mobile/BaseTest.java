package com.applause.auto.test.mobile;

import com.applause.auto.common.data.Constants;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.integrations.base.ApplauseSeleniumTest;
import com.applause.auto.test.mobile.helpers.TestHelper;
import io.appium.java_client.android.AndroidDriver;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class BaseTest extends ApplauseSeleniumTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());
  protected static TestHelper testHelper;
  SoftAssert softAssert = new SoftAssert();

  /** Get a new Appium driver at the start of each test. */
  @BeforeMethod(alwaysRun = true)
  public void beforeMethod(Method method) {

    String runId = String.format("%s:%s", method.getName(), System.currentTimeMillis());
    logger.debug(String.format("Setting runId to %s", runId));
    System.setProperty("runId", runId);

    // Set the default wait time on elements to 20 seconds
    SdkHelper.setTimeout(30);

    // Set the custom mobile test helper
    testHelper = SdkHelper.create(TestHelper.class);

    logger.info("Test case setup complete.");
  }

  /** Get a new Appium driver at the start of each test. */
  @BeforeMethod(
      alwaysRun = true,
      dependsOnMethods = {"beforeMethod"})
  public void beforeMethodWebUI(Method method) {
    Stream<String> stream = Stream.of(method.getAnnotation(Test.class).groups());
    if (stream.anyMatch(Constants.TestNGGroups.WEB_UI::equals)) {
      logger.info("Chrome setup started...");
      try {
        testHelper.setupChrome();
      } catch (Throwable th) {
        logger.info("Something happened during Chrome setup");
      }
    } else {
      logger.info("Chrome setup not needed");
    }
    if (SdkHelper.getEnvironmentHelper().isMobileAndroid()) {
      SdkHelper.getSyncHelper().sleep(5000);
      String currentActivity = ((AndroidDriver) SdkHelper.getDriver()).currentActivity();
      logger.info("Current activity: " + currentActivity);
    }
    logger.info("Test case setup complete.");
  }
}
