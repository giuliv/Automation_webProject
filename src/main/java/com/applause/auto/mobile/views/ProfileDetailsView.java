package com.applause.auto.mobile.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceChunk;
import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceChunkFactory;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.devicecontrols.TextBox;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.web.components.AccountMenuMobileChunk;
import com.applause.auto.mobile.helpers.MobileHelper;

@AndroidImplementation(ProfileDetailsView.class)
@IosImplementation(IosProfileDetailsView.class)
public class ProfileDetailsView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getSignature(), 120000);
		syncHelper.waitForElementToAppear(getFirstnameTextBox(), 120000);
		syncHelper.waitForElementToAppear(getLastnameTextBox(), 120000);
		syncHelper.waitForElementToAppear(getZipCodeTextBox(), 120000);
		syncHelper.waitForElementToAppear(getDOBTextBox(), 120000);
		syncHelper.waitForElementToAppear(getPhoneNumberTextBox(), 120000);
		syncHelper.waitForElementToAppear(getEmailAddressTextBox(), 120000);
		syncHelper.waitForElementToAppear(getChangePasswordButton(), 120000);
		syncHelper.waitForElementToAppear(getSaveButton(), 120000);
		syncHelper.waitForElementToAppear(getSignature(), 120000);
	}

	/**
	 * Sets firstname.
	 *
	 * @param firstname
	 *            the firstname
	 * @return the firstname
	 */
	public ProfileDetailsView setFirstname(String firstname) {
		LOGGER.info("Set first name to: " + firstname);
		getFirstnameTextBox().enterText(firstname);
		return this;
	}

	/**
	 * Change password profile details view.
	 *
	 * @return the profile details view
	 */
	public ChangePasswordView changePassword() {
		LOGGER.info("Tap on change password button");
		getChangePasswordButton().tapCenterOfElement();
		return DeviceViewFactory.create(ChangePasswordView.class);
	}

	/**
	 * Sets lastname.
	 *
	 * @param lastname
	 *            the lastname
	 * @return the lastname
	 */
	public ProfileDetailsView setLastname(String lastname) {
		LOGGER.info("Set last name to: " + lastname);
		getLastnameTextBox().enterText(lastname);
		return this;
	}

	/**
	 * Sets zip code.
	 *
	 * @param zipCode
	 *            the zip code
	 * @return the zip code
	 */
	public ProfileDetailsView setZipCode(String zipCode) {
		LOGGER.info("Set ZIP to: " + zipCode);
		getZipCodeTextBox().enterText(zipCode);
		return this;
	}

	/**
	 * Sets phone number.
	 *
	 * @param phone
	 *            the phone
	 * @return the phone number
	 */
	public ProfileDetailsView setPhoneNumber(String phone) {
		LOGGER.info("Set phone number to: " + phone);
		getPhoneNumberTextBox().clearTextBox();
		getPhoneNumberTextBox().enterText(phone);
		return this;
	}

	/**
	 * Sets email address.
	 *
	 * @param emailAddress
	 *            the email address
	 * @return the email address
	 */
	public ProfileDetailsView setEmailAddress(String emailAddress) {
		LOGGER.info("Set email address to: " + emailAddress);
		getEmailAddressTextBox().clearTextBox();
		getEmailAddressTextBox().enterText(emailAddress);
		return this;
	}

	/**
	 * Sets confirm email address.
	 *
	 * @param emailAddress
	 *            the email address
	 * @return the confirm email address
	 */
	public ProfileDetailsView setConfirmEmailAddress(String emailAddress) {
		LOGGER.info("Set email address to: " + emailAddress);
		getDriver().hideKeyboard();
		MobileHelper.scrollUp(1);
		getConfirmEmailAddressTextBox().clearTextBox();
		getConfirmEmailAddressTextBox().enterText(emailAddress);
		return this;
	}

	/**
	 * Gets phone number.
	 *
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return getPhoneNumberTextBox().getText();
	}

	/**
	 * Gets email address.
	 *
	 * @return the email address
	 */
	public String getEmailAddress() {
		return getEmailAddressTextBox().getText();
	}

	/**
	 * Gets firstname.
	 *
	 * @return the firstname
	 */
	public String getFirstname() {
		return getFirstnameTextBox().getText();
	}

	/**
	 * Gets lastname.
	 *
	 * @return the lastname
	 */
	public String getLastname() {
		return getLastnameTextBox().getText();

	}

	/**
	 * Gets dob.
	 *
	 * @return the dob
	 */
	public String getDOB() {
		return getDOBTextBox().getText();

	}

	/**
	 * Go back t.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param clazz
	 *            the clazz
	 * @return the t
	 */
	public <T extends AbstractDeviceChunk> T goBack(Class<T> clazz) {
		LOGGER.info("Tap back button");
		getBackButton().pressButton();
		syncHelper.suspend(4000);
		return DeviceChunkFactory.create(clazz, "");

	}

	/**
	 * Gets zip code.
	 *
	 * @return the zip code
	 */
	public String getZipCode() {
		return getZipCodeTextBox().getText();
	}

	/**
	 * Save account menu mobile chunk.
	 *
	 * @return the account menu mobile chunk
	 */
	public AccountMenuMobileChunk save() {
		LOGGER.info("Click on SAVE button");
		getDriver().hideKeyboard();
		getSaveButton().pressButton();
		syncHelper.suspend(5000);
		return DeviceChunkFactory.create(AccountMenuMobileChunk.class, "");
	}



	@MobileElementLocator(android = "com.wearehathway.peets.development:id/firstName", iOS = "//XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeTextField")
	protected TextBox getFirstnameTextBox() {
		return new TextBox(getLocator(this, "getFirstnameTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/lastName", iOS = "//XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTextField")
	protected TextBox getLastnameTextBox() {
		return new TextBox(getLocator(this, "getLastnameTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/zipCode", iOS = "//XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeTextField")
	protected TextBox getZipCodeTextBox() {
		return new TextBox(getLocator(this, "getZipCodeTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/birthday", iOS = "//XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[4]/XCUIElementTypeTextField")
	protected TextBox getDOBTextBox() {
		return new TextBox(getLocator(this, "getDOBTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/phoneNumber", iOS = "//XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[5]/XCUIElementTypeTextField")
	protected TextBox getPhoneNumberTextBox() {
		return new TextBox(getLocator(this, "getPhoneNumberTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/emailAddress", iOS = "//XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[6]/XCUIElementTypeTextField")
	protected TextBox getEmailAddressTextBox() {
		return new TextBox(getLocator(this, "getEmailAddressTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/confirmEmailAddress", iOS = "//XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[7]/XCUIElementTypeTextField")
	protected TextBox getConfirmEmailAddressTextBox() {
		return new TextBox(getLocator(this, "getConfirmEmailAddressTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/saveButton", iOS = "Save")
	protected Button getSaveButton() {
		return new Button(getLocator(this, "getSaveButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/changePassword", iOS = "Change Password")
	protected Button getChangePasswordButton() {
		return new Button(getLocator(this, "getChangePasswordButton"));
	}

	@MobileElementLocator(android = "//android.widget.ImageButton[@content-desc='Navigate up']", iOS = "button back")
	protected Button getBackButton() {
		return new Button(getLocator(this, "getBackButton"));
	}

	@MobileElementLocator(android = "//android.widget.TextView[@text='PROFILE DETAILS']", iOS = "//XCUIElementTypeOther[@name=\"PROFILE DETAILS\"]")
	protected Text getSignature() {
		return new Text(getLocator(this, "getSignature"));
	}
}

class IosProfileDetailsView extends ProfileDetailsView {

	public AccountMenuMobileChunk save() {
		LOGGER.info("Click on SAVE button");
		getDoneButton().pressButton();
		getSaveButton().pressButton();
		syncHelper.suspend(5000);
		return DeviceChunkFactory.create(AccountMenuMobileChunk.class, "");
	}

	public ProfileDetailsView setConfirmEmailAddress(String emailAddress) {
		LOGGER.info("Set email address to: " + emailAddress);
		getDoneButton().pressButton();
		getConfirmEmailAddressTextBox().clearTextBox();
		getConfirmEmailAddressTextBox().enterText(emailAddress);
		return this;
	}

	public ProfileDetailsView setZipCode(String zipCode) {
		LOGGER.info("Set ZIP to: " + zipCode);
		getZipCodeTextBox().clearTextBox();
		getZipCodeTextBox().enterText(zipCode);
		return this;
	}

	public ProfileDetailsView setLastname(String lastname) {
		LOGGER.info("Set last name to: " + lastname);
		getLastnameTextBox().clearTextBox();
		getLastnameTextBox().enterText(lastname);
		return this;
	}

	public ProfileDetailsView setFirstname(String firstname) {
		LOGGER.info("Set first name to: " + firstname);
		getFirstnameTextBox().clearTextBox();
		getFirstnameTextBox().enterText(firstname);
		return this;
	}

	@MobileElementLocator(iOS = "Done")
	protected Button getDoneButton() {
		return new Button(getLocator(this, "getDoneButton"));
	}
}
