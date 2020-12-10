package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.helper.sync.Until;
import com.applause.auto.web.components.MiniCartContainerChunk;

@Implementation(is = EquipmentProductPage.class, on = Platform.WEB)
public class EquipmentProductPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = ".product-shop-holder .product-name", on = Platform.WEB)
  private Text getViewSignature;

  @Locate(css = ".product-shop-holder .add-to-cart button", on = Platform.WEB)
  private Button getAddToCartButton;

  @Locate(css = "#shopping-cart-please-wait", on = Platform.WEB)
  private ContainerElement getAddingToCartSpinner;

  @Locate(css = "#attribute198", on = Platform.WEB)
  private SelectList getSelectGrindSelectList;

  /* -------- Actions -------- */

  /** Select a Grind */
  public void selectAGrind(String grind) {
    logger.info(String.format("Selecting a Grind: %s", grind));
    getSelectGrindSelectList.select(grind);
  }

  /**
   * Click Add to Cart Button
   *
   * @return MiniCartContainerChunk
   */
  public MiniCartContainerChunk clickAddToCart() {
    logger.info("Tap on Shop Coffee Button");
    getSyncHelper().sleep(5000);
    getAddToCartButton.click();
    return this.create(MiniCartContainerChunk.class);
  }

  /** Wait for load spinner */
  public void waitForAddingToCartSpinner() {
    logger.info("Checkout loading-spinner...");
    getSyncHelper().wait(Until.uiElement(getAddingToCartSpinner).present());
    getSyncHelper().wait(Until.uiElement(getAddingToCartSpinner).notPresent());
  }
}
