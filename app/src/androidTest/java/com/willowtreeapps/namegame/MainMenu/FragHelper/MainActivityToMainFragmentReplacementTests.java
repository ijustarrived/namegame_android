package com.willowtreeapps.namegame.MainMenu.FragHelper;

import android.content.pm.ActivityInfo;

import androidx.test.rule.ActivityTestRule;

import com.willowtreeapps.namegame.core.NameGameApplication;
import com.willowtreeapps.namegame.MainMenu.MainMenuFragment;
import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.MainMenu.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityToMainFragmentReplacementTests
{
    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity activity;

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

        onView
                (
                        withText
                                (
                                        activity.getBaseContext()
                                                .getResources()
                                                .getText(R.string.practiceModeBtnTxt)
                                                .toString()
                                )
                )
                .check
                        (
                                matches(isDisplayed())
                        );
    }

    @Test
    public void ChangeOrientationFromPortraitToLandscapeTest()
    {
        NameGameApplication.get(activity).GetFrag().Replace(R.id.fragmentContainer, MainMenuFragment.newInstance(), MainMenuFragment.TAG, false);

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView
                (
                        withText
                                (
                                        activity.getBaseContext()
                                                .getResources()
                                                .getText(R.string.practiceModeBtnTxt)
                                                .toString()
                                )
                )
                .check
                        (
                                matches(isDisplayed())
                        );
    }

    @Test
    public void ReplaceActivityWithMainMenuFragmentTest()
    {
        NameGameApplication.get(activity)
                .GetFrag()
                .Replace(R.id.fragmentContainer, MainMenuFragment.newInstance(), MainMenuFragment.TAG, false);

        onView
                (
                        withText
                                (
                                        activity.getBaseContext()
                                                .getResources()
                                                .getText(R.string.practiceModeBtnTxt)
                                                .toString()
                                )
                )
                .check
                        (
                                matches(isDisplayed())
                        );
    }
}