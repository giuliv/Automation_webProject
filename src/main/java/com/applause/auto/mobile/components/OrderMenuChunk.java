package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;

/** Order Menu chunk. */
@Implementation(is = OrderMenuChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = OrderMenuChunk.class, on = Platform.MOBILE_IOS)
public class OrderMenuChunk extends BaseComponent {

  /* -------- Elements -------- */

  // TODO for iOS
  @Locate(id = "Seasonal Favorites", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[@text='Seasonal Favorites']", on = Platform.MOBILE_ANDROID)
  protected Text seasonalFavoritesSubHeader;

  // TODO for iOS
  @Locate(xpath = "(//XCUIElementTypeStaticText[@name=\"Menu\"])[2]", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[@text='Menu']", on = Platform.MOBILE_ANDROID)
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
