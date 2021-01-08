package com.willowtreeapps.namegame.MainMenu.Pojo;

import android.content.Context;
import android.media.SoundPool;

import androidx.annotation.RawRes;
import androidx.lifecycle.ViewModel;

import com.willowtreeapps.namegame.MainMenu.Repository.SoundPoolRepository;

import java.util.List;

public class SoundPoolViewModel extends ViewModel
{
    private SoundPoolRepository soundPoolRepository;

    public void Create(List<Integer> soundIds, int usage, int contentType, Context context)
    {
        soundPoolRepository = new SoundPoolRepository(soundIds, usage, contentType, context);
    }

    public void Play(@RawRes int resId, int loopTimes)
    {
        soundPoolRepository.Play(resId, loopTimes);
    }

    public void Release()
    {
        soundPoolRepository.Release();
    }
}
