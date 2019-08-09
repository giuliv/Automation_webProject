package com.applause.auto.test.web;

import com.applause.auto.base.BaseSeleniumTest;
import com.applause.auto.common.data.TestConstants.TestData;
import com.applause.auto.integrations.RunUtil;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.helper.EnvironmentHelper;
import com.applause.auto.web.views.Landing;
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
    RunUtil.setTimeout(20);

    // Maximize the browser for desktop web platforms
    if (EnvironmentHelper.isChrome(DriverManager.getDriver())
        || EnvironmentHelper.isFirefox(DriverManager.getDriver())
        || EnvironmentHelper.isSafari(DriverManager.getDriver())) {
      // DriverManager.getDriver().manage().window().maximize();
    }

    logger.info("Test case setup complete.");
  }

  /*
   * Platform Agnostic Test Helpers
   */

  protected Landing navigateToLandingPage() {
    logger.info(String.format("Navigating to the landing page '%s'", TestData.LANDING_PAGE_URL));
    DriverManager.getDriver().navigate().to(TestData.LANDING_PAGE_URL);
    return ComponentFactory.create(Landing.class);
  }

  /*
  // TODO: Reincorporate these when we add tests back
  protected ShopTeaPage navigateToShopTeaPage() {
  	logger.info(String.format("Navigating to the Shop Tea page '%s'", TestData.SHOP_TEA_PAGE_URL));
  	driver.navigate().to(TestData.SHOP_TEA_PAGE_URL);
  	return PageFactory.create(ShopTeaPage.class);
  }

  protected ShopEquipmentPage navigateToShopEquipmentPage() {
  	logger.info(String.format("Navigating to the Shop Equipment page '%s'", TestData.SHOP_EQUIPMENT_PAGE_URL));
  	driver.navigate().to(TestData.SHOP_EQUIPMENT_PAGE_URL);
  	return PageFactory.create(ShopEquipmentPage.class);
  }

  protected PeetsCardProductPage navigateToShopPeetsCardPage() {
  	logger.info(String.format("Navigating to the Shop Peets Card page '%s'", TestData.SHOP_PEETS_CARD_PAGE_URL));
  	driver.navigate().to(TestData.SHOP_PEETS_CARD_PAGE_URL);
  	return PageFactory.create(PeetsCardProductPage.class);
  }

  protected ShopCoffeeKCupsPage navigateToShopCoffeeKCupsPage() {
  	logger.info(
  			String.format("Navigating to the Shop Coffee K-Cups page '%s'", TestData.SHOP_COFFEE_KCUPS_PAGE_URL));
  	driver.navigate().to(TestData.SHOP_COFFEE_KCUPS_PAGE_URL);
  	return PageFactory.create(ShopCoffeeKCupsPage.class);
  }
  */
}
