package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.web.components.MiniCartContainerChunk;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.web.helpers.WebHelper;

@Implementation(is = PeetsCardProductPage.class, on = Platform.WEB)
public class PeetsCardProductPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = ".product-shop .product-name", on = Platform.WEB)
  private Text getViewSignature;

  @Locate(css = ".product-shop .add-to-cart button", on = Platform.WEB)
  private Button getAddToCartButton;

  @Locate(css = "#shopping-cart-please-wait", on = Platform.WEB)
  private ContainerElement getAddingToCartSpinner;

  @Locate(css = "#select_1", on = Platform.WEB)
  private SelectList getSelectCardAmountSelectList;

  /* -------- Actions -------- */

  /** Select an Amount for the Card */
  public void selectCardAmount(String amount) {
    logger.info(String.format("Selecting a Grind: %s", amount));
    WebHelper.jsSelect(getSelectCardAmountSelectList.getWebElement(), amount);
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
    SdkHelper.getSyncHelper().wait(Until.uiElement(getAddingToCartSpinner).present());
    // TODO: Doesn't seem to be needed anymore, but confirm on tests other than GuestCheckoutTest
    // SdkHelper.getSyncHelper().wait(Until.uiElement(getAddingToCartSpinner).notPresent());
  }
}
