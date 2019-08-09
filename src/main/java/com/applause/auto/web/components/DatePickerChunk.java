package com.applause.auto.web.components;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.applause.auto.framework.pageframework.UIData;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.AbstractPageChunk;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.web.helpers.WebHelper;

@WebDesktopImplementation(DatePickerChunk.class)
@WebTabletImplementation(DatePickerChunk.class)
@WebPhoneImplementation(DatePickerChunk.class)
public class DatePickerChunk extends AbstractPageChunk {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public DatePickerChunk(UIData parent, String selector) {
		super(parent, selector);
	}

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
	}

	/**
	 * Select date t.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param clazz
	 *            the clazz
	 * @param index
	 *            the index
	 * @return the t
	 */
	public <T extends AbstractPage> T selectDate(Class<T> clazz, int index) {
		LOGGER.info("Clicking available day by index: " + index);
		getAvailableDatesText().get(index).click();
		return PageFactory.create(clazz);
	}

	/*
	 * Public actions
	 */

	@WebElementLocator(webDesktop = "[data-handler='selectDay']")
	protected List<WebElement> getAvailableDatesText() {
		return queryHelper.findElementsByExtendedCss(getLocator(this, "getAvailableDatesText"));
	}

}
