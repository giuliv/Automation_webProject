package com.applause.auto.test.mobile;

import java.lang.reflect.Method;

import com.applause.auto.common.util.RetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;

import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.util.drivers.DriverWrapper;
import com.applause.auto.framework.pageframework.util.drivers.DriverWrapperManager;
import com.applause.auto.framework.pageframework.util.environment.EnvironmentUtil;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.util.queryhelpers.DeviceElementQueryHelper;
import com.applause.auto.framework.pageframework.util.synchronization.MobileNativeSyncHelper;
import com.applause.auto.framework.test.listeners.TestListener;
import com.applause.auto.test.mobile.helpers.TestHelper;

import io.appium.java_client.AppiumDriver;

@Listeners(TestListener.class)
public class BaseTest {

	private static final LogController LOGGER = new LogController(BaseTest.class);

	@SuppressWarnings("unused")
	private static CustomerConfig config;

	protected static DriverWrapper driverWrapper;
	protected static AppiumDriver<?> driver;
	protected static MobileNativeSyncHelper syncHelper;
	protected static DeviceElementQueryHelper queryHelper;
	protected static EnvironmentUtil env;
	protected static TestHelper peetsMobileHelper;
	protected TemplateTestHelper templateTestHelper;
	protected static SoftAssert softAssert;

	static {
		config = new CustomerConfig();
		env = EnvironmentUtil.getInstance();
	}

	/**
	 * Get a new Appium driver at the start of each test.
	 */
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method) {
		String runId = String.format("%s:%s", method.getName(), System.currentTimeMillis());
		LOGGER.debug(String.format("Setting runId to %s", runId));
		System.setProperty("runId", runId);
		driverWrapper = new DriverWrapper(EnvironmentUtil.getDriver(), env.getDriverProvider());
		driver = (AppiumDriver<?>) driverWrapper.getDriver();
		syncHelper = (MobileNativeSyncHelper) driverWrapper.getSyncHelper();
		queryHelper = (DeviceElementQueryHelper) driverWrapper.getQueryHelper();
		templateTestHelper = new TemplateTestHelper();
		peetsMobileHelper = DeviceViewFactory.create(TestHelper.class);
		softAssert = new SoftAssert();
		LOGGER.info("Test case setup complete.");
	}

	/**
	 * Destroy the driver at the end of the test.
	 */
	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		try {
			driver.quit();
		} catch (Throwable th) {
			LOGGER.error("Something happened during driver session release");
		}
		DriverWrapperManager.getInstance().deregisterDriver(driverWrapper);
		LOGGER.info("Test case teardown complete.");
	}

	/**
	 * Retry Failure Functionality
	 * @param context
	 */
	@BeforeSuite(alwaysRun = true)
	public void retestFailures(ITestContext context) {
		for (ITestNGMethod method : context.getAllTestMethods()) {
			method.setRetryAnalyzer(new RetryAnalyzer());
		}
	}
}