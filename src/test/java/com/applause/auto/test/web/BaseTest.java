package com.applause.auto.test.web;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.integrations.base.BaseSeleniumTest;
import com.applause.auto.integrations.helpers.SdkHelper;
import com.applause.auto.web.views.Landing;
import com.applause.auto.web.views.PeetsCardProductPage;
import com.applause.auto.web.views.ShopCoffeeKCupsPage;
import com.applause.auto.web.views.ShopEquipmentPage;
import com.applause.auto.web.views.ShopTeaPage;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;

public class BaseTest extends BaseSeleniumTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

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

  Landing navigateToLanding() {
    logger.info(String.format("Navigating to the landing page '%s'", TestData.LANDING_PAGE_URL));
    getDriver().navigate().to(TestData.LANDING_PAGE_URL);
    return this.create(Landing.class);
  }

  ShopTeaPage navigateToShopTeaPage() {
    logger.info(String.format("Navigating to the Shop Tea page '%s'", TestData.SHOP_TEA_PAGE_URL));
    getDriver().navigate().to(TestData.SHOP_TEA_PAGE_URL);
    return this.create(ShopTeaPage.class);
  }

  ShopEquipmentPage navigateToShopEquipmentPage() {
    logger.info(
        String.format(
            "Navigating to the Shop Equipment page '%s'", TestData.SHOP_EQUIPMENT_PAGE_URL));
    getDriver().navigate().to(TestData.SHOP_EQUIPMENT_PAGE_URL);
    return this.create(ShopEquipmentPage.class);
  }

  PeetsCardProductPage navigateToShopPeetsCardPage() {
    logger.info(
        String.format(
            "Navigating to the Shop Peets Card page '%s'", TestData.SHOP_PEETS_CARD_PAGE_URL));
    getDriver().navigate().to(TestData.SHOP_PEETS_CARD_PAGE_URL);
    return this.create(PeetsCardProductPage.class);
  }

  ShopCoffeeKCupsPage navigateToShopCoffeeKCupsPage() {
    logger.info(
        String.format(
            "Navigating to the Shop Coffee K-Cups page '%s'", TestData.SHOP_COFFEE_KCUPS_PAGE_URL));
    getDriver().navigate().to(TestData.SHOP_COFFEE_KCUPS_PAGE_URL);
    return this.create(ShopCoffeeKCupsPage.class);
  }
}
