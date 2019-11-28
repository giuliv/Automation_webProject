package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;

/** Order Menu chunk. */
@Implementation(is = CoffeeStoreContainerChuck.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = CoffeeStoreContainerChuck.class, on = Platform.MOBILE_IOS)
public class OrderMenuChunk extends BaseComponent {

  /* -------- Elements -------- */

  // TODO for iOS
  @Locate(id = "", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/getStartedButton",
      on = Platform.MOBILE_ANDROID)
  protected Text seasonalFavoritesSubHeader;

  // TODO for iOS
  @Locate(id = "", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/getStartedButton",
      on = Platform.MOBILE_ANDROID)
  protected Text menuSubheader;

  /* -------- Actions -------- */

  /**
   * Get the text value of the seasonal Favorites Sub Header
   *
   * @return
   */
  public String getSeasonalFavoritesSubHeaderTextValue() {
    return seasonalFavoritesSubHeader.getText();
  }

  /**
   * Get the text value of the Menu Sub Header
   *
   * @return
   */
  public String getMenuSubheaderTextValue() {
    return menuSubheader.getText();
  }
}
