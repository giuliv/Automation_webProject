package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Checkbox;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.helper.SyncHelper;

@Implementation(is = AndroidGeneralSettingsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = GeneralSettingsView.class, on = Platform.MOBILE_IOS)
public class GeneralSettingsView extends BaseComponent {

	/* -------- Elements -------- */

	@Locate(id = "button back", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]", on = Platform.MOBILE_ANDROID)
	protected Button getBackButton;

	@Locate(id = "Promotional Emails, Receive offers, news, and more", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/emailSubscription", on = Platform.MOBILE_ANDROID)
	protected Checkbox getPromotionalEmailsButton;

	@Locate(id = "Push Notifications, Receive alerts about offers, news, and more", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/pushNotifications", on = Platform.MOBILE_ANDROID)
	protected Checkbox getPushNotificationButton;

	@Locate(id = "Location Services, Helps us locate your nearest Peet’s", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/enableLocation", on = Platform.MOBILE_ANDROID)
	protected Checkbox getLocationSetvicesButton;

	@Locate(id = "Allow", on = Platform.MOBILE_IOS)
	@Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
	protected Button getAllowLocationServicesButton;

	@Locate(id = "GENERAL SETTINGS", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.widget.TextView[@text='GENERAL SETTINGS']", on = Platform.MOBILE_ANDROID)
	protected Text getHeadingText;

	/* -------- Actions -------- */

	/**
	 * Get the text vaalue of the heading
	 * 
	 * @return
	 */
	public String getHeadingTextValue() {
		return getHeadingText.getText();
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
	public <T extends BaseComponent> T goBack(Class<T> clazz) {
		logger.info("Tap back button");
		getBackButton.click();
		return ComponentFactory.create(clazz, "");

	}

	/**
	 * Is promo email option checked boolean.
	 *
	 * @return the boolean
	 */
	public boolean isPromoEmailOptionChecked() {
		return getPromotionalEmailsButton.getAttributeValue("value").equals("1");
	}

	/**
	 * Is push notification checked boolean.
	 *
	 * @return the boolean
	 */
	public boolean isPushNotificationChecked() {
		return getPushNotificationButton.getAttributeValue("value").equals("1");
	}

	/**
	 * Is location services checked boolean.
	 *
	 * @return the boolean
	 */
	public boolean isLocationServicesChecked() {
		return getLocationSetvicesButton.getAttributeValue("value").equals("1");
	}

	/**
	 * Enable location services general settings view.
	 *
	 * @return the general settings view
	 */
	public GeneralSettingsView enableLocationServices() {
		logger.info("Checking Location services");
		if (!isLocationServicesChecked())
			getLocationSetvicesButton.click();
		logger.info("Tap Allow button");
		getAllowLocationServicesButton.click();
		SyncHelper.sleep(5000);
		logger.info("Accept alert");
		try {
      	DriverManager.getDriver().switchTo().alert().accept();
		} catch (Throwable throwable) {
			logger.info("Alert not found");
		}
		return this;
	}

	/**
	 * Disable location services peets settings view.
	 *
	 * @return the peets settings view
	 */
	public PeetsSettingsView disableLocationServices() {
		logger.info("Unchecking Location services");
		if (isLocationServicesChecked())
			getLocationSetvicesButton.click();
		logger.info("Accept alert");
		SyncHelper.sleep(5000);
		logger.info("Accepting alert");
		DriverManager.getDriver().switchTo().alert().accept();
		return ComponentFactory.create(PeetsSettingsView.class);
	}

	/**
	 * Enable promotional emails general settings view.
	 *
	 * @return the general settings view
	 */
	public GeneralSettingsView enablePromotionalEmails() {
		logger.info("Checking Promo emails services");
		if (!isPromoEmailOptionChecked())
			getPromotionalEmailsButton.click();
		return ComponentFactory.create(GeneralSettingsView.class);
	}

	/**
	 * Disable promotional emails general settings view.
	 *
	 * @return the general settings view
	 */
	public GeneralSettingsView disablePromotionalEmails() {
		logger.info("Unchecking Promo emails services");
		if (isPromoEmailOptionChecked())
			getPromotionalEmailsButton.click();
		return ComponentFactory.create(GeneralSettingsView.class);
	}
}

class AndroidGeneralSettingsView extends GeneralSettingsView {

	/* -------- Elements -------- */

	@Locate(id = "com.android.packageinstaller:id/permission_allow_button", on = Platform.MOBILE_ANDROID)
	protected Button getAllowLocationServices2Button;

	/* -------- Actions -------- */

	@Override
	public boolean isPromoEmailOptionChecked() {
		return getPromotionalEmailsButton.getAttributeValue("checked").equals("true");
	}

	@Override
	public boolean isPushNotificationChecked() {
		return getPushNotificationButton.getAttributeValue("checked").equals("true");
	}

	@Override
	public boolean isLocationServicesChecked() {
		return getLocationSetvicesButton.getAttributeValue("checked").equals("true");
	}

	@Override
	public GeneralSettingsView enableLocationServices() {
		logger.info("Checking Location services");
		if (!isLocationServicesChecked())
			getLocationSetvicesButton.click();
		logger.info("Tap Allow button");
		getAllowLocationServicesButton.click();
		getAllowLocationServices2Button.click();
		SyncHelper.sleep(5000);
		return this;
	}

	public PeetsSettingsView disableLocationServices() {
		logger.info("Unchecking Location services");
		if (isLocationServicesChecked())
			getLocationSetvicesButton.click();
		return ComponentFactory.create(PeetsSettingsView.class);
	}
}
