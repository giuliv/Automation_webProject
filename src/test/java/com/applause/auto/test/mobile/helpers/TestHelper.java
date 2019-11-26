package com.applause.auto.test.mobile.helpers;

import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.components.AccountMenuMobileChunk;
import com.applause.auto.mobile.views.CreditCardDetailsView;
import com.applause.auto.mobile.views.DashboardView;
import com.applause.auto.mobile.views.LandingView;
import com.applause.auto.mobile.views.PaymentMethodsView;
import com.applause.auto.mobile.views.SignInView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;

@Implementation(is = TestHelper.class, on = Platform.MOBILE)
public class TestHelper extends BaseComponent {
  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  /**
   * Sign in dashboard view.
   *
   * @param landingView the landing view
   * @return the dashboard view
   */
  public DashboardView signIn(LandingView landingView) {
    return signIn(
        landingView, MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD, DashboardView.class);
  }

  /**
   * Sign in t.
   *
   * @param <T> the type parameter
   * @param landingView the landing view
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T signIn(
      LandingView landingView, String username, String password, Class<T> clazz) {

    landingView.skipOnboarding();

    logger.info("Tap Sign In");
    SignInView signInView = landingView.signIn();

    logger.info("Tap on Email Address field and enter valid email address");
    signInView.setUsername(username);

    logger.info("Enter valid password");
    signInView.setPassword(password);

    logger.info("Tap Sign In button");
    return signInView.signIn(clazz);
  }

  public PaymentMethodsView deletePaymentMethodTestCardIfAdded(
      PaymentMethodsView paymentMethodsView) {
    if (paymentMethodsView.isPaymentMethodTestCardAdded()) {
      logger.info("Deleting previously added payment test card");
      CreditCardDetailsView creditCardDetailsView =
          paymentMethodsView.clickSavedPaymentMethod(CreditCardDetailsView.class);
      creditCardDetailsView.clickDeleteCard();
      creditCardDetailsView.clickDeleteYes();

      // need this workaround because payment card doesn't disappear from the view without
      // refreshing it
      SyncHelper.sleep(3000);
      paymentMethodsView.clickBackButton();
      ComponentFactory.create(AccountMenuMobileChunk.class).clickPaymentMethods();
      SyncHelper.waitUntil(condition -> !paymentMethodsView.isPaymentMethodTestCardAdded());
    } else {
      logger.info("There is no test payment card added");
    }
    return ComponentFactory.create(PaymentMethodsView.class);
  }
}
