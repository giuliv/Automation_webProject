package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = CheckoutConfirmationPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = CheckoutConfirmationPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = CheckoutConfirmationPage.class, on = Platform.WEB_MOBILE_PHONE)
public class CheckoutConfirmationPage extends BaseComponent {

	/*
	 * Public Actions
	 */

	/**
	 * Get Confirmation message
	 * 
	 * @return String
	 */
	public String getConfirmationMessage() {
		logger.info("Getting confirmation message");
		// Set to upper case as Safari shows the message Capitalized while Chrome not and this does
		// not affect the test
		return getPageSubtitleText.getText().toUpperCase();
	}

	/**
	 * Get Order Number
	 * 
	 * @return String
	 */
	public String getOrderNumber() {
		logger.info("Getting order number");
		return getOrderNumberText.getText();
	}

	/**
	 * Enter password.
	 *
	 * @param password
	 *            the password
	 */
	public void enterPassword(String password) {
		logger.info("Entering password: " + password);
		getCreateAccountPasswordTextBox.sendKeys(password);
	}

	/**
	 * Enter confirm password.
	 *
	 * @param password
	 *            the password
	 */
	public void enterConfirmPassword(String password) {
		logger.info("Entering password confirmation: " + password);
		getCreateAccountConfirmPasswordTextBox.sendKeys(password);
	}

	/**
	 * Create account my account page.
	 *
	 * @return the my account page
	 */
	public MyAccountPage createAccount() {
		logger.info("Click on Create Account button");
		getCreateAccountButton.click();
		return ComponentFactory.create(MyAccountPage.class);
	}

	/**
	 * Gets subscription number.
	 *
	 * @return the subscription number
	 */
	public String getSubscriptionNumber() {
		return getSubscriptionNumberText.getCurrentText().trim();
	}

	/*
	 * Protected Getters
	 */

	@Locate(jQuery = ".default-page-text strong,.default-page-text .disc > li > a[href*='recurring_profile/view/profile']", on = Platform.WEB_DESKTOP)
	protected Text getViewSignature;

	@Locate(jQuery = "h2.sub-title", on = Platform.WEB_DESKTOP)
	protected Text getPageSubtitleText;

	@Locate(jQuery = ".default-page-text strong", on = Platform.WEB_DESKTOP)
	protected Text getOrderNumberText;

	@Locate(jQuery = ".default-page-text .disc > li > a[href*='recurring_profile/view/profile']", on = Platform.WEB_DESKTOP)
	protected Text getSubscriptionNumberText;

	@Locate(jQuery = "#email_address", on = Platform.WEB_DESKTOP)
	protected TextBox getCreateAccountEmailTextBox;

	@Locate(jQuery = "#password", on = Platform.WEB_DESKTOP)
	protected TextBox getCreateAccountPasswordTextBox;

	@Locate(jQuery = "#confirmation", on = Platform.WEB_DESKTOP)
	protected TextBox getCreateAccountConfirmPasswordTextBox;

	@Locate(jQuery = "button[title='Create an Account']", on = Platform.WEB_DESKTOP)
	protected Button getCreateAccountButton;
}
