package com.applause.auto.new_web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;
import org.testng.asserts.SoftAssert;

@Implementation(is = QuickViewComponent.class, on = Platform.WEB)
@Implementation(is = QuickViewComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class QuickViewComponent extends BaseComponent {

  @Locate(css = "#modalQuickAdd .modal__inner--quick-add", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = ".modal__inner--quick-add [class*=title]", on = Platform.WEB)
  private Text productName;

  @Locate(css = ".modal__inner--quick-add .pv-price__original", on = Platform.WEB)
  private Text productPrice;

  @Locate(css = ".modal__inner--quick-add .pv-details", on = Platform.WEB)
  private Text productDetails;

  @Locate(id = "ratings-summary", on = Platform.WEB)
  private ContainerElement starRatings;

  @Locate(css = "div[data-message-id='quickGrind']", on = Platform.WEB)
  private ContainerElement grindSection;

  @Locate(className = "pv-qty", on = Platform.WEB)
  private ContainerElement quantitySection;

  @Locate(id = "quickBtnAddToBag", on = Platform.WEB)
  private Button addToCartButton;

  @Override
  public void afterInit() {
    logger.info("QuickView Init method");
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
  }

  @Step("Get UI elements")
  public SoftAssert validateMainUIElements() {
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(productName.isDisplayed(), "Product name is not displayed");
    softAssert.assertTrue(productPrice.isDisplayed(), "Product price is not displayed");
    softAssert.assertTrue(productDetails.isDisplayed(), "Product details is not displayed");
    softAssert.assertTrue(starRatings.isDisplayed(), "Star Ratings is not displayed");
    softAssert.assertTrue(grindSection.isDisplayed(), "Grind Section is not displayed");
    softAssert.assertTrue(quantitySection.isDisplayed(), "Quantity Section is not displayed");
    softAssert.assertTrue(addToCartButton.isDisplayed(), "Add to Cart is not displayed");

    return softAssert;
  }

  /**
   * Click on the 'Add to cart' button
   *
   * @return MiniCart
   */

  @Step("Click Add to cart")
  public MiniCart clickAddToCart() {
    logger.info("Clicking on the 'Add to cart' button");
    addToCartButton.click();
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    return SdkHelper.create(MiniCart.class);
  }

  /** @return product name */

  @Step("Get product name")
  public String getProductName() {
    return productName.getText().trim();
  }
}
