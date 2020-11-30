package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;

@Implementation(is = ShopGiftSubscriptionsPage.class, on = Platform.WEB)
public class ShopGiftSubscriptionsPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      css =
          "body > div.wrapper > div > div.main-container.full-width > div > div > div.top-banner.wide-content > div.text-holder > div > h1",
      on = Platform.WEB)
  private Text getViewSignature;

  @Locate(
      css = "#product_addtocart_form10995 > div.image-holder > div > a > picture > img",
      on = Platform.WEB)
  private Button getFirstProduct;

  /* -------- Actions -------- */

  /**
   * Click First Product
   *
   * @return CoffeeProductDescriptionPage
   */
  public CoffeeProductDescriptionPage clickFirstProduct() {
    logger.info("Clicking First Product");
    getFirstProduct.click();
    return this.create(CoffeeProductDescriptionPage.class);
  }
}
