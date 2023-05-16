package com.ui.model.pages.login;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.ui.core.report.TestReporter;
import com.ui.model.pages.BasePage;
import com.ui.model.pages.main.MainPage;
import com.ui.model.properties.PropertiesData;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.ui.model.providers.DataProviders.getValue;

public class LoginPage extends BasePage<LoginPage> {
    private static final String PAGE_NAME = "login_page";
    private static final By SUBMIT_BUTTON_ELEMENT = Selectors.byText(getValue(PAGE_NAME, "submit_button"));
    private final SelenideElement userLoginField = $(Selectors.byId(getValue(PAGE_NAME, "user_login_field")));
    private final SelenideElement userNameField = $(Selectors.byId(getValue(PAGE_NAME, "user_name_field")));

    public LoginPage() {
        super(PropertiesData.ENVIRONMENT_URL, SUBMIT_BUTTON_ELEMENT);
    }

    /**
     * Login to system.
     *
     * @param userLogin    client username
     * @param userPassword client password
     * @return main page instance
     */
    public MainPage login(String userLogin, String userPassword) {
        userLoginField.shouldBe(Condition.visible).val(userLogin);
        userNameField.shouldBe(Condition.visible).val(userPassword);
        $(SUBMIT_BUTTON_ELEMENT).shouldBe(Condition.visible).click();
        TestReporter.reportDebugStep("Login to UI:  %s", PropertiesData.ENVIRONMENT_URL);
        TestReporter.reportDebugStep("Username has been logged in : %s ", userLogin);
        return new MainPage();
    }
}
