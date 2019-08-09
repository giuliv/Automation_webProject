package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import java.util.List;
import org.openqa.selenium.WebElement;

@Implementation(is = DatePickerChunk.class, on = Platform.WEB)
public class DatePickerChunk extends BaseComponent {

	/* -------- Elements -------- */

	@Locate(css = "[data-handler='selectDay']", on = Platform.WEB)
	protected List<WebElement> getAvailableDatesText;

	/* -------- Actions -------- */

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
}
