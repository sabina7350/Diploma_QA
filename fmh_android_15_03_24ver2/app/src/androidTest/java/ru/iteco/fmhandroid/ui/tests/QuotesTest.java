package ru.iteco.fmhandroid.ui.tests;

import static ru.iteco.fmhandroid.ui.utilities.LoginData.trueLogin;
import static ru.iteco.fmhandroid.ui.utilities.LoginData.truePassword;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

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
import ru.iteco.fmhandroid.ui.PageObject.CommonPageFunctions;
import ru.iteco.fmhandroid.ui.PageObject.LoginPage;
import ru.iteco.fmhandroid.ui.PageObject.MainPage;
import ru.iteco.fmhandroid.ui.PageObject.QuotesPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)

@Epic("Тест-кейс для проведения функционального тестирования вкладки 'Цитаты' мобильного приложения 'Мобильный хоспис'")
public class QuotesTest {

    static LoginPage loginPage = new LoginPage();
    static QuotesPage quotesPage = new QuotesPage();
    static CommonPageFunctions commonPageFunctions = new CommonPageFunctions();

    private final String title = "\"Хоспис для меня - это то, каким должен быть мир.\"";
    private final String description = "\"Ну, идеальное устройство мира в моих глазах. Где никто не оценивает, никто не осудит, где говоришь, и тебя слышат, где, если страшно, тебя обнимут и возьмут за руку, а если холодно тебя согреют.\" Юля Капис, волонтер";
    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);
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
            loginPage.feelField(LoginPage.loginField,trueLogin);
            commonPageFunctions.selectField(LoginPage.passwordField);
            loginPage.feelField(LoginPage.passwordField,truePassword);
            commonPageFunctions.clickItem(LoginPage.signInButton);
            commonPageFunctions.waitPage(MainPage.mainPageTag);
            commonPageFunctions.PageIsReached(MainPage.mainPageTag);
        }
    }

    @Story("Case 13. Открытие цитаты")
    @Description("Открытие цитаты в развернутом виде во вкладке 'Цитаты' мобильного приложения 'Мобильный хоспис'(Позитивный)")
    @Test
    public void openingQuoteTest() {
        commonPageFunctions.clickItem(MainPage.quotesButton);
        commonPageFunctions.waitPage(QuotesPage.missionLogo);
        quotesPage.openQuote(0);
        quotesPage.checkTextQuote(description);
    }
}
