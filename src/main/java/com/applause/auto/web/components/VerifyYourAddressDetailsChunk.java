package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.UIData;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.CheckoutShippingInfoPage;
import java.lang.invoke.MethodHandles;

@Implementation(is = VerifyYourAddressDetailsChunk.class, on = Platform.WEB_DESKTOP)
@Implementation(is = VerifyYourAddressDetailsChunk.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = VerifyYourAddressDetailsChunk.class, on = Platform.WEB_MOBILE_PHONE)
public class VerifyYourAddressDetailsChunk extends BaseComponent {

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public VerifyYourAddressDetailsChunk(UIData parent, String selector) {
		super(parent, selector);
	}

	/*
	 * Public actions
	 */

	/**
	 * Click Use Entered Address button
	 *
	 * @return CheckoutShippingInfoPage
	 */
	public CheckoutShippingInfoPage clickEnteredAddressButton() {
		logger.info("Clicking Use Entered Address Button");
		getUseEnteredAddressButton.click();
		return ComponentFactory.create(CheckoutShippingInfoPage.class);
	}

	/*
	 * Protected Getters
	 */
	@Locate(jQuery = ".verification-form", on = Platform.WEB_DESKTOP)
	protected Text getViewSignature;

	@Locate(jQuery = "button[title='Use Address As Entered *']", on = Platform.WEB_DESKTOP)
	protected Button getUseEnteredAddressButton;
}
