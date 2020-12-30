package com.willowtreeapps.namegame.ui.gameplay;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class GameplayDef
{
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Mode.PRACTICE, Mode.TIMED})
    public @interface Mode
    {
        int PRACTICE = 1,
                TIMED = 2;
    }
}
