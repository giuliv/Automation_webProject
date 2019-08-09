package com.applause.auto.web.components;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.UIData;
import com.applause.auto.framework.pageframework.util.webDrivers.BrowserType;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = CreateSubscriptionChunk.class, on = Platform.WEB_DESKTOP)
@Implementation(is = CreateSubscriptionChunk.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = CreateSubscriptionChunk.class, on = Platform.WEB_MOBILE_PHONE)
public class CreateSubscriptionChunk extends BaseComponent {

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public CreateSubscriptionChunk(UIData parent, String selector) {
		super(parent, selector);
	}

	public void selectNewSubscription() {
		logger.info("Select New Subscription");
		SyncHelper.waitUntilElementPresent(getNewSubscriptionButton.getAbsoluteSelector());
		WebHelper.waitForElementToBeClickable(getNewSubscriptionButton.getWebElement());
		getNewSubscriptionButton.click();
	}

	/*
	 * Public actions
	 */

	/**
	 * Sets new subscription name.
	 *
	 * @param subscriptionName
	 *            the subscription name
	 */
	public void setNewSubscriptionName(String subscriptionName) {
		logger.info("Set subscription name: " + subscriptionName);
		getNewSubscriptionNameTextBox.sendKeys(subscriptionName);
	}

	/**
	 * Select frequency.
	 *
	 * @param frequency
	 *            the frequency
	 */
	public void selectFrequency(Constants.SubscriptionTerm frequency) {
		logger.info("Set frequency: " + frequency);
		if (env.getBrowserType() == BrowserType.SAFARI) {
			WebHelper webHelper = new WebHelper();
			webHelper.jsSelect(getNewSubscriptionFrequencySelectList.getWebElement(), frequency.miniCartSpell);
		} else {
			getNewSubscriptionFrequencySelectList.select(frequency.miniCartSpell);
		}
	}

	/**
	 * Create subscription mini cart container chunk.
	 *
	 * @return the mini cart container chunk
	 */
	public MiniCartContainerChunk createSubscription() {
		logger.info("Create subscription");
		getNewSubscriptionCreateButton.click();
		return ComponentFactory.create(MiniCartContainerChunk.class, this, "");
	}

	@Locate(jQuery = "#subscription_name", on = Platform.WEB_DESKTOP)
	protected TextBox getNewSubscriptionNameTextBox;

	@Locate(jQuery = "li#subscription-order-new label", on = Platform.WEB_DESKTOP)
	protected Button getNewSubscriptionButton;

	@Locate(jQuery = "[title='Create subscription']", on = Platform.WEB_DESKTOP)
	protected Button getNewSubscriptionCreateButton;

	@Locate(jQuery = "#subscription_period", on = Platform.WEB_DESKTOP)
	protected SelectList getNewSubscriptionFrequencySelectList;

}
