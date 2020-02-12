package com.applause.auto.pageframework.views;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;

import java.lang.invoke.MethodHandles;

@AndroidImplementation(OrderConfirmationView.class)
@IosImplementation(OrderConfirmationView.class)
public class OrderConfirmationView extends AbstractDeviceView {

    protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

    @Override
    protected void waitUntilVisible() {
        syncHelper.waitForElementToAppear(getHeadingText(), 120000);
    }



    /*
     * Protected Getters
     */

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/pickUpOrderLayout", iOS = "Your order will be ready between:")
    protected Text getHeadingText() {
        return new Text(getLocator(this, "getHeadingText"));
    }

}
