package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;

@Implementation(is = ShopCoffeePage.class, on = Platform.WEB)
public class ShopCoffeePage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = "div.top-banner div.text-content h1", on = Platform.WEB)
  private Text getViewSignature;

  @Locate(
      xpath = "//ul[@class='prod-list']//li[strong[@class='product-name' and contains(.,'%s')]]",
      on = Platform.WEB)
  private Button productNameButton;

  /* -------- Actions -------- */

  /**
   * Taps the sign in button.
   *
   * @return a Coffee Product Page
   */
  public CoffeeProductPage clickProductName(String productName) {
    logger.info(String.format("Tap on Product Name: %s", productName));
    productNameButton.initializeWithFormat(productName);
    productNameButton.click();
    return ComponentFactory.create(CoffeeProductPage.class);
  }
}
