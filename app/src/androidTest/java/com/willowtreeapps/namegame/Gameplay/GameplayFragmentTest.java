package com.willowtreeapps.namegame.Gameplay;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.rule.ActivityTestRule;

import com.willowtreeapps.namegame.MainMenu.MainActivity;
import com.willowtreeapps.namegame.MainMenu.Pojo.MainMenuViewModel;
import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.core.NameGameApplication;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.willowtreeapps.namegame.Gameplay.ImageViewDrawableMatcher.HasDrawable;
import static org.hamcrest.Matchers.not;

public class GameplayFragmentTest
{
    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity activity;

    private MainMenuViewModel mainMenuViewModel;

    @Before
    public void Init()
    {
        activity = activityActivityTestRule.getActivity();

        mainMenuViewModel = NameGameApplication.get(activity)
                                               .GetMainMenuViewModel();
    }

    //Must comment the error and placeholder options of the picasso load or this test might pass with erroneous results.
    @Test
    public void WaitForDataAndClickTimedModeAndVerifyIfImageWasLoadedTest()
    {
        try
        {
            //Wait for the main fragment observer to finish
            Thread.sleep(1000);
        }

        catch (InterruptedException e)
        {
            throw new AssertionError(e.getMessage());
        }

        onView(withId(R.id.timedModeBtn)).perform(click());

        try
        {
            //Wait for picasso loader to finish
            Thread.sleep(1000);
        }

        catch (InterruptedException e)
        {
            throw new AssertionError(e.getMessage());
        }

        onView(withId(R.id.employeeImage)).check(matches(HasDrawable()));
    }

    @Test
    public void WaitForDataAndClickTimedModeAndVerifyIfEmployeeFullNameWasSetTest()
    {
        try
        {
            //Wait for the main fragment observer to finish
            Thread.sleep(1000);
        }

        catch (InterruptedException e)
        {
            throw new AssertionError(e.getMessage());
        }

        onView(withId(R.id.timedModeBtn)).perform(click());

        try
        {
            //Wait for picasso loader to finish
            Thread.sleep(1000);
        }

        catch (InterruptedException e)
        {
            throw new AssertionError(e.getMessage());
        }

        onView(withId(R.id.employeeName)).check(matches(not(withText(""))));
    }
}