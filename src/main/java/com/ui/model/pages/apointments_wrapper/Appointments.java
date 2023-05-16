package com.ui.model.pages.apointments_wrapper;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.ui.core.report.TestReporter;
import com.ui.model.entity.registration.Appointment;
import com.ui.model.pages.BasePage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.ui.model.providers.DataProviders.getValue;

public abstract class Appointments<PAGE extends Appointments<PAGE>> extends BasePage<PAGE> {

    private static final String PAGE_NAME = "appointments";
    private final SelenideElement searchAppointment = $(Selectors.byXpath(getValue(PAGE_NAME, "search_appointment")));
    private static final By SCHEDULE_TITTLE_ELEMENT = Selectors.byText(getValue(PAGE_NAME, "new_appointment_button"));
    private final SelenideElement appointmentField = $(Selectors.byXpath(getValue(PAGE_NAME, "appointment_field")));
    private final SelenideElement appointmentDateField = $(Selectors.byXpath(getValue(PAGE_NAME, "appointment_date_field")));
    private final SelenideElement createAppointmentButton = $(Selectors.byText(getValue(PAGE_NAME, "create_appointment_button")));


    protected Appointments() {
        super(SCHEDULE_TITTLE_ELEMENT);
    }

    public PAGE searchAppointment(String appointmentTittle) {
        searchAppointment.shouldBe(Condition.visible).val(appointmentTittle);
        TestReporter.reportDebugStep("%s - appointment form was set in search ", appointmentTittle);
        return (PAGE) this;
    }

    /**
     * Open Appointment Form
     *
     * @return appointment instance
     */
    public PAGE openAppointmentForm() {
        $(SCHEDULE_TITTLE_ELEMENT).shouldBe(Condition.visible).click();
        TestReporter.reportDebugStep("Appointment form was opened ");
        return (PAGE) this;
    }

    /**
     * Create Appointment Form
     *
     * @return appointment instance
     */
    public PAGE createAppointment(Appointment appointment) {
        if (appointment.getAppointmentTittle() != null) {
            appointmentField.shouldBe(Condition.visible).val(appointment.getAppointmentTittle());
            TestReporter.reportDebugStep("%s - appointment tittle was set ", appointment.getAppointmentTittle());
        }
        if (appointment.getDateTime() != null) {
            appointmentDateField.shouldBe(Condition.visible).val(appointment.getDateTime());
            TestReporter.reportDebugStep("%s - appointment date time was set ", appointment.getDateTime());
        }
        createAppointmentButton.shouldBe(Condition.visible).click();
        TestReporter.reportDebugStep("Appointment was created ");
        return (PAGE) this;
    }
}
