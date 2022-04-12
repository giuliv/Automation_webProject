package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;

@Implementation(is = PasswordRecoveryResetPage.class, on = Platform.WEB)
public class PasswordRecoveryResetPage extends BaseComponent {
	@Locate(name = "newPassword", on = Platform.WEB)
	private TextBox passwordTextBox;

	@Locate(name = "confirmPassword", on = Platform.WEB)
	private TextBox confirmPasswordTextBox;

	@Locate(css = "input[value=\"Reset Password\"]", on = Platform.WEB)
	private Button resetPasswordButton;

	@Locate(xpath = "//p[text()='We found some errors. Please review the form and make corrections.']", on = Platform.WEB)
	private Text passwordMismatchErrorText;

	@Locate(xpath = "//p[text()='Password requirements were not met. Password requirements: at least 6 characters, a lowercase letter, a number.']", on = Platform.WEB)
	private Text passwordFormatErrorText;

	/**
	 * Sets password.
	 *
	 * @param password
	 *            the password
	 * @return the password
	 */
	public PasswordRecoveryResetPage setPassword(String password) {
		logger.info("Set new password: " + password);
		passwordTextBox.clearText();
		passwordTextBox.sendKeys(password);
		return this;
	}

	/**
	 * Sets confirm password.
	 *
	 * @param password
	 *            the password
	 * @return the confirm password
	 */
	public PasswordRecoveryResetPage setConfirmPassword(String password) {
		logger.info("Set confirm password: " + password);
		confirmPasswordTextBox.clearText();
		confirmPasswordTextBox.sendKeys(password);
		return this;
	}

	/**
	 * Submit t.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param clazz
	 *            the clazz
	 * @return the t
	 */
	public <T extends BaseComponent> T submit(Class<T> clazz) {
		logger.info("Click submit button");
		resetPasswordButton.click();
		return SdkHelper.create(clazz);
	}

	/**
	 * Is password bad format error displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isPasswordBadFormatErrorDisplayed() {
		return passwordFormatErrorText.isDisplayed();
	}

	/**
	 * Is password mismatch error displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isPasswordMismatchErrorDisplayed() {
		return passwordMismatchErrorText.isDisplayed();
	}
}
