package com.willowtreeapps.namegame.MainMenu.Singleton;

import android.media.AudioAttributes;
import android.media.SoundPool;

public class SoundPoolInstance
{
    public static SoundPool Create(int maxStreams, int usage, int contentType)
    {
        final AudioAttributes AUDIO_ATTRIBUTES = new AudioAttributes.Builder()
                .setUsage(usage)
                .setContentType(contentType)
                .build();

        return new SoundPool.Builder()
                .setMaxStreams(maxStreams)
                .setAudioAttributes(AUDIO_ATTRIBUTES)
                .build();
    }
}
