package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;

/** Order Menu chunk. */
@Implementation(is = MapView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = MapView.class, on = Platform.MOBILE_IOS)
public class MapView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//XCUIElementTypeApplication[@name='Maps']", on = Platform.MOBILE_IOS)
  @Locate(id = "com.google.android.apps.maps:id/mainmap_container", on = Platform.MOBILE_ANDROID)
  protected Text mapSignature;

  @Locate(xpath = "NA", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.google.android.apps.maps:id/search_omnibox_menu_button",
      on = Platform.MOBILE_ANDROID)
  protected Button navigateBackToApp;

  /* -------- Actions -------- */

  public void afterInit() {
    SdkHelper.getSyncHelper().waitUntil(condition -> mapSignature.isDisplayed());
  }
}
