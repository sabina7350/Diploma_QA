package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static ru.iteco.fmhandroid.ui.utilities.LoginData.trueLogin;
import static ru.iteco.fmhandroid.ui.utilities.LoginData.truePassword;
import static ru.iteco.fmhandroid.ui.utilities.Utils.waitDisplayed;

import android.view.View;

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
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.PageObject.CommonPageFunctions;
import ru.iteco.fmhandroid.ui.PageObject.CreateNewsPage;
import ru.iteco.fmhandroid.ui.PageObject.LoginPage;
import ru.iteco.fmhandroid.ui.PageObject.MainPage;
import ru.iteco.fmhandroid.ui.PageObject.NewsPage;
import ru.iteco.fmhandroid.ui.utilities.Utils;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@Epic("Тест-кейсы для проведения функционального тестирования вкладки Новости мобильного приложения 'Мобильный хоспис'")

public class NewsTest {
    static LoginPage loginPage = new LoginPage();
    static CommonPageFunctions commonPageFunctions = new CommonPageFunctions();
    static NewsPage newsPage = new NewsPage();
    static CreateNewsPage createNewsPage = new CreateNewsPage();
    static MainPage mainPage = new MainPage();

    private final String NewsTitle = "Объявление";
    private final String newsMenuItem = "News";

    private final String invalidDate = "11.11.1111";
    private final String invalidTime = "26:75";
    private final String errorMessageWrongDate = "Invalid date!";
    private final String errorMessageWrongTime = "Invalid time!";

    public View decorView;



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

    @Story("Case 10. Добавление новой новости с текущей датой и временем")
    @Description("Создание новой новости с текущей датой и временем во вкладке 'Новости' мобильного приложения 'Мобильный хоспис' (Позитивный)")
    @Test
    public void addFreshNewsCurrentDataTest() {
        String newsDescription = Utils.getRandomNewsDescription();
        commonPageFunctions.clickItem(MainPage.allNewsHeadline); //all news
        commonPageFunctions.clickItem(NewsPage.editNewsButton);
        commonPageFunctions.clickItem(NewsPage.addNewsButton);
        createNewsPage.chooseCategory(NewsTitle);
        createNewsPage.addNewsCurrentDate();
        createNewsPage.addNewsCurrentTime();
        createNewsPage.addNewsDescription(newsDescription);
        createNewsPage.saveFreshNews();
        commonPageFunctions.waitPage(MainPage.mainPageTag);
        mainPage.clickMainMenuItem(newsMenuItem);
        commonPageFunctions.waitPage(NewsPage.newsList);
        newsPage.clickFreshNews();
        onView(isRoot()).perform(waitDisplayed(R.id.news_item_description_text_view, 5000));
        newsPage.findAddedNews(newsDescription);
    }

    @Story("Case 11. Добавление новой новости с текущей датой и временем через 'Главное меню'")
    @Description("Создание новой новости с текущей датой и временем через вкладку 'Главное меню' мобильного приложения 'Мобильный хоспис' (Позитивный)")
    @Test
    public void addFreshNewsCurrentDataMainMenuTest() {   //добавление новой новости через главное меню с текущей датой и временем
        String newsDescription = Utils.getRandomNewsDescription();
        mainPage.clickMainMenuItem(newsMenuItem);
        commonPageFunctions.waitPage(NewsPage.newsList);
        commonPageFunctions.clickItem(NewsPage.editNewsButton);
        commonPageFunctions.clickItem(NewsPage.addNewsButton);
        createNewsPage.chooseCategory(NewsTitle);
        createNewsPage.addNewsCurrentDate();
        createNewsPage.addNewsCurrentTime();
        createNewsPage.addNewsDescription(newsDescription);
        createNewsPage.saveFreshNews();
        commonPageFunctions.waitPage(MainPage.mainPageTag);
        mainPage.clickMainMenuItem(newsMenuItem);
        commonPageFunctions.waitPage(NewsPage.newsList);
        newsPage.clickFreshNews();
        newsPage.findAddedNews(newsDescription);

    }

    @Story("Case 12. Фильтрация новостей по категориям")
    @Description("Фильтрация новостей по выбранной категории во вкладке 'Новости' с помощью кнопки 'Фильтр' мобильного приложения 'Мобильный хоспис' (Позитивный)")
    @Test
    public void newsFilteringByCategoryTest() {
        commonPageFunctions.clickItem(MainPage.allNewsHeadline); //all news
        commonPageFunctions.waitPage(NewsPage.newsList);
        commonPageFunctions.clickItem(NewsPage.filterNewsButton);
        newsPage.chooseCategoryOfNews(NewsTitle);
        newsPage.selectStartFilterTimeInterval();
        newsPage.selectEndFilterTimeInterval();
        commonPageFunctions.clickItem(NewsPage.applyFilterButton);
        commonPageFunctions.waitPage(NewsPage.newsList);
        newsPage.checkNewsCategory(NewsTitle, 0);
    }

    @Story("Case 32. Добавление новой новости с некорректной датой и корректным временем")
    @Description("Попытка создания новой новости с некорректной датой и корректным временем во вкладке 'Новости' мобильного приложения 'Мобильный хоспис' (Негативный)")
    @Test
    public void addFreshNewsInvalidDateTest() {
        String newsDescription = Utils.getRandomNewsDescription();
        commonPageFunctions.clickItem(MainPage.allNewsHeadline); //all news
        commonPageFunctions.clickItem(NewsPage.editNewsButton);
        commonPageFunctions.clickItem(NewsPage.addNewsButton);
        createNewsPage.chooseCategory(NewsTitle);
        createNewsPage.addNewsInvalidDate(invalidDate);
        createNewsPage.addNewsCurrentTime();
        createNewsPage.addNewsDescription(newsDescription);
        createNewsPage.saveFreshNews();
        createNewsPage.checkToastErrorMessage(errorMessageWrongDate, decorView);
    }

    @Story("Case 31. Добавление новой новости с корректной датой и некорректным временем")
    @Description("Попытка создания новой новости с корректной датой и некорректным временем во вкладке 'Новости' мобильного приложения 'Мобильный хоспис' (Негативный)")
    @Test
    public void addFreshNewsInvalidTimeTest() {
        String newsDescription = Utils.getRandomNewsDescription();
        commonPageFunctions.clickItem(mainPage.allNewsHeadline); //all news
        commonPageFunctions.clickItem(NewsPage.editNewsButton);
        commonPageFunctions.clickItem(NewsPage.addNewsButton);
        createNewsPage.chooseCategory(NewsTitle);
        createNewsPage.addNewsCurrentDate();
        createNewsPage.addNewsInvalidTime(invalidTime);
        createNewsPage.addNewsDescription(newsDescription);
        createNewsPage.saveFreshNews();
        createNewsPage.checkToastErrorMessage(errorMessageWrongTime, decorView);
    }


}


