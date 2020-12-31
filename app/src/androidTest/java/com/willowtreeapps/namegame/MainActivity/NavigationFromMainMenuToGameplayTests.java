package com.willowtreeapps.namegame.MainActivity;

import android.content.pm.ActivityInfo;

import androidx.test.rule.ActivityTestRule;

import com.willowtreeapps.namegame.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

//If you run them all, some of the landscape and back to portrait test might fail because the thread sleep needs a bit more time
public class NavigationFromMainMenuToGameplayTests
{
    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity activity;

    @Before
    public void Init()
    {
        activity = activityActivityTestRule.getActivity();
    }

    @Test
    public void ClickPracticeModeTest()
    {
        onView(withId(R.id.practiceModeBtn)).perform(click());

        onView(withId(R.id.gameplayToolbar)).check
                (
                        matches
                                (
                                        hasDescendant
                                                (
                                                        withText(R.string.practiceModeBtnTxt)
                                                )
                                )
                );
    }

    @Test
    public void ClickTimedModeTest()
    {
        onView(withId(R.id.timedModeBtn)).perform(click());

        onView(withId(R.id.gameplayToolbar)).check
                (
                        matches
                                (
                                        hasDescendant
                                                (
                                                        withText(R.string.timedModeBtnTxt)
                                                )
                                )
                );
    }

    @Test
    public void ClickPracticeModeAndChangeToLandscapeTest()
    {
        onView(withId(R.id.practiceModeBtn)).perform(click());

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withId(R.id.gameplayToolbar)).check
                (
                        matches
                                (
                                        hasDescendant
                                                (
                                                        withText(R.string.practiceModeBtnTxt)
                                                )
                                )
                );
    }

    @Test
    public void ClickPracticeModeAndChangeBackToPortraitTest()
    {
        onView(withId(R.id.practiceModeBtn)).perform(click());

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        onView(withId(R.id.gameplayToolbar)).check
                (
                        matches
                                (
                                        hasDescendant
                                                (
                                                        withText(R.string.practiceModeBtnTxt)
                                                )
                                )
                );
    }

    @Test
    public void ClickTimedModeAndChangeToLandscapeTest()
    {
        onView(withId(R.id.timedModeBtn)).perform(click());

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withId(R.id.gameplayToolbar)).check
                (
                        matches
                                (
                                        hasDescendant
                                                (
                                                        withText(R.string.timedModeBtnTxt)
                                                )
                                )
                );
    }

    @Test
    public void ClickTimedModeAndChangeBackToPortraitTest()
    {
        onView(withId(R.id.timedModeBtn)).perform(click());

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        onView(withId(R.id.gameplayToolbar)).check
                (
                        matches
                                (
                                        hasDescendant
                                                (
                                                        withText(R.string.timedModeBtnTxt)
                                                )
                                )
                );
    }

    //Disable device window, transition animations and, animator duration or test will fail
    @Test
    public void ChangeToLandscapeAndClickPracticeModeTest()
    {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        try
        {
            //Had to it put to sleep for a very short time to allow the orientation to finish or it fails to click on the view and throws an exception .
            Thread.sleep(100);
        }

        catch (InterruptedException e)
        {
            throw new AssertionError(e.getMessage());
        }

        onView(withId(R.id.practiceModeBtn)).perform(click());

        onView(withId(R.id.gameplayToolbar)).check
                (
                        matches
                                (
                                        hasDescendant
                                                (
                                                        withText(R.string.practiceModeBtnTxt)
                                                )
                                )
                );

    }

    //Disable device window, transition animations and, animator duration or test will fail
    @Test
    public void ChangeToLandscapeAndClickTimedModeTest()
    {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        try
        {
            //Had to it put to sleep for a very short time to allow the orientation to finish or it fails to click on the view and throws an exception .
            Thread.sleep(100);
        }

        catch (InterruptedException e)
        {
            throw new AssertionError(e.getMessage());
        }

        onView(withId(R.id.timedModeBtn)).perform(click());

        onView(withId(R.id.gameplayToolbar)).check
                (
                        matches
                                (
                                        hasDescendant
                                                (
                                                        withText(R.string.timedModeBtnTxt)
                                                )
                                )
                );
    }

    //Disable device window, transition animations and, animator duration or test will fail
    @Test
    public void ChangeToLandscapeAndToPortraitAndClickPracticeModeTest()
    {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try
        {
            //Had to it put to sleep for a little longer because changing and reverting back to the original orientation takes more time.
            Thread.sleep(500);
        }

        catch (InterruptedException e)
        {
            throw new AssertionError(e.getMessage());
        }

        onView(withId(R.id.practiceModeBtn)).perform(click());

        onView(withId(R.id.gameplayToolbar)).check
                (
                        matches
                                (
                                        hasDescendant
                                                (
                                                        withText(R.string.practiceModeBtnTxt)
                                                )
                                )
                );
    }

    //Disable device window, transition animations and, animator duration or test will fail
    @Test
    public void ChangeToLandscapeAndToPortraitAndClickTimedModeTest()
    {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try
        {
            //Had to it put to sleep for a little longer because changing and reverting back to the original orientation takes more time.
            Thread.sleep(500);
        }

        catch (InterruptedException e)
        {
            throw new AssertionError(e.getMessage());
        }

        onView(withId(R.id.timedModeBtn)).perform(click());

        onView(withId(R.id.gameplayToolbar)).check
                (
                        matches
                                (
                                        hasDescendant
                                                (
                                                        withText(R.string.timedModeBtnTxt)
                                                )
                                )
                );
    }
}
