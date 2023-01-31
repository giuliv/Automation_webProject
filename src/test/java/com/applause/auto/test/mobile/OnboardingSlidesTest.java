package com.applause.auto.test.mobile;

import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.dto.OnboardingDto;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.mobile.views.LandingView;
import com.applause.auto.mobile.views.OnboardingLastView;
import com.applause.auto.mobile.views.OnboardingView;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class OnboardingSlidesTest extends BaseTest {

  @Test(
      groups = {TestNGGroups.REGRESSION},
      description = "625878")
  public void onboardingSlidesTest() {
    SoftAssert softAssert = new SoftAssert();
    logger.info("Launch the app");
    OnboardingView onboardingView = openApp();

    String description;
    if (SdkHelper.getEnvironmentHelper().isAndroid()){
      description = "Welcome to the Peet’s app: the only place to tap into all things Peetnik Rewards.  It's home to all your rewards. Earn free food and drinks, get exclusive offers (aka the good stuff), and order ahead too!";

    }else{
      description = "Welcome to the Peet’s app: the only place to tap into all things Peetnik Rewards. It's home to all your rewards. Earn free food and drinks, get exclusive offers (aka the good stuff), and order ahead too!";
    }
    OnboardingDto firstOnboardingDto =
        OnboardingDto.builder()
            .position(1)
            .title("BEST DECISION YOU’VE MADE ALL DAY")
            .description(
                    description)
            .helpfulMessage("swipe left to continue")
            .isPageIndicatorDisplayed(true)
            .isSkipButtonDisplayed(true)
            .build();
    checkOnboardingSlider(onboardingView, firstOnboardingDto, softAssert);

    logger.info("Swipe left and verify second screen has correct title");
    onboardingView = onboardingView.swipeOnScreen(SwipeDirection.LEFT, OnboardingView.class);

    String title;
    if (SdkHelper.getEnvironmentHelper().isAndroid()){
      title = "WHAT’S IN IT FOR YOU?";

    }else{
      title = "WHAT'S IN IT FOR YOU?";
    }

    OnboardingDto secondOnboardingDto =
        OnboardingDto.builder()
            .position(2)
            .title(title)
            .description(
                "Get 1 point for every $1 you spend, including 125 after your first purchase. Turn points into free drinks, espresso shots, breakfast sandwiches, and more!")
            .isPageIndicatorDisplayed(true)
            .isSkipButtonDisplayed(true)
            .build();
    checkOnboardingSlider(onboardingView, secondOnboardingDto, softAssert);

    logger.info("Swipe left and verify third screen has correct title");
    onboardingView = onboardingView.swipeOnScreen(SwipeDirection.LEFT, OnboardingView.class);

    OnboardingDto thirdOnboardingDto =
        OnboardingDto.builder()
            .position(3)
            .title("NO PICKUP LINES NECESSARY")
            .description(
                "Why wait? Order ahead for pickup or delivery and hop to the front of the line.")
            .isPageIndicatorDisplayed(true)
            .isSkipButtonDisplayed(true)
            .build();
    checkOnboardingSlider(onboardingView, thirdOnboardingDto, softAssert);

    logger.info("Swipe left and verify last screen");
    OnboardingLastView onboardingLastView =
        onboardingView.swipeOnScreen(SwipeDirection.LEFT, OnboardingLastView.class);
    softAssert.assertTrue(
        onboardingLastView.isSignInButtonDisplayed(),
        "Sign In button isn't displayed on last slider");
    softAssert.assertTrue(
        onboardingLastView.isCreateAccountButtonDisplayed(),
        "Create Account button isn't displayed on last slider");
    if (SdkHelper.getEnvironmentHelper().isAndroid()){
      title = "LET'S MAKE IT OFFICIAL";

    }else{
      title = "LET’S MAKE IT OFFICIAL";
    }
    softAssert.assertEquals(
            onboardingLastView.getTitle(),
            title,
            "Landing view title isn't correct");

    // Todo: Ask if we can go back to onboarding on iOS [20.01.2023]
    if (MobileHelper.isAndroid()) {
      logger.info("Swipe right and verify third screen");
      onboardingView = onboardingLastView.swipeRightOnScreen();
      checkOnboardingSlider(onboardingView, thirdOnboardingDto, softAssert);

      logger.info("Swipe right and verify second screen has correct title");
      onboardingView = onboardingView.swipeOnScreen(SwipeDirection.RIGHT, OnboardingView.class);
      checkOnboardingSlider(onboardingView, secondOnboardingDto, softAssert);

      logger.info("Swipe right and verify first screen has correct title");
      onboardingView = onboardingView.swipeOnScreen(SwipeDirection.RIGHT, OnboardingView.class);
      checkOnboardingSlider(onboardingView, firstOnboardingDto, softAssert);

      logger.info("Tap skip from any screen or return to fourth onboarding screen");
      LandingView landingView = onboardingView.skipOnboarding();
      softAssert.assertTrue(
          landingView.isSignInButtonDisplayed(), "Sign In button isn't displayed");
      softAssert.assertTrue(
          landingView.isCreateAccountButtonDisplayed(), "Create Account button isn't displayed");
    }

    softAssert.assertAll();
  }

  private void checkOnboardingSlider(
      OnboardingView onboardingView, OnboardingDto onboardingDto, SoftAssert softAssert) {
    int sliderPosition = onboardingDto.getPosition();
    logger.info("Verify [{}] onboarding slide", sliderPosition);
    softAssert.assertEquals(
        onboardingView.getTitle(),
        onboardingDto.getTitle(),
        String.format("[%s] slider title value is not correct", sliderPosition));
    softAssert.assertEquals(
        onboardingView.getDescription(),
        onboardingDto.getDescription(),
        String.format("[%s] slider description value is not correct", sliderPosition));
    if (onboardingDto.getHelpfulMessage() != null && !onboardingDto.getHelpfulMessage().isEmpty()) {
      softAssert.assertEquals(
          onboardingView.getHelpfulMessage(),
          onboardingDto.getHelpfulMessage(),
          String.format("[%s] slider Helpful Message is not correct", sliderPosition));
    } else {
      softAssert.assertFalse(
          onboardingView.isHelpfulMessageDisplayed(),
          String.format("Helpful is displayed but should not on [%s] slider", sliderPosition));
    }
    if (onboardingDto.isPageIndicatorDisplayed()) {
      softAssert.assertTrue(
          onboardingView.isPageIndicatorDisplayed(),
          String.format("Page indicator isn't displayed on [%s] slider", sliderPosition));
    } else {
      softAssert.assertFalse(
          onboardingView.isPageIndicatorDisplayed(),
          String.format(
              "Page indicator is displayed but should not be on [%s] slider", sliderPosition));
    }
    if (onboardingDto.isSkipButtonDisplayed()) {
      softAssert.assertTrue(
          onboardingView.isSkipButtonDisplayed(),
          String.format("Skip button isn't displayed on [%s] slider", sliderPosition));
    } else {
      softAssert.assertFalse(
          onboardingView.isSkipButtonDisplayed(),
          String.format(
              "Skip button is displayed but should not be on [%s] slider", sliderPosition));
    }
  }
}
