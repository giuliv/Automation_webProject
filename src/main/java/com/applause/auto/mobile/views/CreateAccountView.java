package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Checkbox;
import com.applause.auto.pageobjectmodel.elements.Picker;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import java.lang.invoke.MethodHandles;
import org.openqa.selenium.Dimension;

@Implementation(is = AndroidCreateAccountView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = CreateAccountView.class, on = Platform.MOBILE_IOS)
public class CreateAccountView extends BaseComponent {

	/**
	 * Privacy policy privacy policy view.
	 *
	 * @return the privacy policy view
	 */
	public PrivacyPolicyView privacyPolicy() {
		logger.info("Tap on Privacy Policy");
		getPrivacyPolicyButton.click();
		return ComponentFactory.create(PrivacyPolicyView.class);
	}

	/**
	 * Terms and conditions terms and conditions view.
	 *
	 * @return the terms and conditions view
	 */
	public TermsAndConditionsView termsAndConditions() {
		logger.info("Tap on Terms and Conditions");
		getTermsAndConditionsButton.click();
		return ComponentFactory.create(TermsAndConditionsView.class);
	}

	/**
	 * Sets firstname.
	 *
	 * @param firstname
	 *            the firstname
	 * @return the firstname
	 */
	public CreateAccountView setFirstname(String firstname) {
		logger.info("Set first name to: " + firstname);
		getFirstnameTextBox.clearText();
		getFirstnameTextBox.sendKeys(firstname);
		return this;
	}

	/**
	 * Sets lastname.
	 *
	 * @param lastname
	 *            the lastname
	 * @return the lastname
	 */
	public CreateAccountView setLastname(String lastname) {
		logger.info("Set last name to: " + lastname);
		getLastnameTextBox.clearText();
		getLastnameTextBox.sendKeys(lastname);
		return this;
	}

	/**
	 * Sets zip code.
	 *
	 * @param zipCode
	 *            the zip code
	 * @return the zip code
	 */
	public CreateAccountView setZipCode(String zipCode) {
		logger.info("Set ZIP to: " + zipCode);
		getZipCodeTextBox.clearText();
		getZipCodeTextBox.sendKeys(zipCode);
		return this;
	}

	/**
	 * Sets phone number.
	 *
	 * @param phone
	 *            the phone
	 * @return the phone number
	 */
	public CreateAccountView setPhoneNumber(String phone) {
		logger.info("Set phone number to: " + phone);
		getPhoneNumberTextBox.clearText();
		getPhoneNumberTextBox.sendKeys(phone + "\n");
		return this;
	}

	/**
	 * Sets dob.
	 *
	 * @param day
	 *            the day
	 * @param month
	 *            the month
	 * @param year
	 *            the year
	 * @return the dob
	 */
	public CreateAccountView setDOB(String day, String month, String year) {
		logger.info(String.format("Set DOB number to: %s / %s / %s", day, month, year));
		getDOBValueTextBox.clickBox();
		MobileHelper.setPickerValueReverse(year, getDOBYearPicker);

		return this;
	}

	/**
	 * Sets email address.
	 *
	 * @param emailAddress
	 *            the email address
	 * @return the email address
	 */
	public CreateAccountView setEmailAddress(String emailAddress) {
		logger.info("Set email address to: " + emailAddress);
		getEmailAddressTextBox.clearText();
		getEmailAddressTextBox.sendKeys(emailAddress + "\n");
		return this;
	}

	/**
	 * Sets confirm email address.
	 *
	 * @param emailAddress
	 *            the email address
	 * @return the confirm email address
	 */
	public CreateAccountView setConfirmEmailAddress(String emailAddress) {
		logger.info("Set email address to: " + emailAddress);
		MobileHelper.scrollDown(1);
		getConfirmEmailAddressTextBox.clearText();
		getConfirmEmailAddressTextBox.sendKeys(emailAddress + "\n");
		return this;
	}

	/**
	 * Sets password.
	 *
	 * @param password
	 *            the password
	 * @return the password
	 */
	public CreateAccountView setPassword(String password) {
		logger.info("Set password to: " + password);
		MobileHelper.scrollDown(1);
		getHiddenPasswordTextBox.clearText();
		getHiddenPasswordTextBox.sendKeys(password + "\n");
		return this;
	}

	/**
	 * Sets confirmation password.
	 *
	 * @param password
	 *            the password
	 * @return the confirmation password
	 */
	public CreateAccountView setConfirmationPassword(String password) {
		logger.info("Set confirmation password to: " + password);
		getConfirmPasswordTextBox.clearText();
		getConfirmPasswordTextBox.sendKeys(password + "\n");
		return this;
	}

