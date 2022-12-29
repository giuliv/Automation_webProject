package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.web.components.OneAppManyBenefitsComponent;
import com.applause.auto.web.components.PeetnikRewardsComponent;
import com.applause.auto.web.components.ToutsSectionComponent;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

@Implementation(is = StoreDetailsPage.class, on = Platform.WEB)
@Implementation(is = StoreDetailsPageMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class StoreDetailsPage extends Base {

  @Locate(xpath = "//span[contains(@class, 'store-detail-type')]", on = Platform.WEB)
  private Text type;

  @Locate(xpath = "//div[contains(@class, 'detail-header')]/h2", on = Platform.WEB)
  private Text name;

  @Locate(xpath = "//span[contains(@class, 'store-detail-address')]", on = Platform.WEB)
  private Text address;

  @Locate(xpath = "//span[contains(@class, 'store-detail-phone')]", on = Platform.WEB)
  private Text phoneNumber;

  @Locate(xpath = "//a[contains(@class, 'store-detail-order')]", on = Platform.WEB)
  private Button startOrderButton;

  @Locate(xpath = "//a[contains(@class, 'store-detail-directions')]", on = Platform.WEB)
  private Button getDirectionsButton;

  @Locate(xpath = "//h3[contains(@class, 'detail-hours-title')]", on = Platform.WEB)
  private Text schedule;

  @Locate(xpath = "//ul[contains(@class, 'store-locator__detail-hours-list')]", on = Platform.WEB)
  private Text weeklySchedule;

  @Locate(
      xpath = "//span[contains(@class, 'store-locator__detail-amenities-title')]",
      on = Platform.WEB)
  private Text amenitiesTitle;

  @Locate(
      xpath = "//span[contains(@class, 'store-locator__detail-amenities-text')]",
      on = Platform.WEB)
  private Text amenitiesDetails;

  @Locate(xpath = "//button[contains(@class, 'store-detail-back')]", on = Platform.WEB)
  protected Button backButton;

  @Locate(css = "#storeSubscriptions h2", on = Platform.WEB)
  protected Text coffeeSubscriptionHeader;

  @Locate(css = "#storeSubscriptions p", on = Platform.WEB)
  protected Text coffeeSubscriptionDescription;

  @Locate(css = "#storeSubscriptions a", on = Platform.WEB)
  protected Text coffeeSubscriptionSeeOptionsButton;

  @Step("Get Store Type")
  public String getType() {
    String typeText = type.getText();
    logger.info("Store type - [{}]", typeText);
    return typeText;
  }

  @Step("Get Store name")
  public String getName() {
    String nameText = name.getText();
    logger.info("Store name - [{}]", nameText);
    return nameText;
  }

  @Step("Get Store Address")
  public String getAddress() {
    String addressText = WebHelper.cleanString(address.getText().replaceAll("\n", " "));
    logger.info("Store address - [{}]", addressText);
    return addressText;
  }

  @Step("Check if 'Start Order' Button is Displayed")
  public boolean isStartOrderButtonDisplayed() {
    return WebHelper.isDisplayed(startOrderButton);
  }

  @Step("Check if 'Get Directions' Button is Displayed")
  public boolean isGetDirectionsButtonDisplayed() {
    return WebHelper.isDisplayed(getDirectionsButton);
  }

  @Step("Get Store Schedule")
  public String getSchedule() {
    String scheduleText = schedule.getText();
    logger.info("Store Schedule - [{}]", scheduleText);
    return scheduleText;
  }

  @Step("Check if 'Amenities' is Displayed")
  public boolean isAmenitiesButtonDisplayed() {
    return WebHelper.isDisplayed(amenitiesTitle);
  }

  @Step("Check if 'Back' button is Displayed")
  public boolean isBackButtonDisplayed() {
    return WebHelper.isDisplayed(backButton);
  }

  @Step("Click on 'Get Directions' Button")
  public GoogleMapPage clickOnGetDirectionsButton() {
    logger.info("Clicking on 'Get Directions' Button");
    String windowHandle = SdkHelper.getDriver().getWindowHandle();
    if (WebHelper.isMobile()) {
      WebHelper.jsClick(getDirectionsButton.getWebElement());
    } else {
      getDirectionsButton.click();
    }

    WebHelper.switchToNewTab(windowHandle);
    return SdkHelper.create(GoogleMapPage.class);
  }

  @Step("Click 'Start Order' Button")
  public MenuPage startOrder() {
    logger.info("Clicking on 'Start Order' button");
    String windowHandle = SdkHelper.getDriver().getWindowHandle();
    startOrderButton.click();
    WebHelper.switchToNewTab(windowHandle);
    return SdkHelper.create(MenuPage.class);
  }

  @Step("Click 'Back to results ' Button")
  public StoreLocatorPage backToResults() {
    logger.info("Clicking on 'Back to results ' button");
    backButton.click();
    return SdkHelper.create(StoreLocatorPage.class);
  }

  @Step("Verify Coffee subscription header is displayed")
  public boolean isCoffeeSubscriptionHeaderDisplayed() {
    WebHelper.scrollToPageBottom();
    WebHelper.waitForElementToAppear(coffeeSubscriptionHeader);
    WebHelper.scrollToElement(coffeeSubscriptionHeader);
    return WebHelper.isDisplayed(coffeeSubscriptionHeader);
  }

  @Step("Verify Coffee subscription description is displayed")
  public boolean isCoffeeSubscriptionDescriptionDisplayed() {
    return WebHelper.isDisplayed(coffeeSubscriptionDescription);
  }

  @Step("Verify Coffee subscription 'See Subscription options' button is displayed")
  public boolean isCoffeeSubscriptionSeeOptionsButtonDisplayed() {
    return WebHelper.isDisplayed(coffeeSubscriptionSeeOptionsButton);
  }

  @Step("Click on the 'See Subscription options' button")
  public SubscriptionsPage clickCoffeeSubscriptionSeeOptionsButton() {
    logger.info("Clicking on the 'See Subscription options' button");
    coffeeSubscriptionSeeOptionsButton.click();
    return SdkHelper.create(SubscriptionsPage.class);
  }

  @Step("Verify store item is displayed with all expected data")
  public boolean areStoreDetailsDisplayed() {
    if (!WebHelper.isDisplayed(name)) {
      logger.error("Store name isn't displayed");
      return false;
    }

    if (!WebHelper.isDisplayed(address)) {
      logger.error("Store address isn't displayed");
      return false;
    }

    if (!WebHelper.isDisplayed(phoneNumber)) {
      logger.error("Store phone number isn't displayed");
      return false;
    }

    if (!isStartOrderButtonDisplayed()) {
      logger.error("Store Start Order button isn't displayed");
      return false;
    }

    if (!isGetDirectionsButtonDisplayed()) {
      logger.error("Store Get Direction button isn't displayed");
      return false;
    }

    if (!WebHelper.isDisplayed(schedule)) {
      logger.error("Store Schedule button isn't displayed");
      return false;
    }

    if (!WebHelper.isDisplayed(schedule)) {
      logger.error("Store Schedule isn't displayed");
      return false;
    }

    if (!WebHelper.isDisplayed(weeklySchedule)) {
      logger.error("Store Weekly Schedule isn't displayed");
      return false;
    }

    if (!isAmenitiesButtonDisplayed()) {
      logger.error("Store Amenities Title isn't displayed");
      return false;
    }

    if (!WebHelper.isDisplayed(amenitiesDetails) || amenitiesDetails.getText().isEmpty()) {
      logger.error("Store Amenities details isn't displayed or is empty");
      return false;
    }

    return true;
  }

  @Step("Get OneAppManyBenefitsComponent")
  public OneAppManyBenefitsComponent getOneAppManyBenefitsComponent() {
    return SdkHelper.create(OneAppManyBenefitsComponent.class);
  }

  @Step("Get ToutsSectionComponent")
  public ToutsSectionComponent getToutsSectionComponent() {
    return SdkHelper.create(ToutsSectionComponent.class);
  }

  @Step("Get PeetnikRewardsComponent")
  public PeetnikRewardsComponent getPeetnikRewardsComponent() {
    return SdkHelper.create(PeetnikRewardsComponent.class);
  }
}

class StoreDetailsPageMobile extends StoreDetailsPage {

  @Step("Click 'Back to results ' Button")
  public StoreLocatorPage backToResults() {
    logger.info("Clicking on 'Back to results ' button");
    WebHelper.scrollToPageTop();
    backButton.click();
    return SdkHelper.create(StoreLocatorPage.class);
  }
}
