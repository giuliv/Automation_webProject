package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = AndroidPersonalSettingsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = PersonalSettingsView.class, on = Platform.MOBILE_IOS)
public class PersonalSettingsView extends BaseComponent {

  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeNavigationBar[`name == 'PERSONAL SETTINGS'`]/XCUIElementTypeButton",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.ImageButton[contains(@content-desc,\"Navigate up\") or contains(@content-desc,\"Nach oben\")]",
      on = Platform.MOBILE_ANDROID)
  protected Button backButton;

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
  protected Button getLocationServicesButton;

  @Locate(accessibilityId = "Allow Once", on = Platform.MOBILE_IOS)
  @Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
  protected Button getAllowLocationServicesButton;

  @Locate(id = "GENERAL SETTINGS", on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[@text='GENERAL SETTINGS']",
      on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate(
      xpath =
          "//XCUIElementTypeApplication[@name=\"Peets-Sandbox\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/loader", on = Platform.MOBILE_ANDROID)
  protected ContainerElement getLoader;

  @Locate(xpath = "//android.widget.Button[@text='ALLOW']", on = Platform.MOBILE_ANDROID)
  protected Button allowFindNearbyCoffeeBarsButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Go to Settings\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.Button[@text='SETTINGS']", on = Platform.MOBILE_ANDROID)
  protected Button openDeviceSettingsButton;

  @Locate(
      xpath = "//XCUIElementTypeButton[contains(@name,'Allow') or contains(@name,'allow')]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.Button[starts-with(@text,'Allow') or starts-with(@text,'allow') or starts-with(@text,'TOLAK') or @text='ALLOW']",
      on = Platform.MOBILE_ANDROID)
  protected Button allowPeetsAccessDeviceLocationButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeCell[`label BEGINSWITH \"Location\"`]",
      on = Platform.MOBILE_IOS)
  protected Button iosSettingsLocationButton;

  @Locate(iOSClassChain = "**/XCUIElementTypeCell[`label == \"Never\"`]", on = Platform.MOBILE_IOS)
  protected Button iosSettingsLocationNeverButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeCell[`label == \"While Using\"`]",
      on = Platform.MOBILE_IOS)
  protected Button iosSettingsLocationOnButton;

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
    backButton.click();
    SdkHelper.getSyncHelper().sleep(2000);
    return SdkHelper.create(clazz);
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
    SdkHelper.getSyncHelper().sleep(5000);
    getLocationServicesButton.initialize();
    return getLocationServicesButton.getAttributeValue("value").equals("1");
  }

  /**
   * Enable promotional emails general settings view.
   *
   * @return the general settings view
   */
  public PersonalSettingsView enablePromotionalEmails() {
    if (!isPromoEmailOptionChecked()) {
      logger.info("Checking Promo emails services");
      getPromotionalEmailsButton.initialize();
      MobileHelper.tapByCoordinatesOnElementCenter(getPromotionalEmailsButton);
      SdkHelper.getSyncHelper().sleep(5000);
    } else {
      logger.info("Promo emails services is enabled");
    }
    return SdkHelper.create(PersonalSettingsView.class);
  }

  /**
   * Disable promotional emails general settings view.
   *
   * @return the general settings view
   */
  public PersonalSettingsView disablePromotionalEmails() {
    logger.info("Unchecking Promo emails services");
    if (isPromoEmailOptionChecked()) {
      getPromotionalEmailsButton.initialize();
      MobileHelper.tapByCoordinatesOnElementCenter(getPromotionalEmailsButton);
      SdkHelper.getSyncHelper().sleep(10000);
    }
    return SdkHelper.create(PersonalSettingsView.class);
  }

  @Step("Tap on the Location Services toggle")
  public PersonalSettingsView toggleLocationServicesOff() {
    logger.info("Tapping on the Location Services");
    getLocationServicesButton.initialize();
    MobileHelper.tapByCoordinatesOnElementCenter(getLocationServicesButton);
    SdkHelper.getSyncHelper().sleep(5000); // need to wait for one of the modals to appear
    if (MobileHelper.isElementDisplayed(openDeviceSettingsButton, 5)) {
      openDeviceSettingsLocationOff();
    }
    return SdkHelper.create(PersonalSettingsView.class);
  }

  @Step("Tap on the Location Services toggle")
  public PersonalSettingsView toggleLocationServicesOn() {
    logger.info("Tapping on the Location Services");
    getLocationServicesButton.initialize();
    MobileHelper.tapByCoordinatesOnElementCenter(getLocationServicesButton);
    if (MobileHelper.isElementDisplayed(openDeviceSettingsButton, 5)) {
      openDeviceSettingsLocationOn();
      MobileHelper.tapByCoordinatesOnElementCenter(getLocationServicesButton);
      allowPeetsAccessDeviceLocation();
      MobileHelper.tapByCoordinatesOnElementCenter(getAllowLocationServicesButton);
    } else {
      allowPeetsAccessDeviceLocation();
      MobileHelper.tapByCoordinatesOnElementCenter(getAllowLocationServicesButton);
    }
    return SdkHelper.create(PersonalSettingsView.class);
  }

  protected void allowPeetsAccessDeviceLocation() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(allowPeetsAccessDeviceLocationButton).visible());
    SdkHelper.getDeviceControl().tapElementCenter(allowPeetsAccessDeviceLocationButton);
  }

  protected void openDeviceSettingsLocationOn() {
    if (MobileHelper.isElementDisplayed(openDeviceSettingsButton, 5)) {
      openDeviceSettingsButton.click();
    }
    SdkHelper.getSyncHelper().sleep(5000);
    MobileHelper.openSettings()
        .init()
        .openMenuItem("Privacy", IosSettingsView.class)
        .openMenuItem("Location Services", IosSettingsView.class);
    if (MobileHelper.isElementDisplayed(iosSettingsLocationButton, 5)) {
      logger.info("Taping on the Location button");
      SdkHelper.getSyncHelper().sleep(3000);
      iosSettingsLocationButton.click();
    }

    settingsLocationOn();
  }

  protected void openDeviceSettingsLocationOff() {
    if (MobileHelper.isElementDisplayed(openDeviceSettingsButton, 5)) {
      openDeviceSettingsButton.click();
    }

    logger.info("Taping Off the Location button");
    SdkHelper.getSyncHelper().sleep(3000);
    SdkHelper.getDeviceControl().tapElementCenter(iosSettingsLocationButton);

    settingsLocationOff();
  }

  protected void settingsLocationOn() {

    if (MobileHelper.isElementDisplayed(iosSettingsLocationOnButton, 5)) {
      logger.info("Taping on the Location On button");
      SdkHelper.getSyncHelper().sleep(3000);
      iosSettingsLocationOnButton.click();
    }

    SdkHelper.getSyncHelper().sleep(3000);
    MobileHelper.activateApp();
  }

  protected void settingsLocationOff() {
    logger.info("Taping Location Never button");
    SdkHelper.getSyncHelper().sleep(3000);
    iosSettingsLocationNeverButton.click();
    SdkHelper.getSyncHelper().sleep(3000);
    MobileHelper.activateApp();
  }
}

