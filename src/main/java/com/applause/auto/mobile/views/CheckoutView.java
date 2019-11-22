package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;

@Implementation(is = AndroidCheckoutView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = CheckoutView.class, on = Platform.MOBILE_IOS)
public class CheckoutView extends BaseComponent {

    /* -------- Elements -------- */

    @Locate(xpath = "//android.widget.TextView[@text='CHECKOUT']", on = Platform.MOBILE_ANDROID)
    @Locate(xpath = "//XCUIElementTypeNavigationBar[@name=\"CHECKOUT\"]", on = Platform.MOBILE_IOS)
    protected Text getHeadingText;

    @Locate(id = "com.wearehathway.peets.development:id/checkoutButton", on = Platform.MOBILE_ANDROID)
    @Locate(id = "Place Order", on = Platform.MOBILE_IOS)
    protected Button getPlaceOrderButton;

    /* -------- Actions -------- */

    public <T extends BaseComponent> T placeOrder(Class<T> clazz) {
        logger.info("Tap place order");
        MobileHelper.scrollDownHalfScreen(2);
        getPlaceOrderButton.click();
        return ComponentFactory.create(clazz);
    }
}

class AndroidCheckoutView extends CheckoutView {

    public <T extends BaseComponent> T placeOrder(Class<T> clazz) {
        logger.info("Tap place order");
        MobileHelper.swipeAcrossScreenCoordinates(0.5, 0.8, 0.5, 0.2, 10000);
        getPlaceOrderButton.click();
        return ComponentFactory.create(clazz);
    }
}
