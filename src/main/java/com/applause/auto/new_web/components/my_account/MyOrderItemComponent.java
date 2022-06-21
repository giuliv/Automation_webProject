package com.applause.auto.new_web.components.my_account;

import com.applause.auto.common.data.dto.MyOrderDto;
import com.applause.auto.common.data.enums.Attribute;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.AcceptancePage;
import com.applause.auto.new_web.views.CartPage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

@Implementation(is = MyOrderItemComponent.class, on = Platform.WEB)
public class MyOrderItemComponent extends BaseComponent {

  @Locate(xpath = ".//h4[@aria-label='Order #']", on = Platform.WEB)
  private Text orderNumber;

  @Locate(xpath = ".//h4[@aria-label='Date Ordered']", on = Platform.WEB)
  private Text dateOrdered;

  @Locate(xpath = ".//h4[@aria-label='Order Status']", on = Platform.WEB)
  private Text orderStatus;

  @Locate(xpath = ".//h4[@aria-label='Order Status']", on = Platform.WEB)
  private Text total;

  @Locate(xpath = ".//button", on = Platform.WEB)
  private Button detailsButton;

  @Locate(xpath = ".//div[contains(@class, 'animations')]", on = Platform.WEB)
  private Button animation;

  @Locate(xpath = "./div[contains(@class, 'ac-table-row')][2]", on = Platform.WEB)
  private Button isBlockExpanded;

  @Locate(
      xpath = ".//div[contains(@class, 'product') and contains(@class, 'img')]/img",
      on = Platform.WEB)
  private Button orderImage;

  @Locate(xpath = ".//h4[contains(@class, 'title')]", on = Platform.WEB)
  private Text orderName;

  @Locate(xpath = ".//p[contains(@class, 'product-info')][2]", on = Platform.WEB)
  private Text orderQuantity;

  @Locate(xpath = ".//p[contains(@class, 'product-info')][1]", on = Platform.WEB)
  private Text orderInfo;

  @Locate(xpath = ".//p[contains(@class, 'product-price')]", on = Platform.WEB)
  private Text orderPrice;

  @Locate(xpath = ".//div[contains(@class, 'product-ctas')]/a", on = Platform.WEB)
  private Button viewProductButton;

  @Locate(xpath = ".//a[contains(@class, 'tracking-btn')]", on = Platform.WEB)
  private Button orderTrackingButton;

  @Locate(xpath = ".//div[contains(@class, 'tracking')]/button", on = Platform.WEB)
  private Button reorderButton;

  @Step("Verify Order item displayed with: Order #, Date order, Order status and Total")
  public boolean isDisplayedCorrectly() {
    if (!WebHelper.isTextDisplayedAndNotEmpty(orderNumber)) {
      logger.info("Order number isn't displayed");
      return false;
    }

    if (!WebHelper.isTextDisplayedAndNotEmpty(dateOrdered)) {
      logger.info("Date ordered isn't displayed for [{}] order", dateOrdered.getText());
      return false;
    }

    if (!WebHelper.isTextDisplayedAndNotEmpty(orderStatus)) {
      logger.info("Order status isn't displayed for [{}] order", orderStatus.getText());
      return false;
    }

    if (!WebHelper.isTextDisplayedAndNotEmpty(total)) {
      logger.info("Total isn't displayed for [{}] order", total.getText());
      return false;
    }

    if (!WebHelper.isDisplayed(detailsButton)) {
      logger.info("Details Button isn't displayed for [{}] order", detailsButton.getText());
      return false;
    }

    return true;
  }

  @Step("Click on 'Details' button")
  public MyOrderItemComponent clickOnDetails() {
    logger.info("Clicking on 'Details' button");
    detailsButton.click();
    WebHelper.waitForElementToDisappear(animation, 10);
    return this;
  }

  @Step("Check if Order is Expanded")
  public boolean isExpanded() {
    boolean expanded =
        isBlockExpanded.getAttributeValue(Attribute.CLASS.getValue()).contains("open");
    logger.info("Order [{}] is expanded - [{}]", orderNumber.getText(), expanded);
    return expanded;
  }

