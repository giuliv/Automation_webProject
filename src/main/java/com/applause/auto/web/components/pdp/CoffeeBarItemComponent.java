package com.applause.auto.web.components.pdp;

import com.applause.auto.common.data.enums.Attribute;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.OrderPage;
import io.qameta.allure.Step;

@Implementation(is = CoffeeBarItemComponent.class, on = Platform.WEB)
@Implementation(is = CoffeeBarItemComponentMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class CoffeeBarItemComponent extends BaseComponent {

  @Locate(css = "h3.pi__title", on = Platform.WEB)
  protected Text productName;

  @Locate(css = "img.pi__photo", on = Platform.WEB)
  protected Image productImage;

  @Locate(css = ".pi__quick-add a", on = Platform.WEB)
  protected Button orderNowButton;

  @Step("Get product name")
  public String getName() {
    return productName.getText().trim();
  }

  @Step("Verify product name is displayed")
  public boolean isProductNameDisplayed() {
    logger.info("Checking product name is displayed");
    return WebHelper.isDisplayed(productName);
  }

  @Step("Verify product image is displayed")
  public boolean isProductImageDisplayed() {
    logger.info("Checking product image is displayed for the item: {}", getName());
    return WebHelper.isDisplayed(productImage, 10);
  }

  @Step("Hover over item")
  public void hoverOver() {
    logger.info("Hovering over item: {}", getName());
    WebHelper.hoverByAction(productName);
  }

  @Step("Verify if the 'Order Now' button is displayed")
  public Boolean isOrderNowDisplayed() {
    logger.info("Checking if the 'Order Now' button is displayed");
    return WebHelper.isDisplayed(orderNowButton);
  }

  @Step("Click on the 'Order Now' button")
  public OrderPage clickOrderNowButton() {
    logger.info("Clicking on the 'Order Now' button for item: {}", getName());
    orderNowButton.click();
    WebHelper.getNewTab();
    return SdkHelper.create(OrderPage.class);
  }

  @Step("Verify item is visible")
  public Boolean isVisible() {
    return this.getParent()
        .getWebElement()
        .getAttribute(Attribute.CLASS.getValue())
        .contains("is-selected");
  }
}

class CoffeeBarItemComponentMobile extends CoffeeBarItemComponent {

  @Override
  @Step("Verify product image is displayed")
  public boolean isProductImageDisplayed() {
    logger.info("Checking product image is displayed for the item: {}", getName());
    return productImage.exists();
  }
}
