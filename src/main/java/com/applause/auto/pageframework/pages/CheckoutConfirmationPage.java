package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.EditField;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.helpers.WebHelper;

@WebDesktopImplementation(CheckoutConfirmationPage.class)
@WebTabletImplementation(CheckoutConfirmationPage.class)
@WebPhoneImplementation(CheckoutConfirmationPage.class)
public class CheckoutConfirmationPage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Get Confirmation message
	 * 
	 * @return String
	 */
	public String getConfirmationMessage() {
		LOGGER.info("Getting confirmation message");
		// Set to upper case as Safari shows the message Capitalized while Chrome not and this does
		// not affect the test
		return getPageSubtitleText().getStringValue().toUpperCase();
	}

	/**
	 * Get Order Number
	 * 
	 * @return String
	 */
	public String getOrderNumber() {
		LOGGER.info("Getting order number");
		return getOrderNumberText().getStringValue();
	}

	/**
	 * Enter password.
	 *
	 * @param password
	 *            the password
	 */
	public void enterPassword(String password) {
		LOGGER.info("Entering password: " + password);
		getCreateAccountPasswordEditField().setText(password);
	}

	/**
	 * Enter confirm password.
	 *
	 * @param password
	 *            the password
	 */
	public void enterConfirmPassword(String password) {
		LOGGER.info("Entering password confirmation: " + password);
		getCreateAccountConfirmPasswordEditField().setText(password);
	}

	/**
	 * Create account my account page.
	 *
	 * @return the my account page
	 */
	public MyAccountPage createAccount() {
		LOGGER.info("Click on Create Account button");
		getCreateAccountButton().click();
		return PageFactory.create(MyAccountPage.class);
	}

	/**
	 * Gets subscription number.
	 *
	 * @return the subscription number
	 */
	public String getSubscriptionNumber() {
		return getSubscriptionNumberText().getText().trim();
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = ".default-page-text strong,.default-page-text .disc > li > a[href*='recurring_profile/view/profile']")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "h2.sub-title")
	protected Text getPageSubtitleText() {
		return new Text(this, getLocator(this, "getPageSubtitleText"));
	}

	@WebElementLocator(webDesktop = ".default-page-text strong")
	protected Text getOrderNumberText() {
		return new Text(this, getLocator(this, "getOrderNumberText"));
	}

	@WebElementLocator(webDesktop = ".default-page-text .disc > li > a[href*='recurring_profile/view/profile']")
	protected Text getSubscriptionNumberText() {
		return new Text(this, getLocator(this, "getSubscriptionNumberText"));
	}

	@WebElementLocator(webDesktop = "#email_address")
	protected EditField getCreateAccountEmailEditField() {
		return new EditField(this, getLocator(this, "getCreateAccountEmailEditField"));
	}

	@WebElementLocator(webDesktop = "#password")
	protected EditField getCreateAccountPasswordEditField() {
		return new EditField(this, getLocator(this, "getCreateAccountPasswordEditField"));
	}

	@WebElementLocator(webDesktop = "#confirmation")
	protected EditField getCreateAccountConfirmPasswordEditField() {
		return new EditField(this, getLocator(this, "getCreateAccountConfirmPasswordEditField"));
	}

	@WebElementLocator(webDesktop = "button[title='Create an Account']")
	protected Button getCreateAccountButton() {
		return new Button(this, getLocator(this, "getCreateAccountButton"));
	}
}