	/**
	 * Sets promo.
	 *
	 * @param promo
	 *            the promo
	 * @return the promo
	 */
	public CreateAccountView setPromo(String promo) {
		logger.info("Set promo to: " + promo);
		getPromoCodeTextBox.clearText();
		getPromoCodeTextBox.sendKeys(promo + "\n");
		MobileHelper.scrollDown(1);
		return this;
	}

	/**
	 * Show password create account view.
	 *
	 * @return the create account view
	 */
	public CreateAccountView showPassword() {
		logger.info("Click to show password");
		getShowPasswordButton.click();
		return this;
	}

	/**
	 * Hide password create account view.
	 *
	 * @return the create account view
	 */
	public CreateAccountView hidePassword() {
		logger.info("Click to hide password");
		getHidePasswordButton.click();
		return this;
	}

	/**
	 * Check privacy policy and terms and conditions.
	 */
	public void checkPrivacyPolicyAndTermsAndConditions() {
		logger.info("Click on Privacy Policy button");
		getAgreePrivacyPolicyAndTermsAndConditions.tapCenterOfElement();
	}

	/**
	 * Create account dashboard view.
	 *
	 * @return the dashboard view
	 */
	public DashboardView createAccount() {
		logger.info("Create account");
		getCreateAccountButton.click();
		SyncHelper.waitUntilElementNotPresent(getCreateAccountButton.getSelector());
		return ComponentFactory.create(DashboardView.class);
	}

	/**
	 * Gets phone number.
	 *
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return getPhoneNumberTextBox.getCurrentText();
	}

	/**
	 * Gets email address.
	 *
	 * @return the email address
	 */
	public String getEmailAddress() {
		return getEmailAddressTextBox.getCurrentText();
	}

	/**
	 * Gets firstname.
	 *
	 * @return the firstname
	 */
	public String getFirstname() {
		return getFirstnameTextBox.getCurrentText();
	}

	/**
	 * Gets lastname.
	 *
	 * @return the lastname
	 */
	public String getLastname() {
		return getLastnameTextBox.getCurrentText();

	}

	/**
	 * Gets dob.
	 *
	 * @return the dob
	 */
	public String getDOB() {
		return getDOBValueTextBox.getCurrentText();

	}

	/**
	 * Gets hidden password.
	 *
	 * @return the hidden password
	 */
	public String getHiddenPassword() {
		return getHiddenPasswordTextBox.getCurrentText();

	}

	/**
	 * Gets password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return getPasswordTextBox.getCurrentText();

	}

	/**
	 * Is email opt in checked boolean.
	 *
	 * @return the boolean
	 */
	public boolean isEmailOptInChecked() {
		return MobileHelper.isAttribtuePresent(getEmailsWithOffersCheckBox.getMobileElement(), "value");

	}

	/**
	 * Is create account button enabled boolean.
	 *
	 * @return the boolean
	 */
	public boolean isCreateAccountButtonEnabled() {
		return getCreateAccountButton.isEnabled();

	}

	/**
	 * Tap email opt in.
	 */
	public void tapEmailOptIn() {
		logger.info("Tap on email opt in checkbo");
		getEmailsWithOffersCheckBox.tapCenterOfElement();

	}

	/**
	 * Is privacy policy and terms and conditions checked boolean.
	 *
	 * @return the boolean
	 */
	public boolean isPrivacyPolicyAndTermsAndConditionsChecked() {
		getDriver().getPageSource();
		MobileHelper.scrollDown(1);
		SyncHelper.sleep(10000);
		return MobileHelper.isAttribtuePresent(getAgreePrivacyPolicyAndTermsAndConditions.getMobileElement(),
				"value");
	}

	/**
	 * Gets zip code.
	 *
	 * @return the zip code
	 */
	public String getZipCode() {
		return getZipCodeTextBox.getCurrentText();
	}

	@Locate(xpath = "(//XCUIElementTypeButton[@name=\"hide password\"])[1]", on = Platform.MOBILE_IOS)
	@Locate(id = "", on = Platform.MOBILE_ANDROID)
	protected Button getShowPasswordButton;

	@Locate(xpath = "(//XCUIElementTypeButton[@name=\"reveal password\"])[1]", on = Platform.MOBILE_IOS)
	@Locate(id = "", on = Platform.MOBILE_ANDROID)
	protected Button getHidePasswordButton;

