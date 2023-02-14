package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.components.BottomNavigationMenuChunk;
import com.applause.auto.mobile.components.tooltips.CheckInTooltipComponent;
import com.applause.auto.mobile.components.tooltips.PointsTurnIntoRewardsTooltipComponent;
import com.applause.auto.mobile.components.tooltips.ReorderTooltipComponent;
import com.applause.auto.mobile.components.tooltips.SwipeTooltipComponent;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.mobile.helpers.SwipeDirectionCloseToMiddle;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.qameta.allure.Step;
import java.time.Duration;
import lombok.Getter;

@Implementation(is = HomeView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = HomeView.class, on = Platform.MOBILE_IOS)
public class HomeView extends BaseComponent {

  @Getter @Locate ReorderTooltipComponent reorderTooltipComponent;
  @Getter @Locate CheckInTooltipComponent checkInTooltipComponent;
  @Getter @Locate PointsTurnIntoRewardsTooltipComponent pointsTurnIntoRewardsTooltipComponent;
  @Getter @Locate SwipeTooltipComponent swipeTooltipComponent;
  @Getter @Locate BottomNavigationMenuChunk bottomNavigationMenuChunk;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == 'More'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/moreButton\")",
      on = Platform.MOBILE_ANDROID)
  protected Button moreButton;

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@value = 'Swipe left or tap the icon to scan your QR code in-store.']|//XCUIElementTypeButton[@label = 'QR Code']",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/dashboard_motion_layout\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox signature;

  @Locate(accessibilityId = "More", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/moreButton", on = Platform.MOBILE_ANDROID)
  protected Button getMoreScreenButton;

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"GOOD MORNING\"]|//XCUIElementTypeStaticText[@name=\"GOOD AFTERNOON\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/goodAfternoonLbl\")",
      on = Platform.MOBILE_ANDROID)
  protected Button welcomeMessageText;

  @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"jyothiupdate\"]", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/nameText\")",
      on = Platform.MOBILE_ANDROID)
  protected Button userNameText;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == 'More'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/reward_image\")",
      on = Platform.MOBILE_ANDROID)
  protected ContainerElement rewardsPointImage;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"How Rewards Work\"]/parent::*/parent::*",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator =
          "new UiSelector().resourceIdMatches(\".*id/promotions_offers_recycler\")",
      on = Platform.MOBILE_ANDROID)
  protected ContainerElement readyToUseRewardsSection;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"Ready to Use Rewards & Offers\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.LinearLayout[@content-desc=\"Ready to Use\n"
              + "Rewards & Offers\"]/android.widget.TextView",
      on = Platform.MOBILE_ANDROID)
  protected Text readyToUseRewardsAndOffersTab;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Rewards Store\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.LinearLayout[@content-desc=\"Rewards Store\"]",
      on = Platform.MOBILE_ANDROID)
  protected Button rewardsStoreTab;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == 'More'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator =
          "new UiSelector().resourceIdMatches(\".*id/reward_store_tooltip_button\")",
      on = Platform.MOBILE_ANDROID)
  protected Button gotItButton;

  @Locate(
      xpath = "//XCUIElementTypeButton[@name=\"Use 25 Points\"]/parent::*",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/rewards_and_details_pager\")",
      on = Platform.MOBILE_ANDROID)
  protected ContainerElement rewardsStoreSection;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"QR Code\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/qrCodeIcon\")",
      on = Platform.MOBILE_ANDROID)
  protected ContainerElement qrCodeButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Tip My Barista\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/tipButton", on = Platform.MOBILE_ANDROID)
  protected Button tipMyBaristaButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Peet’s Card\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/myPeetsCardLabel",
      on = Platform.MOBILE_ANDROID)
  protected ContainerElement peetsCardText;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"How Rewards Work\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/reward_detail_link\")",
      on = Platform.MOBILE_ANDROID)
  protected Button howRewardsWorkButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Reward\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/reward_type_text\")",
      on = Platform.MOBILE_ANDROID)
  protected Button rewardsTypeText;

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[contains(@name, \"Point Reward\")]|//XCUIElementTypeButton[@name=\"Use Reward\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/reward_title\")",
      on = Platform.MOBILE_ANDROID)
  protected Button rewardNameText;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[contains(@name,\"Expires\")]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/expiration_text\")",
      on = Platform.MOBILE_ANDROID)
  protected Button expirationText;

  @Locate(xpath = "//XCUIElementTypeButton[contains(@name, \"Use\")]", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/redeem_button\")",
      on = Platform.MOBILE_ANDROID)
  protected Button useRewardsButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"View Details\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/reward_view_details_text\")",
      on = Platform.MOBILE_ANDROID)
  protected Button viewDetailsButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Hide Details\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/reward_view_details_text\")",
      on = Platform.MOBILE_ANDROID)
  protected Button hideDetailsButton;

  @Locate(
      xpath =
          "//XCUIElementTypeButton[@name=\"Use Reward\"]/following-sibling::XCUIElementTypeStaticText[2]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/reward_description_text\")",
      on = Platform.MOBILE_ANDROID)
  protected Text detailsDescriptionText;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"Peet’s Card\"]/parent::*/XCUIElementTypeOther",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/dollarSign", on = Platform.MOBILE_ANDROID)
  protected Text peetsCardAvailableCurrencyText;

  @Locate(xpath = "\t\n" + "//XCUIElementTypeStaticText[@name=\"25\"]", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/tier_tabs\")",
      on = Platform.MOBILE_ANDROID)
  protected Text pointMenu;

  @Locate(xpath = "\t\n" + "//XCUIElementTypeStaticText[@name=\"%s\"]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.LinearLayout[@content-desc=\"%s\"]",
      on = Platform.MOBILE_ANDROID)
  protected Button pointMenuOption;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(signature).present().setTimeout(Duration.ofSeconds(60)));
  }

  @Step("Tap on 'More' button")
  public MoreOptionsView tapOnMoreButton() {
    logger.info("Tapping on 'More' button");
    moreButton.click();
    return SdkHelper.create(MoreOptionsView.class);
  }

  @Step(" Gets account profile menu.")
  public MoreOptionsView getAccountProfileMenu() {
    logger.info("Open account profile menu");
    getMoreScreenButton.initialize();
    MobileHelper.tapOnElementCenter(getMoreScreenButton);
    SdkHelper.getSyncHelper().sleep(5000);
    return SdkHelper.create(MoreOptionsView.class);
  }

  @Step("Gets welcome message.")
  public String getWelcomeMessage() {
    logger.info("Welcome message: " + welcomeMessageText.getText());
    return welcomeMessageText.getText();
  }

  @Step("Gets user name.")
  public String getUserName() {
    logger.info("Username: " + userNameText.getText());
    return userNameText.getText();
  }

  @Step("Rewards point image is displayed.")
  public Boolean isRewardsPointImageDisplayed() {
    logger.info("Checking if rewards point image is displayed");
    return rewardsPointImage.isDisplayed();
  }

  @Step("Ready to use rewards section is displayed.")
  public Boolean isReadyToUseRewardsSectionDisplayed() {
    logger.info("Checking if Ready to use rewards section is displayed");
    return readyToUseRewardsSection.isDisplayed();
  }

  @Step("Rewards store section is displayed.")
  public Boolean isRewardsStoreSectionDisplayed() {
    logger.info("Checking if Rewards store section is displayed");
    return rewardsStoreSection.isDisplayed();
  }

  @Step("Open ready to use rewards & offers")
  public void clickReadyToUseRewardsAndOffersTab() {
    logger.info("Click on ready to use rewards & offers tab");
    readyToUseRewardsAndOffersTab.click();
    getCheckInTooltipComponent().closeCheckInTooltipIfDisplayed(HomeView.class);
  }

  @Step("Open rewards store")
  public void clickRewardsStoreTab() {
    logger.info("Click on rewards store tab");
    rewardsStoreTab.click();
    gotItButton.click();
    getCheckInTooltipComponent().closeCheckInTooltipIfDisplayed(HomeView.class);
  }

  @Step("QR code is displayed.")
  public Boolean isQrCodeDisplayed() {
    logger.info("Checking if QR code is displayed");
    return qrCodeButton.isDisplayed();
  }

  @Step("Click qr code")
  public void clickQrCode() {
    logger.info("Click on QR code");
    qrCodeButton.click();
    getCheckInTooltipComponent().closeCheckInTooltipIfDisplayed();
  }

  @Step("Tip my barista button is displayed")
  public Boolean isTipMyBaristaButtonDisplayed() {
    logger.info("My barista button is displayed?");
    return tipMyBaristaButton.isDisplayed();
  }

  @Step("Peet's card text is displayed")
  public Boolean isPeetsCardTextDisplayed() {
    logger.info("Peet's card text button is displayed?");
    return peetsCardText.isDisplayed();
  }

  @Step("Get peet's available currency")
  public String getPeetsAvailableCurrency() {
    logger.info("Peet's available currency" + peetsCardAvailableCurrencyText.getText());
    return peetsCardAvailableCurrencyText.getText();
  }

  @Step("How rewards button is displayed")
  public Boolean isHowRewardsWorkDisplayed() {
    logger.info("How rewards button is displayed?");
    return howRewardsWorkButton.isDisplayed();
  }

  @Step("Click how rewards work button")
  public PeetnikRewardsHelpView clickHowRewardsWorkButton() {
    logger.info("Click on how rewards work button");
    howRewardsWorkButton.click();
    return SdkHelper.create(PeetnikRewardsHelpView.class);
  }

  @Step("Rewards circle name text is displayed")
  public Boolean isRewardsCircleNameDisplayed() {
    logger.info("Rewards circle name text is displayed?");
    return rewardsTypeText.isDisplayed();
  }

  @Step("Rewards name text is displayed")
  public Boolean isRewardsNameDisplayed() {
    logger.info("Rewards name text is displayed?");
    MobileHelper.swipeRapidlyWithCount(SwipeDirectionCloseToMiddle.UP, 1);
    return rewardNameText.isDisplayed();
  }

  @Step("Get reward Name")
  public String getRewardName() {
    logger.info("Reward name: {}", rewardNameText.getText());
    return rewardNameText.getText();
  }

  @Step("Expiration text is displayed")
  public Boolean isExpirationDisplayed() {
    logger.info("Expiration text is displayed?");
    return expirationText.isDisplayed();
  }

  @Step("Use rewards button is displayed")
  public Boolean isUseRewardsButtonDisplayed() {
    logger.info("Use rewards button is displayed?");
    return useRewardsButton.isDisplayed();
  }

  @Step("View details button is displayed")
  public Boolean isViewDetailsButtonDisplayed() {
    logger.info("View details button is displayed?");
    MobileHelper.scrollDownToElementCloseToMiddle(viewDetailsButton, 1);
    return viewDetailsButton.isDisplayed();
  }

  @Step("Hide details button is displayed")
  public Boolean isHideDetailsButtonDisplayed() {
    logger.info("Hide details button is displayed?");
    MobileHelper.scrollDownToElementCloseToMiddle(hideDetailsButton, 1);
    return hideDetailsButton.isDisplayed();
  }

  @Step("Details description text is displayed")
  public Boolean isViewDetailsDescriptionTextDisplayed() {
    logger.info("Details description text is displayed?");
    return detailsDescriptionText.isDisplayed();
  }

  @Step("Click on view details")
  public void clickViewDetails() {
    logger.info("Click on view details");
    MobileHelper.scrollDownToElementCloseToMiddle(viewDetailsButton, 1);
    viewDetailsButton.click();
  }

  @Step("Click on view details")
  public void clickHideDetails() {
    logger.info("Click on view details");
    hideDetailsButton.click();
  }

  @Step("Details description text is displayed")
  public String getDetailsDescription() {
    logger.info("Details description text: " + detailsDescriptionText.getText());
    return detailsDescriptionText.getText();
  }

  @Step("Details description text is displayed")
  public Boolean isDetailsDescriptionDisplayed() {
    logger.info("Details description displayed: " + detailsDescriptionText.isDisplayed());
    return detailsDescriptionText.isDisplayed();
  }

  @Step("Point menu is displayed")
  public Boolean isPointMenuDisplayed() {
    logger.info("Details description text is displayed?");
    return pointMenu.isDisplayed();
  }

  public void clickPointMenuOption(String option) {
    logger.info("Select option: {} from point menu", option);
    pointMenuOption.format(option).click();
  }
}
