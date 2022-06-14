package com.applause.auto.new_web.components;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.enums.Attribute;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

public class ReviewItemComponent extends BaseComponent {

  @Locate(css = "abbr.bv-rating-max", on = Platform.WEB)
  private Text ratingText;

  @Locate(css = ".bv-author-avatar div.bv-content-author-name", on = Platform.WEB)
  private Text personNameText;

  @Locate(css = "div.bv-author-location span", on = Platform.WEB)
  private Text locationText;

  @Locate(css = "span.bv-content-datetime-stamp", on = Platform.WEB)
  private Text dateText;

  @Locate(css = "div.bv-content-feedback-vote-request", on = Platform.WEB)
  private Text helpfulText;

  @Locate(css = "span.bv-content-data-icon", on = Platform.WEB)
  private Text yesNoVoteIcon;

  @Locate(css = "button.bv-content-report-btn", on = Platform.WEB)
  private Text reportButton;

  @Step("Get rating")
  public int getRating() {
    return Integer.parseInt(
        ratingText.getAttributeValue(Attribute.TITLE.getValue()).split(" ")[0].trim());
  }

  @Step("Verify rating is displayed")
  public boolean isRatingDisplayed() {
    return WebHelper.isDisplayed(ratingText);
  }

  @Step("Verify person name is displayed")
  public boolean isPersonNameDisplayed() {
    return WebHelper.isDisplayed(personNameText);
  }

  @Step("Verify location is displayed")
  public boolean isLocationDisplayed() {
    return WebHelper.isDisplayed(locationText);
  }

  @Step("Verify date is displayed")
  public boolean isDateDisplayed() {
    return WebHelper.isDisplayed(dateText);
  }

  @Step("Verify Helpful? is displayed")
  public boolean isHelpfulDisplayed() {
    return WebHelper.isDisplayed(dateText);
  }

  @Step("Verify Yes/No is displayed")
  public boolean isYesNoVoteDisplayed() {
    return WebHelper.isDisplayed(yesNoVoteIcon);
  }

  @Step("Verify Report button is displayed")
  public boolean isReportButtonDisplayed() {
    return WebHelper.isDisplayed(reportButton);
  }

  @Step("Click 'Report' button")
  public void clickReportButton() {
    logger.info("Clicking on the 'Report' button");
    if (isReportedButtonDisplayed()) {
      logger.info("Review is already reported");
      return;
    }

    WebHelper.jsClick(reportButton.getWebElement());
    SdkHelper.getSyncHelper()
        .waitUntil(button -> reportButton.getText().trim().equals(TestData.REPORTED_BUTTON));
  }

  @Step("Verify 'Reported' button is displayed")
  public boolean isReportedButtonDisplayed() {
    return reportButton.getText().trim().equals(TestData.REPORTED_BUTTON);
  }
}
