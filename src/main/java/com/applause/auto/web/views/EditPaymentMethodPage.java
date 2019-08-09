package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = EditPaymentMethodPage.class, on = Platform.WEB)
public class EditPaymentMethodPage extends BaseComponent {

	/* -------- Elements -------- */

	/* -------- Actions -------- */

	WebHelper webHelper = new WebHelper();

	// Public actions

	/**
	 * Enter Name on Card
	 *
	 * @param name
	 * @return String
	 */
	public String enterNameOnCard(String name) {
		logger.info("Entering Name on Card");
		name = webHelper.getTimestamp(name);
		getNameOnCardField.sendKeys(name);
		return name;
	}

	/**
	 * Click Save Payment Method Button
	 *
	 * @return PaymentMethodsPage
	 */
	public PaymentMethodsPage clickSavePaymentMethod() {
		logger.info("Clicking Save Payment Method");
		getSavePaymentMethodButton.click();
		// wait for animation
		SyncHelper.sleep(2000);
		return ComponentFactory.create(PaymentMethodsPage.class);
	}

	// Protected getters
	@Locate(css = "div.main-container.col2-left-layout > div > div.col-main > div > div.page-title > h1", on = Platform.WEB)
	protected Text getViewSignature;

	@Locate(css = "#NameOnCard", on = Platform.WEB)
	protected TextBox getNameOnCardField;

	@Locate(css = "#send2", on = Platform.WEB)
	protected Button getSavePaymentMethodButton;

}
