package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.helpers.WebHelper;
import io.qameta.allure.Step;

public class WelcomeBackDialogRecommendedForYouComponent extends BaseComponent {

  @Locate(css = ".welcome-back__category-primary-image img", on = Platform.WEB)
  private Image image;

  @Locate(css = ".welcome-back__copy div", on = Platform.WEB)
  private Text name;

  @Locate(css = "button.js-quick-add", on = Platform.WEB)
  private Button addButton;

  @Override
  public void afterInit() {
    super.afterInit();
    WebHelper.waitForElementToAppear(name, 30);
  }

  public boolean isSectionContainsAllDetails() {
    if (!WebHelper.isDisplayed(image)) {
      logger.error("Image is not displayed");
      return false;
    }

    if (!WebHelper.isDisplayed(name) || name.getText().isEmpty()) {
      logger.error("Name is not displayed or is empty");
      return false;
    }

    if (!WebHelper.isDisplayed(addButton)) {
      logger.error("Add button is not displayed");
      return false;
    }

    return true;
  }

  @Step("Return Name")
  public String getName() {
    String nameText = name.getText().trim();
    logger.info("Recommended for you item name [{}]", nameText);
    return nameText;
  }

  @Step("Click on the Add button")
  public QuickViewComponent clickAdd() {
    logger.info("Clicking on the add button for the [{}]", getName());
    addButton.click();
    return SdkHelper.create(QuickViewComponent.class);
  }
}
