package com.applause.auto.test.mobile;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.MobileTestData;
import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.mobile.components.PeetsCardsTransferAmountChunk;
import com.applause.auto.mobile.components.PeetsCardsTransferAmountWarningChunk;
import com.applause.auto.mobile.views.HomeView;
import com.applause.auto.mobile.views.PeetsCardsView;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PeetsCardsTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.PEETS_CARDS, TestNGGroups.REGRESSION},
      description = "1959019",
      enabled = false)
  // Todo:Needs cards, already requested
  public void negativeTestTransferBalance() {
    SoftAssert softAssert = new SoftAssert();
    HomeView homeView =
        testHelper.skipOnboardingAndLogin(MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD);
    softAssert.assertNotNull(homeView, "Home view is not displayed");

    logger.info("Tap Peet's Card icon from bottom nav bar");
    PeetsCardsView peetsCardsView = homeView.getBottomNavigationMenuChunk().peetsCards();

    logger.info("User should be taken to peet's card screen");
    softAssert.assertNotNull(peetsCardsView, "User does not taken to Peets card screen");

    logger.info("Tap Transfer Value button");
    PeetsCardsTransferAmountChunk peetsCardsTransferAmountChunk = peetsCardsView.transferValue();

    logger.info("User should be taken to transfer value overlay that appears from bottom");
    softAssert.assertNotNull(
        peetsCardsTransferAmountChunk,
        "User does not taken to transfer value overlay that appears from bottom");

    logger.info("Tap into Card Number field");
    logger.info("Enter invalid Peet's Card number" + "Tap next on numeric keypad");
    peetsCardsTransferAmountChunk.enterCardNumber(Constants.MobileTestData.INVALID_PEETS_CC_NUM_1);

    logger.info("Enter valid Peet's Card PIN number" + "Tap Done on numeric keypad");
    peetsCardsTransferAmountChunk.enterCardPin(Constants.MobileTestData.VALID_PEETS_CC_NUM_1);

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

    logger.info("Enter valid Peet's Card number" + "Tap next on numeric keypad");
    peetsCardsTransferAmountChunk.enterCardNumber(Constants.MobileTestData.VALID_PEETS_CC_NUM_1);

    logger.info("Enter invalid Peet's Card PIN number" + "Tap Done on numeric keypad");
    peetsCardsTransferAmountChunk.enterCardPin(Constants.MobileTestData.INVALID_PEETS_CC_PIN_1);

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
