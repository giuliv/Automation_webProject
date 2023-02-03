package com.applause.auto.web.components;

import com.applause.auto.common.data.Constants.Touts;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.helpers.WebHelper;
import io.qameta.allure.Step;

@Implementation(is = ToutsSectionComponent.class, on = Platform.WEB)
@Implementation(is = MobileToutsSectionComponent.class, on = Platform.WEB_MOBILE)
public class ToutsSectionComponent extends BaseComponent {

  @Locate(xpath = "//div[@id='iconTouts']", on = Platform.WEB)
  protected ContainerElement mainContainer;

  @Locate(xpath = "//h3[@class='icon-tout__title' and normalize-space()='%s']", on = Platform.WEB)
  protected Text toutsTitle;

  @Locate(
      xpath = "//h3[@class='icon-tout__title' and normalize-space()='%s']/../p",
      on = Platform.WEB)
  protected Text toutsDescription;

  @Override
  public void afterInit() {
    super.afterInit();
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
    WebHelper.scrollToElement(mainContainer);
  }

  @Step("Verify touts with expected title and description is displayed")
  public boolean isToutDisplayed(Touts touts) {
    String title = touts.getTitle();
    String description = touts.getDescription();
    logger.info(
        "Checking touts with title - [{}] and description - [{}] is displayed", title, description);
    toutsTitle.format(title).initialize();
    if (!WebHelper.isDisplayed(toutsTitle)) {
      logger.info(String.format("Touts with title [%s] isn't displayed", title));
      return false;
    }

    toutsDescription.format(title).initialize();
    if (!toutsDescription.getText().equalsIgnoreCase(description)) {
      logger.info(
          String.format("Touts with title [%s] is displayed with wrong description", title));
      return false;
    }

    return true;
  }
}

class MobileToutsSectionComponent extends ToutsSectionComponent {

  @Locate(
      xpath = "//div[contains(@class,'icon-touts__carousel')]//span[@class='js-index-label']",
      on = Platform.WEB)
  protected Text sequenceNumberText;

  @Locate(
      xpath =
          "//div[contains(@class,'icon-touts__carousel')]//button[contains(@class,'flickity-prev-next-button next')]",
      on = Platform.WEB)
  protected Button carouselNextButton;

  private int getActiveSequenceNumber() {
    sequenceNumberText.initialize();
    return Integer.parseInt(sequenceNumberText.getText().trim());
  }

  @Step("Verify touts with expected title and description is displayed")
  public boolean isToutDisplayed(Touts touts) {
    String title = touts.getTitle();
    String description = touts.getDescription();
    int sequenceNumber = touts.getSequenceNumber();

    if (sequenceNumber > getActiveSequenceNumber()) {
      logger.info("Clicking on the carousel next button");
      carouselNextButton.click();
    }

    logger.info(
        "Checking touts with title - [{}] and description - [{}] is displayed", title, description);
    toutsTitle.format(title).initialize();
    if (!WebHelper.isDisplayed(toutsTitle)) {
      logger.info(String.format("Touts with title [%s] isn't displayed", title));
      return false;
    }

    toutsDescription.format(title).initialize();
    if (!toutsDescription.getText().equalsIgnoreCase(description)) {
      logger.info(
          String.format("Touts with title [%s] is displayed with wrong description", title));
      return false;
    }

    return true;
  }
}
