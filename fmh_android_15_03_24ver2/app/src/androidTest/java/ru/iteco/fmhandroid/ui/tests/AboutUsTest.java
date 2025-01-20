package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Story;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.PageObject.AboutUsPage;
import ru.iteco.fmhandroid.ui.PageObject.CommonPageFunctions;
import ru.iteco.fmhandroid.ui.PageObject.LoginPage;
import ru.iteco.fmhandroid.ui.PageObject.MainPage;
import ru.iteco.fmhandroid.ui.utilities.LoginData;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@Epic("Тест-кейсы для проведения функционального тестирования вкладки 'О приложении' мобильного приложения 'Мобильный хоспис'")
public class AboutUsTest {
    static LoginPage loginPage = new LoginPage();
    static CommonPageFunctions commonPageFunctions = new CommonPageFunctions();
    static MainPage mainPage = new MainPage();
    private final String aboutMenuItem = "About";

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);
    @Rule
    public ActivityTestRule<AppActivity> mActivityTestRule =
            new ActivityTestRule<>(AppActivity.class);
    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE,
            String.valueOf(System.currentTimeMillis()));


    @Before
    public void setUp() {
        try {
            commonPageFunctions.waitPage(MainPage.mainPageTag);
        } catch (Exception e) {
            commonPageFunctions.waitPage(LoginPage.loginPageTag);
            commonPageFunctions.selectField(LoginPage.loginField);
            loginPage.feelField(LoginPage.loginField, LoginData.trueLogin);
            commonPageFunctions.selectField(LoginPage.passwordField);
            loginPage.feelField(LoginPage.passwordField,LoginData.truePassword);
            commonPageFunctions.clickItem(LoginPage.signInButton);
            commonPageFunctions.waitPage(MainPage.mainPageTag);
            commonPageFunctions.PageIsReached(MainPage.mainPageTag);
        }
    }

    @Story("Case 24. Переход по ссылке 'Политика конфиденциальности'")
    @Description("Перейти по ссылке 'Политика конфиденциальности' во вкладке 'О приложении' мобильного приложения 'Мобильный хоспис' и дождаться загрузки информации (Позитивный)")
    @Test
    public void followTheLinkPrivacyPolicyTest() throws InterruptedException {
        mainPage.clickMainMenuItem(aboutMenuItem);
        Thread.sleep(5000);
        Intents.init();
        commonPageFunctions.clickItem(AboutUsPage.privacyPolicyLink);
        intended(hasData("https://vhospice.org/#/privacy-policy"));
        intended(hasAction(Intent.ACTION_VIEW));
        Intents.release();
        AboutUsPage.policyText.check(matches(isDisplayed()));
        pressBack();
    }

    @Story("Case 25. Переход по ссылке 'Правила использования'")
    @Description("Перейти по ссылке 'Правила использования' во вкладке 'О приложении' мобильного приложения 'Мобильный хоспис' и дождаться загрузки информации (Позитивный)")
    @Test
    public void followTheLinkTermsOfUseTest() throws InterruptedException {
        mainPage.clickMainMenuItem(aboutMenuItem);
        Thread.sleep(5000);
        Intents.init();
        commonPageFunctions.clickItem(AboutUsPage.termsOfUseLink);
        intended(hasData("https://vhospice.org/#/terms-of-use"));
        intended(hasAction(Intent.ACTION_VIEW));
        Intents.release();
        AboutUsPage.termsOfUseText.check(matches(isDisplayed()));
        pressBack();
    }

}