package com.applause.auto.web.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.BaseHtmlElement;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Checkbox;
import com.applause.auto.framework.pageframework.webcontrols.EditField;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.common.data.TestConstants;

@WebDesktopImplementation(SignUpPage.class)
@WebTabletImplementation(SignUpPage.class)
@WebPhoneImplementation(SignUpPage.class)
public class SignUpPage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());
	public String email;

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Continue after entering required Shipping info
	 * 
	 */
	public <T extends AbstractPage> T submitSignUpInfo(Class<T> clazz) {
		LOGGER.info("Clicking Submit after filling sign-up info");
		fillSignUpInfo();
		clickSubmit();
		return PageFactory.create(clazz);
	}

	/**
	 * Fill Required Fields for Shipping
	 * 
	 */
	public void fillSignUpInfo() {
		LOGGER.info("Filling Sign up info");
		syncHelper.suspend(5000);
		long timeStamp = System.currentTimeMillis();
		email = String.format(TestConstants.TestData.EMAIL, timeStamp);
		getFirstNameEditField().setText(TestConstants.TestData.FIRST_NAME);
		getLastNameEditField().setText(TestConstants.TestData.LAST_NAME);
		getEmailEditField().setText(email);
		getConfirmEmailEditField().setText(email);
		getPasswordEditField().setText(TestConstants.TestData.PASSWORD);
		getConfirmPasswordEditField().setText(TestConstants.TestData.PASSWORD);
		getZipCodeEditField().setText(TestConstants.TestData.ZIP_CODE);
		disableSendEmail();
		disableRememberMe();
	}

	/**
	 * Disable Send Email Checkbox
	 * 
	 */
	public void disableSendEmail() {
		LOGGER.info("Disable Send Email Checkbox");
		if (getSendEmailsCheckbox().isSelected()) {
			getSendEmailsCheckbox().check();
		}
	}

	/**
	 * Disable Remember me Checkbox
	 * 
	 */
	public void disableRememberMe() {
		LOGGER.info("Disable Remember me Checkbox");
		if (getRememberMeCheckbox().isSelected()) {
			getRememberMeCheckbox().check();
		}
	}

	/**
	 * Click Submit
	 * 
	 */
	public void clickSubmit() {
		LOGGER.info("Click Submit button");
		getSubmitButton().click();
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = ".account-create-form")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "#firstname")
	protected EditField getFirstNameEditField() {
		return new EditField(this, getLocator(this, "getFirstNameEditField"));
	}

	@WebElementLocator(webDesktop = "#lastname")
	protected EditField getLastNameEditField() {
		return new EditField(this, getLocator(this, "getLastNameEditField"));
	}

	@WebElementLocator(webDesktop = "#email_address")
	protected EditField getEmailEditField() {
		return new EditField(this, getLocator(this, "getEmailEditField"));
	}

	@WebElementLocator(webDesktop = "#confirm_email")
	protected EditField getConfirmEmailEditField() {
		return new EditField(this, getLocator(this, "getConfirmEmailEditField"));
	}

	@WebElementLocator(webDesktop = "#password")
	protected EditField getPasswordEditField() {
		return new EditField(this, getLocator(this, "getPasswordEditField"));
	}

	@WebElementLocator(webDesktop = "#confirmation")
	protected EditField getConfirmPasswordEditField() {
		return new EditField(this, getLocator(this, "getConfirmPasswordEditField"));
	}

	@WebElementLocator(webDesktop = "#zipcode")
	protected EditField getZipCodeEditField() {
		return new EditField(this, getLocator(this, "getZipCodeEditField"));
	}

	@WebElementLocator(webDesktop = "#is_subscribed")
	protected Checkbox getSendEmailsCheckbox() {
		return new Checkbox(this, getLocator(this, "getSendEmailsCheckbox"));
	}

	@WebElementLocator(webDesktop = "#remember-me-box input")
	protected Checkbox getRememberMeCheckbox() {
		return new Checkbox(this, getLocator(this, "getRememberMeCheckbox"));
	}

	@WebElementLocator(webDesktop = ".buttons-set button")
	protected Button getSubmitButton() {
		return new Button(this, getLocator(this, "getSubmitButton"));
	}

	@WebElementLocator(webDesktop = "opc-please-wait")
	protected BaseHtmlElement getShippingLoadingSpinner() {
		return new BaseHtmlElement(this, getLocator(this, "getShippingLoadingSpinner"));
	}

}