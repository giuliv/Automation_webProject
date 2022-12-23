package com.applause.auto.test.mobile.helpers;

import com.applause.auto.common.data.dto.SignUpUserDto;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.views.CreateAccountView;
import com.applause.auto.mobile.views.HomeView;
import com.applause.auto.mobile.views.LandingView;
import com.applause.auto.mobile.views.OnboardingView;
import com.applause.auto.mobile.views.SignInView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.test.mobile.BaseTest;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScreenOrientation;

@Implementation(is = TestHelper.class, on = Platform.MOBILE)
public class TestHelper extends BaseComponent {
  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  public HomeView createNewAccountWithDefaults() {
    logger.info("Creating account");
    long uniq = System.currentTimeMillis();

    logger.info("Tap Create Account");
    CreateAccountView createAccountView = navigateToLandingView().createAccount();

    logger.info("Tap on First Name field and enter valid first name");
    String firstname = "Firstname";
    createAccountView.setFirstname(firstname);

    logger.info("Enter valid last name");
    String lastname = "Lastname";
    createAccountView.setLastname(lastname);

    logger.info("Scroll through and select birthday");
    String dobDay = "27";
    String dobMonth = "May";
    String dobYear = "2000";
    createAccountView.setDOB(dobDay, dobMonth, dobYear);

    logger.info("Enter valid email address");
    String email = String.format("a+%s@gmail.com", uniq);

    createAccountView.setEmailAddress(email);

    logger.info("Enter valid password");
    String password = "Password1";
    createAccountView.setPassword(password);

    logger.info("Tap on checkbox to agree to terms of service");
    createAccountView.checkPrivacyPolicyAndTermsAndConditions();

    logger.info("Tap Create Account button");
    return closeHomeViewTooltipIfDisplayed(createAccountView.tapOnCreateAccount());
  }

  public <T extends BaseComponent> T createNewAccount(
      CreateAccountView createAccountView, SignUpUserDto userDto, Class<T> expectingPage) {
    logger.info("Tap on First Name field and enter valid first name");
    createAccountView.setFirstname(userDto.getFirstName());

    logger.info("Enter valid last name");
    createAccountView.setLastname(userDto.getLastName());

    logger.info("Scroll through and select birthday");
    createAccountView.setDOB(userDto.getDobDay(), userDto.getDobMonth(), userDto.getDobYear());

    logger.info("Enter valid email address");
    String email = userDto.getEmail();
    createAccountView.setEmailAddress(email);

    logger.info("Enter valid password");
    createAccountView.setPassword(userDto.getPassword());

    if (userDto.isAgreePrivacyPolicyAndTermsAndConditions()) {
      logger.info("Tap on checkbox to agree to terms of service");
      createAccountView.checkPrivacyPolicyAndTermsAndConditions();
    }

    logger.info("Tap Create Account button");
    return createAccountView.createAccount(expectingPage);
  }

  public static HomeView openAppAndCreateNewAccountWithDefaults() {
    logger.info("Creating account");
    long uniq = System.currentTimeMillis();

    logger.info("Launch the app and arrive at the first onboarding screen view");
    OnboardingView onboardingView = BaseTest.openApp();

    logger.info("Skip Onboarding");
    LandingView landingView = onboardingView.skipOnboarding();

    logger.info("Tap Create Account");
    CreateAccountView createAccountView = landingView.createAccount();

    logger.info("Tap on First Name field and enter valid first name");
    String firstname = "Firstname";
    createAccountView.setFirstname(firstname);

    logger.info("Enter valid last name");
    String lastname = "Lastname";
    createAccountView.setLastname(lastname);

    logger.info("Scroll through and select birthday");
    String dobDay = "27";
    String dobMonth = "May";
    String dobYear = "2000";
    createAccountView.setDOB(dobDay, dobMonth, dobYear);

    logger.info("Enter valid email address");
    String email = String.format("a+%s@gmail.com", uniq);
    createAccountView.setEmailAddress(email);

    logger.info("Enter valid password");
    String password = "Password1";
    createAccountView.setPassword(password);

    logger.info("Tap on checkbox to agree to terms of service");
    createAccountView.checkPrivacyPolicyAndTermsAndConditions();

    logger.info("Tap Create Account button");
    return createAccountView
        .tapOnCreateAccount()
        .getReorderTooltipComponent()
        .closeReorderTooltipIfDisplayed(HomeView.class)
        .getCheckInTooltipComponent()
        .closeCheckInTooltipIfDisplayed(HomeView.class)
        .getPointsTurnIntoRewardsTooltipComponent()
        .closeTooltipIfDisplayed(HomeView.class);
  }

  /**
   * Open the app and login
   *
   * @param userName
   * @param password
   * @return HomeView
   */
  public static HomeView skipOnboardingAndLogin(String userName, String password) {
    return login(navigateToLandingView(), userName, password);
  }

  public static HomeView login(LandingView landingView, String userName, String password) {
    logger.info("Tap Sign In");
    SignInView signInView = landingView.signIn();

    logger.info("Log in to the app");
    signInView.setEmail(userName);
    signInView.setPassword(password);
    return signInView
        .signIn(HomeView.class)
        .getReorderTooltipComponent()
        .closeReorderTooltipIfDisplayed(HomeView.class)
        .getCheckInTooltipComponent()
        .closeCheckInTooltipIfDisplayed(HomeView.class)
        .getPointsTurnIntoRewardsTooltipComponent()
        .closeTooltipIfDisplayed(HomeView.class);
  }

  public static HomeView closeHomeViewTooltipIfDisplayed(HomeView homeView) {
    return homeView
        .getReorderTooltipComponent()
        .closeReorderTooltipIfDisplayed(HomeView.class)
        .getCheckInTooltipComponent()
        .closeCheckInTooltipIfDisplayed(HomeView.class)
        .getPointsTurnIntoRewardsTooltipComponent()
        .closeTooltipIfDisplayed(HomeView.class);
  }

  public static LandingView navigateToLandingView() {
    logger.info("Launch the app and arrive at the first onboarding screen view");
    OnboardingView onboardingView = BaseTest.openApp();

    logger.info("Skip Onboarding");
    return onboardingView.skipOnboarding();
  }

  public static SignInView openSignInView() {
    logger.info("Launch the app and arrive at the first onboarding screen view");
    OnboardingView onboardingView = BaseTest.openApp();

    logger.info("Skip Onboarding");
    LandingView landingView = onboardingView.skipOnboarding();

    logger.info("Tap Sign In");
    return landingView.signIn();
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
