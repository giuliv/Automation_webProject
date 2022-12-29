package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import java.time.Duration;

@Implementation(is = NotifyMeComponent.class, on = Platform.WEB)
@Implementation(is = NotifyMeComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class NotifyMeComponent extends BaseComponent {

  @Locate(id = "notifyModalContent", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "#notifyModalContent h2", on = Platform.WEB)
  private Text thanksMessage;

  @Locate(css = "#notifyModal button.modal__close", on = Platform.WEB)
  private Text closeButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContainer).present().setTimeout(Duration.ofSeconds(40)));
  }

  public boolean isModalDisplayed() {
    return mainContainer.isDisplayed();
  }

  public String getSuccessMessage() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(thanksMessage).visible());
    logger.info("Success message: " + thanksMessage.getText());
    return thanksMessage.getText();
  }

  public void closeModal() {
    logger.info("Closing modal...");
    closeButton.click();
    SdkHelper.getSyncHelper().wait(Until.uiElement(closeButton).notVisible());
  }
}
