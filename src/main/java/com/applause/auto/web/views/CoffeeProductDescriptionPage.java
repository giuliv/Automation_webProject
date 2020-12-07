package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.integrations.helpers.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;



import com.applause.auto.web.components.MiniCartContainerChunk;
import com.applause.auto.web.helpers.WebHelper;

@Implementation(is = CoffeeProductDescriptionPage.class, on = Platform.WEB)
public class CoffeeProductDescriptionPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = ".button.btn-cart.btn-dark", on = Platform.WEB)
  private Button getAddToCartButton;

  @Locate(css = "#attribute198", on = Platform.WEB)
  private SelectList getGrindSelectList;

  /* -------- Actions -------- */

  /**
   * Select Grind Option
   *
   * @param grind
   */
  public void selectGrind(String grind) {
    logger.info("Select Grind");
    if (SdkHelper.getEnvironmentHelper().isSafari())
      WebHelper.jsSelect(getGrindSelectList.getWebElement(), grind);
    else getGrindSelectList.select(grind);
  }

  /**
   * Click Add To Cart
   *
   * @return MiniCartContainerChunk
   */
  public MiniCartContainerChunk addToCart() {
    logger.info("Clicking Add To Cart");
    getSyncHelper().sleep(5000);
    getAddToCartButton.click();
    return this.create(MiniCartContainerChunk.class);
  }
}