class AndroidPersonalSettingsView extends PersonalSettingsView {

  /* -------- Elements -------- */

  @Locate(
      xpath =
          "//*[@text=\"Allow only while using the app\" or @text=\"ALLOW ONLY WHILE USING THE APP\"]",
      on = Platform.MOBILE_ANDROID)
  protected Button allowPeetsToAccessDeviceLocationButton;

  @Locate(xpath = "//*[contains(@resource-id,'switch_widget')]", on = Platform.MOBILE_ANDROID)
  protected Button androidSwitchLocationToggle;

  /* -------- Actions -------- */

  @Override
  public boolean isPromoEmailOptionChecked() {
    SdkHelper.getSyncHelper().sleep(3000);
    return getPromotionalEmailsButton.getAttributeValue("checked").equals("true");
  }

  @Override
  public boolean isPushNotificationChecked() {
    SdkHelper.getSyncHelper().sleep(3000);
    return getPushNotificationButton.getAttributeValue("checked").equals("true");
  }

  @Override
  public boolean isLocationServicesChecked() {
    SdkHelper.getSyncHelper().sleep(3000);
    return getLocationServicesButton.getAttributeValue("checked").equals("true");
  }

  @Override
  public PersonalSettingsView disablePromotionalEmails() {
    logger.info("Unchecking Promo emails services");
    if (isPromoEmailOptionChecked()) {
      getPromotionalEmailsButton.click();
      if (getLoader.exists()) {
        SdkHelper.getSyncHelper()
            .wait(Until.uiElement(getLoader).notPresent().setTimeout(Duration.ofSeconds(30)));
      }
    }
    return SdkHelper.create(PersonalSettingsView.class);
  }

  @Override
  public PersonalSettingsView toggleLocationServicesOn() {
    logger.info("Tapping on the Location Services");
    getLocationServicesButton.initialize();
    MobileHelper.tapByCoordinatesOnElementCenter(getLocationServicesButton);
    SdkHelper.getSyncHelper().wait(Until.uiElement(getLoader).notPresent());
    allowPeetsAccessDeviceLocation();
    allowPeetsToAccessDeviceLocation();
    if (MobileHelper.isElementDisplayed(openDeviceSettingsButton, 5)) {
      openDeviceSettingsLocationOn();
      toggleLocationSettings();
    }
    return SdkHelper.create(PersonalSettingsView.class);
  }

  @Override
  public PersonalSettingsView toggleLocationServicesOff() {
    logger.info("Tapping on the Location Services");
    getLocationServicesButton.initialize();
    MobileHelper.tapByCoordinatesOnElementCenter(getLocationServicesButton);
    SdkHelper.getSyncHelper().wait(Until.uiElement(getLoader).notPresent());
    toggleLocationSettings();
    return SdkHelper.create(PersonalSettingsView.class);
  }

  private void allowPeetsToAccessDeviceLocation() {
    logger.info("Accept modal 'Allo Peet's to access this device's location");
    MobileHelper.tapByCoordinatesOnElementCenter(allowPeetsToAccessDeviceLocationButton);
  }

  private void allowFindNearbyCoffeeBars() {
    if (MobileHelper.isElementDisplayed(allowFindNearbyCoffeeBarsButton, 5)) {
      logger.info("Allowing Location Services to help you find nearby Peet's Coffee");
      allowFindNearbyCoffeeBarsButton.click();
    }
  }

  @Override
  protected void openDeviceSettingsLocationOn() {
    logger.info("Opening device settings");
    openDeviceSettingsButton.click();
  }

  @Override
  protected void openDeviceSettingsLocationOff() {
    if (MobileHelper.isElementDisplayed(openDeviceSettingsButton, 5)) {
      logger.info("Opening device settings");
      openDeviceSettingsButton.click();
    }
  }

  private void toggleLocationSettings() {
    logger.info("Switching toggle location in the device settings");
    SdkHelper.getSyncHelper().wait(Until.uiElement(androidSwitchLocationToggle).present());
    androidSwitchLocationToggle.click();
    SdkHelper.getSyncHelper().sleep(5000);
    MobileHelper.tapAndroidDeviceBackButton();
    if (!MobileHelper.isElementDisplayed(getLocationServicesButton, 10)) {
      MobileHelper.tapAndroidDeviceBackButton();
    }
    MobileHelper.activateApp();
  }
}
