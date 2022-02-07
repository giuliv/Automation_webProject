package com.applause.auto.mobile.components;

import com.applause.auto.common.data.Constants.MobileTestData;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.BaseElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

/** Order Menu chunk. */
@Implementation(is = OrderMenuChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = OrderMenuChunkIos.class, on = Platform.MOBILE_IOS)
public class OrderMenuChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "Favorites", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[@text='Favorites']", on = Platform.MOBILE_ANDROID)
  protected Text favoritesSubHeader;

  @Locate(xpath = "(//XCUIElementTypeStaticText[@name=\"Menu\"])[2]", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[@text='Menu']", on = Platform.MOBILE_ANDROID)
  protected Text menuSubheader;

  @Locate(id = "Recents", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[@text='Recents']", on = Platform.MOBILE_ANDROID)
  protected Text recentsSubHeader;

  /* -------- Actions -------- */

  /**
   * Get the text value of the seasonal Favorites Sub Header
   *
   * @return
   */
  public String getFavoritesSubHeaderTextValue() {
    return favoritesSubHeader.getText();
  }

  /**
   * Get the text value of the Menu Sub Header
   *
   * @return
   */
  public String getMenuSubheaderTextValue() {
    return menuSubheader.getText();
  }

  @Step("Tap On Recents")
  public void tapOnRecents() {
    recentsSubHeader.click();
  }

  @Step("Tap On favorites")
  public void tapOnFavorites() {
    favoritesSubHeader.click();
  }

  @Step("Check if Recents tab is Highlighted")
  public boolean isRecentsHighlighted() {
    return isMenuButtonHighlighted(recentsSubHeader);
  }

  @Step("Check if Favorites tab is Highlighted")
  public boolean isFavoritesHighlighted() {
    return isMenuButtonHighlighted(favoritesSubHeader);
  }

  protected boolean isMenuButtonHighlighted(BaseElement element) {
    int colourRed = MobileHelper.getMobileElementColour(element.getMobileElement()).getRed();
    logger.info("Color: " + colourRed);
    return (colourRed == 193) || (colourRed == 194);
  }
}

class OrderMenuChunkIos extends OrderMenuChunk {

  protected boolean isMenuButtonHighlighted(BaseElement element) {
    int colourRed = MobileHelper.getMobileElementColour(element.getMobileElement()).getRed();
    logger.info("Color: " + colourRed);
    return MobileTestData.RED_COLORS.stream().allMatch(colour -> colour.equals(colourRed));
  }
}
