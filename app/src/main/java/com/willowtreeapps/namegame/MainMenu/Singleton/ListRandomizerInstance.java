package com.willowtreeapps.namegame.MainMenu.Singleton;

import androidx.annotation.Nullable;

import com.willowtreeapps.namegame.core.ListRandomizer;

import java.util.Random;

public class ListRandomizerInstance
{
    private static ListRandomizer listRandomizer;

    /**
     * @param random If null, will use a new instance of Random.
     */
    public static synchronized ListRandomizer GetInstance(@Nullable Random random)
    {
        if(listRandomizer == null)
            listRandomizer = new ListRandomizer(random == null ? new Random() : random);

        return listRandomizer;
    }
}
