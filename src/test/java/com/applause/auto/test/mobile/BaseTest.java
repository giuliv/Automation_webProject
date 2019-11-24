package com.applause.auto.test.mobile;

import com.applause.auto.base.BaseSeleniumTest;
import com.applause.auto.integrations.RunUtil;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.test.mobile.helpers.TestHelper;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

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
    RunUtil.setTimeout(20);

    // Set the custom mobile test helper
    testHelper = ComponentFactory.create(TestHelper.class);

    logger.info("Test case setup complete.");
  }

  /**
   * Retry Failure Functionality
   *
   * @param context
   */
  @BeforeSuite(alwaysRun = true)
  public void retestFailures(ITestContext context) {
    /*for (ITestNGMethod method : context.getAllTestMethods()) {
      method.setRetryAnalyzer(new RetryAnalyzer());
    }*/
  }
}
