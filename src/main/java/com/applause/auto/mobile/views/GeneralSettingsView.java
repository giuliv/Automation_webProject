package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = AndroidGeneralSettingsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = GeneralSettingsView.class, on = Platform.MOBILE_IOS)
public class GeneralSettingsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "button back", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.ImageButton[contains(@content-desc,\"Navigate up\") or contains(@content-desc,\"Nach oben\")]",
      on = Platform.MOBILE_ANDROID)
  protected Button getBackButton;

  @Locate(id = "Promotional Emails, Receive offers, news, and more", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/emailSubscription",
      on = Platform.MOBILE_ANDROID)
  protected Button getPromotionalEmailsButton;

  @Locate(
      id = "Push Notifications, Receive alerts about offers, news, and more",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/pushNotifications",
      on = Platform.MOBILE_ANDROID)
  protected Button getPushNotificationButton;

  @Locate(id = "Location Services, Helps us locate your nearest Peet’s", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/enableLocation", on = Platform.MOBILE_ANDROID)
  protected Button getLocationSetvicesButton;

  @Locate(id = "Allow", on = Platform.MOBILE_IOS)
  @Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
  protected Button getAllowLocationServicesButton;

  @Locate(id = "GENERAL SETTINGS", on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[@text='GENERAL SETTINGS']",
      on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  /* -------- Actions -------- */

  /**
   * Get the text vaalue of the heading
   *
   * @return
   */
  public String getHeadingTextValue() {
    return getHeadingText.getText();
  }

  /**
   * Go back t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T goBack(Class<T> clazz) {
    logger.info("Tap back button");
    getBackButton.click();
    getSyncHelper().sleep(2000);
    return this.create(clazz);
  }

  /**
   * Is promo email option checked boolean.
   *
   * @return the boolean
   */
  public boolean isPromoEmailOptionChecked() {
    return getPromotionalEmailsButton.getAttributeValue("value").equals("1");
  }

  /**
   * Is push notification checked boolean.
   *
   * @return the boolean
   */
  public boolean isPushNotificationChecked() {
    return getPushNotificationButton.getAttributeValue("value").equals("1");
  }

  /**
   * Is location services checked boolean.
   *
   * @return the boolean
   */
  public boolean isLocationServicesChecked() {
    return getLocationSetvicesButton.getAttributeValue("value").equals("1");
  }

  /**
   * Enable promotional emails general settings view.
   *
   * @return the general settings view
   */
  public GeneralSettingsView enablePromotionalEmails() {
    logger.info("Checking Promo emails services");
    if (!isPromoEmailOptionChecked()) {
      getPromotionalEmailsButton.initialize();
      MobileHelper.tapByCoordinatesOnElementCenter(getPromotionalEmailsButton);
      getSyncHelper().sleep(20000);
    }
    return this.create(GeneralSettingsView.class);
  }

  /**
   * Disable promotional emails general settings view.
   *
   * @return the general settings view
   */
  public GeneralSettingsView disablePromotionalEmails() {
    logger.info("Unchecking Promo emails services");
    if (isPromoEmailOptionChecked()) {
      getPromotionalEmailsButton.initialize();
      MobileHelper.tapByCoordinatesOnElementCenter(getPromotionalEmailsButton);
      getSyncHelper().sleep(20000);
    }
    return this.create(GeneralSettingsView.class);
  }
}

class AndroidGeneralSettingsView extends GeneralSettingsView {

  /* -------- Elements -------- */

  @Locate(
      id = "com.android.packageinstaller:id/permission_allow_button",
      on = Platform.MOBILE_ANDROID)
  protected Button getAllowLocationServices2Button;

  /* -------- Actions -------- */

  @Override
  public boolean isPromoEmailOptionChecked() {
    getSyncHelper().sleep(7000);
    return getPromotionalEmailsButton.getAttributeValue("checked").equals("true");
  }

  @Override
  public boolean isPushNotificationChecked() {
    getSyncHelper().sleep(7000);
    return getPushNotificationButton.getAttributeValue("checked").equals("true");
  }

  @Override
  public boolean isLocationServicesChecked() {
    getSyncHelper().sleep(7000);
    return getLocationSetvicesButton.getAttributeValue("checked").equals("true");
  }

  @Override
  public GeneralSettingsView disablePromotionalEmails() {
    logger.info("Unchecking Promo emails services");
    if (isPromoEmailOptionChecked()) {
      getPromotionalEmailsButton.click();
      logger.info(">>>>1" + getDriver().getPageSource());
      getSyncHelper().sleep(2000);
      logger.info(">>>>2" + getDriver().getPageSource());
      getSyncHelper().sleep(2000);
      logger.info(">>>>3" + getDriver().getPageSource());
      getSyncHelper().sleep(20000);
    }
    return this.create(GeneralSettingsView.class);
  }
}
