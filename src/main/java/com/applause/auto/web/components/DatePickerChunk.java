package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.UIData;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;
import java.util.List;
import org.openqa.selenium.WebElement;

@Implementation(is = DatePickerChunk.class, on = Platform.WEB_DESKTOP)
@Implementation(is = DatePickerChunk.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = DatePickerChunk.class, on = Platform.WEB_MOBILE_PHONE)
public class DatePickerChunk extends BaseComponent {

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public DatePickerChunk(UIData parent, String selector) {
		super(parent, selector);
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
	public <T extends BaseComponent> T selectDate(Class<T> clazz, int index) {
		logger.info("Clicking available day by index: " + index);
		getAvailableDatesText.get(index).click();
		return ComponentFactory.create(clazz);
	}

	/*
	 * Public actions
	 */

	@Locate(jQuery = "[data-handler='selectDay']", on = Platform.WEB_DESKTOP)
	protected List<WebElement> getAvailableDatesText;

}
