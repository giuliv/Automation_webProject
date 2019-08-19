package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import java.lang.invoke.MethodHandles;

@Implementation(is = PeetsCardSettingsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = PeetsCardSettingsView.class, on = Platform.MOBILE_IOS)
public class PeetsCardSettingsView extends BaseComponent {

    // Public actions

    /**
     * Click Back Button
     *
     * @return PaymentMethodsView
     */
    public PaymentMethodsView clickBackButton() {
        logger.info("Click Back Button");
        getBackButton().click();
        return ComponentFactory.create(PaymentMethodsView.class);
    }

    // Protected getters
    @Locate(xpath = "//XCUIElementTypeOther[@name=\"Card Settings\"]", on = Platform.MOBILE_IOS)
    @Locate(xpath = "//android.widget.TextView[@text='Card Settings']", on = Platform.MOBILE_ANDROID)
    protected Text getViewSignature() { return new Text(getLocator(this, "getViewSignature")); }

    @Locate(id = "button back", on = Platform.MOBILE_IOS)
    @Locate(xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]", on = Platform.MOBILE_ANDROID)
    protected Button getBackButton() { return new Button(getLocator(this, "getBackButton")); }
}
