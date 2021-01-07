package com.willowtreeapps.namegame.Gameplay;

import android.content.pm.ActivityInfo;

import androidx.annotation.IdRes;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.willowtreeapps.namegame.Gameplay.Pojo.EmployeeInfo;
import com.willowtreeapps.namegame.Gameplay.Pojo.GameplayViewModel;
import com.willowtreeapps.namegame.MainMenu.MainActivity;
import com.willowtreeapps.namegame.MainMenu.Pojo.EmployeeViewModel;
import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.core.NameGameApplication;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.willowtreeapps.namegame.Gameplay.ViewBackgroundMatcher.HasBackground;
import static com.willowtreeapps.namegame.Gameplay.ImageViewDrawableMatcher.HasDrawable;

public class GameplayFragmentTest
{
    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity activity;

    private EmployeeViewModel employeeViewModel;

    private GameplayViewModel gameplayViewModel;

    @Before
    public void Init()
    {
        activity = activityActivityTestRule.getActivity();

        employeeViewModel = NameGameApplication.get(activity).
                GetEmployeeViewModel();

        gameplayViewModel = NameGameApplication.get(activity).
                GetGameplayViewModel();
    }

    //Must comment the error and placeholder options of the picasso load or this test might pass with erroneous results.
    @Test
    public void VerifyIfImageWasLoadedAfterTimeModeStartedTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        onView(withId(R.id.employeeImage)).check(matches(HasDrawable()));
    }

    @Test
    public void VerifyIfImageWasLoadedAfterPracticeModeStartedTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        onView(withId(R.id.employeeImage)).check(matches(HasDrawable()));
    }

    @Test
    public void VerifyIfEmployeeFullNameWasSetAfterTimeModeLoadedTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        ValidateRandomEmployeeFullName();
    }

    @Test
    public void VerifyIfEmployeeFullNameWasSetAfterPracticeModeLoadedTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

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

        WaitToFinish();

        onView(withText(R.string.gameOverTitleTxt)).inRoot(isDialog()).check(matches(isDisplayed()));
    }

    @Test
    public void LoseTimedGameAndVerifyDialogIsVisibleTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        ValidateGameOverInTimedMode();
    }

    @Test
    public void LosePracticeGameAndValidateIncorrectAnswerImageTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        onView
                (
                        withId
                                (
                                        GetResultVwResIdFromSelectedResId
                                                (
                                                        LoseGame
                                                                (
                                                                        employeeViewModel.GetRandomListOf6().indexOf(employeeViewModel.GetRandomEmployee())
                                                                )
                                                )
                                )
                ).check
                (
                        matches
                                (
                                        HasBackground()
                                )
                );
    }

    @Test
    public void SelectIncorrectAnswerInTimedModeAndValidateIncorrectAnswerImageTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        onView
                (
                        withId
                                (
                                        GetResultVwResIdFromSelectedResId
                                                (
                                                        LoseGame
                                                                (
                                                                        employeeViewModel.GetRandomListOf6().indexOf(employeeViewModel.GetRandomEmployee())
                                                                )
                                                )
                                )
                ).check
                (
                        matches
                                (
                                        HasBackground()
                                )
                );
    }

    @Test
    public void ValidateNextRoundLoadsInPracticeModeTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        SelectCorrectAnswer(1);

        ValidateRandomEmployeeFullName();
    }

    @Test
    public void ValidateNextRoundLoadsInTimedModeTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        SelectCorrectAnswer(1);

        ValidateRandomEmployeeFullName();
    }

    @Test
    public void Select2CorrectAnswerAndValidateNextRoundLoadsInPracticeModeTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        SelectCorrectAnswer(2);

        ValidateRandomEmployeeFullName();
    }

    @Test
    public void Select2CorrectAnswerAndValidateNextRoundLoadsInTimeModeTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        SelectCorrectAnswer(2);

        ValidateRandomEmployeeFullName();
    }

    //This test will fail unless the delay of the answer handler, in the gameplay view model, is manually increased to 3000 or higher
    @Test
    public void SelectCorrectAnswerAndValidateCorrectImageInPracticeModeTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        onView
                (
                        withId
                                (
                                        GetResultVwResIdFromSelectedIndex
                                                (
                                                        SelectCorrectAnswer(1)
                                                )
                                )
                ).check
                (
                        matches
                                (
                                        HasBackground()
                                )
                );
    }

    //This test will fail unless the delay of the answer handler, in the gameplay view model, is manually increased to 3000 or higher
    @Test
    public void Select2CorrectAnswerAndValidateCorrectImageInPracticeModeTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        onView
                (
                        withId
                                (
                                        GetResultVwResIdFromSelectedIndex
                                                (
                                                        SelectCorrectAnswer(2)
                                                )
                                )
                ).check
                (
                        matches
                                (
                                        HasBackground()
                                )
                );
    }

    //This test will fail unless the delay of the answer handler, in the gameplay view model, is manually increased to 3000 or higher
    @Test
    public void SelectCorrectAnswerAndValidateCorrectImageInTimedModeTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        onView
                (
                        withId
                                (
                                        GetResultVwResIdFromSelectedIndex
                                                (
                                                        SelectCorrectAnswer(1)
                                                )
                                )
                ).check
                (
                        matches
                                (
                                        HasBackground()
                                )
                );
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
    public void SelectCorrectAnswerAndLoseInTimedModeTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        SelectCorrectAnswer(1);

        WaitToFinish((int)gameplayViewModel.getTimeModeDuration());
    }

    @Test
    public void ValidateGameOverScoreInPracticeModeTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        SelectCorrectAnswer(1);

        LoseGame(employeeViewModel.GetRandomListOf6().indexOf(employeeViewModel.GetRandomEmployee()));

        ValidateGameOverScore("1");
    }

    @Test
    public void ValidateGameOverScoreInTimedModeTest()
    {
        SelectCorrectAnswerAndLoseInTimedModeTest();

        ValidateGameOverScore("1/1");
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
    public void NavigatesBackToMainMenuAfterGameOverFromTimedModeTest()
    {
        SelectCorrectAnswerAndLoseInTimedModeTest();

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
    public void ChangeToLandscapeAndLoseTimedModeAndReturnToMainMenuTest()
    {
        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        WaitToFinish((int)gameplayViewModel.getTimeModeDuration());

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
    public void StartTimedModeAndChangeToLandscapeAndLoseAndReturnToMainMenuTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        WaitToFinish((int) gameplayViewModel.getTimeModeDuration());

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
    public void ChangeToLandscapeAndBackToPortAndLoseTimedModeAndReturnToMainMenuTest()
    {
        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        WaitToFinish((int) gameplayViewModel.getTimeModeDuration());

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
    public void StartTimedModeAndChangeToLandAndBackToPortAndLoseAndReturnToMainMenuTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        WaitToFinish((int) gameplayViewModel.getTimeModeDuration());

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
    public void LoseTimedModeAndChangeToLandAndReturnToMainMenuTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        WaitToFinish((int) gameplayViewModel.getTimeModeDuration());

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
    public void LoseTimedModeAndChangeToLandAndBackToPortAndReturnToMainMenuTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        WaitToFinish((int) gameplayViewModel.getTimeModeDuration());

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

        VerifyIfEmployeeFullNameWasSetAfterPracticeModeLoadedTest();
    }
    @Test
    public void LoseTimedModeAndReturnToMainMenuAndChangeToLandAndStartTimedModeTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        WaitToFinish((int) gameplayViewModel.getTimeModeDuration());

        ReturnToMainMenu(activity);

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        VerifyIfEmployeeFullNameWasSetAfterTimeModeLoadedTest();
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

        VerifyIfEmployeeFullNameWasSetAfterPracticeModeLoadedTest();
    }

    @Test
    public void LoseTimedModeAndReturnToMainMenuAndChangeToLandAndBackToPortAndStartTimedModeTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        WaitToFinish((int) gameplayViewModel.getTimeModeDuration());

        ReturnToMainMenu(activity);

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        VerifyIfEmployeeFullNameWasSetAfterTimeModeLoadedTest();
    }

    @Test
    public void PracticeModeAndSelectCorrectAndChangeToLandAndLoseAndValidateScoreTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        SelectCorrectAnswer(1);

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        LoseGame(employeeViewModel.GetRandomListOf6().indexOf(employeeViewModel.GetRandomEmployee()));

        ValidateGameOverScore("1");
    }

    @Test
    public void TimedModeAndSelectCorrectAndChangeToLandAndLoseAndValidateScoreTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        SelectCorrectAnswer(1);

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        WaitToFinish((int) gameplayViewModel.getTimeModeDuration());

        ValidateGameOverScore("1/1");
    }

    @Test
    public void PracticeModeAndSelectCorrectAndChangeToLandAndBackToPortAndLoseAndValidateScoreTest()
    {
        WaitForDataAndClickMode(R.id.practiceModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        SelectCorrectAnswer(1);

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        LoseGame(employeeViewModel.GetRandomListOf6().indexOf(employeeViewModel.GetRandomEmployee()));

        ValidateGameOverScore("1");
    }

    @Test
    public void TimedModeAndSelectCorrectAndChangeToLandAndBackToPortAndLoseAndValidateScoreTest()
    {
        WaitForDataAndClickMode(R.id.timedModeBtn);

        //Wait for picasso loader to finish
        WaitToFinish();

        SelectCorrectAnswer(1);

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ChangeOrientationTo(activity, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        WaitToFinish((int) gameplayViewModel.getTimeModeDuration());

        ValidateGameOverScore("1/1");
    }

    private int GetEmployeeImgVwResId(int index)
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

    private int GetResultVwResIdFromSelectedIndex(int index)
    {
        switch (index)
        {
            case 0:

                return R.id.resultView1;

            case 1:

                return R.id.resultView2;

            case 2:

                return R.id.resultView3;

            case 3:

                return R.id.resultView4;

            case 4:

                return R.id.resultView5;

            default:

                return R.id.resultView6;
        }
    }

    private int GetResultVwResIdFromSelectedResId(int resId)
    {
        switch (resId)
        {
            case R.id.employeeImage:

                return R.id.resultView1;

            case R.id.employeeImage2:

                return R.id.resultView2;

            case R.id.employeeImage3:

                return R.id.resultView3;

            case R.id.employeeImage4:

                return R.id.resultView4;

            case R.id.employeeImage5:

                return R.id.resultView5;

            default:

                return R.id.resultView6;
        }
    }

    /**
     *
     * @return Selected view res Id
     */
    private int LoseGame(int randomEmployeeIndex)
    {
        int resId = randomEmployeeIndex == 0 ? R.id.employeeImage2 :R.id.employeeImage;

        onView(withId(resId)).perform(click());

        return resId;
    }

    /**
     *
     * @param correctCount How many times should select the correct answer
     *
     * @return The index of the last correct answer
     */
    private int SelectCorrectAnswer(int correctCount)
    {
        int selectedIndex = 0;

        while (correctCount > 0)
        {
            correctCount--;

            EmployeeInfo randomEmployee = employeeViewModel.GetRandomEmployee();

            selectedIndex = employeeViewModel.GetRandomListOf6().indexOf(randomEmployee);

            onView(withId(GetEmployeeImgVwResId(selectedIndex))).perform(click());

            WaitToFinish();
        }

        return selectedIndex;
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

    private void WaitToFinish(int sleep)
    {
        try
        {
            Thread.sleep(sleep);
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
        WaitToFinish();

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
        WaitToFinish();

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

    /**
     *
     * @param expectedValue Two valid values are "N" (N = final score) and "N/N2" (N = final score, N2 = attempts)
     */
    private void ValidateGameOverScore(String expectedValue)
    {
        WaitToFinish();

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
                                                        String.format("%s %s", activity.getText(R.string.gameOverBodyTxt), expectedValue)
                                                )
                                )
                );
    }

    private void ValidateRandomEmployeeFullName()
    {
        final EmployeeInfo RANDOM_EMPLOYEE = employeeViewModel.GetRandomEmployee();

        onView(withId(R.id.employeeName)).check(matches(withText(String.format("%s %s", RANDOM_EMPLOYEE.GetFirstName(), RANDOM_EMPLOYEE.GetLastName()))));
    }

    private void ValidateGameOverInTimedMode()
    {
        WaitToFinish((int)gameplayViewModel.getTimeModeDuration());

        onView(withText(R.string.gameOverTitleTxt)).inRoot(isDialog()).check(matches(isDisplayed()));
    }

}