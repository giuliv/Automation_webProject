package com.applause.auto.web.views;

import java.lang.invoke.MethodHandles;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.applause.auto.framework.pageframework.util.drivers.BrowserType;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.EditField;
import com.applause.auto.framework.pageframework.webcontrols.Image;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.common.data.TestConstants;

@WebDesktopImplementation(PaypalLoginPage.class)
@WebTabletImplementation(PaypalLoginPage.class)
@WebPhoneImplementation(PaypalLoginPage.class)
public class PaypalLoginPage extends AbstractPage {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());
	protected final static WebHelper webHelper = new WebHelper();

	@Override
	protected void waitUntilVisible() {
		syncHelper.suspend(45000);
		syncHelper.waitForElementToAppear(getEmailField());
	}

	// Public actions

	/**
	 * Enter Paypal Email Address
	 *
	 * @param email
	 */
	public void enterEmail(String email) {
		LOGGER.info("Entering email address");
		getEmailField().clearText();
		getEmailField().setText(email);
	}

	/**
	 * Click Next Button
	 */
	public void clickNext() {
		LOGGER.info("Clicking Next button");
		getNextButton().click();
	}

	/**
	 * Enter Paypal Password
	 *
	 * @param password
	 */
	public void enterPassword(String password) {
		LOGGER.info("Entering password");
		syncHelper.waitForElementToAppear(getPasswordField());
		getPasswordField().setText(password);
	}

	/**
	 * Click Log In
	 *
	 * @return PaypalReviewYourPurchasePage
	 */
	public PaypalReviewYourPurchasePage clickLogIn() {
		LOGGER.info("Clicking Log In");
		getLogInButton().click();

		// SAFARI flow
		if (env.getBrowserType() == BrowserType.SAFARI) {
			// Move to iFrame
			syncHelper.suspend(45000);
			getDriver().switchTo().defaultContent();
			try {
				syncHelper.waitForElementToAppear("[name='injectedUl']");
				getDriver().switchTo()
						.frame((WebElement) getDriver().findElement(By.cssSelector("[name='injectedUl']")));
				LOGGER.info("Switched to Iframe successfully");
			} catch (Throwable throwable) {
				LOGGER.info("Switching to iFrame failed");
				LOGGER.warn(throwable.getMessage());
			}
			getPasswordField().clearText();
			getPasswordField().setText(TestConstants.TestData.PAYPAL_PASSWORD);
			if (env.getBrowserType() == BrowserType.SAFARI) {
				webHelper.jsClick(getLogInButton().getWebElement());
			} else {
				getLogInButton().click();
			}
			getDriver().switchTo().defaultContent();
		}

		return PageFactory.create(PaypalReviewYourPurchasePage.class);
	}

	// Protected getters
	@WebElementLocator(webDesktop = "//*[@id=\"content\"]")
	protected Image getViewSignature() {
		return new Image(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "//*[@id=\"email\"]")
	protected EditField getEmailField() {
		return new EditField(this, getLocator(this, "getEmailField"));
	}

	@WebElementLocator(webDesktop = "//*[@id=\"btnNext\"]")
	protected Button getNextButton() {
		return new Button(this, getLocator(this, "getNextButton"));
	}

	@WebElementLocator(webDesktop = "//*[@id=\"password\"]")
	protected EditField getPasswordField() {
		return new EditField(this, getLocator(this, "getPasswordField"));
	}

	@WebElementLocator(webDesktop = "//*[@id=\"btnLogin\"]")
	protected Button getLogInButton() {
		return new Button(this, getLocator(this, "getLogInButton"));
	}
}
