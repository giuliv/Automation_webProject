package com.applause.auto.test.mobile;

import com.applause.auto.base.BaseSeleniumTest;
import com.applause.auto.common.data.Constants;
import com.applause.auto.integrations.RunUtil;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.test.mobile.helpers.TestHelper;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.helper.EnvironmentHelper;
import com.applause.auto.util.helper.SyncHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Arrays;

import io.appium.java_client.android.AndroidDriver;

public class BaseTest extends BaseSeleniumTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());
  static TestHelper testHelper;
  SoftAssert softAssert = new SoftAssert();

  /** Get a new Appium driver at the start of each test. */
  @BeforeMethod(alwaysRun = true)
  public void beforeMethod(Method method) {

    String runId = String.format("%s:%s", method.getName(), System.currentTimeMillis());
    logger.debug(String.format("Setting runId to %s", runId));
    System.setProperty("runId", runId);

    // Set the default wait time on elements to 20 seconds
    RunUtil.setTimeout(30);

    // Set the custom mobile test helper
    testHelper = ComponentFactory.create(TestHelper.class);

    logger.info("Test case setup complete.");
  }

  /** Get a new Appium driver at the start of each test. */
  @BeforeMethod(
      alwaysRun = true,
      dependsOnMethods = {"beforeMethod"})
  public void beforeMethodWebUI(Method method) {
    if (Arrays.stream(method.getAnnotation(Test.class).groups())
        .anyMatch(Constants.TestNGGroups.WEB_UI::equals)) {
      logger.info("Chrome setup started...");
      try {
        testHelper.setupChrome();
      } catch (Throwable th) {
        logger.info("Something happened during Chrome setup");
      }
    } else {
      logger.info("Chrome setup not needed");
    }
    if (EnvironmentHelper.isMobileAndroid(DriverManager.getDriver())) {
      SyncHelper.sleep(5000);
      String currentActivity = ((AndroidDriver) DriverManager.getDriver()).currentActivity();
      logger.info("Current activity: " + currentActivity);
    }
    logger.info("Test case setup complete.");
  }
}
