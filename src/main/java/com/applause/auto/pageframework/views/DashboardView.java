package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceChunkFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.TextBox;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.chunks.AccountMenuMobileChunk;

@AndroidImplementation(DashboardView.class)
@IosImplementation(DashboardView.class)
public class DashboardView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getSignature());
	}

	public AccountMenuMobileChunk getAccountProfileMenu() {
		LOGGER.info("Open account profile menu");
		getMoreScreenButton().pressButton();
		return DeviceChunkFactory.create(AccountMenuMobileChunk.class, "");
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/yourFeedTextView", iOS = "Your Feed")
	protected TextBox getSignature() {
		return new TextBox(getLocator(this, "getSignature"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/actionMore", iOS = "")
	protected Button getMoreScreenButton() {
		return new Button(getLocator(this, "getMoreScreenButton"));
	}
}
