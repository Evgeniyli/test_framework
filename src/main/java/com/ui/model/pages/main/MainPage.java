package com.ui.model.pages.main;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.ui.core.report.TestReporter;
import com.ui.core.utils.WaitingUtils;
import com.ui.model.pages.BasePage;
import com.ui.model.pages.schedule.CalendarPage;
import com.ui.model.pages.schedule.SchedulePage;
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.ui.model.providers.DataProviders.getValue;

public class MainPage extends BasePage<MainPage> {
    private static final String PAGE_NAME = "main_page";
    private static final By CUSTOMERS_TITTLE_ELEMENT = Selectors.byText(getValue(PAGE_NAME, "customer_tittle_element"));
    private final SelenideElement appointmentLinkWrapper = $(Selectors.byXpath(getValue(PAGE_NAME, "appointment_link_wrapper")));
    private final SelenideElement calendarItem = $(Selectors.byXpath(getValue(PAGE_NAME, "calendar_item")));
    private final SelenideElement scheduleItem = $(Selectors.byXpath(getValue(PAGE_NAME, "schedule_item")));

    public MainPage() {
        super(CUSTOMERS_TITTLE_ELEMENT);
    }

    /**
     * Open schedule page
     *
     * @return Schedule page instance
     */
    public SchedulePage openSchedulePage() {
        openAppointmentWrapperIfDoNotOpen(scheduleItem);
        scheduleItem.shouldBe(Condition.visible).click();
        TestReporter.reportDebugStep("Appointment wrapper was opened ");
        return new SchedulePage();
    }

    /**
     * Open Calendar page
     *
     * @return Schedule page instance
     */
    public CalendarPage openCalendarPage() {
        openAppointmentWrapperIfDoNotOpen(calendarItem);
        calendarItem.shouldBe(Condition.visible).click();
        TestReporter.reportDebugStep("Appointment wrapper was opened ");
        return new CalendarPage();
    }

    private void openAppointmentWrapperIfDoNotOpen(SelenideElement appointment) {
        if (!WaitingUtils.isSelenideElement(Condition.visible, appointment, 5, TimeUnit.SECONDS)) {
            executeJavaScript("arguments[0].click()", appointmentLinkWrapper);
            TestReporter.reportDebugStep("Appointment wrapper was opened");
        } else {
            TestReporter.reportDebugStep("Appointment wrapper has been already opened");
        }
    }
}
