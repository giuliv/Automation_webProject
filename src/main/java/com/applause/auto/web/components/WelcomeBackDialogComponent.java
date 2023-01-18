package com.applause.auto.web.components;

import com.applause.auto.common.data.enums.Attribute;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.helpers.WebHelper;
import io.qameta.allure.Step;

public class WelcomeBackDialogComponent extends BaseComponent {

  @Locate(id = "welcomeBackDialog", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = ".welcome-back__welcome-text", on = Platform.WEB)
  private Text dialogTitle;

  @Locate(css = ".welcome-back__welcome-text", on = Platform.WEB)
  private Text recommendedForYou;

  @Locate(id = "personalRecommendationsClose", on = Platform.WEB)
  private Button closeButton;

  @Locate(id = "welcome-back-recommendation", on = Platform.WEB)
  private WelcomeBackDialogRecommendedForYouComponent firstRecommendedForYouSection;

  @Locate(id = "welcome-back-recommendation-secondary", on = Platform.WEB)
  private WelcomeBackDialogRecommendedForYouComponent secondRecommendedForYouSection;

  @Step("Verify Welcome Back Dialog component is displayed")
  public boolean isDisplayed() {
    return WebHelper.isDisplayed(mainContainer, 30)
        && mainContainer.getAttributeValue(Attribute.CLASS.getValue()).contains("is-open");
  }

  @Step("Return section title")
  public String getTitle() {
    String dialogTitleText = WebHelper.cleanString(dialogTitle.getText().trim());
    logger.info("Dialog title [{}]", dialogTitleText);
    return dialogTitleText;
  }

  @Step("Verify two Recommended for you sections")
  public boolean areTwoRecommendedForYouSectionsDisplayed() {
    if (!WebHelper.isDisplayed(firstRecommendedForYouSection.getParent())) {
      logger.error("First Recommended for you section is not displayed");
      return false;
    }

    if (!WebHelper.isDisplayed(secondRecommendedForYouSection.getParent())) {
      logger.error("Second Recommended for you section is not displayed");
      return false;
    }

    return true;
  }

  @Step("Get first Recommended for you component")
  public WelcomeBackDialogRecommendedForYouComponent getFirstRecommendedForYouSection() {
    return firstRecommendedForYouSection;
  }

  @Step("Get second Recommended for you component")
  public WelcomeBackDialogRecommendedForYouComponent getSecondRecommendedForYouSection() {
    return secondRecommendedForYouSection;
  }

  @Step("Click close button")
  public void close() {
    logger.info("Clicking on the close button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(closeButton).present());
    closeButton.click();
  }
}
