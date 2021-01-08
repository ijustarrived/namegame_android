package com.willowtreeapps.namegame.Gameplay.Pojo;

import androidx.lifecycle.ViewModel;

public class GameplayViewModel extends ViewModel
{
    private long timeModeDuration;

    public long GetTimeModeDuration()
    {
        return timeModeDuration;
    }

    public void SetTimeModeDuration(long timeModeDuration)
    {
        this.timeModeDuration = timeModeDuration;
    }
}
