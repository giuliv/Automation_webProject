package com.applause.auto.test.mobile;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.mobile.helpers.SwipeDirectionCloseToMiddle;
import com.applause.auto.mobile.views.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardTest extends BaseTest {

  @Test(
      groups = {TestNGGroups.REGRESSION},
      description = "11128851")
  public void homePageUiElementsTest() {
    logger.info("STEP #1 - Launch the app and arrive at the first on boarding screen view");
    HomeView homeView =
        testHelper.skipOnboardingAndLogin(
            Constants.MyAccountTestData.EMAIL, Constants.MyAccountTestData.PASSWORD);

    logger.info("VERIFY welcome message is displayed in the home page");
    Assert.assertFalse(
        homeView.getWelcomeMessage().isEmpty(), "Welcome message should be displayed");

    logger.info("VERIFY user name is displayed in the home page");
    Assert.assertFalse(homeView.getUserName().isEmpty(), "User name should be displayed");

    logger.info("VERIFY rewards points image is displayed in the home page");
    Assert.assertTrue(
        homeView.isRewardsPointImageDisplayed(), "Rewards points image should be displayed");

    logger.info("STEP #2 - Open ready to use rewards store view");
    homeView.clickReadyToUseRewardsAndOffersTab();

    logger.info("VERIFY Ready to use rewards and offers section is displayed in the home page");
    Assert.assertTrue(
        homeView.isReadyToUseRewardsSectionDisplayed(),
        "Ready to use rewards section should be displayed");

    logger.info("STEP #3 - Open rewards store view");
    homeView.clickRewardsStoreTab();

    logger.info("VERIFY rewards store section id displayed");
    Assert.assertTrue(
        homeView.isRewardsStoreSectionDisplayed(), "Rewards store section should be displayed");

    logger.info("VERIFY QR code is displayed in the home page");
    Assert.assertTrue(homeView.isQrCodeDisplayed(), "QR code should be displayed");
  }

  @Test(
      groups = {TestNGGroups.REGRESSION},
      description = "11128852")
  public void bottomMenuFeaturesTest() {
    logger.info("STEP #1 - Launch the app and arrive at the first on boarding screen view");
    HomeView homeView =
        testHelper.skipOnboardingAndLogin(
            Constants.MyAccountTestData.EMAIL, Constants.MyAccountTestData.PASSWORD);

    logger.info("STEP #2 - Open menu view");
    if (SdkHelper.getEnvironmentHelper().isiOS()) {
      homeView.getReorderTooltipComponent().closeReorderTooltipIfDisplayed(HomeView.class);
    }
    OrderView orderView = homeView.getBottomNavigationMenuChunk().tapMenu(OrderView.class);

    logger.info("VERIFY Order view is opened");
    Assert.assertNotNull(orderView, "Order view should be displayed");

    logger.info("STEP #3 - Go back to home view");
    orderView.navigateBack(HomeView.class);
    homeView = homeView.getCheckInTooltipComponent().closeCheckInTooltipIfDisplayed(HomeView.class);

    logger.info("STEP #3 - Open rewards view");
    RewardsStoreView rewardsStoreView =
        homeView.getBottomNavigationMenuChunk().tapRewards(RewardsStoreView.class);

    logger.info("VERIFY Rewards view is opened");
    Assert.assertNotNull(rewardsStoreView, "Rewards view should be displayed");

    logger.info("STEP #4 - Open cart view");
    CartView cartView = homeView.getBottomNavigationMenuChunk().tapReorderButton(CartView.class);

    logger.info("VERIFY Cart view is opened");
    Assert.assertNotNull(cartView, "Cart view should be displayed");

    logger.info("STEP #5 - Go back to home page");
    cartView.navigateBack(OrderView.class);
    cartView.navigateBack(HomeView.class);

    logger.info("STEP #6 - Open scan view");
    rewardsStoreView =
        homeView.getBottomNavigationMenuChunk().tapScanButton(RewardsStoreView.class);

    logger.info("VERIFY Qr code is displayed");
    Assert.assertTrue(
        rewardsStoreView.getQrScanChunk().isQrCodeDisplayed(), "QR code should be displayed");

    logger.info("STEP #7 - Open more options view");
    MoreOptionsView moreOptionsView =
        homeView.getBottomNavigationMenuChunk().tapMoreButton(MoreOptionsView.class);

    logger.info("VERIFY more options view is opened");
    Assert.assertNotNull(moreOptionsView, "More options view should be displayed");
  }

  @Test(
      groups = {TestNGGroups.REGRESSION},
      description = "11128853")
  public void qrCodeUiElementsTest() {
    logger.info("STEP #1 - Launch the app and arrive at the first on boarding screen view");
    HomeView homeView =
        testHelper.skipOnboardingAndLogin(
            Constants.MyAccountTestData.EMAIL, Constants.MyAccountTestData.PASSWORD);

    logger.info("VERIFY QR code is displayed in the home page");
    Assert.assertTrue(homeView.isQrCodeDisplayed(), "QR code should be displayed");

    logger.info("STEP #2 - Click on QR code");
    homeView.clickQrCode();

    logger.info("VERIFY Tip my barista button is displayed in the home page");
    Assert.assertTrue(
        homeView.isTipMyBaristaButtonDisplayed(), "Tip my barista button should be displayed");

    logger.info("VERIFY Peet's card text is displayed in the home page");
    Assert.assertTrue(homeView.isPeetsCardTextDisplayed(), "Peet's card text should be displayed");

    logger.info("VERIFY Peet's card available currency is displayed in the home page");
    Assert.assertNotNull(
        homeView.getPeetsAvailableCurrency(), "Peet's card available currency should not be null");
  }

  @Test(
      groups = {TestNGGroups.REGRESSION},
      description = "11128854")
  public void readyToUseRewardsTest() {
    logger.info("STEP #1 - Launch the app and arrive at the first on boarding screen view");
    HomeView homeView =
        testHelper.skipOnboardingAndLogin(
            Constants.MyAccountTestData.EMAIL, Constants.MyAccountTestData.PASSWORD);

    logger.info("VERIFY How rewards work is displayed in the home page");
    Assert.assertTrue(homeView.isHowRewardsWorkDisplayed(), "How rewards work should be displayed");

    logger.info("STEP #2 - Click on How rewards work");
    PeetnikRewardsHelpView peetnikRewardsHelpView = homeView.clickHowRewardsWorkButton();

    logger.info("STEP #2 - Close How rewards work section and return to home view");
    peetnikRewardsHelpView.close(HomeView.class);

    logger.info("VERIFY rewards name is displayed in the home page");
    Assert.assertTrue(homeView.isRewardsNameDisplayed(), "Rewards name should be displayed");

    logger.info("VERIFY rewards circle name is displayed in the home page");
    Assert.assertTrue(
        homeView.isRewardsCircleNameDisplayed(), "Rewards circle name should be displayed");

    logger.info("VERIFY Expiration date is displayed in the home page");
    Assert.assertTrue(homeView.isExpirationDisplayed(), "Expiration date should be displayed");

    logger.info("VERIFY use rewards button is displayed in the home page");
    Assert.assertTrue(
        homeView.isUseRewardsButtonDisplayed(), "Use rewards button should be displayed");

    logger.info("VERIFY view details is displayed in the home page");
    Assert.assertTrue(homeView.isViewDetailsButtonDisplayed(), "View details should be displayed");

    logger.info("STEP #3 - Click on view details");
    homeView.clickViewDetails();

    logger.info("VERIFY view details description is displayed in the home page");
    Assert.assertNotNull(homeView.getDetailsDescription(), "View details should not be null");

    logger.info("STEP #3 - Click on hide details");
    /** TODO: Bug in Android, the Hide Details button is not displayed */
    homeView.clickHideDetails();

    /** TODO, in iOS the description is not locatable */
    logger.info("VERIFY view details description is displayed in the home page");
    if (SdkHelper.getEnvironmentHelper().isiOS()) {
      Assert.assertFalse(
          homeView.isHideDetailsButtonDisplayed(), "Hide details button should not be displayed");
    } else {
      Assert.assertFalse(
          homeView.isDetailsDescriptionDisplayed(),
          "View details description should not be displayed");
    }
  }

  @Test(
      groups = {TestNGGroups.REGRESSION},
      description = "11128855")
  public void rewardsStoreTest() {
    logger.info("STEP #1 - Launch the app and arrive at the first on boarding screen view");
    HomeView homeView =
        testHelper.skipOnboardingAndLogin(
            Constants.MyAccountTestData.EMAIL, Constants.MyAccountTestData.PASSWORD);

    logger.info("VERIFY How rewards work is displayed in the home page");
    Assert.assertTrue(homeView.isHowRewardsWorkDisplayed(), "How rewards work should be displayed");

    logger.info("STEP #2 - Click on Rewards store tab and scroll to bottom");
    homeView.clickRewardsStoreTab();
    MobileHelper.swipeRapidlyWithCount(SwipeDirectionCloseToMiddle.UP, 1);

    logger.info("VERIFY rewards name is displayed in the home page");
    Assert.assertTrue(homeView.isRewardsNameDisplayed(), "Rewards name should be displayed");

    logger.info("VERIFY rewards circle name is displayed in the home page");
    Assert.assertTrue(
        homeView.isRewardsCircleNameDisplayed(), "Rewards circle name should be displayed");

    logger.info("VERIFY use rewards button is displayed in the home page");
    Assert.assertTrue(
        homeView.isUseRewardsButtonDisplayed(), "Use rewards button should be displayed");

    logger.info("VERIFY view details button should be displayed");
    Assert.assertTrue(
        homeView.isViewDetailsButtonDisplayed(), "View details button should be displayed");

    logger.info("VERIFY point menu should be displayed");
    Assert.assertTrue(homeView.isPointMenuDisplayed(), "Point menu should be displayed");

    logger.info("STEP #3 - Select point option from point menu");
    String points = "400";
    homeView.clickPointMenuOption(points);

    logger.info("VERIFY reward name changed and it is displayed");
    Assert.assertTrue(
        homeView.getRewardName().contains(points),
        "New reward name containing: " + points + " points should be displayed ");
  }
}
