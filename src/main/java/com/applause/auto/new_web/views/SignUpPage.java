package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import java.time.Duration;

@Implementation(is = SignUpPage.class, on = Platform.WEB)
public class SignUpPage extends BaseComponent {

  @Locate(css = "#page_email_signup", on = Platform.WEB)
  private ContainerElement signupSection;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(signupSection).visible().setTimeout(Duration.ofSeconds(40)));
  }
}
