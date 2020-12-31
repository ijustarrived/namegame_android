package com.willowtreeapps.namegame.Gameplay;

import androidx.annotation.IntDef;

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
