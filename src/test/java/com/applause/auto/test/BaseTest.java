package com.applause.auto.test;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import com.applause.auto.framework.pageframework.util.drivers.DriverWrapper;
import com.applause.auto.framework.pageframework.util.drivers.DriverWrapperManager;
import com.applause.auto.framework.pageframework.util.environment.EnvironmentUtil;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.util.queryhelpers.WebElementQueryHelper;
import com.applause.auto.framework.pageframework.util.synchronization.WebSyncHelper;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.pageframework.helpers.TemplateTestHelper;
import com.applause.auto.pageframework.pages.LandingPage;
import com.applause.auto.pageframework.pages.PeetsCardProductPage;
import com.applause.auto.pageframework.pages.ShopCoffeeKCupsPage;
import com.applause.auto.pageframework.pages.ShopEquipmentPage;
import com.applause.auto.pageframework.pages.ShopTeaPage;
import com.applause.auto.pageframework.testdata.CustomerConfig;
import com.applause.auto.pageframework.testdata.TestConstants.TestData;
import com.applause.auto.pageframework.testrail.TemplateWebTestListener;

@Listeners(TemplateWebTestListener.class)
public class BaseTest {

	protected static final LogController LOGGER;

	@SuppressWarnings("unused")
	private static CustomerConfig config;

	protected static DriverWrapper driverWrapper;
	protected static WebDriver driver;
	protected static WebSyncHelper syncHelper;
	protected static WebElementQueryHelper queryHelper;
	protected static EnvironmentUtil env;
	protected TemplateTestHelper templateTestHelper;

	static {
		config = new CustomerConfig();
		env = EnvironmentUtil.getInstance();
		LOGGER = new LogController(MethodHandles.lookup().lookupClass());
	}

	/**
	 * Get a new WebDriver at the start of each test.
	 */
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method) {
		String runId = String.format("%s:%s", method.getName(), System.currentTimeMillis());
		LOGGER.debug(String.format("Setting runId to %s", runId));
		System.setProperty("runId", runId);
		driverWrapper = new DriverWrapper(EnvironmentUtil.getDriver(), env.getDriverProvider());
		driver = (WebDriver) driverWrapper.getDriver();
		syncHelper = (WebSyncHelper) driverWrapper.getSyncHelper();
		queryHelper = (WebElementQueryHelper) driverWrapper.getQueryHelper();

		templateTestHelper = new TemplateTestHelper();

		// Maximize the browser for desktop platforms
		if (!env.getIsMobileWebTest()) {
			driver.manage().window().maximize();
		}
		if (env.isPhone()) {
			LOGGER.info("Mobile web test settings");
			env.setIsMobileTest(true);
			env.setRawCssOnly(true);
		}

		LOGGER.info("Test case setup complete.");
	}

	/**
	 * Destroy the WebDriver at the end of the test.
	 */
	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		try {
			driver.quit();
		} catch (Throwable throwable) {
			LOGGER.info("Something wrong #1 happened during test teardown.");
		}
		try {
			DriverWrapperManager.getInstance().deregisterDriver(driverWrapper);
			LOGGER.info("Test case teardown complete.");
		} catch (Throwable throwable) {
			LOGGER.info("Something wrong happened during test teardown.");
		}
	}

	/*
	 * Platform Agnostic Test Helpers
	 */

	protected LandingPage navigateToLandingPage() {
		LOGGER.info(String.format("Navigating to the landing page '%s'", TestData.LANDING_PAGE_URL));
		driver.navigate().to(TestData.LANDING_PAGE_URL);
		return PageFactory.create(LandingPage.class);
	}

	protected ShopTeaPage navigateToShopTeaPage() {
		LOGGER.info(String.format("Navigating to the Shop Tea page '%s'", TestData.SHOP_TEA_PAGE_URL));
		driver.navigate().to(TestData.SHOP_TEA_PAGE_URL);
		return PageFactory.create(ShopTeaPage.class);
	}

	protected ShopEquipmentPage navigateToShopEquipmentPage() {
		LOGGER.info(String.format("Navigating to the Shop Equipment page '%s'", TestData.SHOP_EQUIPMENT_PAGE_URL));
		driver.navigate().to(TestData.SHOP_EQUIPMENT_PAGE_URL);
		return PageFactory.create(ShopEquipmentPage.class);
	}

	protected PeetsCardProductPage navigateToShopPeetsCardPage() {
		LOGGER.info(String.format("Navigating to the Shop Peets Card page '%s'", TestData.SHOP_PEETS_CARD_PAGE_URL));
		driver.navigate().to(TestData.SHOP_PEETS_CARD_PAGE_URL);
		return PageFactory.create(PeetsCardProductPage.class);
	}

	protected ShopCoffeeKCupsPage navigateToShopCoffeeKCupsPage() {
		LOGGER.info(
				String.format("Navigating to the Shop Coffee K-Cups page '%s'", TestData.SHOP_COFFEE_KCUPS_PAGE_URL));
		driver.navigate().to(TestData.SHOP_COFFEE_KCUPS_PAGE_URL);
		return PageFactory.create(ShopCoffeeKCupsPage.class);
	}

}