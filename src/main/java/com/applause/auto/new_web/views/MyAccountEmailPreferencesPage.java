package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Implementation(is = MyAccountEmailPreferencesPage.class, on = Platform.WEB)
public class MyAccountEmailPreferencesPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = ".form-list>.form-item input[type=checkbox]", on = Platform.WEB)
  private List<Button> getCheckboxesList;

  @Locate(id = "emailPreferencesSubmit", on = Platform.WEB)
  private Link getSubmitButton;

  @Locate(id = "emailPreferencesUnsubscribe", on = Platform.WEB)
  private Link getUnSubscribeButton;

  @Locate(css = ".form-messages > li", on = Platform.WEB)
  private Text getMessageText;

  /* -------- Actions -------- */

  /**
   * Select random checkboxes
   *
   * @param selectCount
   */
  @Step("Select random checkboxes")
  public void selectRandomCheckboxes(int selectCount) {
    IntStream.range(0, selectCount)
        .forEach(
            checkbox -> {
              int index = new Random().nextInt((getCheckboxesList.size() - 1));
              logger.info("Selecting checkbox with index [{}]", index);
              SdkHelper.getBrowserControl().jsClick(getCheckboxesList.get(index));
              SdkHelper.getSyncHelper().sleep(1000); // Wait for action
            });
  }

  @Step("Click Submit button")
  public MyAccountEmailPreferencesPage clickSubmitButton() {
    logger.info("Clicking Submit button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(getSubmitButton).visible());
    getSubmitButton.click();
    SdkHelper.getSyncHelper().sleep(1000); // wait for action
    return SdkHelper.create(MyAccountEmailPreferencesPage.class);
  }

  @Step("Click Unsubscribe button")
  public MyAccountEmailPreferencesPage clickUnsubscribeButton() {
    logger.info("Clicking Unsubscribe button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(getUnSubscribeButton).visible());
    getUnSubscribeButton.click();
    SdkHelper.getSyncHelper().sleep(1000); // wait for action
    return SdkHelper.create(MyAccountEmailPreferencesPage.class);
  }

  @Step("Verify Message is displayed properly")
  public boolean messageIsDisplayed(String expectedMessage) {
    logger.info("Checking message [{}] is displayed", expectedMessage);
    SdkHelper.getSyncHelper().wait(Until.uiElement(getMessageText).present());
    String actualMessage = getMessageText.getText().trim();
    if (!actualMessage.equals(expectedMessage)) {
      logger.error(
          "Message isn't matched with expected. Expected [{}]. Actual [{}].",
          expectedMessage,
          actualMessage);
      return false;
    }

    return true;
  }
}
