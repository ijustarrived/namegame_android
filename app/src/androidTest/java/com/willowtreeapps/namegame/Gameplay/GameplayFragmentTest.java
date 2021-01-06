package com.willowtreeapps.namegame.Gameplay;

import android.content.pm.ActivityInfo;

import androidx.annotation.IdRes;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.willowtreeapps.namegame.Gameplay.Pojo.EmployeeInfo;
import com.willowtreeapps.namegame.MainMenu.MainActivity;
import com.willowtreeapps.namegame.MainMenu.Pojo.EmployeeViewModel;
import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.core.NameGameApplication;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.willowtreeapps.namegame.Gameplay.ImageViewDrawableMatcher.HasDrawable;

public class GameplayFragmentTest
{
    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity activity;

    private EmployeeViewModel employeeViewModel;

    @Before
    public void Init()
    {
        activity = activityActivityTestRule.getActivity();

        employeeViewModel = NameGameApplication.get(activity).
                GetEmployeeViewModel();
    }

    //Must comment the error and placeholder options of the picasso load or this test might pass with erroneous results.
    @Test
    public void VerifyIfImageWasLoadedAfterGameStartedTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        onView(withId(R.id.employeeImage)).check(matches(HasDrawable()));
    }

    @Test
    public void VerifyIfEmployeeFullNameWasSetAfterGameLoadedTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        final EmployeeInfo RANDOM_EMPLOYEE = employeeViewModel.GetRandomEmployee();

        onView(withId(R.id.employeeName)).check(matches(withText(String.format("%s %s", RANDOM_EMPLOYEE.GetFirstName(), RANDOM_EMPLOYEE.GetLastName()))));
    }

    @Test
    public void LosePracticeGameAndVerifyDialogIsVisibleTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        LoseGame(employeeViewModel.GetRandomListOf6().indexOf(employeeViewModel.GetRandomEmployee()));

        onView(withText(R.string.gameOverTitleTxt)).inRoot(isDialog()).check(matches(isDisplayed()));
    }

    @Test
    public void SelectCorrectAnswerInPracticeModeTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        SelectCorrectAnswer(1);

        onView(withText(R.string.gameOverTitleTxt)).check(doesNotExist());
    }

    @Test
    public void Select2CorrectAnswerInARowInPracticeModeTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        SelectCorrectAnswer(2);

        onView(withText(R.string.gameOverTitleTxt)).check(doesNotExist());
    }

    @Test
    public void SelectCorrectAnswerAndLoseInPracticeModeTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        SelectCorrectAnswer(1);

        LoseGame(employeeViewModel.GetRandomListOf6().indexOf(employeeViewModel.GetRandomEmployee()));
    }

    @Test
    public void ValidateGameOverScoreInPracticeModeTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        SelectCorrectAnswer(1);

        LoseGame(employeeViewModel.GetRandomListOf6().indexOf(employeeViewModel.GetRandomEmployee()));

        //16908299
        final int ALERT_DIALOG_TXT_VW_ID = activity.getResources()
                                         .getIdentifier
                                                 (
                                                         "16908299",
                                                         "id",
                                                         InstrumentationRegistry.getInstrumentation()
                                                                                .getTargetContext()
                                                                                .getPackageName()
                                                 );

        onView(withId(ALERT_DIALOG_TXT_VW_ID)).check
                (
                        matches
                                (
                                        withText
                                                (
                                                        String.format("%s %s", activity.getText(R.string.gameOverBodyTxt), "1")
                                                )
                                )
                );
    }

    @Test
    public void NavigatesBackToMainMenuAfterGameOverFromPracticeModeTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        SelectCorrectAnswer(1);

        LoseGame(employeeViewModel.GetRandomListOf6().indexOf(employeeViewModel.GetRandomEmployee()));

        ReturnToMainMenuAndVerifyIsVisible(activity);
    }

    @Test
    public void ChangeToLandscapeAndLosePracticeModeAndReturnToMainMenuTest()
    {
        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        LoseGame(employeeViewModel.GetRandomListOf6().indexOf(employeeViewModel.GetRandomEmployee()));

        ReturnToMainMenuAndVerifyIsVisible(activity);
    }

    @Test
    public void StartPracticeModeAndChangeToLandscapeAndLoseAndReturnToMainMenuTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        LoseGame(employeeViewModel.GetRandomListOf6().indexOf(employeeViewModel.GetRandomEmployee()));

        ReturnToMainMenuAndVerifyIsVisible(activity);
    }

    @Test
    public void ChangeToLandscapeAndBackToPortAndLosePracticeModeAndReturnToMainMenuTest()
    {
        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        LoseGame(employeeViewModel.GetRandomListOf6().indexOf(employeeViewModel.GetRandomEmployee()));

        ReturnToMainMenuAndVerifyIsVisible(activity);
    }

    @Test
    public void StartPracticeModeAndChangeToLandAndBackToPortAndLoseAndReturnToMainMenuTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        LoseGame(employeeViewModel.GetRandomListOf6().indexOf(employeeViewModel.GetRandomEmployee()));

        ReturnToMainMenuAndVerifyIsVisible(activity);
    }

    @Test
    public void LosePracticeModeAndChangeToLandAndReturnToMainMenuTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        LoseGame(employeeViewModel.GetRandomListOf6().indexOf(employeeViewModel.GetRandomEmployee()));

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ReturnToMainMenuAndVerifyIsVisible(activity);
    }

    @Test
    public void LosePracticeModeAndChangeToLandAndBackToPortAndReturnToMainMenuTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        LoseGame(employeeViewModel.GetRandomListOf6().indexOf(employeeViewModel.GetRandomEmployee()));

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ReturnToMainMenuAndVerifyIsVisible(activity);
    }

    @Test
    public void LosePracticeModeAndReturnToMainMenuAndChangeToLandAndStartPracticeModeTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        LoseGame(employeeViewModel.GetRandomListOf6().indexOf(employeeViewModel.GetRandomEmployee()));

        ReturnToMainMenu(activity);

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        VerifyIfEmployeeFullNameWasSetAfterGameLoadedTest();
    }

    @Test
    public void LosePracticeModeAndReturnToMainMenuAndChangeToLandAndBackToPortAndStartPracticeModeTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        LoseGame(employeeViewModel.GetRandomListOf6().indexOf(employeeViewModel.GetRandomEmployee()));

        ReturnToMainMenu(activity);

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        VerifyIfEmployeeFullNameWasSetAfterGameLoadedTest();
    }

    private int GetImgVwResId(int index)
    {
        switch (index)
        {
            case 0:

                return R.id.employeeImage;

            case 1:

                return R.id.employeeImage2;

            case 2:

                return R.id.employeeImage3;

            case 3:

                return R.id.employeeImage4;

            case 4:

                return R.id.employeeImage5;

            default:

                return R.id.employeeImage6;
        }
    }

    private void LoseGame(int randomEmployeeIndex)
    {
        int resId = randomEmployeeIndex == 0 ? R.id.employeeImage2 :R.id.employeeImage;

        onView(withId(resId)).perform(click());
    }

    private void SelectCorrectAnswer(int correctCount)
    {
        while (correctCount > 0)
        {
            correctCount--;

            EmployeeInfo randomEmployee = employeeViewModel.GetRandomEmployee();

            int selectedIndex = employeeViewModel.GetRandomListOf6().indexOf(randomEmployee);

            onView(withId(GetImgVwResId(selectedIndex))).perform(click());

            WaitToFinish();
        }
    }

    private void WaitForDataAndClickMode(@IdRes int id)
    {
        WaitToFinish();

        onView(withId(id)).perform(click());
    }

    private void WaitToFinish()
    {
        try
        {
            Thread.sleep(1000);
        }

        catch (InterruptedException e)
        {
            throw new AssertionError(e.getMessage());
        }
    }

    private void ChangeOrientationTo(MainActivity activity, int orientation)
    {
        activity.setRequestedOrientation(orientation);

        WaitToFinish();
    }

    private void ReturnToMainMenuAndVerifyIsVisible(MainActivity activity)
    {
        final int ALERT_DIALOG_BTN_ID = activity.getResources()
                                                .getIdentifier
                                                        (
                                                                "16908313",
                                                                "id",
                                                                InstrumentationRegistry.getInstrumentation()
                                                                                       .getTargetContext()
                                                                                       .getPackageName()
                                                        );

        onView(withId(ALERT_DIALOG_BTN_ID)).perform(click());

        WaitToFinish();

        onView(withId(R.id.practiceModeBtn)).check(matches(isDisplayed()));
    }

    private void ReturnToMainMenu(MainActivity activity)
    {
        final int ALERT_DIALOG_BTN_ID = activity.getResources()
                                                .getIdentifier
                                                        (
                                                                "16908313",
                                                                "id",
                                                                InstrumentationRegistry.getInstrumentation()
                                                                                       .getTargetContext()
                                                                                       .getPackageName()
                                                        );

        onView(withId(ALERT_DIALOG_BTN_ID)).perform(click());

        WaitToFinish();
    }
}