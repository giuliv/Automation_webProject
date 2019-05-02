package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceChunk;
import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceChunkFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Checkbox;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;

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

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]", iOS = "button back")
	protected Button getBackButton() {
		return new Button(getLocator(this, "getBackButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/emailSubscription", iOS = "Promotional Emails, Receive offers, news, and more")
	protected Checkbox getPromotionalEmailsButton() {
		return new Checkbox(getLocator(this, "getPromotionalEmailsButton"));
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
}
