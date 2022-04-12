package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;

import java.time.Duration;

@Implementation(is = TimelinePage.class, on = Platform.WEB)
public class TimelinePage extends BaseComponent {

    @Locate(css = "div.simple-hero.ir.simple-hero__ir", on = Platform.WEB)
    private ContainerElement timelinePageHeader;

    @Override
    public void afterInit() {
        SdkHelper.getSyncHelper()
                .wait(Until.uiElement(timelinePageHeader).visible().setTimeout(Duration.ofSeconds(40)));
    }
}
