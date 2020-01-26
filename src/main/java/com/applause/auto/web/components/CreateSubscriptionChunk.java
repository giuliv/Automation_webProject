package com.applause.auto.web.components;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.helper.EnvironmentHelper;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;
import com.applause.auto.web.helpers.WebHelper;
import java.time.Duration;

@Implementation(is = CreateSubscriptionChunk.class, on = Platform.WEB)
public class CreateSubscriptionChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = "#subscription_name", on = Platform.WEB)
  private TextBox getNewSubscriptionNameTextBox;

  @Locate(css = "li#subscription-order-new label", on = Platform.WEB)
  private Button getNewSubscriptionButton;

  @Locate(
      css = "#subscription-order-subscription-new > div.subscription-actions > a > span > span",
      on = Platform.WEB)
  private Button getNewSubscriptionCreateButton;

  @Locate(css = "#subscription_period", on = Platform.WEB)
  private SelectList getNewSubscriptionFrequencySelectList;

  /* -------- Actions -------- */

  public void selectNewSubscription() {
    logger.info("Select New Subscription");

    // TODO: DO we really need an absolute selector here?
    // SyncHelper.waitUntilElementPresent(getNewSubscriptionButton.getAbsoluteSelector());
    // WebHelper.waitForElementToBeClickable(getNewSubscriptionButton.getWebElement());
    // getNewSubscriptionButton.click();
    SyncHelper.wait(
            Until.uiElement(getNewSubscriptionButton)
                .clickable()
                .setTimeout(Duration.ofSeconds(60)))
        .click();
  }

  /*
   * Public actions
   */

  /**
   * Sets new subscription name.
   *
   * @param subscriptionName the subscription name
   */
  public void setNewSubscriptionName(String subscriptionName) {
    logger.info("Set subscription name: " + subscriptionName);
    getNewSubscriptionNameTextBox.sendKeys(subscriptionName);
  }

  /**
   * Select frequency.
   *
   * @param frequency the frequency
   */
  public void selectFrequency(Constants.SubscriptionTerm frequency) {
    logger.info("Set frequency: " + frequency);
    if (EnvironmentHelper.isSafari(DriverManager.getDriver())) {

      WebHelper.jsSelect(
          getNewSubscriptionFrequencySelectList.getWebElement(), frequency.miniCartSpell);
    } else {
      getNewSubscriptionFrequencySelectList.select(frequency.miniCartSpell);
    }
  }

  /**
   * Create subscription mini cart container chunk.
   *
   * @return the mini cart container chunk
   */
  public MiniCartContainerChunk createSubscription() {
    logger.info("Create subscription");
    getNewSubscriptionCreateButton.click();
    return ComponentFactory.create(MiniCartContainerChunk.class);
  }
}
