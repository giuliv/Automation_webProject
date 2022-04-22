package com.applause.auto.new_web.components.plp;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriverException;

@Implementation(is = PlpItemComponent.class, on = Platform.WEB)
@Implementation(is = PlpItemComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class PlpItemComponent extends BaseComponent {

  @Locate(xpath = ".//a[contains(@href, '/products')]/div[1]", on = Platform.WEB)
  private Button clickElement;

  @Locate(xpath = ".//h3/a", on = Platform.WEB)
  private Text productName;

  @Locate(xpath = ".//p[contains(@class, 'price')]", on = Platform.WEB)
  private Text productPrice;

  @Step("Click On Product")
  public ProductDetailsPage clickOnProduct() {
    WebHelper.scrollToElement(clickElement);

    SdkHelper.getSyncHelper().wait(Until.uiElement(clickElement).visible());
    WebHelper.scrollToElement(clickElement);

    if (WebHelper.isSafari() && !WebHelper.isMobile()) {
      logger.info("Safari Desktop");
      WebHelper.jsClick(clickElement.getWebElement());
    } else {
      // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
      try {
        clickElement.click();
      } catch (WebDriverException e) {
        logger.info("Frame detached issue seen");
      }
    }

    return SdkHelper.create(ProductDetailsPage.class);
  }

  @Step("Get Product Name")
  public String getProductName() {
    String name = WebHelper.cleanString(productName.getText());
    logger.info("Product name [{}]", name);
    return name;
  }

  @Step("Get Product Price")
  public String getProductPrice() {
    String price = WebHelper.cleanString(productPrice.getText());
    logger.info("Product price [{}]", price);
    return price;
  }
}
