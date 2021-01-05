package com.willowtreeapps.namegame.Gameplay;

import android.view.View;
import android.widget.ImageView;

import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;

public class ImageViewDrawableMatcher
{
    public static BoundedMatcher<View, ImageView> HasDrawable()
    {
        return new DrawableMatcher(ImageView.class);
    }

    public static class DrawableMatcher extends BoundedMatcher<View, ImageView>
    {
        public DrawableMatcher(Class<? extends ImageView> expectedType)
        {
            super(expectedType);
        }

        @Override
        protected boolean matchesSafely(ImageView item)
        {
            return item.getDrawable() != null;
        }

        @Override
        public void describeTo(Description description)
        {
            description.appendText("has drawable");
        }
    }
}
