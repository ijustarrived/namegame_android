package com.willowtreeapps.namegame.MainMenu.Singleton;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import androidx.annotation.RawRes;

import java.io.FileDescriptor;

public class MediaPlayerInstance
{
    public static MediaPlayer Create(boolean looping, @RawRes int resId, Context context, int streamType)
    {
        final MediaPlayer MEDIA_PLAYER = MediaPlayer.create(context, resId);

        MEDIA_PLAYER.setLooping(looping);

        MEDIA_PLAYER.setAudioStreamType(streamType);

        return MEDIA_PLAYER;
    }
}
