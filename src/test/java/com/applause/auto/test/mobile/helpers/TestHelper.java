package com.applause.auto.test.mobile.helpers;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.TestDataUtils;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.components.AllowLocationServicesPopupChunk;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.mobile.views.CheckoutView;
import com.applause.auto.mobile.views.CreateAccountView;
import com.applause.auto.mobile.views.CreditCardDetailsView;
import com.applause.auto.mobile.views.DashboardView;
import com.applause.auto.mobile.views.FindACoffeeBarView;
import com.applause.auto.mobile.views.LandingView;
import com.applause.auto.mobile.views.MoreOptionsView;
import com.applause.auto.mobile.views.NearbySelectCoffeeBarView;
import com.applause.auto.mobile.views.NewOrderView;
import com.applause.auto.mobile.views.PaymentMethodsView;
import com.applause.auto.mobile.views.SignInView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.appmanagement.ApplicationState;
import java.lang.invoke.MethodHandles;
import java.util.List;
import org.aeonbits.owner.util.Collections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

@Implementation(is = TestHelper.class, on = Platform.MOBILE)
public class TestHelper extends BaseComponent {
  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  /**
   * Sign in dashboard view.
   *
   * @param landingView the landing view
   * @return the dashboard view
   */
  public DashboardView signIn(LandingView landingView) {
    return signIn(
        landingView, MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD, DashboardView.class);
  }

