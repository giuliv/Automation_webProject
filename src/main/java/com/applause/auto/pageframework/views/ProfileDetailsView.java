package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceChunk;
import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceChunkFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.devicecontrols.TextBox;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.chunks.AccountMenuMobileChunk;

@AndroidImplementation(ProfileDetailsView.class)
@IosImplementation(ProfileDetailsView.class)
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
	public ProfileDetailsView setLastname(String lastname) {
		LOGGER.info("Set last name to: " + lastname);
		getLastnameTextBox().clearTextBox();
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
		getZipCodeTextBox().clearTextBox();
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
		return getDOBTextBox().getCurrentText();

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
		return DeviceChunkFactory.create(clazz, "");

	}

	/**
	 * Gets zip code.
	 *
	 * @return the zip code
	 */
	public String getZipCode() {
		return getZipCodeTextBox().getCurrentText();
	}

	/**
	 * Save account menu mobile chunk.
	 *
	 * @return the account menu mobile chunk
	 */
	public AccountMenuMobileChunk save() {
		LOGGER.info("Click on SAVE button");
		getSaveButton().pressButton();
		syncHelper.suspend(5000);
		return DeviceChunkFactory.create(AccountMenuMobileChunk.class, "");
	}

	/*
	 * Protected Getters
	 */

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

	@MobileElementLocator(android = "Navigate up", iOS = "button back")
	protected Button getBackButton() {
		return new Button(getLocator(this, "getBackButton"));
	}

	@MobileElementLocator(android = "//android.widget.TextView[@text='Profile Details']", iOS = "//XCUIElementTypeOther[@name=\"Profile Details\"]")
	protected Text getSignature() {
		return new Text(getLocator(this, "getSignature"));
	}
}
