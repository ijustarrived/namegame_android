package com.willowtreeapps.namegame.util.FragHelper;

import android.content.pm.ActivityInfo;

import androidx.test.core.app.ActivityScenario;
import androidx.test.rule.ActivityTestRule;

import com.willowtreeapps.namegame.ui.MainMenuFragment;
import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.core.NameGameApplication;
import com.willowtreeapps.namegame.ui.NameGameActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class FragHelperTest
{
    @Rule
    public ActivityTestRule<NameGameActivity> activityActivityTestRule = new ActivityTestRule<>(NameGameActivity.class);

    private NameGameActivity activity;

    @Before
    public void Init()
    {
        activity = activityActivityTestRule.getActivity();

        NameGameApplication.get(activity).SetFrag(new FragHelper(activity.getSupportFragmentManager()));
    }

    @Test
    public void ChangeOrientationFromLandscapeToPortraitTest()
    {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        NameGameApplication.get(activity).GetFrag().Replace(R.id.fragmentContainer, MainMenuFragment.newInstance(), MainMenuFragment.TAG, false);

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        onView(withText(activity.getBaseContext().getResources().getText(R.string.practiceModeBtnTxt).toString())).check(matches(isDisplayed()));
    }

    @Test
    public void ChangeOrientationFromPortraitToLandscapeTest()
    {
        NameGameApplication.get(activity).GetFrag().Replace(R.id.fragmentContainer, MainMenuFragment.newInstance(), MainMenuFragment.TAG, false);

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withText(activity.getBaseContext().getResources().getText(R.string.practiceModeBtnTxt).toString())).check(matches(isDisplayed()));
    }

    @Test
    public void ReplaceActivityWithMainMenuFragmentTest()
    {
        NameGameApplication.get(activity).GetFrag().Replace(R.id.fragmentContainer, MainMenuFragment.newInstance(), MainMenuFragment.TAG, false);

        onView(withText(activity.getBaseContext().getResources().getText(R.string.practiceModeBtnTxt).toString())).check(matches(isDisplayed()));
    }

    @Test
    public void LaunchPracticeModeFragmentTest()
    {
        onView(withId(R.id.practiceModeBtn)).perform(click());

        onView(withId(R.id.gameplayToolbarText)).check(matches(withText(R.string.practiceModeBtnTxt)));
    }

    @Test
    public void LaunchTimedModeFragmentTest()
    {
        onView(withId(R.id.timedModeBtn)).perform(click());

        onView(withId(R.id.gameplayToolbarText)).check(matches(withText(R.string.timedModeBtnTxt)));
    }
}