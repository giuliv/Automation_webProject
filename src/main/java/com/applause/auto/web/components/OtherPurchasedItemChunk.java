package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.helpers.WebHelper;

@Implementation(is = OtherPurchasedItemChunk.class, on = Platform.WEB)
public class OtherPurchasedItemChunk extends BaseComponent {

  @Locate(xpath = ".//button", on = Platform.WEB)
  private Button addToCartButton;

  @Locate(xpath = ".//h3/a", on = Platform.WEB)
  private Text name;

  /**
   * get Name
   *
   * @return String
   */
  public String getName() {
    String nameString = name.getText();
    logger.info("Item name - [{}]", nameString);
    return WebHelper.cleanString(nameString);
  }

  /**
   * Click on 'Add to cart' button
   *
   * @return QuickViewComponent
   */
  public QuickViewComponent clickAddToCartButton() {
    logger.info("Clicking on 'Add to cart' button");
    if (!SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      WebHelper.hoverByAction(addToCartButton);
    }

    addToCartButton.click();
    return SdkHelper.create(QuickViewComponent.class);
  }
}
