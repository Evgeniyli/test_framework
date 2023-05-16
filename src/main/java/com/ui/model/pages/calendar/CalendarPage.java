package com.ui.model.pages.schedule;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.ui.core.report.TestReporter;
import com.ui.core.utils.WaitingUtils;
import com.ui.model.pages.apointments_wrapper.Appointments;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.$;
import static com.ui.model.providers.DataProviders.getValue;

public class CalendarPage extends Appointments<CalendarPage> {

    private static final String PAGE_NAME = "calendar_page";
    private final String appointmentITem = getValue(PAGE_NAME, "appointment_calendar");
    private final SelenideElement dayTab = $(Selectors.byText(getValue(PAGE_NAME, "day_tab")));


    public CalendarPage clickDayItem() {
        dayTab.shouldBe(Condition.visible).click();
        TestReporter.reportDebugStep("Day time was clicked");
        return this;
    }

    public boolean isAppointmentExist(String appointmentTittle) {
        SelenideElement appointmentItemElement = $(Selectors.byXpath(String.format(appointmentITem, appointmentTittle)));
        return WaitingUtils.isSelenideElement(Condition.visible, appointmentItemElement, 25, TimeUnit.SECONDS);
    }
}
