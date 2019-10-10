package com.applause.auto.pageframework.views;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.helpers.MobileHelper;

import java.lang.invoke.MethodHandles;

@AndroidImplementation(AndroidCheckoutView.class)
@IosImplementation(CheckoutView.class)
public class CheckoutView extends AbstractDeviceView {

    protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

    @Override
    protected void waitUntilVisible() {
        syncHelper.waitForElementToAppear(getHeadingText(), 120000);
    }



    /*
     * Protected Getters
     */

    @MobileElementLocator(android = "//android.widget.TextView[@text='CHECKOUT']", iOS = "//XCUIElementTypeNavigationBar[@name=\"CHECKOUT\"]")
    protected Text getHeadingText() {
        return new Text(getLocator(this, "getHeadingText"));
    }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/checkoutButton", iOS = "Place Order")
    protected Button getPlaceOrderButton() {
        return new Button(getLocator(this, "getPlaceOrderButton"));
    }

    public <T extends AbstractDeviceView> T placeOrder(Class<T> clazz) {
        LOGGER.info("Tap place order");
        MobileHelper.scrollDownCloseToMiddle(5);
        getPlaceOrderButton().pressButton();
        return DeviceViewFactory.create(clazz);
    }
}

class AndroidCheckoutView extends CheckoutView {
    @Override
    public <T extends AbstractDeviceView> T placeOrder(Class<T> clazz) {
        LOGGER.info("Tap place order");
        MobileHelper.androidScrollTo(getLocator(this, "getPlaceOrderButton"));
        getPlaceOrderButton().pressButton();
        return DeviceViewFactory.create(clazz);
    }
}