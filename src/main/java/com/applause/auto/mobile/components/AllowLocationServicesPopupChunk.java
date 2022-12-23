package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.views.NearbySelectCoffeeBarView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = AllowLocationServicesPopupChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = AllowLocationServicesPopupChunk.class, on = Platform.MOBILE_IOS)
public class AllowLocationServicesPopupChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "com.wearehathway.peets.development:id/textView3", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeStaticText[`name CONTAINS 'Allow location services to help you find nearby Peet'`]",
      on = Platform.MOBILE_IOS)
  protected Text getTitleText;

  @Locate(id = "Allow", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/allowButton\")",
      on = Platform.MOBILE_ANDROID)
  protected Button getAllowButton;

  /* -------- Actions -------- */

  /**
   * Gets title.
   *
   * @return the title
   */
  public String getTitle() {
    return getTitleText.getText().replaceAll("’", "'").replaceAll("\\.$", "");
  }

  /**
   * Allow select coffee bar view.
   *
   * @return the select coffee bar view
   */
  public NearbySelectCoffeeBarView allow() {
    return allow(NearbySelectCoffeeBarView.class);
  }

  /**
   * Allow t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T allow(Class<T> clazz) {
    logger.info("Tap Allow button");
    getAllowButton.click();
    AllowLocationServicesSystemPopupChunk allowLocationServicesSystemPopupChunk =
        SdkHelper.create(AllowLocationServicesSystemPopupChunk.class);

    logger.info("Tap System pop up Allow");
    allowLocationServicesSystemPopupChunk.allow();
    return SdkHelper.create(clazz);
  }

  /**
   * Allow if request displayed nearby select coffee bar view.
   *
   * @return the nearby select coffee bar view
   */
  public NearbySelectCoffeeBarView allowIfRequestDisplayed() {
    logger.info("Tap Allow button");
    return allowIfRequestDisplayed(NearbySelectCoffeeBarView.class);
  }

  /**
   * Allow if request displayed t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T allowIfRequestDisplayed(Class<T> clazz) {
    logger.info("Tap Allow button");
    try {
      getAllowButton.click();
      AllowLocationServicesSystemPopupChunk allowLocationServicesSystemPopupChunk =
          SdkHelper.create(AllowLocationServicesSystemPopupChunk.class);

      logger.info("Tap System pop up Allow");
      allowLocationServicesSystemPopupChunk.allow();
    } catch (Throwable throwable) {
      logger.info("Popup not displayed");
    }
    return SdkHelper.create(clazz);
  }
}
