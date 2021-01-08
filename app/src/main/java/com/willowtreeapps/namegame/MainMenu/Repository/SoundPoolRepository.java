package com.willowtreeapps.namegame.MainMenu.Repository;

import android.content.Context;
import android.media.SoundPool;

import androidx.annotation.RawRes;

import com.willowtreeapps.namegame.MainMenu.Singleton.SoundPoolInstance;

import java.util.ArrayList;
import java.util.List;

public class SoundPoolRepository
{
    private SoundPool soundPool;

    private List<Integer> soundPoolIds = new ArrayList<>(),
    soundIds;

    public SoundPoolRepository(List<Integer> soundIds, int usage, int contentType, Context context)
    {
        soundPool = SoundPoolInstance.Create(soundIds.size(), usage, contentType);

        this.soundIds = soundIds;

        for (int s: soundIds)
        {
            soundPoolIds.add(soundPool.load(context, s, 1));
        }
    }

    public void Play(@RawRes int soundId, int loopTimes)
    {
        soundPool.autoPause();

        soundPool.play(soundPoolIds.get(this.soundIds.indexOf(soundId)),1, 1, 0, loopTimes,1);
    }

    public void Release()
    {
        if(soundPool != null)
        {
            soundPool.release();

            soundPool = null;
        }
    }
}
