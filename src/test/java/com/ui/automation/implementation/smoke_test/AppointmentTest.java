package com.ui.automation.implementation.smoke_test;

import com.ui.automation.implementation.BaseTest;
import com.ui.core.utils.StringUtils;
import com.ui.model.entity.registration.Appointment;
import com.ui.model.pages.login.LoginPage;
import com.ui.model.pages.main.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.ui.core.utils.DateUtils.*;
import static com.ui.model.providers.DataProviders.getEnvValue;
import static com.ui.model.providers.DataProviders.getUserPassword;

public class AppointmentTest extends BaseTest {
    private String userLogin;
    private String userPassword;
    private Appointment appointment;
    private String firstRangeDate;
    private String secondRangeDate;

    @BeforeClass
    @Parameters({"userId"})
    public void initialization(@Optional("1") int userId) {
        userLogin = getEnvValue("users", "user_login");
        userPassword = getUserPassword(getEnvValue("users", "user_password"));
        appointment = Appointment.builder()
                .build();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 6);
        String datePattern = MONTH_FULL + " " + DAY + ", " + YEAR + ", " + HOUR_12_FORMAT + ":" + MINUTE + " " + AM_PM_MARKER;
        firstRangeDate = getDateTime(EET_TIME_ZONE, datePattern, calendar);
        calendar.add(Calendar.HOUR, 1);
        secondRangeDate = getDateTime(EET_TIME_ZONE, datePattern, calendar);
    }

    @Test
    @Issue("https://tickets.medeanalytics.com/browse/TEST-11111")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that appointment is displayed on the schedule view page.")
    public void checkThatAppointmentWillAppearedInCalendarViewPageTest() {
        String dateAppointmentTime = firstRangeDate + " - " + secondRangeDate;
        appointment.setAppointmentTittle("appointment_test" + StringUtils.getRandomNumber());
        appointment.setDateTime(dateAppointmentTime);
        MainPage mainPage = new LoginPage()
                .openWindow()
                .waitPageLoading(35, TimeUnit.SECONDS)
                .login(userLogin, userPassword);

        mainPage.waitPageLoading(35, TimeUnit.SECONDS)
                .openSchedulePage()
                .openAppointmentForm()
                .createAppointment(appointment)
                .waitPageLoading(15, TimeUnit.SECONDS);

        Assert.assertTrue(mainPage.openCalendarPage()
                .waitPageLoading(15, TimeUnit.SECONDS)
                .clickDayItem()
                .searchAppointment(appointment.getAppointmentTittle())
                .isAppointmentExist(appointment.getAppointmentTittle()));
    }
}
