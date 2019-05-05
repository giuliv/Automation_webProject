package com.applause.auto.pageframework.chunks.mobile;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceChunk;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.views.DashboardView;
import com.applause.auto.pageframework.views.PeetsCardsView;

@AndroidImplementation(BottomNavigationMenuChunk.class)
@IosImplementation(BottomNavigationMenuChunk.class)
public class BottomNavigationMenuChunk extends AbstractDeviceChunk {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	public BottomNavigationMenuChunk(String selector) {
		super(selector);
	}

	@Override
	protected void waitUntilVisible() {

	}

	/**
	 * Peets cards peets cards view.
	 *
	 * @return the peets cards view
	 */
	/*
	 * Public actions
	 */
	public PeetsCardsView peetsCards() {
		LOGGER.info("Tap on Peeds Cards");
		getPeetsCardsButton().pressButton();
		return DeviceViewFactory.create(PeetsCardsView.class);
	}

	/**
	 * Home peets cards view.
	 *
	 * @return the peets cards view
	 */
	public DashboardView home() {
		LOGGER.info("Tap on Home");
		getHomeButton().pressButton();
		return DeviceViewFactory.create(DashboardView.class);
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "//android.widget.RelativeLayout[@resource-id=\"com.wearehathway.peets.development:id/bottom_navigation_container\"]/android.widget.TextView[@text=\"Home\"]/..", iOS = "//XCUIElementTypeButton[@name=\"Home\"]")
	protected Button getHomeButton() {
		return new Button(getLocator(this, "getHomeButton"));
	}

	@MobileElementLocator(android = "//android.widget.RelativeLayout[@resource-id=\"com.wearehathway.peets.development:id/bottom_navigation_container\"]/android.widget.TextView[@text=\"Peet's Card\"]/..", iOS = "//XCUIElementTypeButton[@name=\"Peet's Card\"]")
	protected Button getPeetsCardsButton() {
		return new Button(getLocator(this, "getPeetsCardsButton"));
	}

}
