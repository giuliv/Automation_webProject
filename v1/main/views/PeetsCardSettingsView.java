package com.applause.auto.pageframework.views;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;

import java.lang.invoke.MethodHandles;

@AndroidImplementation(PeetsCardSettingsView.class)
@IosImplementation(PeetsCardSettingsView.class)
public class PeetsCardSettingsView extends AbstractDeviceView {
    protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

    @Override
    protected void waitUntilVisible() {
        syncHelper.waitForElementToAppear(getLocator(this, "getViewSignature"));
    }

    // Public actions

    /**
     * Click Back Button
     *
     * @return PaymentMethodsView
     */
    public PaymentMethodsView clickBackButton() {
        LOGGER.info("Click Back Button");
        getBackButton().pressButton();
        return DeviceViewFactory.create(PaymentMethodsView.class);
    }

    // Protected getters
    @MobileElementLocator(android = "//android.widget.TextView[@text='Card Settings']", iOS = "//XCUIElementTypeOther[@name=\"Card Settings\"]")
    protected Text getViewSignature() { return new Text(getLocator(this, "getViewSignature")); }

    @MobileElementLocator(android = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]", iOS = "button back")
    protected Button getBackButton() { return new Button(getLocator(this, "getBackButton")); }
}
