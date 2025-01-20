package ru.iteco.fmhandroid.ui.PageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.utilities.Utils.childAtPosition;
import static ru.iteco.fmhandroid.ui.utilities.Utils.waitDisplayed;

import android.view.View;

import org.hamcrest.Matchers;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;

public class LoginPage {
    public static final int loginField = R.id.login_text_input_layout;
    public static final int loginPageTag = loginField;
    public static final int passwordField = R.id.password_text_input_layout;
    public static final int signInButton = R.id.enter_button;
    public static final String errorLoginOrPassword = "Wrong login or password";
    public static final String errorEmptyField = "Login and password cannot be empty";

    static CommonPageFunctions commonPageFunctions = new CommonPageFunctions();

    public void waitLoginPage() {
        onView(isRoot()).perform(waitDisplayed(R.id.splashscreen_image_view, 8_000));
        //onView(isRoot()).perform(waitDisplayed(loginField,10000));
    }

    public void feelField(int field, String inputText) {
        Allure.step("Заполнение выбранного поля текстом: {inputText}");
        onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(field),
                                0),
                        0)))
                .perform(replaceText(inputText), closeSoftKeyboard());
    }

    public void isNotLogin() {
        Allure.step("Проверка того, что войти в приложение (логин) НЕ удалось");
        try {
            commonPageFunctions.waitPage(MainPage.mainPageTag);
        } catch (Exception e) {
            //CommonPageFunctions.PageIsReached(loginPageTag);
        } finally {
            commonPageFunctions.PageIsReached(loginPageTag);
        }
    }


}

