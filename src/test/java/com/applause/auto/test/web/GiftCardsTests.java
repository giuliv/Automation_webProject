package com.applause.auto.test.web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.Constants.WebTestData;
import com.applause.auto.web.components.CheckYourCardBalanceModal;
import com.applause.auto.web.components.MiniCart;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.GiftCardsPage;
import com.applause.auto.web.views.ProductDetailsPage;
import com.applause.auto.web.views.SignInPage;
import com.applause.auto.web.views.my_account.MyAccountPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GiftCardsTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.GIFT_CARDS},
      description = "11102908",
      enabled = false)
  // Todo:UI seems to have changed
  public void sendCardByMailTest() {
    SoftAssert softAssert = new SoftAssert();

    logger.info("1. Navigate to GiftCard page");
    GiftCardsPage giftCardsPage = navigateToGiftCardsPage();
    Assert.assertNotNull(giftCardsPage, "Failed to navigate to the Gift card page.");

    logger.info("2. Select Send a card by mail");
    ProductDetailsPage detailsPage = giftCardsPage.clickSendCardByEmailCard();

    logger.info("Verify Peets card details display");
    Assert.assertNotNull(detailsPage, "Peet's cards details page isn't displayed");
    Assert.assertEquals(
        detailsPage.getProductName().equalsIgnoreCase(WebTestData.PEETS_CARDS_NAME),
        "Peets card details page is display with wrong name");

    logger.info("3. Select an amount");
    detailsPage.selectAmount(WebTestData.FIFTEEN_DOLLARS);
    int productQuantity = 2;
    int firstProduct = 1;

    logger.info("4. Select quantity");
    detailsPage.addMoreProducts(productQuantity);

    logger.info("5. Click on Add to cart");
    MiniCart miniCart = detailsPage.clickAddToMiniCart();

    logger.info("Verify Product added to minicart matching name and price.");
    softAssert.assertEquals(
        miniCart.getProductNameByIndex(firstProduct),
        WebTestData.PEETS_CARDS_NAME,
        "Product is displayed with wrong name");
    softAssert.assertTrue(
        miniCart.getPriceByIndex(firstProduct).contains(WebTestData.FIFTEEN_DOLLARS),
        "Product is displayed with wrong price");
    softAssert.assertEquals(
        miniCart.getProductQuantityByIndex(firstProduct),
        productQuantity,
        "Product is displayed with wrong quantity");
    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.GIFT_CARDS},
      description = "11102909")
  public void manageCardsTest() {

    logger.info("1. Navigate to GiftCard page");
    GiftCardsPage giftCardsPage = navigateToGiftCardsPage();
    Assert.assertNotNull(giftCardsPage, "Failed to navigate to the Gift card page.");

    logger.info("2. Select Manage cards");
    SignInPage signInPage = giftCardsPage.clickManageCardsButton();

    logger.info("Verify Login page is displayed");
    Assert.assertNotNull(signInPage, "Failed to navigate to the Login page.");

    logger.info("3. Login");
    signInPage.enterEmail(Constants.Mail.Mail3.getValue());
    signInPage.enterPassword(Constants.TestData.WEB_PASSWORD);
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("welcome"),
        "User is not signed in or welcome name is wrong");
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.GIFT_CARDS, TestNGGroups.SANITY},
      description = "11102910")
  public void checkCardBalanceTest() {

    logger.info("1. Navigate to GiftCard page");
    GiftCardsPage giftCardsPage = navigateToGiftCardsPage();
    Assert.assertNotNull(giftCardsPage, "Failed to navigate to the Gift card page.");

    logger.info("2. Select Check card balance");
    CheckYourCardBalanceModal balanceModal = giftCardsPage.clickCheckBalanceButton();

    logger.info("Verify Card balance modal is displayed");
    Assert.assertNotNull(balanceModal, "Card balance modal isn't displayed");

    logger.info("3. Enter Peet's Card Number");
    balanceModal.enterCardNumber(WebTestData.PEETS_CARD);

    logger.info("4. Enter Pin");
    balanceModal.enterPinNumber(WebTestData.PEETS_CARD_NIP);

    logger.info("5. Click on Check Balance");
    balanceModal.clickCheckBalance();

    logger.info("Verify Balance is displayed");
    Assert.assertTrue(balanceModal.isBalanceDisplayed(), "Balance isn't displayed");
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.GIFT_CARDS, TestNGGroups.SANITY},
      description = "11107529",
      enabled = false)
  public void giftCardsMainPageTest() {
    // Gift case, no longer needed
    logger.info("1. Navigate to GiftCard page");
    GiftCardsPage giftCardsPage = navigateToGiftCardsPage();
    Assert.assertNotNull(giftCardsPage, "Failed to navigate to the Gift card page.");

    logger.info("2. Validate header, body copy, and image and should be editable in the CMS");
    Assert.assertTrue(giftCardsPage.isPageHeaderDisplayed(), "Page header isn't displayed");
    Assert.assertTrue(
        giftCardsPage.isPageDescriptionDisplayed(), "Page description isn't displayed");
    Assert.assertTrue(giftCardsPage.isPageBannerImageDisplayed(), "Page banner isn't displayed");

    logger.info("3. Click Send an E-Gift Card Now Button");
    giftCardsPage.clickSendEGiftCardNowButton();

    logger.info(
        "4. Validate the user is directed to https://peets.cashstar.com/store/recipient?locale=en-us");
    Assert.assertEquals(
        WebHelper.getCurrentUrl(),
        "https://peets.cashstar.com/store/recipient?locale=en-us",
        "The page is opened with a wrong URL");

    logger.info("5. Validate two banners are shown");
    giftCardsPage = navigateToGiftCardsPage();
    Assert.assertTrue(
        giftCardsPage.isBannerDisplayed("Most Popular - $20 EGIFT CARD"),
        String.format("Banner '%s' isn't displayed", "Most Popular - $20 EGIFT CARD"));
    Assert.assertTrue(
        giftCardsPage.isBannerDisplayed("EASY AS 1,2,3"),
        String.format("Banner '%s' isn't displayed", "EASY AS 1,2,3"));

    logger.info("6. Click on any of the Banner");
    giftCardsPage.clickOnBanner("Most Popular - $20 EGIFT CARD");

    logger.info(
        "7. Validate the user is directed to https://peets.cashstar.com/store/recipient?locale=en-us");
    Assert.assertEquals(
        WebHelper.getCurrentUrl(),
        "https://peets.cashstar.com/store/recipient?locale=en-us",
        "The page is opened with a wrong URL");

    logger.info(
        "8. Register, Reload & More section. Validate Header, body copy, icon are displayed");
  }
}
