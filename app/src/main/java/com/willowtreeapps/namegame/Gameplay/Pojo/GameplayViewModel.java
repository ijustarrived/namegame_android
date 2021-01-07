package com.willowtreeapps.namegame.Gameplay.Pojo;

import androidx.lifecycle.ViewModel;

public class GameplayViewModel extends ViewModel
{
    private long timeModeDuration;

    public long getTimeModeDuration()
    {
        return timeModeDuration;
    }

    public void setTimeModeDuration(long timeModeDuration)
    {
        this.timeModeDuration = timeModeDuration;
    }
}
