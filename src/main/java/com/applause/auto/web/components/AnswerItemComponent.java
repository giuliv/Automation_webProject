package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.helpers.WebHelper;
import io.qameta.allure.Step;

@Implementation(is = AnswerItemComponent.class, on = Platform.WEB)
@Implementation(is = AnswerItemComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class AnswerItemComponent extends BaseComponent {

  @Locate(xpath = ".//div[contains(@class, 'name')]", on = Platform.WEB)
  private Text name;

  @Locate(xpath = "//div[contains(@class, 'loadingA')]", on = Platform.WEB)
  private Text animation;

  @Step("Get Answer Name")
  public String getName() {
    String text = name.getText();
    logger.info("Answer name - [{}]", text);
    return text;
  }

  @Step("Click on Answer")
  public <T extends BaseComponent> T click(Class<T> clazz) {
    logger.info("Clicking on [{}] Answer", name.getText());
    getParent().click();
    try {
      WebHelper.waitForElementToDisappear(animation, 10);
    } catch (Exception e) {
      logger.info("Animation is still displayed after 10 sec wait");
    }
    return SdkHelper.create(clazz);
  }
}
