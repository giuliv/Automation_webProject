package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.web.components.MiniCartContainerChunk;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.web.helpers.WebHelper;

@Implementation(is = CoffeeKCupsProductPage.class, on = Platform.WEB)
public class CoffeeKCupsProductPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = ".product-shop-holder .add-to-cart button", on = Platform.WEB)
  private Button getAddToCartButton;

  @Locate(css = "#shopping-cart-please-wait", on = Platform.WEB)
  private ContainerElement getAddingToCartSpinner;

  @Locate(css = "#attribute269", on = Platform.WEB)
  private SelectList getSelectBoxSelectList;

  /* -------- Actions -------- */

  /** Select a Box */
  public void selectBoxContent(String boxContent) {
    logger.info("Selecting a Box with: %s" + boxContent);
    WebHelper.jsSelectByContainedText(getSelectBoxSelectList.getWebElement(), boxContent);
  }

  /**
   * Click Add to Cart Button
   *
   * @return MiniCartContainerChunk
   */
  public MiniCartContainerChunk clickAddToCart() {
    logger.info("Tap on Shop Coffee Button");
    getAddToCartButton.click();
    waitForAddingToCartSpinner();
    return SdkHelper.create(MiniCartContainerChunk.class);
  }

  /** Click Add to Cart Button */
  public void waitForAddingToCartSpinner() {
    logger.info("Adding item to Shopping Cart...");
    SdkHelper.getSyncHelper().wait(Until.uiElement(getAddingToCartSpinner).visible());
    SdkHelper.getSyncHelper().wait(Until.uiElement(getAddingToCartSpinner).notVisible());
  }
}
