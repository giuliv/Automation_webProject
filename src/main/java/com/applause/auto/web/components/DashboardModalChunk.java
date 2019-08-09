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
import com.applause.auto.web.views.DashboardPage;
import java.lang.invoke.MethodHandles;

@Implementation(is = DashboardModalChunk.class, on = Platform.WEB_DESKTOP)
@Implementation(is = DashboardModalChunk.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = DashboardModalChunk.class, on = Platform.WEB_MOBILE_PHONE)
public class DashboardModalChunk extends BaseComponent {

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public DashboardModalChunk(UIData parent, String selector) {
		super(parent, selector);
	}

	/*
	 * Public actions
	 */

	/**
	 * Click Explore Dashboard Button
	 *
	 * @return DashboardPage
	 */
	public DashboardPage clickExploreDashboard() {
		logger.info("Clicking Explore Dashboard Button");
		getExploreDashboardButton.click();
		return ComponentFactory.create(DashboardPage.class);
	}

	/**
	 * Click Close Button on explore-dashboard-modal
	 *
	 * @return DashboardPage
	 */
	public DashboardPage clickCloseModal() {
		logger.info("Clicking Close button on Dashboard Modal");
		getCloseDashboardModalButton.click();
		return ComponentFactory.create(DashboardPage.class);
	}

	/*
	 * Protected Getters
	 */

	@Locate(jQuery = "div#modal-new-message-2018 a", on = Platform.WEB_DESKTOP)
	protected Text getViewSignature;

	@Locate(jQuery = "div#modal-new-message-2018 a", on = Platform.WEB_DESKTOP)
	protected Button getExploreDashboardButton;

	@Locate(jQuery = "#modal-new-message-2018 .close-button", on = Platform.WEB_DESKTOP)
	protected Button getCloseDashboardModalButton;

}
