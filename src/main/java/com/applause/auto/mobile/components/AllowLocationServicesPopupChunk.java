package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.mobile.views.NearbySelectCoffeeBarView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = AndroidAllowLocationServicesPopupChunk.class, on = Platform.MOBILE_ANDROID)
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

  @Locate(id = "Not Now", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/notNowButton", on = Platform.MOBILE_ANDROID)
  protected Button getNotNowButton;

  @Locate(id = "Location Services will:", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/textView4", on = Platform.MOBILE_ANDROID)
  protected Text getSubTitleText;

  @Locate(id = "cancel", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/cancelButton", on = Platform.MOBILE_ANDROID)
  protected Button getCancelButton;

  @Locate(
      xpath =
          "(//XCUIElementTypeStaticText[@name=\"Location Services will:\"]/following-sibling::XCUIElementTypeOther//XCUIElementTypeStaticText)[1]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/textView5", on = Platform.MOBILE_ANDROID)
  protected Text getMessageText1Text;

  @Locate(
      xpath =
          "(//XCUIElementTypeStaticText[@name=\"Location Services will:\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText)[2]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/textView6", on = Platform.MOBILE_ANDROID)
  protected Text getMessageText2Text;

  @Locate(
      xpath =
          "(//XCUIElementTypeStaticText[@name=\"Location Services will:\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText)[3]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/textView7", on = Platform.MOBILE_ANDROID)
  protected Text getMessageText3Text;

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
   * Gets formatted message.
   *
   * @return the formatted message
   */
  public String getFormattedMessage() {
    return String.format(
        "%s %s %s",
        getTitle(),
        getSubTitleText.getText().replaceAll("’", "'").replaceAll("\\.$", ""),
        getMessageText1Text.getText().replaceAll("• ", ""));
  }

  /**
   * Is allow button displayed boolean.
   *
   * @return the boolean
   */
  public boolean isAllowButtonDisplayed() {
    return getAllowButton.isDisplayed();
  }

  /**
   * Is not now button displayed boolean.
   *
   * @return the boolean
   */
  public boolean isNotNowButtonDisplayed() {
    return MobileHelper.isDisplayed(getNotNowButton);
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

  /**
   * Not now allow location services system popup chunk.
   *
   * @return the allow location services system popup chunk
   */
  public void notNow() {
    logger.info("Tap Not Now button");
    getNotNowButton.click();
  }

  /** Click on Cancel button */
  public void clickCancelButton() {
    logger.info("Click Cancel button");
    getCancelButton.click();
  }
}

class AndroidAllowLocationServicesPopupChunk extends AllowLocationServicesPopupChunk {

  /* -------- Actions -------- */

  // Todo: Commented as part of update on pom to 4.1.2 [REVIEW AGAIN!!!]
  public void notNow() {
    logger.info("Tap Not Now button");
    SdkHelper.getDeviceControl().tapElementCenter(getNotNowButton);
    //        Point point = getNotNowButton.getMobileElement().getCenter();
    //        AppiumDriver driver = (AppiumDriver) SdkHelper.getDriver();
    //        new TouchAction(driver)
    //            .tap(PointOption.point(point.x, point.y))
    //            .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
    //            .tap(
    //                PointOption.point(
    //                    point.x + driver.manage().window().getSize().width / 4,
    //                    point.y + driver.manage().window().getSize().height / 10))
    //            .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
    //            .tap(PointOption.point(point.x, point.y))
    //            .perform();
  }
}
