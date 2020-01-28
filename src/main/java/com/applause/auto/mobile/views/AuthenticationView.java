package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = AuthenticationView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = AuthenticationView.class, on = Platform.MOBILE_IOS)
public class AuthenticationView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "//XCUIElementTypeButton[@name='Create Account']", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/signUp", on = Platform.MOBILE_ANDROID)
  protected Button getCreateAccountButton;

  @Locate(id = "Peetnik Rewards", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/rewardTitle", on = Platform.MOBILE_ANDROID)
  protected Text getRewardTitleText;

  /* -------- Actions -------- */

  /**
   * Get the text value of the reward title
   *
   * @return
   */
  public String getRewardTitleTextValue() {
    return getRewardTitleText.getText();
  }

  /**
   * Is user signed out boolean.
   *
   * @return the boolean
   */
  public boolean isUserSignedOut() {
    logger.info("Checking if user signed out");
    return getCreateAccountButton.exists();
  }
}
