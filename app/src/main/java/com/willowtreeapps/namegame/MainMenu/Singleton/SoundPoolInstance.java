package com.willowtreeapps.namegame.MainMenu.Singleton;

import android.media.AudioAttributes;
import android.media.SoundPool;

public class SoundPoolInstance
{
    private static SoundPool soundPool;

    public static synchronized SoundPool GetInstance(int maxStreams, int usage, int contentType)
    {
        if(soundPool == null)
        {
            final AudioAttributes AUDIO_ATTRIBUTES = new AudioAttributes.Builder()
                    .setUsage(usage)
                    .setContentType(contentType)
                //.setUsage(AudioAttributes.USAGE_GAME)
                //.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(maxStreams)
                    .setAudioAttributes(AUDIO_ATTRIBUTES)
                    .build();
        }

        return soundPool;
    }
}