	@Locate(xpath = "(//XCUIElementTypeButton[@name=\"hide password\"])[2]", on = Platform.MOBILE_IOS)
	@Locate(id = "", on = Platform.MOBILE_ANDROID)
	protected Button getShowConfirmationPasswordButton;

	@Locate(xpath = "(//XCUIElementTypeButton[@name=\"reveal password\"])[2]", on = Platform.MOBILE_IOS)
	@Locate(id = "", on = Platform.MOBILE_ANDROID)
	protected Button getHideConfirmationPasswordButton;

	@Locate(xpath = "//XCUIElementTypeDatePicker/XCUIElementTypeOther/XCUIElementTypePicker[2]", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//*[@resource-id='android:id/pickers']/android.widget.NumberPicker[2]/android.widget.EditText", on = Platform.MOBILE_ANDROID)
	protected Picker getDOBDayPicker;

	@Locate(xpath = "//XCUIElementTypeDatePicker/XCUIElementTypeOther/XCUIElementTypePicker[1]", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//*[@resource-id='android:id/pickers']/android.widget.NumberPicker[1]/android.widget.EditText", on = Platform.MOBILE_ANDROID)
	protected Picker getDOBMonthPicker;

	@Locate(xpath = "//XCUIElementTypeDatePicker/XCUIElementTypeOther/XCUIElementTypePicker[3]", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//*[@resource-id='android:id/pickers']/android.widget.NumberPicker[3]/android.widget.EditText", on = Platform.MOBILE_ANDROID)
	protected Picker getDOBYearPicker;

	@Locate(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeTextField", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/firstName", on = Platform.MOBILE_ANDROID)
	protected TextBox getFirstnameTextBox;

	@Locate(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTextField", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/lastName", on = Platform.MOBILE_ANDROID)
	protected TextBox getLastnameTextBox;

	@Locate(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeTextField", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/zipCodeEditText", on = Platform.MOBILE_ANDROID)
	protected TextBox getZipCodeTextBox;

	@Locate(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[4]/XCUIElementTypeTextField", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/birthday", on = Platform.MOBILE_ANDROID)
	protected TextBox getDOBValueTextBox;

	@Locate(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[5]/XCUIElementTypeTextField", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/phoneNumber", on = Platform.MOBILE_ANDROID)
	protected TextBox getPhoneNumberTextBox;

	@Locate(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[6]/XCUIElementTypeTextField", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/emailAddress", on = Platform.MOBILE_ANDROID)
	protected TextBox getEmailAddressTextBox;

	@Locate(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[7]/XCUIElementTypeTextField", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/confirmEmailAddress", on = Platform.MOBILE_ANDROID)
	protected TextBox getConfirmEmailAddressTextBox;

	@Locate(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[8]/XCUIElementTypeSecureTextField", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/password", on = Platform.MOBILE_ANDROID)
	protected TextBox getHiddenPasswordTextBox;

	@Locate(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[8]/XCUIElementTypeTextField", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/password", on = Platform.MOBILE_ANDROID)
	protected TextBox getPasswordTextBox;

	@Locate(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[9]/XCUIElementTypeSecureTextField", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/confirmPassword", on = Platform.MOBILE_ANDROID)
	protected TextBox getConfirmPasswordTextBox;

	@Locate(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[10]/XCUIElementTypeTextField", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/promoCode", on = Platform.MOBILE_ANDROID)
	protected TextBox getPromoCodeTextBox;

	@Locate(xpath = "//XCUIElementTypeTextView[@value='Yes, please send me emails with exclusive offers, rewards, news, and more.']/following-sibling::XCUIElementTypeButton", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/receiveMessageFromPeetCheckBox", on = Platform.MOBILE_ANDROID)
	protected Checkbox getEmailsWithOffersCheckBox;

	@Locate(xpath = "//XCUIElementTypeTextView[@value='I agree to the Privacy Policy and Terms & Conditions']/following-sibling::XCUIElementTypeButton", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/agreePrivacyPolicyCheckBox", on = Platform.MOBILE_ANDROID)
	protected Checkbox getAgreePrivacyPolicyAndTermsAndConditions;

	@Locate(id = "Privacy Policy", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//*[contains(@text,'Privacy Policy')]", on = Platform.MOBILE_ANDROID)
	protected Button getPrivacyPolicyButton;

	@Locate(xpath = "//XCUIElementTypeButton[@name=\"Create Account\"]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/createAccount", on = Platform.MOBILE_ANDROID)
	protected Button getCreateAccountButton;

	@Locate(id = "Terms & Conditions", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//*[contains(@text,'Conditions')]", on = Platform.MOBILE_ANDROID)
	protected Button getTermsAndConditionsButton;

	@Locate(xpath = "//XCUIElementTypeNavigationBar[@name=\"CREATE ACCOUNT\"]", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.widget.TextView[@text='CREATE ACCOUNT']", on = Platform.MOBILE_ANDROID)
	protected Text getHeadingText;

}

class AndroidCreateAccountView extends CreateAccountView {
	@Override
	public DashboardView createAccount() {
		logger.info("Create account");
		getCreateAccountButton.click();
		return ComponentFactory.create(DashboardView.class);
	}

	@Override
	public TermsAndConditionsView termsAndConditions() {
		logger.info("Tap on Terms and Conditions");
		Dimension size = getTermsAndConditionsButton.getMobileElement().getSize();
		MobileHelper.tapOnElementWithOffset(getTermsAndConditionsButton, size.getWidth() / 3, 0);
		return ComponentFactory.create(TermsAndConditionsView.class);
	}

	@Override
	public CreateAccountView setDOB(String day, String month, String year) {
		logger.info(String.format("Set DOB number to: %s / %s / %s", day, month, year));
		getDOBValueTextBox.clickBox();
		MobileHelper.setPickerValueReverse(year, getDOBYearPicker);
		getDOBYearPicker.getMobileElement().click();
		getDOBDayPicker.getMobileElement().click();
		getDriver().hideKeyboard();
		getDOBOkButton.click();
		return ComponentFactory.create(CreateAccountView.class);
	}

	@Override
	public CreateAccountView showPassword() {
		logger.info("Click on Show Password button");
		MobileElement element = getPasswordTextBox.getMobileElement();
		int x = element.getCenter().getX();
		int y = element.getCenter().getY();
		int width = element.getSize().getWidth();
		(new TouchAction(getDriver())).tap(PointOption.point(x + width / 2 - 5, y)).perform();
		return this;
	}

	@Override
	public CreateAccountView hidePassword() {
		logger.info("Click to hide password");
		MobileElement element = getPasswordTextBox.getMobileElement();
		int x = element.getCenter().getX();
		int y = element.getCenter().getY();
		int width = element.getSize().getWidth();
		(new TouchAction(getDriver())).tap(PointOption.point(x + width / 2 - 5, y)).perform();
		return this;
	}

	@Override
	public boolean isEmailOptInChecked() {
		return getEmailsWithOffersCheckBox.getAttributeValue("checked").equals("true");
	}

	@Override
	public boolean isPrivacyPolicyAndTermsAndConditionsChecked() {
		return getAgreePrivacyPolicyAndTermsAndConditions.getAttributeValue("checked").equals("true");
	}

	@Override
	public String getHiddenPassword() {
		return getPasswordTextBox.getCurrentText();
	}

	public CreateAccountView setPromo(String promo) {
		logger.info("Set promo to: " + promo);
		getPromoCodeTextBox.clearText();
		getPromoCodeTextBox.sendKeys(promo);
		getDriver().hideKeyboard();
		return this;
	}

	public CreateAccountView setPassword(String password) {
		logger.info("Set password to: " + password);
		getHiddenPasswordTextBox.clearText();
		getHiddenPasswordTextBox.sendKeys(password);
		getDriver().hideKeyboard();
		return this;
	}

	public CreateAccountView setConfirmationPassword(String password) {
		logger.info("Set confirmation password to: " + password);
		getConfirmPasswordTextBox.clearText();
		getConfirmPasswordTextBox.sendKeys(password);
		getDriver().hideKeyboard();
		return this;
	}

	public CreateAccountView setPhoneNumber(String phone) {
		logger.info("Set phone number to: " + phone);
		getPhoneNumberTextBox.clearText();
		getPhoneNumberTextBox.sendKeys(phone);
		getDriver().hideKeyboard();
		return this;
	}

	public CreateAccountView setEmailAddress(String emailAddress) {
		logger.info("Set email address to: " + emailAddress);
		getEmailAddressTextBox.clearText();
		getEmailAddressTextBox.sendKeys(emailAddress);
		getDriver().hideKeyboard();
		return this;
	}

	public CreateAccountView setConfirmEmailAddress(String emailAddress) {
		logger.info("Set email address to: " + emailAddress);
		MobileHelper.scrollDown(1);
		getConfirmEmailAddressTextBox.clearText();
		getConfirmEmailAddressTextBox.sendKeys(emailAddress);
		getDriver().hideKeyboard();
		return this;
	}

	@Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
	protected Button getDOBOkButton;

}
