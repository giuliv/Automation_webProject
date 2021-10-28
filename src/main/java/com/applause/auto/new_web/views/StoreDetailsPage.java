package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

@Implementation(is = StoreDetailsPage.class, on = Platform.WEB)
public class StoreDetailsPage extends BaseComponent {

  @Locate(xpath = "//span[contains(@class, 'store-detail-type')]", on = Platform.WEB)
  private Text type;

  @Locate(xpath = "//div[contains(@class, 'detail-header')]/h2", on = Platform.WEB)
  private Text name;

  @Locate(xpath = "//span[contains(@class, 'store-detail-address')]", on = Platform.WEB)
  private Text address;

  @Locate(xpath = "//a[contains(@class, 'store-detail-order')]", on = Platform.WEB)
  private Button startOrderButton;

  @Locate(xpath = "//a[contains(@class, 'store-detail-directions')]", on = Platform.WEB)
  private Button getDirectionsButton;

  @Locate(xpath = "//h3[contains(@class, 'detail-hours-title')]", on = Platform.WEB)
  private Text schedule;

  @Locate(xpath = "//div[contains(@class, 'store-amenities')]", on = Platform.WEB)
  private Text amenities;

  @Locate(xpath = "//button[contains(@class, 'store-detail-back')]", on = Platform.WEB)
  private Button backButton;

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
    return WebHelper.isDisplayed(amenities);
  }

  @Step("Check if 'Back' button is Displayed")
  public boolean isBackButtonDisplayed() {
    return WebHelper.isDisplayed(backButton);
  }

  @Step("Click on 'Get Directions' Button")
  public GoogleMapPage clickOnGetDirectionsButton() {
    logger.info("Clicking on 'Get Directions' Button");
    String windowHandle = SdkHelper.getDriver().getWindowHandle();
    getDirectionsButton.click();
    WebHelper.switchToNewTab(windowHandle);
    return SdkHelper.create(GoogleMapPage.class);
  }
}
