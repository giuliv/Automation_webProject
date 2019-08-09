package com.applause.auto.web.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Checkbox;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = SignUpPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = SignUpPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = SignUpPage.class, on = Platform.WEB_MOBILE_PHONE)
public class SignUpPage extends BaseComponent {

	public String email;

	/*
	 * Public Actions
	 */

	/**
	 * Continue after entering required Shipping info
	 * 
	 */
	public <T extends BaseComponent> T submitSignUpInfo(Class<T> clazz) {
		logger.info("Clicking Submit after filling sign-up info");
		fillSignUpInfo();
		clickSubmit();
		return ComponentFactory.create(clazz);
	}

	/**
	 * Fill Required Fields for Shipping
	 * 
	 */
	public void fillSignUpInfo() {
		logger.info("Filling Sign up info");
		SyncHelper.sleep(5000);
		long timeStamp = System.currentTimeMillis();
		email = String.format(Constants.TestData.EMAIL, timeStamp);
		getFirstNameTextBox.sendKeys(Constants.TestData.FIRST_NAME);
		getLastNameTextBox.sendKeys(Constants.TestData.LAST_NAME);
		getEmailTextBox.sendKeys(email);
		getConfirmEmailTextBox.sendKeys(email);
		getPasswordTextBox.sendKeys(Constants.TestData.PASSWORD);
		getConfirmPasswordTextBox.sendKeys(Constants.TestData.PASSWORD);
		getZipCodeTextBox.sendKeys(Constants.TestData.ZIP_CODE);
		disableSendEmail();
		disableRememberMe();
	}

	/**
	 * Disable Send Email Checkbox
	 * 
	 */
	public void disableSendEmail() {
		logger.info("Disable Send Email Checkbox");
		if (getSendEmailsCheckbox.isSelected()) {
			getSendEmailsCheckbox.click();
		}
	}

	/**
	 * Disable Remember me Checkbox
	 * 
	 */
	public void disableRememberMe() {
		logger.info("Disable Remember me Checkbox");
		if (getRememberMeCheckbox.isSelected()) {
			getRememberMeCheckbox.click();
		}
	}

	/**
	 * Click Submit
	 * 
	 */
	public void clickSubmit() {
		logger.info("Click Submit button");
		getSubmitButton.click();
	}

	/*
	 * Protected Getters
	 */

	@Locate(jQuery = ".account-create-form", on = Platform.WEB_DESKTOP)
	protected Text getViewSignature;

	@Locate(jQuery = "#firstname", on = Platform.WEB_DESKTOP)
	protected TextBox getFirstNameTextBox;

	@Locate(jQuery = "#lastname", on = Platform.WEB_DESKTOP)
	protected TextBox getLastNameTextBox;

	@Locate(jQuery = "#email_address", on = Platform.WEB_DESKTOP)
	protected TextBox getEmailTextBox;

	@Locate(jQuery = "#confirm_email", on = Platform.WEB_DESKTOP)
	protected TextBox getConfirmEmailTextBox;

	@Locate(jQuery = "#password", on = Platform.WEB_DESKTOP)
	protected TextBox getPasswordTextBox;

	@Locate(jQuery = "#confirmation", on = Platform.WEB_DESKTOP)
	protected TextBox getConfirmPasswordTextBox;

	@Locate(jQuery = "#zipcode", on = Platform.WEB_DESKTOP)
	protected TextBox getZipCodeTextBox;

	@Locate(jQuery = "#is_subscribed", on = Platform.WEB_DESKTOP)
	protected Checkbox getSendEmailsCheckbox;

	@Locate(jQuery = "#remember-me-box input", on = Platform.WEB_DESKTOP)
	protected Checkbox getRememberMeCheckbox;

	@Locate(jQuery = ".buttons-set button", on = Platform.WEB_DESKTOP)
	protected Button getSubmitButton;

	@Locate(jQuery = "opc-please-wait", on = Platform.WEB_DESKTOP)
	protected ContainerElement getShippingLoadingSpinner;

}