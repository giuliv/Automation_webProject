package com.applause.auto.new_web.components;

import com.applause.auto.common.data.dto.StoreDetailsDto;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.MenuPage;
import com.applause.auto.new_web.views.StoreDetailsPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

@Implementation(is = StockResultItemComponent.class, on = Platform.WEB)
@Implementation(is = StockResultItemComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class StockResultItemComponent extends BaseComponent {

  @Locate(xpath = ".//span[contains(@class, 'item-number-text')]", on = Platform.WEB)
  private Text shopNumber;

  @Locate(xpath = ".//span[contains(@class, 'item-detail-type')]", on = Platform.WEB)
  private Text shopType;

  @Locate(xpath = ".//span[contains(@class, 'item-detail-name')]", on = Platform.WEB)
  private Text shopName;

  @Locate(xpath = ".//span[contains(@class, 'item-detail-address')]", on = Platform.WEB)
  private Text shopAddress;

  @Locate(xpath = ".//span[contains(@class, 'item-detail-distance')]", on = Platform.WEB)
  private Text shopDistance;

  @Locate(xpath = ".//span[contains(@class, 'item-detail-open')]", on = Platform.WEB)
  private Text shopSchedule;

  @Locate(xpath = ".//div[contains(@class, 'actions')]/a", on = Platform.WEB)
  private Button startOrderButton;

  @Step("Check If Shop Item is Fully Displayed")
  public boolean checkIfShopItemIsFullyDisplayed() {
    if (!WebHelper.isTextDisplayedAndNotEmpty(shopNumber)) {
      logger.info("Number isn't displayed for [{}] shop", shopName.getText());
      return false;
    }

    if (!WebHelper.isTextDisplayedAndNotEmpty(shopName)) {
      logger.info("Shop name isn't displayed for [{}] shop", shopNumber.getText());
      return false;
    }

    if (!WebHelper.isTextDisplayedAndNotEmpty(shopType)) {
      logger.info(
          "Type isn't displayed for [{}] shop on [{}] position",
          shopName.getText(),
          shopNumber.getText());
      return false;
    }

    if (!WebHelper.isTextDisplayedAndNotEmpty(shopAddress)) {
      logger.info(
          "Address isn't displayed for [{}] shop on [{}] position",
          shopName.getText(),
          shopNumber.getText());
      return false;
    }

    if (!WebHelper.isTextDisplayedAndNotEmpty(shopDistance)) {
      logger.info(
          "Distance isn't displayed for [{}] shop on [{}] position",
          shopName.getText(),
          shopNumber.getText());
      return false;
    }

    if (!WebHelper.isTextDisplayedAndNotEmpty(shopSchedule)) {
      logger.info(
          "Schedule isn't displayed for [{}] shop on [{}] position",
          shopName.getText(),
          shopNumber.getText());
      return false;
    }

    if (!isStartOrderButtonDisplayed()) {
      logger.info(
          "'Start Order' button isn't displayed for [{}] shop on [{}] position",
          shopName.getText(),
          shopNumber.getText());
      return false;
    }
    return true;
  }

  @Step("Get shop Name")
  public String getName() {
    String name = shopName.getText();
    logger.info("Shop name - [{}]", name);
    return name;
  }

  @Step("Get shop Type")
  public String getType() {
    String type = shopType.getText();
    logger.info("Shop type - [{}]", type);
    return type;
  }

  @Step("Get shop address")
  public String getAddress() {
    String address = shopAddress.getText();
    logger.info("Shop address - [{}]", address);
    return address;
  }

  @Step("Get shop Name")
  public StoreDetailsPage click() {
    logger.info("Clicking on [{}] store", shopName.getText());
    getParent().click();
    return SdkHelper.create(StoreDetailsPage.class);
  }

  @Step("Get shop direction")
  public String getDirection() {
    String direction = shopDistance.getText();
    logger.info("Shop direction - [{}]", direction);
    return direction;
  }

  @Step("Get shop Schedule")
  public String getSchedule() {
    String schedule = shopSchedule.getText();
    logger.info("Shop schedule - [{}]", schedule);
    return schedule;
  }

  @Step("Check if 'Start Order' Button is Displayed")
  public boolean isStartOrderButtonDisplayed() {
    return WebHelper.isDisplayed(startOrderButton);
  }

  public StoreDetailsDto getStoreDetailsDto() {
    return StoreDetailsDto.builder()
        .type(getType())
        .name(getName())
        .address(getAddress())
        .direction(getDirection())
        .schedule(getSchedule())
        .isStartOrder(isStartOrderButtonDisplayed())
        .build();
  }

  @Step("Click 'Start Order' Button")
  public MenuPage startOrder() {
    logger.info("Clicking on 'Start Order' button");
    String windowHandle = SdkHelper.getDriver().getWindowHandle();
    startOrderButton.click();
    WebHelper.switchToNewTab(windowHandle);
    return SdkHelper.create(MenuPage.class);
  }
}
