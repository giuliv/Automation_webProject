package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = PaypalReviewYourPurchasePage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = PaypalReviewYourPurchasePage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = PaypalReviewYourPurchasePage.class, on = Platform.WEB_MOBILE_PHONE)
public class PaypalReviewYourPurchasePage extends BaseComponent {
	protected static String winHandleBefore = "";

	// Public actions

	/**
	 * Click Agree and Continue
	 *
	 * @return CheckoutPlaceOrderPage
	 */
	public CheckoutPlaceOrderPage clickAgreeAndContinue() {
		logger.info("Clicking Agree and Continue");
		SyncHelper.sleep(45000); // just waiting sandbox to completed
		new WebHelper().jsClick(getContinueButton.getWebElement());
		SyncHelper.sleep(45000); // just waiting sandbox to completed
		getAgreeAndContinueButton.click();
		getDriver().switchTo().window(winHandleBefore);
		SyncHelper.sleep(45000); // just waiting sandbox to completed
		return ComponentFactory.create(CheckoutPlaceOrderPage.class);
	}

	// Protected getters
	@Locate(xpath = "//*[@id=\"paypalLogo\"]", on = Platform.WEB_DESKTOP)
	protected Image getViewSignature;

	@Locate(xpath = "//*[contains(@class,'confirmButton')]", on = Platform.WEB_DESKTOP)
	protected Button getContinueButton;

	@Locate(xpath = "//*[@id='confirmButtonTop' or @class='confirmButton']", on = Platform.WEB_DESKTOP)
	protected Button getAgreeAndContinueButton;
}
