package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;

import org.openqa.selenium.Dimension;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Checkbox;
import com.applause.auto.framework.pageframework.devicecontrols.PickerWheel;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.devicecontrols.TextBox;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.helpers.MobileHelper;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

@AndroidImplementation(AndroidCreateAccountView.class)
@IosImplementation(CreateAccountView.class)
public class CreateAccountView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getHeadingText());
	}

	/**
	 * Privacy policy privacy policy view.
	 *
	 * @return the privacy policy view
	 */
	public PrivacyPolicyView privacyPolicy() {
		LOGGER.info("Tap on Privacy Policy");
		getPrivacyPolicyButton().pressButton();
		return DeviceViewFactory.create(PrivacyPolicyView.class);
	}

	/**
	 * Terms and conditions terms and conditions view.
	 *
	 * @return the terms and conditions view
	 */
	public TermsAndConditionsView termsAndConditions() {
		LOGGER.info("Tap on Terms and Conditions");
		getTermsAndConditionsButton().pressButton();
		return DeviceViewFactory.create(TermsAndConditionsView.class);
	}

	/**
	 * Sets firstname.
	 *
	 * @param firstname
	 *            the firstname
	 * @return the firstname
	 */
	public CreateAccountView setFirstname(String firstname) {
		LOGGER.info("Set first name to: " + firstname);
		getFirstnameTextBox().clearTextBox();
		getFirstnameTextBox().enterText(firstname);
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
		LOGGER.info("Set last name to: " + lastname);
		getLastnameTextBox().clearTextBox();
		getLastnameTextBox().enterText(lastname);
		MobileHelper.scrollDown(1);
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
		LOGGER.info("Set ZIP to: " + zipCode);
		getZipCodeTextBox().clearTextBox();
		getZipCodeTextBox().enterText(zipCode);
		MobileHelper.scrollDown(1);
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
		LOGGER.info("Set phone number to: " + phone);
		getPhoneNumberTextBox().clearTextBox();
		getPhoneNumberTextBox().enterText(phone + "\n");

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
		LOGGER.info(String.format("Set DOB number to: %s / %s / %s", day, month, year));
		getDOBValueTextBox().clickTextBox();
		MobileHelper.setPickerValue(day, getDOBDayPickerWheel());
		MobileHelper.setPickerValue(month, getDOBMonthPickerWheel());
		MobileHelper.setPickerValueReverse(year, getDOBYearPickerWheel());

		return DeviceViewFactory.create(CreateAccountView.class);
	}

	/**
	 * Sets email address.
	 *
	 * @param emailAddress
	 *            the email address
	 * @return the email address
	 */
	public CreateAccountView setEmailAddress(String emailAddress) {
		LOGGER.info("Set email address to: " + emailAddress);
		getEmailAddressTextBox().clearTextBox();
		getEmailAddressTextBox().enterText(emailAddress + "\n");
		MobileHelper.scrollDown(1);
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
		LOGGER.info("Set email address to: " + emailAddress);
		getConfirmEmailAddressTextBox().clearTextBox();
		getConfirmEmailAddressTextBox().enterText(emailAddress + "\n");
		MobileHelper.scrollDown(1);
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
		LOGGER.info("Set password to: " + password);
		getPasswordTextBox().clearTextBox();
		getPasswordTextBox().enterText(password + "\n");
		MobileHelper.scrollDown(1);
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
		LOGGER.info("Set confirmation password to: " + password);
		getConfirmPasswordTextBox().clearTextBox();
		getConfirmPasswordTextBox().enterText(password + "\n");
		MobileHelper.scrollDown(1);
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
		LOGGER.info("Set promo to: " + promo);
		getPromoCodeTextBox().clearTextBox();
		getPromoCodeTextBox().enterText(promo + "\n");
		MobileHelper.scrollDown(1);
		return this;
	}

	/**
	 * Show password create account view.
	 *
	 * @return the create account view
	 */
	public CreateAccountView showPassword() {
		LOGGER.info("Click to show password");
		getShowPasswordButton().pressButton();
		return this;
	}

	/**
	 * Hide password create account view.
	 *
	 * @return the create account view
	 */
	public CreateAccountView hidePassword() {
		LOGGER.info("Click to hide password");
		getHidePasswordButton().pressButton();
		return this;
	}

	/**
	 * Check privacy policy and terms and conditions.
	 */
	public void checkPrivacyPolicyAndTermsAndConditions() {
		LOGGER.info("Click on Privacy Policy button");
		getAgreePrivacyPolicyAndTermsAndConditions().tapCenterOfElement();
	}

	/**
	 * Create account dashboard view.
	 *
	 * @return the dashboard view
	 */
	public DashboardView createAccount() {
		LOGGER.info("Create account");
		getCreateAccountButton().pressButton();
		syncHelper.waitForElementToDisappear(getCreateAccountButton().getSelector());
		return DeviceViewFactory.create(DashboardView.class);
	}

	/**
	 * Gets phone number.
	 *
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return getPhoneNumberTextBox().getCurrentText();
	}

	/**
	 * Gets email address.
	 *
	 * @return the email address
	 */
	public String getEmailAddress() {
		return getEmailAddressTextBox().getCurrentText();
	}

	/**
	 * Gets firstname.
	 *
	 * @return the firstname
	 */
	public String getFirstname() {
		return getFirstnameTextBox().getCurrentText();
	}

	/**
	 * Gets lastname.
	 *
	 * @return the lastname
	 */
	public String getLastname() {
		return getLastnameTextBox().getCurrentText();

	}

	/**
	 * Gets dob.
	 *
	 * @return the dob
	 */
	public String getDOB() {
		return getDOBValueTextBox().getCurrentText();

	}

	/**
	 * Gets hidden password.
	 *
	 * @return the hidden password
	 */
	public String getHiddenPassword() {
		return getHiddenPasswordTextBox().getCurrentText().trim();

	}

	/**
	 * Gets password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return getPasswordTextBox().getCurrentText().trim();

	}

	/**
	 * Is email opt in checked boolean.
	 *
	 * @return the boolean
	 */
	public boolean isEmailOptInChecked() {
		return MobileHelper.isAttribtuePresent(getEmailsWithOffersCheckBox().getMobileElement(), "value");

	}

	/**
	 * Is create account button enabled boolean.
	 *
	 * @return the boolean
	 */
	public boolean isCreateAccountButtonEnabled() {
		return getCreateAccountButton().isEnabled();

	}

	/**
	 * Tap email opt in.
	 */
	public void tapEmailOptIn() {
		LOGGER.info("Tap on email opt in checkbo");
		getEmailsWithOffersCheckBox().tapCenterOfElement();

	}

	/**
	 * Is privacy policy and terms and conditions checked boolean.
	 *
	 * @return the boolean
	 */
	public boolean isPrivacyPolicyAndTermsAndConditionsChecked() {
		return MobileHelper.isAttribtuePresent(getAgreePrivacyPolicyAndTermsAndConditions().getMobileElement(),
				"value");
	}

	/**
	 * Gets zip code.
	 *
	 * @return the zip code
	 */
	public String getZipCode() {
		return getZipCodeTextBox().getCurrentText();
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "", iOS = "(//XCUIElementTypeButton[@name=\"hide password\"])[1]")
	protected Button getShowPasswordButton() {
		return new Button(getLocator(this, "getShowPasswordButton"));
	}

	@MobileElementLocator(android = "", iOS = "(//XCUIElementTypeButton[@name=\"reveal password\"])[1]")
	protected Button getHidePasswordButton() {
		return new Button(getLocator(this, "getHidePasswordButton"));
	}

	@MobileElementLocator(android = "", iOS = "(//XCUIElementTypeButton[@name=\"hide password\"])[2]")
	protected Button getShowConfirmationPasswordButton() {
		return new Button(getLocator(this, "getShowConfirmationPasswordButton"));
	}

	@MobileElementLocator(android = "", iOS = "(//XCUIElementTypeButton[@name=\"reveal password\"])[2]")
	protected Button getHideConfirmationPasswordButton() {
		return new Button(getLocator(this, "getHideConfirmationPasswordButton"));
	}

	@MobileElementLocator(android = "//*[@resource-id='android:id/pickers']/android.widget.NumberPicker[1]/android.widget.EditText", iOS = "//XCUIElementTypeDatePicker/XCUIElementTypeOther/XCUIElementTypePickerWheel[2]")
	protected PickerWheel getDOBDayPickerWheel() {
		return new PickerWheel(getLocator(this, "getDOBDayPickerWheel"));
	}

	@MobileElementLocator(android = "//*[@resource-id='android:id/pickers']/android.widget.NumberPicker[2]/android.widget.EditText", iOS = "//XCUIElementTypeDatePicker/XCUIElementTypeOther/XCUIElementTypePickerWheel[1]")
	protected PickerWheel getDOBMonthPickerWheel() {
		return new PickerWheel(getLocator(this, "getDOBMonthPickerWheel"));
	}

	@MobileElementLocator(android = "//*[@resource-id='android:id/pickers']/android.widget.NumberPicker[3]/android.widget.EditText", iOS = "//XCUIElementTypeDatePicker/XCUIElementTypeOther/XCUIElementTypePickerWheel[3]")
	protected PickerWheel getDOBYearPickerWheel() {
		return new PickerWheel(getLocator(this, "getDOBYearPickerWheel"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/firstName", iOS = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeTextField")
	protected TextBox getFirstnameTextBox() {
		return new TextBox(getLocator(this, "getFirstnameTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/lastName", iOS = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTextField")
	protected TextBox getLastnameTextBox() {
		return new TextBox(getLocator(this, "getLastnameTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/zipCodeEditText", iOS = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeTextField")
	protected TextBox getZipCodeTextBox() {
		return new TextBox(getLocator(this, "getZipCodeTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/birthday", iOS = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[4]/XCUIElementTypeTextField")
	protected TextBox getDOBValueTextBox() {
		return new TextBox(getLocator(this, "getDOBValueTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/phoneNumber", iOS = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[5]/XCUIElementTypeTextField")
	protected TextBox getPhoneNumberTextBox() {
		return new TextBox(getLocator(this, "getPhoneNumberTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/emailAddress", iOS = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[6]/XCUIElementTypeTextField")
	protected TextBox getEmailAddressTextBox() {
		return new TextBox(getLocator(this, "getEmailAddressTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/confirmEmailAddress", iOS = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[7]/XCUIElementTypeTextField")
	protected TextBox getConfirmEmailAddressTextBox() {
		return new TextBox(getLocator(this, "getConfirmEmailAddressTextBox"));
	}

	@MobileElementLocator(android = "", iOS = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[8]/XCUIElementTypeSecureTextField")
	protected TextBox getHiddenPasswordTextBox() {
		return new TextBox(getLocator(this, "getHiddenPasswordTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/password", iOS = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[8]/XCUIElementTypeTextField")
	protected TextBox getPasswordTextBox() {
		return new TextBox(getLocator(this, "getPasswordTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/confirmPassword", iOS = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[9]/XCUIElementTypeSecureTextField")
	protected TextBox getConfirmPasswordTextBox() {
		return new TextBox(getLocator(this, "getConfirmPasswordTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/promoCode", iOS = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[10]/XCUIElementTypeTextField")
	protected TextBox getPromoCodeTextBox() {
		return new TextBox(getLocator(this, "getPromoCodeTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/receiveMessageFromPeetCheckBox", iOS = "//XCUIElementTypeTextView[@value='Yes, please send me emails with exclusive offers, rewards, news, and more.']/following-sibling::XCUIElementTypeButton")
	protected Checkbox getEmailsWithOffersCheckBox() {
		return new Checkbox(getLocator(this, "getEmailsWithOffersCheckBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/agreePrivacyPolicyCheckBox", iOS = "//XCUIElementTypeTextView[@value='I agree to the Privacy Policy and Terms & Conditions']/following-sibling::XCUIElementTypeButton")
	protected Checkbox getAgreePrivacyPolicyAndTermsAndConditions() {
		return new Checkbox(getLocator(this, "getAgreePrivacyPolicyAndTermsAndConditions"));
	}

	@MobileElementLocator(android = "//*[contains(@text,'Privacy Policy')]", iOS = "Privacy Policy")
	protected Button getPrivacyPolicyButton() {
		return new Button(getLocator(this, "getPrivacyPolicyButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/createAccount", iOS = "//XCUIElementTypeButton[@name=\"Create Account\"]")
	protected Button getCreateAccountButton() {
		return new Button(getLocator(this, "getCreateAccountButton"));
	}

	@MobileElementLocator(android = "//*[contains(@text,'Conditions')]", iOS = "Terms & Conditions")
	protected Button getTermsAndConditionsButton() {
		return new Button(getLocator(this, "getTermsAndConditionsButton"));
	}

	@MobileElementLocator(android = "//android.widget.TextView[@text='Create Account']", iOS = "//XCUIElementTypeNavigationBar[@name=\"Create Account\"]")
	protected Text getHeadingText() {
		return new Text(getLocator(this, "getHeadingText"));
	}

}

class AndroidCreateAccountView extends CreateAccountView {
	@Override
	public DashboardView createAccount() {
		LOGGER.info("Create account");
		getCreateAccountButton().pressButton();
		return DeviceViewFactory.create(DashboardView.class);
	}

	@Override
	public TermsAndConditionsView termsAndConditions() {
		LOGGER.info("Tap on Terms and Conditions");
		Dimension size = getTermsAndConditionsButton().getMobileElement().getSize();
		MobileHelper.tapOnElementWithOffset(getTermsAndConditionsButton(), size.getWidth() / 3, 0);
		return DeviceViewFactory.create(TermsAndConditionsView.class);
	}

	@Override
	public CreateAccountView setDOB(String day, String month, String year) {
		LOGGER.info(String.format("Set DOB number to: %s / %s / %s", day, month, year));
		getDOBValueTextBox().clickTextBox();
		MobileHelper.setPickerValue(day + "\t", getDOBDayPickerWheel());
		MobileHelper.setPickerValue(month.substring(0, 3), getDOBMonthPickerWheel());
		MobileHelper.setPickerValueReverse(year + "\n", getDOBYearPickerWheel());
		getDOBOkButton().pressButton();
		return DeviceViewFactory.create(CreateAccountView.class);
	}

	@Override
	public CreateAccountView showPassword() {
		LOGGER.info("Click on Show Password button");
		MobileElement element = getPasswordTextBox().getMobileElement();
		int x = element.getCenter().getX();
		int y = element.getCenter().getY();
		int width = element.getSize().getWidth();
		(new TouchAction(getDriver())).tap(PointOption.point(x + width / 2 - 5, y)).perform();
		return this;
	}

	@Override
	public CreateAccountView hidePassword() {
		LOGGER.info("Click to hide password");
		MobileElement element = getPasswordTextBox().getMobileElement();
		int x = element.getCenter().getX();
		int y = element.getCenter().getY();
		int width = element.getSize().getWidth();
		(new TouchAction(getDriver())).tap(PointOption.point(x + width / 2 - 5, y)).perform();
		return this;
	}

	@Override
	public boolean isEmailOptInChecked() {
		return getEmailsWithOffersCheckBox().getAttributeValue("checked").equals("true");
	}

	@Override
	public boolean isPrivacyPolicyAndTermsAndConditionsChecked() {
		return getAgreePrivacyPolicyAndTermsAndConditions().getAttributeValue("checked").equals("true");
	}

	@Override
	public String getHiddenPassword() {
		return getPasswordTextBox().getCurrentText();
	}

	@MobileElementLocator(android = "android:id/button1")
	protected Button getDOBOkButton() {
		return new Button(getLocator(this, "getDOBOkButton"));
	}

}
