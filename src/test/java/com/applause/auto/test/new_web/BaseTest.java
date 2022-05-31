package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.enums.SubscriptionType;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.integrations.base.ApplauseSeleniumTest;
import com.applause.auto.listeners.allure.TestMethodFailureRetryInterceptor;
import com.applause.auto.listeners.allure.WebTestMethodExecutionListener;
import com.applause.auto.new_web.helpers.TestHelper;
import com.applause.auto.new_web.views.CoffeeBarPage;
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
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;

@Listeners({WebTestMethodExecutionListener.class, TestMethodFailureRetryInterceptor.class})
public class BaseTest extends ApplauseSeleniumTest {

  public static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());
  public TestHelper testHelper = new TestHelper();
  public String mail;
  public String coffeeSelected;
  protected SoftAssert softAssert;

  /** Get a new WebDriver at the start of each test. */
  @BeforeMethod(alwaysRun = true)
  @Step("Test Before Method")
  public void beforeMethod(Method method) {
    String runId = String.format("%s:%s", method.getName(), System.currentTimeMillis());
    logger.debug(String.format("Setting runId to %s", runId));
    mail = Constants.Mail.getRandomMail().getValue();
    coffeeSelected = Constants.StandardCoffeeInventory.getRandomCoffee().getValue();

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
    softAssert = new SoftAssert();
    logger.info("Test case setup complete.");
  }

  /*
   * Platform Agnostic Test Helpers
   */
  @Step("Navigate to Home")
  public HomePage navigateToHome() {
    logger.info(String.format("Navigating to the home page '%s'", TestData.LANDING_PAGE_URL));

    // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
    try {
      SdkHelper.getDriver().navigate().to(TestData.LANDING_PAGE_URL);
      SdkHelper.getSyncHelper().sleep(1000); // Once chromedriver issue fix, remove it
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
    }
    return SdkHelper.create(HomePage.class);
  }

  @Step("Navigate to PLP")
  public ProductListPage navigateToPLP() {
    navigateToHome();
    logger.info(String.format("Navigating to PLP page '%s'", TestData.PLP_URL));

    // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
    try {
      SdkHelper.getDriver().navigate().to(TestData.PLP_URL);
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
    }
    return SdkHelper.create(ProductListPage.class);
  }

  @Step("Navigate to PLP Medium Roast")
  public ProductListPage navigateToPLPMediumRoast() {
    navigateToHome();
    logger.info(String.format("Navigating to PLP page '%s'", TestData.PLP_MEDIUM_ROAST_URL));

    // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
    try {
      SdkHelper.getDriver().navigate().to(TestData.PLP_MEDIUM_ROAST_URL);
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
    }
    return SdkHelper.create(ProductListPage.class);
  }

  @Step("Navigate to PDP")
  public ProductDetailsPage navigateToPDP() {
    navigateToHome();
    logger.info(String.format("Navigating to PDP page '%s'", TestData.PDP_URL));

    // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
    try {
      SdkHelper.getDriver().navigate().to(TestData.PDP_URL);
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
    }
    return SdkHelper.create(ProductDetailsPage.class);
  }

  @Step("Navigate to PDP")
  public ProductDetailsPage navigateToPDP(String product) {
    navigateToHome();
    logger.info(String.format("Navigating to PDP page '%s'", TestData.GENERAL_PDP_URL + product));

    // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
    try {
      SdkHelper.getDriver().navigate().to(TestData.GENERAL_PDP_URL + product);
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
    }
    return SdkHelper.create(ProductDetailsPage.class);
  }

  @Step("Navigate to Gear Section")
  public ProductListPage navigateToGearSection() {
    navigateToHome();
    logger.info(String.format("Navigating to the Gear page '%s'", TestData.GEAR_PAGE_URL));

    // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
    try {
      SdkHelper.getDriver().navigate().to(TestData.GEAR_PAGE_URL);
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
    }
    return SdkHelper.create(ProductListPage.class);
  }

  @Step("Navigate to Sign in page")
  public SignInPage navigateToSignInPage() {
    navigateToHome();
    logger.info(String.format("Navigating to the Sign in page '%s'", TestData.LANDING_PAGE_URL));

    // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
    try {
      SdkHelper.getDriver().navigate().to(TestData.LANDING_PAGE_URL);
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
    }
    return SdkHelper.create(HomePage.class).clickSignInButton();
  }

  public CoffeeBarPage navigateToCoffeeBarMenuPage() {
    logger.info(
        String.format("Navigating to the Coffee Bar page '%s'", TestData.COFFEEBAR_MENU_URL));
    // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
    try {
      SdkHelper.getDriver().get(TestData.COFFEEBAR_MENU_URL);
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
    }
    return SdkHelper.create(CoffeeBarPage.class);
  }

  @Step("Navigate to Subscription page")
  public GiftCardsPage navigateToGiftCardsPage() {
    navigateToHome();
    logger.info(
        String.format("Navigating to Subscription page '%s'", TestData.GIFT_CARDS_PAGE_URL));

    // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
    try {
      SdkHelper.getDriver().navigate().to(TestData.GIFT_CARDS_PAGE_URL);
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
    }
    return SdkHelper.create(GiftCardsPage.class);
  }

  @Step("Navigate to Subscription page")
  public ProductDetailsPage navigateToSubscriptionPage(SubscriptionType subscriptionType) {
    logger.info(String.format("Navigating to Subscription page '%s'", subscriptionType));

    if (subscriptionType.getValue().equalsIgnoreCase(SubscriptionType.SIGNATURE_BLEND.getValue())) {
      // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
      try {
        SdkHelper.getDriver().navigate().to(TestData.SUBSCRIPTION_SB_URL);
      } catch (WebDriverException e) {
        logger.info("Frame detached issue seen");
      }
    } else if (subscriptionType
        .getValue()
        .equalsIgnoreCase(SubscriptionType.SINGLE_ORIGIN.getValue())) {
      // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
      try {
        SdkHelper.getDriver().navigate().to(TestData.SUBSCRIPTION_SO_URL);
      } catch (WebDriverException e) {
        logger.info("Frame detached issue seen");
      }
    } else if (subscriptionType
        .getValue()
        .equalsIgnoreCase(SubscriptionType.SMALL_BATCHES.getValue())) {
      // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
      try {
        SdkHelper.getDriver().navigate().to(TestData.SUBSCRIPTION_SMB_URL);
      } catch (WebDriverException e) {
        logger.info("Frame detached issue seen");
      }
    }

    return SdkHelper.create(ProductDetailsPage.class);
  }

  @Step("Navigate to Tea Best Sellers page")
  public ProductListPage navigateToPLP(String url) {
    navigateToHome();
    logger.info(String.format("Navigating to PLP '%s'", url));

    // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
    try {
      SdkHelper.getDriver().navigate().to(url);
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
    }
    return SdkHelper.create(ProductListPage.class);
  }
}
