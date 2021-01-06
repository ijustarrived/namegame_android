package com.willowtreeapps.namegame.Gameplay;

import androidx.lifecycle.ViewModel;

public class GameplayViewModule extends ViewModel
{
    private int answerHandlerDelay;

    public GameplayViewModule()
    {
        answerHandlerDelay = 1000;
    }

    public int getAnswerHandlerDelay()
    {
        return answerHandlerDelay;
    }
}
