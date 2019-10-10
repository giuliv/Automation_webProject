package com.applause.auto.pageframework.chunks.mobile;

import com.applause.auto.framework.pageframework.device.AbstractDeviceChunk;
import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.helpers.MobileHelper;
import com.applause.auto.pageframework.views.CheckInView;
import com.applause.auto.pageframework.views.DashboardView;
import com.applause.auto.pageframework.views.OrderAheadView;
import com.applause.auto.pageframework.views.PeetsCardsView;
import com.applause.auto.pageframework.views.SelectCoffeeBarView;

import java.lang.invoke.MethodHandles;

@AndroidImplementation(BottomNavigationMenuChunk.class)
@IosImplementation(IosBottomNavigationMenuChunk.class)
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
     * Check in check in view.
     *
     * @return the check in view
     */
    public CheckInView checkIn() {
        LOGGER.info("Tap on Check In");
        getCheckInButton().pressButton();
        return DeviceViewFactory.create(CheckInView.class);
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

    /**
     * Order order ahead view.
     *
     * @return the order ahead view
     */
    public OrderAheadView order() {
        LOGGER.info("Tap on Order");
        getOrdersButton().pressButton();
        return DeviceViewFactory.create(OrderAheadView.class);
    }

    public <T extends AbstractDeviceView> T order(Class<T> clazz) {
        LOGGER.info("Tap on Order");
        getOrdersButton().pressButton();
        return DeviceViewFactory.create(clazz);
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

    //	@MobileElementLocator(android = "//android.widget.RelativeLayout[@resource-id=\"com.wearehathway.peets.development:id/bottom_navigation_container\"]/android.widget.TextView[@text=\"Order\"]/..", iOS = "//XCUIElementTypeButton[@name=\"Order\"]")
    @MobileElementLocator(android = "(//android.widget.RelativeLayout[@resource-id=\"com.wearehathway.peets.development:id/bottom_navigation_container\"])[3]", iOS = "//XCUIElementTypeButton[@name=\"Order\"]")
    protected Button getOrdersButton() {
        return new Button(getLocator(this, "getOrdersButton"));
    }

    @MobileElementLocator(android = "//android.widget.RelativeLayout[@resource-id=\"com.wearehathway.peets.development:id/bottom_navigation_container\"]/android.widget.TextView[@text=\"Check In\"]/..", iOS = "//XCUIElementTypeButton[@name=\"Check In\"]")
    protected Button getCheckInButton() {
        return new Button(getLocator(this, "getCheckInButton"));
    }
}

class IosBottomNavigationMenuChunk extends BottomNavigationMenuChunk {

    public IosBottomNavigationMenuChunk(String selector) {
        super(selector);
    }

    public <T extends AbstractDeviceView> T order(Class<T> clazz) {
        LOGGER.info("Tap on Order");
        SelectCoffeeBarView selectCoffeeBarView = super.order(SelectCoffeeBarView.class);
        selectCoffeeBarView = selectCoffeeBarView.enableLocation().allow();
        selectCoffeeBarView.search("Emeryville, CA, 94608, 1400 park avenue");
        syncHelper.suspend(10000);
        MobileHelper.getSnapshotManager().takeRemoteDeviceScreenshot("" + System.currentTimeMillis());

        selectCoffeeBarView.openCoffeebarFromSearchResults(1);
        return DeviceViewFactory.create(clazz);
    }


}