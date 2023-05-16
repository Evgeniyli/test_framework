package com.ui.model.pages;

import com.codeborne.selenide.SelenideElement;
import com.ui.core.exceptions.ExceptionError;
import com.ui.core.report.TestReporter;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public abstract class BasePage<PAGE extends BasePage<PAGE>> {
    private String pageURL;
    private final SelenideElement pageLoadingElement;

    protected BasePage(String pageURL, By pageLoadingElement) {
        this(pageLoadingElement);
        this.pageURL = pageURL;
    }

    protected BasePage(By pageLoadingElement) {
        this.pageLoadingElement = $(pageLoadingElement);
    }

    public PAGE openWindow() {
        if (StringUtils.isNotEmpty(pageURL)) {
            open(pageURL);
            TestReporter.reportDebugStep("%s was opened", this.getClass().getSimpleName());
        } else {
            throw new ExceptionError("Page URL not establish");
        }

        return (PAGE) this;
    }

    /**
     * Wait page loading.
     * Loading locator use from constructor <b>pageLoadingLocator</b> argument.
     *
     * @param time     wait time
     * @param timeUnit unit of time
     * @return current page instance
     */
    public PAGE waitPageLoading(long time, TimeUnit timeUnit) {
        TestReporter.reportDebugStep("Wait %s loading", this.getClass().getSimpleName());
        pageLoadingElement.waitUntil(visible, timeUnit.toMillis(time));
        return (PAGE) this;
    }
}
