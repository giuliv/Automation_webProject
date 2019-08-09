package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;
import com.applause.auto.web.components.CreateSubscriptionChunk;
import com.applause.auto.web.components.MiniCartContainerChunk;
import com.applause.auto.web.helpers.WebHelper;

@Implementation(is = CoffeeProductPage.class, on = Platform.WEB)
public class CoffeeProductPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = ".product-shop-holder .add-to-cart button", on = Platform.WEB)
  private Button getAddToSubscriptionCart;

  @Locate(css = ".product-shop-holder .add-to-cart button", on = Platform.WEB)
  private Button getAddToCartButton;

  @Locate(css = "label[for='option-subscription']", on = Platform.WEB)
  private Button getSubscriptionButton;

  @Locate(css = "#shopping-cart-please-wait", on = Platform.WEB)
  private ContainerElement getAddingToCartSpinner;

  @Locate(css = "#attribute198", on = Platform.WEB)
  private SelectList getSelectGrindSelectList;

  /* -------- Actions -------- */

  /** Select a Grind */
  public void selectAGrind(String grind) {
    logger.info(String.format("Selecting a Grind: %s", grind));
    WebHelper.jsSelect(getSelectGrindSelectList.getWebElement(), grind);
  }

  /**
   * Click Add to Cart Button
   *
   * @return MiniCartContainerChunk
   */
  public MiniCartContainerChunk clickAddToCart() {
    logger.info("Tap on Shop Coffee Button");
    SyncHelper.wait(Until.uiElement(getAddToCartButton).clickable()).click();
    waitForAddingToCartSpinner();
    return ComponentFactory.create(MiniCartContainerChunk.class);
  }

  public CreateSubscriptionChunk clickAddToSubscription() {
    logger.info("Tap on Add To Subscription Button");
    SyncHelper.wait(Until.uiElement(getAddToSubscriptionCart).clickable()).click();
    waitForAddingToCartSpinner();
    return ComponentFactory.create(CreateSubscriptionChunk.class);
  }

  /** Click Add to Cart Button */
  public void waitForAddingToCartSpinner() {
    logger.info("Adding item to Shopping Cart...");
    SyncHelper.wait(Until.uiElement(getAddingToCartSpinner).present());
    SyncHelper.wait(Until.uiElement(getAddingToCartSpinner).notPresent());
  }

  /**
   * Navigate back t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T navigateBack(Class<T> clazz) {
    logger.info("Navigate back");
    DriverManager.getDriver().navigate().back();
    return ComponentFactory.create(clazz);
  }

  public void selectSubscription() {
    logger.info("Click on subscription");
    getSubscriptionButton.click();
  }
}