  /**
   * Sign in t.
   *
   * @param <T> the type parameter
   * @param landingView the landing view
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T signIn(
      LandingView landingView, String username, String password, Class<T> clazz) {
    landingView.skipOnboarding();
    logger.info("Tap Sign In");
    SignInView signInView = landingView.signIn();

    logger.info("Tap on Email Address field and enter valid email address");
    signInView.setEmail(username);

    logger.info("Enter valid password");
    signInView.setPassword(password);

    logger.info("Tap Sign In button");
    return signInView.signIn(clazz);
  }

  public PaymentMethodsView deletePaymentMethodTestCardIfAdded(
      PaymentMethodsView paymentMethodsView, String methodName) {
    if (paymentMethodsView.isPaymentMethodTestCardAdded(methodName)) {
      logger.info("Deleting previously added payment test card");
      CreditCardDetailsView creditCardDetailsView =
          paymentMethodsView.clickSavedPaymentMethod(CreditCardDetailsView.class, methodName);
      creditCardDetailsView.clickDeleteCard();
      creditCardDetailsView.clickDeleteYes();

      // need this workaround because payment card doesn't disappear from the view without
      // refreshing it
      SdkHelper.getSyncHelper().sleep(15000);
      paymentMethodsView.clickBackButton();
      SdkHelper.getSyncHelper().sleep(5000);
      SdkHelper.create(MoreOptionsView.class).clickPaymentMethods();
      SdkHelper.getSyncHelper()
          .waitUntil(condition -> !paymentMethodsView.isPaymentMethodTestCardAdded(methodName));
    } else {
      logger.info("There is no test payment card added");
    }
    return SdkHelper.create(PaymentMethodsView.class);
  }

  public DashboardView createNewAccountWithDefaults(LandingView landingView) {
    logger.info("Creating account");
    long uniq = System.currentTimeMillis();

    landingView.skipOnboarding();

    logger.info("Tap Create Account");
    CreateAccountView createAccountView = landingView.createAccount();

    logger.info("Tap on First Name field and enter valid first name");
    String firstname = "Firstname";
    createAccountView.setFirstname(firstname);

    logger.info("Enter valid last name");
    String lastname = "Lastname";
    createAccountView.setLastname(lastname);

    logger.info("Enter valid zip code / Skip this field");
    String zipCode = "11214";
    createAccountView.setZipCode(zipCode);

    logger.info("Scroll through and select birthday");
    String dobDay = "27";
    String dobMonth = "May";
    String dobYear = "2000";
    createAccountView.setDOB(dobDay, dobMonth, dobYear);

    logger.info("Enter valid ten digit phone number / Skip this field");
    String phone = TestDataUtils.PhoneNumberDataUtils.getRandomPhoneNumber();
    createAccountView.setPhoneNumber(phone);

    logger.info("Enter valid email address");
    String email = String.format("a+%s@gmail.com", uniq);

    createAccountView.setEmailAddress(email);

    logger.info("Enter confirm email address");
    createAccountView.setConfirmEmailAddress(email);

    logger.info("Enter valid password");
    String password = "Password1";
    createAccountView.setPassword(password);

    logger.info("Enter confirm password");
    createAccountView.setConfirmationPassword(password);

    logger.info("Tap on checkbox to agree to terms of service");
    createAccountView.checkPrivacyPolicyAndTermsAndConditions();

    logger.info("Tap Create Account button");
    return createAccountView.createAccount();
  }

  public static void denyLocationServices() {
    if (SdkHelper.getEnvironmentHelper().isMobileAndroid()) {
      // toggleLocationServicesCommand is opening Android settings view
      // AndroidMobileCommandHelper.toggleLocationServicesCommand();
      logger.info("Disabling Location permissions for Android");
      ((AndroidDriver<WebElement>) SdkHelper.getDriver())
          .executeScript(
              "mobile:changePermissions",
              ImmutableMap.of(
                  "action",
                  "revoke",
                  "appPackage",
                  Constants.MobileApp.ANDROID_PACKAGE_ID,
                  "permissions",
                  Collections.list(
                      "android.permission.ACCESS_COARSE_LOCATION",
                      "android.permission.ACCESS_FINE_LOCATION")));
      SdkHelper.getSyncHelper().sleep(4000);

      if (notRunningAppStates().contains(getAppState(Constants.MobileApp.ANDROID_PACKAGE_ID))) {
        try {
          MobileHelper.activateApp();
        } catch (Exception e) {
          logger.error("Error activating app");
        }
      }
    }
  }

  public static ApplicationState getAppState(String appId) {
    logger.info("Getting app state");
    try {
      ApplicationState applicationState =
          ((AndroidDriver<WebElement>) SdkHelper.getDriver()).queryAppState(appId);
      logger.info(String.format("App state is: %s", applicationState.name()));
      return ((AndroidDriver<WebElement>) SdkHelper.getDriver()).queryAppState(appId);
    } catch (Exception e) {
      logger.error("Error getting current app state, probably it is not running");
      return ApplicationState.NOT_RUNNING;
    }
  }

  public static NewOrderView openOrderMenuForRecentCoffeeBar(DashboardView dashboardView) {
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      AllowLocationServicesPopupChunk allowLocationServicesPopupChunk =
          dashboardView.getBottomNavigationMenu().order(AllowLocationServicesPopupChunk.class);
      NearbySelectCoffeeBarView nearbySelectCoffeeBarView = allowLocationServicesPopupChunk.allow();
      FindACoffeeBarView findACoffeeBarView = nearbySelectCoffeeBarView.openRecentTab();
      return findACoffeeBarView.selectFirstRecentCoffeeBar();
    }
    return dashboardView.getBottomNavigationMenu().order(NewOrderView.class);
  }

  public static CheckoutView checkoutOnItemsYouMightLike(NewOrderView newOrderView) {
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      return SdkHelper.create(CheckoutView.class);
    } else {
      return newOrderView.checkout();
    }
  }

  private static List<ApplicationState> notRunningAppStates() {
    return Collections.list(
        ApplicationState.NOT_RUNNING,
        ApplicationState.RUNNING_IN_BACKGROUND,
        ApplicationState.RUNNING_IN_BACKGROUND_SUSPENDED);
  }

  public void setupChrome() {
    logger.info("Submitting search form");
    if (SdkHelper.getEnvironmentHelper().isMobileAndroid()) {
      // if Samsung browser installed

      AndroidDriver androidDriver = ((AndroidDriver) SdkHelper.getDriver());
      String currentPackage = androidDriver.getCurrentPackage();
      try {
        androidDriver.activateApp("com.sec.android.app.sbrowser");
        logger.info("Orientation: " + androidDriver.getOrientation());
        logger.info("Orientation: Forcing to PORTRAIT");
        androidDriver.rotate(ScreenOrientation.PORTRAIT);
        SdkHelper.getSyncHelper().sleep(5000);
        logger.info("Orientation: " + androidDriver.getOrientation());
        androidDriver.startActivity(
            new Activity("com.android.settings", ".Settings")
                .setAppWaitPackage("com.android.settings")
                .setAppWaitActivity(".Settings")
                .setStopApp(false));
      } catch (Throwable th) {
        logger.info("No  Samsung browser found");
      }
      selectMenuItem("Apps");

      selectMenuItem("Chrome");

      selectMenuItem("Browser app");

      selectMenuItem("Chrome");
      androidDriver.activateApp(currentPackage);
      logger.info("Orientation: " + androidDriver.getOrientation());
      logger.info("Orientation: Forcing to PORTRAIT");
      androidDriver.rotate(ScreenOrientation.PORTRAIT);
      SdkHelper.getSyncHelper().sleep(5000);
      logger.info("Orientation: " + androidDriver.getOrientation());
      logger.info("Chrome default successfully configured");
    } else {
    }
  }

  private static void selectMenuItem(String key) {
    AndroidDriver androidDriver = ((AndroidDriver) SdkHelper.getDriver());
    int i = 5;
    while (i-- > 0) {
      try {
        androidDriver
            .findElement(
                MobileBy.xpath(
                    String.format(
                        "//*[contains(@resource-id,'content_layout') or contains(@resource-id,'list_container')]//android.widget.TextView[@text='%s']",
                        key)))
            .click();
        SdkHelper.getSyncHelper().sleep(2000);
        break;
      } catch (NoSuchElementException nse) {
        SdkHelper.getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.UP);
      }
    }
  }
}
