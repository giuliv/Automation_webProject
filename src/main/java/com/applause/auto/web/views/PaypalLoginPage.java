package com.applause.auto.web.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.util.webDrivers.BrowserType;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Implementation(is = PaypalLoginPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = PaypalLoginPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = PaypalLoginPage.class, on = Platform.WEB_MOBILE_PHONE)
public class PaypalLoginPage extends BaseComponent {
	protected final static WebHelper webHelper = new WebHelper();

	// Public actions

	/**
	 * Enter Paypal Email Address
	 *
	 * @param email
	 */
	public void enterEmail(String email) {
		logger.info("Entering email address");
		getEmailField.clearText();
		getEmailField.sendKeys(email);
	}

	/**
	 * Click Next Button
	 */
	public void clickNext() {
		logger.info("Clicking Next button");
		getNextButton.click();
	}

	/**
	 * Enter Paypal Password
	 *
	 * @param password
	 */
	public void enterPassword(String password) {
		logger.info("Entering password");
		SyncHelper.waitUntilElementPresent(getPasswordField);
		getPasswordField.sendKeys(password);
	}

	/**
	 * Click Log In
	 *
	 * @return PaypalReviewYourPurchasePage
	 */
	public PaypalReviewYourPurchasePage clickLogIn() {
		logger.info("Clicking Log In");
		getLogInButton.click();

		// SAFARI flow
		if (env.getBrowserType() == BrowserType.SAFARI) {
			// Move to iFrame
			SyncHelper.sleep(45000);
			getDriver().switchTo().defaultContent();
			try {
				SyncHelper.waitUntilElementPresent("[name='injectedUl']");
				getDriver().switchTo()
						.frame((WebElement) getDriver().findElement(By.cssSelector("[name='injectedUl']")));
				logger.info("Switched to Iframe successfully");
			} catch (Throwable throwable) {
				logger.info("Switching to iFrame failed");
				logger.warn(throwable.getMessage());
			}
			getPasswordField.clearText();
			getPasswordField.sendKeys(Constants.TestData.PAYPAL_PASSWORD);
			if (env.getBrowserType() == BrowserType.SAFARI) {
				webHelper.jsClick(getLogInButton.getWebElement());
			} else {
				getLogInButton.click();
			}
			getDriver().switchTo().defaultContent();
		}

		return ComponentFactory.create(PaypalReviewYourPurchasePage.class);
	}

	// Protected getters
	@Locate(xpath = "//*[@id=\"content\"]", on = Platform.WEB_DESKTOP)
	protected Image getViewSignature;

	@Locate(xpath = "//*[@id=\"email\"]", on = Platform.WEB_DESKTOP)
	protected TextBox getEmailField;

	@Locate(xpath = "//*[@id=\"btnNext\"]", on = Platform.WEB_DESKTOP)
	protected Button getNextButton;

	@Locate(xpath = "//*[@id=\"password\"]", on = Platform.WEB_DESKTOP)
	protected TextBox getPasswordField;

	@Locate(xpath = "//*[@id=\"btnLogin\"]", on = Platform.WEB_DESKTOP)
	protected Button getLogInButton;
}
