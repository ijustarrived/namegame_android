package com.willowtreeapps.namegame.Gameplay;

import android.view.View;

import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;

public class ViewBackgroundMatcher
{
    public static BoundedMatcher<View, View> HasBackground()
    {
        return new Matcher(View.class);
    }

    public static class Matcher extends BoundedMatcher<View, View>
    {
        public Matcher(Class<? extends View> expectedType)
        {
            super(expectedType);
        }

        @Override
        protected boolean matchesSafely(View item)
        {
            return item.getBackground() != null;
        }

        @Override
        public void describeTo(Description description)
        {
            description.appendText("has background");
        }
    }
}
