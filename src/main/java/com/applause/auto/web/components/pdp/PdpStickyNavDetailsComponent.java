package com.applause.auto.web.components.pdp;

import com.applause.auto.common.data.enums.Attribute;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.web.components.MiniCart;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

public class PdpStickyNavDetailsComponent extends BaseComponent {

  @Locate(css = "#pvStickyAtc", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = ".sticky-atc__title", on = Platform.WEB)
  private Text nameText;

  @Locate(css = "#pvStickyAtc #productPrice .pv-price__original", on = Platform.WEB)
  private Text priceText;

  @Locate(css = "#stickyBtnAddToBag", on = Platform.WEB)
  private Text addToCartButton;

  @Override
  public void afterInit() {
    super.afterInit();
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
  }

  @Step("Verify sticky nav component is displayed")
  public boolean isDisplayed() {
    logger.info("Checking sticky nav component is displayed");
    return mainContainer.getAttributeValue(Attribute.CLASS.getValue()).contains("visible");
  }

  @Step("Verify product name is displayed")
  public boolean isProductNameDisplayed() {
    return WebHelper.isDisplayed(nameText);
  }

  @Step("Verify product price is displayed")
  public boolean isProductPriceDisplayed() {
    return WebHelper.isDisplayed(priceText);
  }

  @Step("Verify Add to Cart button is displayed")
  public boolean isAddToCartButtonDisplayed() {
    return WebHelper.isDisplayed(addToCartButton);
  }

  @Step("Click on the 'Add to Cart' button")
  public MiniCart clickAddToCart() {
    logger.info("Clicking on the 'Add to Cart' button");
    addToCartButton.click();
    return SdkHelper.create(MiniCart.class);
  }

  @Step("Get product name")
  public String getProductName() {
    return nameText.getText().trim();
  }
}