package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.util.webDrivers.BrowserType;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = EditAccountInformationPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = EditAccountInformationPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = EditAccountInformationPage.class, on = Platform.WEB_MOBILE_PHONE)
public class EditAccountInformationPage extends BaseComponent {

	// Public actions

	/**
	 * Enter First Name
	 *
	 * @param firstName
	 */
	public void enterFirstName(String firstName) {
		logger.info("Entering First Name");
		getFirstNameField.clearText();
		getFirstNameField.sendKeys(firstName);
	}

	/**
	 * Enter Email
	 *
	 * @param email
	 */
	public void enterEmail(String email) {
		logger.info("Entering Email");
		getEmailField.clearText();
		getEmailField.sendKeys(email);
	}

	/**
	 * Enter Email By Browser
	 *
	 * @param email
	 * @param safariEmail
	 */
	public void enterEmailByBrowser(String email, String safariEmail) {
		logger.info("Entering Email");
		if (env.getBrowserType() == BrowserType.SAFARI) {
			enterEmail(safariEmail);
		} else {
			enterEmail(email);
		}
	}

	/**
	 * Enter Password
	 *
	 * @param password
	 */
	public void enterCurrentPassword(String password) {
		logger.info("Entering Current Password");
		getCurrentPasswordField.sendKeys(password);
	}

	/**
	 * Click Save Button
	 *
	 * @return MyAccountPage
	 */
	public MyAccountPage clickSave() {
		logger.info("Clicking Save");
		getSaveButton.click();
		return ComponentFactory.create(MyAccountPage.class);
	}

	/**
	 * Change current password.
	 */
	public void changeCurrentPassword() {
		logger.info("Check current password checkbox");
		getChangeCurrentPasswordButton.click();
	}

	/**
	 * Enter new password.
	 *
	 * @param password
	 *            the password
	 */
	public void enterNewPassword(String password) {
		logger.info("Typing new password: " + password);
		getNewPasswordTextBox.sendKeys(password);
	}

	/**
	 * Enter confirm password.
	 *
	 * @param password
	 *            the password
	 */
	public void enterConfirmPassword(String password) {
		logger.info("Typing confirmation password: " + password);
		getNewPasswordConfirmationTextBox.sendKeys(password);
	}

	// Protected getters
	@Locate(jQuery = "body > div.wrapper > div > div.main-container.col2-left-layout > div > div.col-main > div > div.page-title > h1", on = Platform.WEB_DESKTOP)
	protected Text getViewSignature;

	@Locate(jQuery = "#firstname", on = Platform.WEB_DESKTOP)
	protected TextBox getFirstNameField;

	@Locate(jQuery = "#email", on = Platform.WEB_DESKTOP)
	protected TextBox getEmailField;

	@Locate(jQuery = "button.button.btn-save", on = Platform.WEB_DESKTOP)
	protected Button getSaveButton;

	@Locate(jQuery = "#current_password", on = Platform.WEB_DESKTOP)
	protected TextBox getCurrentPasswordField;

	@Locate(jQuery = "label[for='change_password']", on = Platform.WEB_DESKTOP)
	protected Button getChangeCurrentPasswordButton;

	@Locate(jQuery = "#password", on = Platform.WEB_DESKTOP)
	protected TextBox getNewPasswordTextBox;

	@Locate(jQuery = "#confirmation", on = Platform.WEB_DESKTOP)
	protected TextBox getNewPasswordConfirmationTextBox;

}
