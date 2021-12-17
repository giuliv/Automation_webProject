package com.applause.auto.test.new_web.my_account;

import com.applause.auto.common.data.Constants.MyAccountLeftMenuOption;
import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.components.my_account.ShareLinkComponent;
import com.applause.auto.new_web.components.my_account.ShareViaEmailComponent;
import com.applause.auto.new_web.components.my_account.UnlockMyDiscountComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.my_account.MyAccountPage;
import com.applause.auto.new_web.views.my_account.ReferralsPage;
import com.applause.auto.test.new_web.BaseTest;
import org.testng.annotations.Test;

public class ReferralsTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.MY_ACCOUNT},
      description = "11102933")
  public void referralsElementsTest() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), TestData.USER_EMAIL_WITH_SUBSCRIPTIONS, TestData.WEB_PASSWORD);

    logger.info("4. Click on Referrals");
    ReferralsPage referralsPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.REFERRALS);

    logger.info(
        "Verify Referrals title, image, links, Your pin..., Times shared, Friends referrals, Possible rewards and Rewards earned text should display.\n"
            + "Facebook, email and link icons should display.");
    softAssert.assertEquals(
        referralsPage.getTitle().toLowerCase(),
        MyAccountTestData.REFERRALS_TITLE_HEADER.toLowerCase(),
        "Title isn't correct");
    softAssert.assertTrue(referralsPage.isShareButtonDisplayed(), "Share button isn't displayed");
    softAssert.assertTrue(referralsPage.isEmailButtonDisplayed(), "Email button isn't displayed");
    softAssert.assertTrue(referralsPage.isCopyButtonDisplayed(), "Copy button isn't displayed");

    softAssert.assertEquals(
        referralsPage.getListOfStats(),
        MyAccountTestData.REFERRALS_STATS,
        "Not all stats are displayed");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.MY_ACCOUNT},
      description = "11102934")
  public void throughFacebookTest() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), TestData.USER_EMAIL_WITH_SUBSCRIPTIONS, TestData.WEB_PASSWORD);

    logger.info("4. Click on Referrals");
    ReferralsPage referralsPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.REFERRALS);

    logger.info("5. Click on Facebook icon");
    referralsPage.clickOnShareButton();

    logger.info("Verify that Another window with Facebook should open.");
    softAssert.assertTrue(
        SdkHelper.getDriver().getCurrentUrl().contains("facebook"),
        "Facebook page didn't appear in the new tab");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.MY_ACCOUNT},
      description = "11102935")
  public void throughEmailTest() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), TestData.USER_EMAIL_WITH_SUBSCRIPTIONS, TestData.WEB_PASSWORD);

    logger.info("4. Click on Referrals");
    ReferralsPage referralsPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.REFERRALS);

    logger.info("5. Click on Email icon");
    ShareViaEmailComponent shareViaEmailComponent = referralsPage.clickOnEmailButton();

    logger.info("6. Enter data and Click on Send email");
    shareViaEmailComponent =
        shareViaEmailComponent.enterShareData(MyAccountTestData.SHARE_VIA_EMAIL_DTO);

    logger.info("Verify that email should be sent.");
    softAssert.assertFalse(
        shareViaEmailComponent.isErrorDisplayed(), "User can't send referral as error appear");

    logger.info("7. Close modal");
    referralsPage = shareViaEmailComponent.close();
    softAssert.assertTrue(referralsPage.isDisplayed(), "Modal wasn't closed");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.MY_ACCOUNT},
      description = "11102936")
  public void throughLinkTest() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), TestData.USER_EMAIL_WITH_SUBSCRIPTIONS, TestData.WEB_PASSWORD);

    logger.info("4. Click on Referrals");
    ReferralsPage referralsPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.REFERRALS);

    logger.info("5. Click on Copy icon");
    ShareLinkComponent shareLinkComponent = referralsPage.clickOnCopyButton();

    logger.info("6. Click on Copy link");
    String personalLink = shareLinkComponent.getPersonalLink();
    shareLinkComponent = shareLinkComponent.clickCopyLinkButton();

    logger.info("7. Close modal");
    referralsPage = shareLinkComponent.close();
    softAssert.assertTrue(referralsPage.isDisplayed(), "Modal wasn't closed");

    logger.info("8. Open copied link");
    UnlockMyDiscountComponent unlockMyDiscountComponent =
        WebHelper.navigateToUrl(personalLink, UnlockMyDiscountComponent.class);
    softAssert.assertTrue(
        unlockMyDiscountComponent.isDisplayed(), "Unlock My Discount is displayed");

    softAssert.assertAll();
  }
}
