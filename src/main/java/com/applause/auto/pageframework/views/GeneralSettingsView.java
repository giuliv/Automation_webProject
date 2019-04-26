package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceChunk;
import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceChunkFactory;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Checkbox;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.helpers.MobileHelper;

@AndroidImplementation(AndroidGeneralSettingsView.class)
@IosImplementation(GeneralSettingsView.class)
public class GeneralSettingsView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getHeadingText());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Get the text vaalue of the heading
	 * 
	 * @return
	 */
	public String getHeadingTextValue() {
		return getHeadingText().getStringValue();
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
	 * Is promo email option checked boolean.
	 *
	 * @return the boolean
	 */
	public boolean isPromoEmailOptionChecked() {
		return getPromotionalEmailsButton().getAttributeValue("value").equals("1");
	}

	/**
	 * Is push notification checked boolean.
	 *
	 * @return the boolean
	 */
	public boolean isPushNotificationChecked() {
		return getPushNotificationButton().getAttributeValue("value").equals("1");
	}

	/**
	 * Is location services checked boolean.
	 *
	 * @return the boolean
	 */
	public boolean isLocationServicesChecked() {
		return getLocationSetvicesButton().getAttributeValue("value").equals("1");
	}

	/**
	 * Enable location services general settings view.
	 *
	 * @return the general settings view
	 */
	public GeneralSettingsView enableLocationServices() {
		LOGGER.info("Checking Location services");
		if (!isLocationServicesChecked())
			getLocationSetvicesButton().checkCheckbox();
		LOGGER.info("Tap Allow button");
		getAllowLocationServicesButton().pressButton();
		syncHelper.suspend(5000);
		LOGGER.info("Accept alert");
		getDriver().switchTo().alert().accept();
		return this;
	}

	/**
	 * Disable location services peets settings view.
	 *
	 * @return the peets settings view
	 */
	public PeetsSettingsView disableLocationServices() {
		LOGGER.info("Unchecking Location services");
		if (isLocationServicesChecked())
			getLocationSetvicesButton().checkCheckbox();
		LOGGER.info("Accept alert");
		syncHelper.suspend(5000);
		getDriver().switchTo().alert().accept();
		return DeviceViewFactory.create(PeetsSettingsView.class);
	}

	/**
	 * Enable promotional emails general settings view.
	 *
	 * @return the general settings view
	 */
	public GeneralSettingsView enablePromotionalEmails() {
		LOGGER.info("Checking Promo emails services");
		if (!isPromoEmailOptionChecked())
			getPromotionalEmailsButton().checkCheckbox();
		return DeviceViewFactory.create(GeneralSettingsView.class);
	}

	/**
	 * Disable promotional emails general settings view.
	 *
	 * @return the general settings view
	 */
	public GeneralSettingsView disablePromotionalEmails() {
		LOGGER.info("Unchecking Promo emails services");
		if (isPromoEmailOptionChecked())
			getPromotionalEmailsButton().checkCheckbox();
		return DeviceViewFactory.create(GeneralSettingsView.class);
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "Navigate up", iOS = "button back")
	protected Button getBackButton() {
		return new Button(getLocator(this, "getBackButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/emailSubscription", iOS = "Promotional Emails, Receive offers, news, and more")
	protected Checkbox getPromotionalEmailsButton() {
		return new Checkbox(getLocator(this, "getPromotionalEmailsButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/pushNotifications", iOS = "Push Notifications, Receive alerts about offers, news, and more")
	protected Checkbox getPushNotificationButton() {
		return new Checkbox(getLocator(this, "getPushNotificationButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/enableLocation", iOS = "Location Services, Helps us locate your nearest Peet’s")
	protected Checkbox getLocationSetvicesButton() {
		return new Checkbox(getLocator(this, "getLocationSetvicesButton"));
	}

	@MobileElementLocator(android = "android:id/button1", iOS = "Allow")
	protected Button getAllowLocationServicesButton() {
		return new Button(getLocator(this, "getAllowLocationServicesButton"));
	}

	@MobileElementLocator(android = "//android.widget.TextView[@text='General Settings']", iOS = "General Settings")
	protected Text getHeadingText() {
		return new Text(getLocator(this, "getHeadingText"));
	}

}

class AndroidGeneralSettingsView extends GeneralSettingsView {
	@Override
	public boolean isPromoEmailOptionChecked() {
		return getPromotionalEmailsButton().getAttributeValue("checked").equals("true");
	}

	@Override
	public boolean isPushNotificationChecked() {
		return getPushNotificationButton().getAttributeValue("checked").equals("true");
	}

	@Override
	public boolean isLocationServicesChecked() {
		return getLocationSetvicesButton().getAttributeValue("checked").equals("true");
	}

	@Override
	public GeneralSettingsView enableLocationServices() {
		LOGGER.info("Checking Location services");
		if (!isLocationServicesChecked())
			getLocationSetvicesButton().checkCheckbox();
		LOGGER.info("Tap Allow button");
		getAllowLocationServicesButton().pressButton();
		getAllowLocationServices2Button().pressButton();
		syncHelper.suspend(5000);
		return this;
	}

	public PeetsSettingsView disableLocationServices() {
		LOGGER.info("Unchecking Location services");
		if (isLocationServicesChecked())
			getLocationSetvicesButton().checkCheckbox();
		MobileHelper.scrollToBottom(10);
		syncHelper.suspend(5000);
		LOGGER.info(">>>>>" + getDriver().getPageSource());
		getDriver().switchTo().alert().accept();
		return DeviceViewFactory.create(PeetsSettingsView.class);
	}

	@MobileElementLocator(android = "com.android.packageinstaller:id/permission_allow_button")
	protected Button getAllowLocationServices2Button() {
		return new Button(getLocator(this, "getAllowLocationServices2Button"));
	}

}
