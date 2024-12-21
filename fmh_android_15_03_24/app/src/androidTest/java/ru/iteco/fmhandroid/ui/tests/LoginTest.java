package ru.iteco.fmhandroid.ui.tests;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Story;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.PageObject.CommonPageFunctions;
import ru.iteco.fmhandroid.ui.PageObject.LoginPage;
import ru.iteco.fmhandroid.ui.PageObject.MainPage;
import ru.iteco.fmhandroid.ui.utilities.LoginData;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@Epic("Тесты для функционального тестирования: логин(вход) и логаут(выход) из личного кабинета(ЛК) мобильного приложения 'Мобильный хоспис' ")
public class LoginTest {
    static LoginPage loginPage = new LoginPage();
    static CommonPageFunctions commonPageFunctions = new CommonPageFunctions();
    static MainPage mainPage = new MainPage();
    public View decorView;

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void goToLoginPage() {
        loginPage.waitLoginPage();
        try {
            commonPageFunctions.waitPage(LoginPage.loginPageTag);
        } catch (Exception e) {
            mainPage.logOut();
        }
    }

    @Story("Case 1. Успешный логин с валидными данными")
    @Description("Успешный логин с валидными данными login2 и password2 через страницу авторизации приложения 'Мобильный хоспис' (Позитивный)")
    @Test
    public void successfulLogin() {
        commonPageFunctions.waitPage(LoginPage.loginPageTag);
        commonPageFunctions.selectField(LoginPage.loginField);
        loginPage.feelField(LoginPage.loginField, LoginData.trueLogin);
        commonPageFunctions.selectField(LoginPage.passwordField);
        loginPage.feelField(LoginPage.passwordField,LoginData.truePassword);
        commonPageFunctions.clickItem(LoginPage.signInButton);
        commonPageFunctions.waitPage(MainPage.mainPageTag);
        commonPageFunctions.PageIsReached(MainPage.mainPageTag);
    }

    @Story("Case 2. Успешный logout из ЛК")
    @Description("Успешный логаут из ЛК через меню ЛК приложения 'Мобильный хоспис' (Позитивный)")
    @Test
    public void successfulLogOut() {
        commonPageFunctions.waitPage(LoginPage.loginPageTag);
        commonPageFunctions.selectField(LoginPage.loginField);
        loginPage.feelField(LoginPage.loginField, LoginData.trueLogin);
        commonPageFunctions.selectField(LoginPage.passwordField);
        loginPage.feelField(LoginPage.passwordField,LoginData.truePassword);
        commonPageFunctions.clickItem(LoginPage.signInButton);
        commonPageFunctions.waitPage(MainPage.mainPageTag);
        mainPage.logOut();
        commonPageFunctions.waitPage(LoginPage.loginPageTag);
        commonPageFunctions.PageIsReached(LoginPage.loginPageTag);
    }

    @Story("Case 3. Безуспешеный логин с пустыми логином и паролем")
    @Description("Безуспешеный логин с с пустыми логином и паролем через страницу авторизации приложения 'Мобильный хоспис' (Негативный)")
    @Test
    public void failedLoginWithEmptyLoginAndPass() {
        commonPageFunctions.waitPage(LoginPage.loginPageTag);
        commonPageFunctions.selectField(LoginPage.loginField);
        loginPage.feelField(LoginPage.loginField, LoginData.emptyLogin);
        commonPageFunctions.selectField(LoginPage.passwordField);
        loginPage.feelField(LoginPage.passwordField, LoginData.emptyPassword);
        commonPageFunctions.clickItem(LoginPage.signInButton);
        loginPage.isNotLogin();
    }

    @Story("Case 4. Безуспешеный логин с невалидным логином и валидным паролем")
    @Description("Безуспешеный логин с невалидным логином login и валидным паролем password2 через страницу авторизации приложения 'Мобильный хоспис' (Негативный)")
    @Test
    public void failedLoginWithWrongLogin() {
        commonPageFunctions.waitPage(LoginPage.loginPageTag);
        commonPageFunctions.selectField(LoginPage.loginField);
        loginPage.feelField(LoginPage.loginField, LoginData.wrongLogin);
        commonPageFunctions.selectField(LoginPage.passwordField);
        loginPage.feelField(LoginPage.passwordField,LoginData.truePassword);
        commonPageFunctions.clickItem(LoginPage.signInButton);

        loginPage.isNotLogin();
//        LoginPage.checkToastErrorMessage(errorLoginOrPassword, decorView);
    }
    @Story("Case 5. Безуспешеный логин с валидным логином и невалидным паролем")
    @Description("Безуспешеный логин с валидным логином login2 и невалидным паролем password через страницу авторизации приложения 'Мобильный хоспис' (Негативный)")
    @Test
    public void failedLoginWithWrongPassword() {
        commonPageFunctions.waitPage(LoginPage.loginPageTag);
        commonPageFunctions.selectField(LoginPage.loginField);
        loginPage.feelField(LoginPage.loginField, LoginData.trueLogin);
        commonPageFunctions.selectField(LoginPage.passwordField);
        loginPage.feelField(LoginPage.passwordField, LoginData.wrongPassword);
        commonPageFunctions.clickItem(LoginPage.signInButton);
        loginPage.isNotLogin();
    }

}
