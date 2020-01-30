package com.applause.auto.test.mobile.helpers;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.components.AccountMenuMobileChunk;
import com.applause.auto.mobile.components.AllowLocationServicesPopupChunk;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.mobile.views.CreditCardDetailsView;
import com.applause.auto.mobile.views.DashboardView;
import com.applause.auto.mobile.views.FindACoffeeBarView;
import com.applause.auto.mobile.views.LandingView;
import com.applause.auto.mobile.views.NearbySelectCoffeeBarView;
import com.applause.auto.mobile.views.NewOrderView;
import com.applause.auto.mobile.views.PaymentMethodsView;
import com.applause.auto.mobile.views.SignInView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.helper.EnvironmentHelper;
import com.applause.auto.util.helper.SyncHelper;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.appmanagement.ApplicationState;
import org.aeonbits.owner.util.Collections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.lang.invoke.MethodHandles;
import java.util.List;

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
    signInView.setUsername(username);

    logger.info("Enter valid password");
    signInView.setPassword(password);

    logger.info("Tap Sign In button");
    return signInView.signIn(clazz);
  }

  public PaymentMethodsView deletePaymentMethodTestCardIfAdded(
      PaymentMethodsView paymentMethodsView) {
    if (paymentMethodsView.isPaymentMethodTestCardAdded()) {
      logger.info("Deleting previously added payment test card");
      CreditCardDetailsView creditCardDetailsView =
          paymentMethodsView.clickSavedPaymentMethod(CreditCardDetailsView.class);
      creditCardDetailsView.clickDeleteCard();
      creditCardDetailsView.clickDeleteYes();

      // need this workaround because payment card doesn't disappear from the view without
      // refreshing it
      SyncHelper.sleep(3000);
      paymentMethodsView.clickBackButton();
      ComponentFactory.create(AccountMenuMobileChunk.class).clickPaymentMethods();
      SyncHelper.waitUntil(condition -> !paymentMethodsView.isPaymentMethodTestCardAdded());
    } else {
      logger.info("There is no test payment card added");
    }
    return ComponentFactory.create(PaymentMethodsView.class);
  }

  public static void denyLocationServices() {
    if (EnvironmentHelper.isMobileAndroid(DriverManager.getDriver())) {
      // toggleLocationServicesCommand is opening Android settings view
      // AndroidMobileCommandHelper.toggleLocationServicesCommand();
      logger.info("Disabling Location permissions for Android");
      ((AndroidDriver<WebElement>) DriverManager.getDriver())
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
      SyncHelper.sleep(4000);

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
          ((AndroidDriver<WebElement>) DriverManager.getDriver()).queryAppState(appId);
      logger.info(String.format("App state is: %s", applicationState.name()));
      return ((AndroidDriver<WebElement>) DriverManager.getDriver()).queryAppState(appId);
    } catch (Exception e) {
      logger.error("Error getting current app state, probably it is not running");
      return ApplicationState.NOT_RUNNING;
    }
  }

  public static NewOrderView openOrderMenuForRecentCoffeeBar(DashboardView dashboardView) {
    if (EnvironmentHelper.isMobileIOS(DriverManager.getDriver())) {
      AllowLocationServicesPopupChunk allowLocationServicesPopupChunk =
          dashboardView.getBottomNavigationMenu().order(AllowLocationServicesPopupChunk.class);
      NearbySelectCoffeeBarView nearbySelectCoffeeBarView = allowLocationServicesPopupChunk.allow();
      FindACoffeeBarView findACoffeeBarView = nearbySelectCoffeeBarView.openRecentTab();
      return findACoffeeBarView.selectFirstRecentCoffeeBar();
    }
    return dashboardView.getBottomNavigationMenu().order(NewOrderView.class);
  }

  private static List<ApplicationState> notRunningAppStates() {
    return Collections.list(
        ApplicationState.NOT_RUNNING,
        ApplicationState.RUNNING_IN_BACKGROUND,
        ApplicationState.RUNNING_IN_BACKGROUND_SUSPENDED);
  }
}