  @Step("Check if Order image is displayed")
  public boolean isOrderImageDisplayed() {
    boolean isDisplayed = WebHelper.isDisplayed(orderImage);
    logger.info("Image for Order [{}] is displayed - [{}]", orderNumber.getText(), isDisplayed);
    return isDisplayed;
  }

  @Step("Check if Order name is displayed")
  public boolean isOrderNameDisplayed() {
    boolean isDisplayed = WebHelper.isTextDisplayedAndNotEmpty(orderName);
    logger.info("Name for Order [{}] is displayed - [{}]", orderName.getText(), isDisplayed);
    return isDisplayed;
  }

  @Step("Check if Order number is displayed")
  public boolean isOrderNumberDisplayed() {
    boolean isDisplayed = WebHelper.isTextDisplayedAndNotEmpty(orderNumber);
    logger.info("Order number [{}] is displayed - [{}]", orderNumber.getText(), isDisplayed);
    return isDisplayed;
  }

  @Step("Check if Order info is displayed")
  public boolean isOrderInfoDisplayed() {
    boolean isDisplayed = WebHelper.isTextDisplayedAndNotEmpty(orderInfo);
    logger.info("Info for Order [{}] is displayed - [{}]", orderInfo.getText(), isDisplayed);
    return isDisplayed;
  }

  @Step("Check if Order Quantity is displayed")
  public boolean isOrderQuantityDisplayed() {
    boolean isDisplayed = WebHelper.isTextDisplayedAndNotEmpty(orderQuantity);
    logger.info(
        "Quantity for Order [{}] is displayed - [{}]", orderQuantity.getText(), isDisplayed);
    return isDisplayed;
  }

  @Step("Check if Order Price is displayed")
  public boolean isOrderPriceDisplayed() {
    boolean isDisplayed = WebHelper.isTextDisplayedAndNotEmpty(orderPrice);
    logger.info("Price for Order [{}] is displayed - [{}]", orderPrice.getText(), isDisplayed);
    return isDisplayed;
  }

  @Step("Check if 'View Product' Button is displayed")
  public boolean isViewProductButtonDisplayed() {
    boolean isDisplayed = WebHelper.isDisplayed(viewProductButton);
    logger.info(
        "'View product' button for Order [{}] is displayed - [{}]",
        viewProductButton.getText(),
        isDisplayed);
    return isDisplayed;
  }

  @Step("Get order name")
  public String getName() {
    String name = WebHelper.cleanString(orderName.getText());
    logger.info("Order name - [{}]", name);
    return name;
  }

  @Step("Get order info")
  public String getInfo() {
    String info = WebHelper.cleanString(orderInfo.getText());
    logger.info("Order info - [{}]", info);
    return info;
  }

  @Step("Get order quantity")
  public int getQuantity() {
    String quantity = WebHelper.cleanString(orderQuantity.getText());
    logger.info("Order quantity - [{}]", quantity);
    return WebHelper.getNumberFromString(quantity);
  }

  @Step("Get order price")
  public String getPrice() {
    String price = WebHelper.cleanString(orderPrice.getText()).split(" ")[0];
    logger.info("Order price - [{}]", price);
    return price;
  }

  @Step("Get order DTO")
  public MyOrderDto getOrderDTO() {
    return MyOrderDto.builder()
        .name(getName())
        .info(getInfo())
        .quantity(getQuantity())
        .price(getPrice())
        .build();
  }

  @Step("Click on 'View Product' button")
  public ProductDetailsPage clickOnViewProduct() {
    logger.info("Clicking on 'View Product' button");
    viewProductButton.click();
    return SdkHelper.create(ProductDetailsPage.class);
  }

  @Step("Click on 'Order Tracking' button")
  public AcceptancePage clickOnOrderTrackingButton() {
    logger.info("Clicking on 'Order Tracking' button");
    orderTrackingButton.click();
    return SdkHelper.create(AcceptancePage.class);
  }

  @Step("Click on 'Reorder' button")
  public CartPage clickOnReorderButton() {
    logger.info("Clicking on 'Reorder' button");
    reorderButton.click();
    return SdkHelper.create(CartPage.class);
  }
}
