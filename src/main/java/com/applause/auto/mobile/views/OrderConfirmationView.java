package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = OrderConfirmationView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = OrderConfirmationView.class, on = Platform.MOBILE_IOS)
public class OrderConfirmationView extends BaseComponent {

    /* -------- Elements -------- */

    @Locate(id = "com.wearehathway.peets.development:id/pickUpOrderLayout", on = Platform.MOBILE_ANDROID)
    @Locate(id = "Your order will be ready between:", on = Platform.MOBILE_IOS)
    protected Text geHeadingText;
}
