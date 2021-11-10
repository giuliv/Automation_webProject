package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.integrations.base.ApplauseSeleniumTest;
import com.applause.auto.listeners.allure.TestMethodFailureRetryInterceptor;
import com.applause.auto.listeners.allure.WebTestMethodExecutionListener;
import com.applause.auto.new_web.helpers.TestHelper;
import com.applause.auto.new_web.views.GiftCardsPage;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.new_web.views.SignInPage;
import io.qameta.allure.Step;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({WebTestMethodExecutionListener.class, TestMethodFailureRetryInterceptor.class})
public class BaseTest extends ApplauseSeleniumTest {

  public static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());
  public TestHelper testHelper = new TestHelper();
  public String Mail;

  /** Get a new WebDriver at the start of each test. */
  @BeforeMethod(alwaysRun = true)
  @Step("Test Before Method")
  public void beforeMethod(Method method) {
    String runId = String.format("%s:%s", method.getName(), System.currentTimeMillis());
    logger.debug(String.format("Setting runId to %s", runId));
    Mail = Constants.Mail.getRandomMail().getValue();
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
  @Step("Navigate to Home")
  public HomePage navigateToHome() {
    logger.info(String.format("Navigating to the home page '%s'", TestData.LANDING_PAGE_URL));
    SdkHelper.getDriver().navigate().to(TestData.LANDING_PAGE_URL);
    return SdkHelper.create(HomePage.class);
  }

  @Step("Navigate to PLP")
  public ProductListPage navigateToPLP() {
    navigateToHome();
    logger.info(String.format("Navigating to PLP page '%s'", TestData.PLP_URL));
    SdkHelper.getDriver().navigate().to(TestData.PLP_URL);
    return SdkHelper.create(ProductListPage.class);
  }

  @Step("Navigate to PDP")
  public ProductDetailsPage navigateToPDP() {
    navigateToHome();
    logger.info(String.format("Navigating to PDP page '%s'", TestData.PDP_URL));
    SdkHelper.getDriver().navigate().to(TestData.PDP_URL);
    return SdkHelper.create(ProductDetailsPage.class);
  }

  @Step("Navigate to Gear Section")
  public ProductListPage navigateToGearSection() {
    logger.info(String.format("Navigating to the Gear page '%s'", TestData.GEAR_PAGE_URL));
    SdkHelper.getDriver().navigate().to(TestData.GEAR_PAGE_URL);
    return SdkHelper.create(ProductListPage.class);
  }

  @Step("Navigate to Sign in page")
  public SignInPage navigateToSignInPage() {
    logger.info(String.format("Navigating to the Sign in page '%s'", TestData.LANDING_PAGE_URL));
    SdkHelper.getDriver().navigate().to(TestData.LANDING_PAGE_URL);
    return SdkHelper.create(HomePage.class).clickSignInButton();
  }

  @Step("Navigate to Gift Cards page")
  public GiftCardsPage navigateToGiftCardsPage() {
    logger.info(String.format("Navigating to Gift Cards page '%s'", TestData.GIFT_CARDS_PAGE_URL));
    SdkHelper.getDriver().navigate().to(TestData.GIFT_CARDS_PAGE_URL);
    return SdkHelper.create(GiftCardsPage.class);
  }
}
