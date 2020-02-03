package com.applause.auto.test.mobile;

import com.applause.auto.common.data.Constants.MobileTestData;
import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.TestDataUtils;
import com.applause.auto.mobile.components.AccountMenuMobileChunk;
import com.applause.auto.mobile.components.PeetsCardsTransferAmountChunk;
import com.applause.auto.mobile.components.PeetsCardsTransferAmountWarningChunk;
import com.applause.auto.mobile.views.*;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import java.lang.invoke.MethodHandles;
import java.text.ParseException;

import com.applause.auto.util.helper.SyncHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.applause.auto.common.data.Constants.MobileTestData.*;

public class PeetsCardsTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.PEETS_CARDS},
      description = "1957258")
  public void addValueToNewDigitalCard25SavedCC() throws ParseException {
    logger.info("Launch the app and arrive at the first on boarding screen view");
    LandingView landingView = ComponentFactory.create(LandingView.class);

    DashboardView dashboardView =
        testHelper.signIn(
            landingView, MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD, DashboardView.class);
    Assert.assertNotNull(dashboardView, "Dashboard View does not displayed");

    logger.info("Tap on ... at top right of home screen");
    AccountMenuMobileChunk accountProfileMenu = dashboardView.getAccountProfileMenu();

    logger.info("Tap on Payment Methods field/row");
    PaymentMethodsView paymentMethodsView = accountProfileMenu.clickPaymentMethods();

    logger.info("Tap on a saved payment method");
    CreditCardDetailsView creditCardDetailsView =
            paymentMethodsView.clickSavedPaymentMethod(CreditCardDetailsView.class, CC_MASTER_NAME);

    logger.info("Tap on expiration date field and update expiration date");
    if (creditCardDetailsView.getExpDate().equals(CC_EXP_DATE)) {
      // If CC exp date is the valid one, modify it and then update it back to the valid one, to avoid transaction error
      creditCardDetailsView.enterExpDate(CC_MODIFIED_EXP_DATE);
      paymentMethodsView = creditCardDetailsView.saveCard();
      creditCardDetailsView = paymentMethodsView.clickSavedPaymentMethod(CreditCardDetailsView.class, CC_MASTER_NAME);
      creditCardDetailsView.enterExpDate(CC_EXP_DATE);
    } else {
      creditCardDetailsView.enterExpDate(CC_EXP_DATE);
    }

    logger.info("Tap Save Card button");
    paymentMethodsView = creditCardDetailsView.saveCard();

    logger.info("Tap back arrow to return to Payment Methods screen");
    paymentMethodsView.clickBackButton();

    logger.info("Tap X on more screen");
    accountProfileMenu.clickBackButton();

    logger.info("Tap Peet's Card icon on bottom nav bar");
    PeetsCardsView peetsCardsView = dashboardView.getBottomNavigationMenu().peetsCards();

    logger.info("User should be taken to peet's card screen");
    Assert.assertNotNull(peetsCardsView, "User does not taken to Peets Cards view");
    int oldBalance = TestDataUtils.BillingUtils.parseBalance(peetsCardsView.getBalance());

    logger.info("Tap Add Value button");
    peetsCardsView.addValue();

    logger.info("Tap pencil icon");
    paymentMethodsView = peetsCardsView.edit();

    logger.info("User should be taken to payment method screen");
    Assert.assertNotNull(paymentMethodsView, "User does not taken to payment method view");

    logger.info("Select a saved credit card");
    peetsCardsView = paymentMethodsView.clickSavedPaymentMethodAndSaveChanges(PeetsCardsView.class, CC_MASTER_NAME);

    logger.info("User should return to add value to my peet's card screen");
    Assert.assertNotNull(paymentMethodsView, "User does taken to Peets Cards screen");

    logger.info("$25 tile should be default highlighted");
    int cardAmount = 25;
    Assert.assertTrue(
        peetsCardsView.isAmountSelected("$" + cardAmount), "$25 tile does not highlighted");

    logger.info("Tap Confirm Value button");
    peetsCardsView = peetsCardsView.confirm();
    int newBalance = TestDataUtils.BillingUtils.parseBalance(peetsCardsView.getBalance());

    logger.info(
        "Make sure user is able to successfully add value to card and peet's card screen shows card balance of $25.00");
    Assert.assertEquals(newBalance - oldBalance, cardAmount, "Balance does not changed properly");
  }

  @Test(
      groups = {TestNGGroups.PEETS_CARDS},
      description = "1292900")
  public void reloadDigitalCard25SavedCC() throws ParseException {
    logger.info("Launch the app and arrive at the first on boarding screen view");
    LandingView landingView = ComponentFactory.create(LandingView.class);

    DashboardView dashboardView =
        testHelper.signIn(
            landingView, MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD, DashboardView.class);
    softAssert.assertNotNull(dashboardView, "Dashboard View does not displayed");

    logger.info("Tap Check In icon on bottom nav bar");
    CheckInView checkInView = dashboardView.getBottomNavigationMenu().checkIn();

    logger.info("User should be taken to check in screen");
    softAssert.assertNotNull(checkInView, "User does not taken to Check In view");
    int oldBalance = TestDataUtils.BillingUtils.parseBalance(checkInView.getBalance());

    logger.info("Tap Add Value button from Peet's Card Balance tile");
    checkInView.addValue();

    logger.info("Tap pencil icon to view Payment Method screen");
    PaymentMethodsView paymentMethodsView = checkInView.edit();

    logger.info("User should be taken to payment method screen");
    softAssert.assertNotNull(paymentMethodsView, "User does not taken to payment method view");

    logger.info("Select a saved credit card");
    checkInView = paymentMethodsView.clickSavedPaymentMethod2(CheckInView.class);

    logger.info("User should return to add value to my peet's card screen");
    softAssert.assertNotNull(paymentMethodsView, "User does taken to Peets Cards screen");

    logger.info("$25 tile should be default highlighted");
    int cardAmount = 25;
    softAssert.assertTrue(
        checkInView.isAmountSelected("$" + cardAmount), "$25 tile does not highlighted");

    logger.info("Tap Confirm Value button");
    checkInView = checkInView.confirm();
    int newBalance = TestDataUtils.BillingUtils.parseBalance(checkInView.getBalance());

    logger.info(
        "Make sure user is able to successfully add value to card and peet's card screen shows card balance of $25.00");
    softAssert.assertEquals(
        newBalance - oldBalance, cardAmount, "Balance does not changed properly");

    logger.info("Check account history");
    dashboardView = checkInView.getBottomNavigationMenu().home();
    AccountHistoryView accountHistory = dashboardView.getAccountProfileMenu().accountHistory();

    logger.info(
        "Make sure it shows Peet's Card transaction details:\n"
            + "\n"
            + "* Peet's Card Load + $25.00\n"
            + "\n"
            + "* Date [Month Day, Year]\n"
            + "\n");
    softAssert.assertEquals(
        accountHistory.getTransactionDate(0),
        TestDataUtils.BillingUtils.getFormatCurrentTransactionDate(),
        "Incorrect transaction date");
    softAssert.assertEquals(
        StringUtils.deleteWhitespace(accountHistory.getTransactionAmount(0)),
        TestDataUtils.BillingUtils.getFormatTransactionAmount(cardAmount),
        "Incorrect transaction amount");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.PEETS_CARDS},
      description = "1292907")
  public void transferBalanceFromPhysicalCardDigitalCardWithNoBalance() {
    logger.info("Launch the app and arrive at the first on boarding screen view");
    LandingView landingView = ComponentFactory.create(LandingView.class);

    DashboardView dashboardView =
        testHelper.signIn(
            landingView, MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD, DashboardView.class);
    softAssert.assertNotNull(dashboardView, "Dashboard View does not displayed");

    logger.info("Tap Peet's Card icon from bottom nav bar");
    PeetsCardsView peetsCardsView = dashboardView.getBottomNavigationMenu().peetsCards();

    logger.info("User should be taken to peet's card screen");
    softAssert.assertNotNull(peetsCardsView, "User does not taken to Peets card screen");

    logger.info("Tap Transfer Value button");
    PeetsCardsTransferAmountChunk peetsCardsTransferAmountChunk = peetsCardsView.transferValue();

    logger.info("User should be taken to transfer value overlay that appears from bottom");
    softAssert.assertNotNull(
        peetsCardsTransferAmountChunk,
        "User does not taken to transfer value overlay that appears from bottom");

    logger.info("Tap into Card Number field");
    logger.info("Enter invalid peet's card number");
    peetsCardsTransferAmountChunk.enterCardNumber(IVALID_PEETS_CC_NUM_1);
    peetsCardsTransferAmountChunk.enterCardPin(INVALID_PEETS_CC_PIN_1);

    logger.info("Tap Transfer Value button");
    PeetsCardsTransferAmountWarningChunk peetsCardsTransferAmountWarningChunk =
        peetsCardsTransferAmountChunk.transfer();

    logger.info(
        "Branded UI alert should display:\n"
            + "\n"
            + "Title: One last thing\n"
            + "\n"
            + "When you transfer a card into the app, you will:\n"
            + "\n"
            + "* Not be able to transfer the value back to the original card\n"
            + "\n"
            + "* No longer be able to add funds to your physical card\n"
            + "\n"
            + "* Be able to access the new value with your digital Peet's Card located in the app.\n"
            + "\n"
            + "[Button] Cancel [Button] Continue\n");
    softAssert.assertNotNull(
        peetsCardsTransferAmountWarningChunk, "Branded UI alert does not display");
    softAssert.assertTrue(
        peetsCardsTransferAmountWarningChunk
            .getFormattedMessage()
            .contains(MobileTestData.TRANSFER_ERROR),
        "Wrong warning message displayed");
    softAssert.assertTrue(
        peetsCardsTransferAmountWarningChunk.isContinueButtonDisplayed(),
        "Continue button does not displayed");
    softAssert.assertTrue(
        peetsCardsTransferAmountWarningChunk.isCancelButtonDisplayed(),
        "Cancel button does not displayed");

    logger.info("Tap Continue button");
    peetsCardsTransferAmountWarningChunk.tapContinue();

    logger.info(
        "User should see branded UI error alert:\n"
            + "\n"
            + "Title: We couldn't process your transfer.\n"
            + "\n"
            + "* Please check your card number and pin code and try again\n"
            + "\n"
            + "* If there's no value remaining on the card you are trying to transfer, you won't be able to transfer value\n"
            + "\n"
            + "* If this issue persists, please contact Peet's customer service at cs@peets.com <mailto:cs@peets.com>\n"
            + "\n"
            + "[Button] Cancel [Button] Try Again\n");
    softAssert.assertTrue(
        peetsCardsTransferAmountWarningChunk
            .getFormattedMessageCouldNotProcess()
            .contains(MobileTestData.TRANSFER_PROCESS_ERROR),
        "Wrong process message displayed");
    softAssert.assertTrue(
        peetsCardsTransferAmountWarningChunk.isTryAgainButtonCouldNotProcessDisplayed(),
        "Try again button does not displayed");
    softAssert.assertTrue(
        peetsCardsTransferAmountWarningChunk.isCancelButtonCouldNotProcessDisplayed(),
        "Cancel button does not displayed");

    logger.info("Tap Try Again button");
    peetsCardsTransferAmountChunk =
        peetsCardsTransferAmountWarningChunk.tapTryAgain(PeetsCardsTransferAmountChunk.class);

    logger.info(
        "User should be directed to transfer value screen with card number and pin number fields still filled in");
    softAssert.assertNotNull(
        peetsCardsTransferAmountChunk, "User does not navigated to Transfer value screen");
    softAssert.assertEquals(
        peetsCardsTransferAmountChunk.getCardNumber(),
        IVALID_PEETS_CC_NUM_1,
        "Card number field not filled in");
    softAssert.assertEquals(
        peetsCardsTransferAmountChunk.getPinNumber(),
        INVALID_PEETS_CC_PIN_1,
        "Pin number field not filled in");

    logger.info("Enter valid peet's card number\n" + "\n" + "Tap done on numeric keypad\n");
    peetsCardsTransferAmountChunk.enterCardNumber(VALID_PEETS_CC_NUM_1);

    logger.info("Tap Transfer Value button");
    peetsCardsTransferAmountWarningChunk = peetsCardsTransferAmountChunk.transfer();

    logger.info("Tap Continue button");
    peetsCardsTransferAmountWarningChunk.tapContinue();

    // TODO Cannot be completed without new card used every run
    logger.info(
        "User should see branded UI loading alert:\n"
            + "\n"
            + "Title: Transferring the value to your digital Peet's card...\n"
            + "\n"
            + "* Digital peet's card image\n"
            + "\n"
            + "* Loading animation\n"
            + "\n"
            + "* Gold check mark with text \"Done!\" should appear after successful value transfer\n"
            + "\n");

    logger.info("Tap Done!");

    logger.info(
        "User should see main Peet's card screen with card balance reflecting amount that was transferred");

    logger.info("Check account history");

    logger.info(
        "Make sure it shows Peet's Card transaction details:\n"
            + "\n"
            + "* Check No: web\n"
            + "\n"
            + "* Transaction Type: Transfer Balances to Card\n"
            + "\n"
            + "* Activity: Added xx.00\n"
            + "\n"
            + "* Balance: xx.xx\n"
            + "\n");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.PEETS_CARDS},
      description = "1959019")
  public void negativeTestTransferBalance() {
    logger.info("Launch the app and arrive at the first on boarding screen view");
    LandingView landingView = ComponentFactory.create(LandingView.class);

    DashboardView dashboardView =
        testHelper.signIn(
            landingView, MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD, DashboardView.class);
    softAssert.assertNotNull(dashboardView, "Dashboard View does not displayed");

    logger.info("Tap Peet's Card icon from bottom nav bar");
    PeetsCardsView peetsCardsView = dashboardView.getBottomNavigationMenu().peetsCards();

    logger.info("User should be taken to peet's card screen");
    softAssert.assertNotNull(peetsCardsView, "User does not taken to Peets card screen");

    logger.info("Tap Transfer Value button");
    PeetsCardsTransferAmountChunk peetsCardsTransferAmountChunk = peetsCardsView.transferValue();

    logger.info("User should be taken to transfer value overlay that appears from bottom");
    softAssert.assertNotNull(
        peetsCardsTransferAmountChunk,
        "User does not taken to transfer value overlay that appears from bottom");

    logger.info("Tap into Card Number field");
    logger.info("Enter valid peet's card number\n" + "\n" + "Tap done on numeric keypad\n");
    peetsCardsTransferAmountChunk.enterCardNumber(VALID_PEETS_CC_NUM_2);

    logger.info("Enter invalid peet's card pin number");
    peetsCardsTransferAmountChunk.enterCardPin(INVALID_PEETS_CC_PIN_2);

    PeetsCardsTransferAmountWarningChunk peetsCardsTransferAmountWarningChunk =
        peetsCardsTransferAmountChunk.transfer();

    logger.info(
        "Branded UI alert should display:\n"
            + "\n"
            + "Title: One last thing\n"
            + "\n"
            + "When you transfer a card into the app, you will:\n"
            + "\n"
            + "* Not be able to transfer the value back to the original card\n"
            + "\n"
            + "* No longer be able to add funds to your physical card\n"
            + "\n"
            + "* Be able to access the new value with your digital Peet's Card located in the app.\n"
            + "\n"
            + "[Button] Cancel [Button] Continue\n");
    softAssert.assertNotNull(
        peetsCardsTransferAmountWarningChunk, "Branded UI alert does not display");
    softAssert.assertEquals(
        peetsCardsTransferAmountWarningChunk.getFormattedMessage(),
        peetsCardsTransferAmountWarningChunk.getValidMessage(),
        "Wrong message displayed");
    softAssert.assertTrue(
        peetsCardsTransferAmountWarningChunk.isContinueButtonDisplayed(),
        "Continue button does not displayed");
    softAssert.assertTrue(
        peetsCardsTransferAmountWarningChunk.isCancelButtonDisplayed(),
        "Cancel button does not displayed");

    logger.info("Tap Continue button");
    peetsCardsTransferAmountWarningChunk.tapContinue();

    logger.info(
        "User should see branded UI error alert:\n"
            + "\n"
            + "Title: We couldn't process your transfer\n"
            + "\n"
            + "* Please check your card number and pin code and try again\n"
            + "\n"
            + "* If there's no value remaining on the card you are trying to transfer, you won't be able to transfer value\n"
            + "\n"
            + "* If this issue persists, please contact Peet's customer service at cs@peets.com <mailto:cs@peets.com>\n"
            + "\n"
            + "[Button] Cancel [Button] Try Again\n");
    softAssert.assertTrue(
        peetsCardsTransferAmountWarningChunk
            .getFormattedMessageCouldNotProcess()
            .contains(MobileTestData.TRANSFER_PROCESS_ERROR),
        "Wrong message displayed");
    softAssert.assertTrue(
        peetsCardsTransferAmountWarningChunk.isTryAgainButtonCouldNotProcessDisplayed(),
        "Try again button does not displayed");
    softAssert.assertTrue(
        peetsCardsTransferAmountWarningChunk.isCancelButtonCouldNotProcessDisplayed(),
        "Cancel button does not displayed");

    logger.info("Tap Try Again button");
    peetsCardsTransferAmountChunk =
        peetsCardsTransferAmountWarningChunk.tapTryAgain(PeetsCardsTransferAmountChunk.class);

    logger.info("Enter Card number of already added card with invalid PIN");
    peetsCardsTransferAmountChunk.enterCardNumber(VALID_PEETS_CC_NUM_1);

    peetsCardsTransferAmountWarningChunk = peetsCardsTransferAmountChunk.transfer();

    logger.info(
        "Branded UI alert should display:\n"
            + "\n"
            + "Title: One last thing\n"
            + "\n"
            + "When you transfer a card into the app, you will:\n"
            + "\n"
            + "* Not be able to transfer the value back to the original card\n"
            + "\n"
            + "* No longer be able to add funds to your physical card\n"
            + "\n"
            + "* Be able to access the new value with your digital Peet's Card located in the app.\n"
            + "\n"
            + "[Button] Cancel [Button] Continue\n");
    softAssert.assertNotNull(
        peetsCardsTransferAmountWarningChunk, "Branded UI alert does not display");
    softAssert.assertEquals(
        peetsCardsTransferAmountWarningChunk.getFormattedMessage(),
        peetsCardsTransferAmountWarningChunk.getValidMessage(),
        "Wrong message displayed");
    softAssert.assertTrue(
        peetsCardsTransferAmountWarningChunk.isContinueButtonDisplayed(),
        "Continue button does not displayed");
    softAssert.assertTrue(
        peetsCardsTransferAmountWarningChunk.isCancelButtonDisplayed(),
        "Cancel button does not displayed");

    logger.info("Tap Continue button");
    peetsCardsTransferAmountWarningChunk.tapContinue();

    logger.info(
        "User should see branded UI error alert:\n"
            + "\n"
            + "Title: We couldn't process your transfer\n"
            + "\n"
            + "* Please check your card number and pin code and try again\n"
            + "\n"
            + "* If there's no value remaining on the card you are trying to transfer, you won't be able to transfer value\n"
            + "\n"
            + "* If this issue persists, please contact Peet's customer service at cs@peets.com <mailto:cs@peets.com>\n"
            + "\n"
            + "[Button] Cancel [Button] Try Again\n");
    softAssert.assertTrue(
        peetsCardsTransferAmountWarningChunk
            .getFormattedMessageCouldNotProcess()
            .contains(MobileTestData.TRANSFER_PROCESS_ERROR),
        "Wrong message displayed");
    softAssert.assertTrue(
        peetsCardsTransferAmountWarningChunk.isTryAgainButtonCouldNotProcessDisplayed(),
        "Try again button does not displayed");
    softAssert.assertTrue(
        peetsCardsTransferAmountWarningChunk.isCancelButtonCouldNotProcessDisplayed(),
        "Cancel button does not displayed");

    softAssert.assertAll();
  }
}
