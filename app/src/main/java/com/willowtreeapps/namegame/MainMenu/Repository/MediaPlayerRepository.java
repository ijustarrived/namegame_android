package com.willowtreeapps.namegame.MainMenu.Repository;

import android.content.Context;
import android.media.MediaPlayer;

import androidx.annotation.RawRes;

import com.willowtreeapps.namegame.MainMenu.Singleton.MediaPlayerInstance;

public class MediaPlayerRepository
{
    private MediaPlayer mediaPlayer;

    private int currentSongId;

    public MediaPlayerRepository(boolean looping, @RawRes int resId, Context context, int streamType)
    {
        mediaPlayer = MediaPlayerInstance.Create(looping, resId, context, streamType);

        currentSongId = resId;
    }

    public void Start()
    {
        if(mediaPlayer != null)
            mediaPlayer.start();
    }

    public void Release()
    {
        if(mediaPlayer != null)
        {
            mediaPlayer.release();

            mediaPlayer = null;
        }
    }

    public void SetOnCompleteListener(MediaPlayer.OnCompletionListener onCompletionListener)
    {
        mediaPlayer.setOnCompletionListener(onCompletionListener);
    }

    public boolean IsReleased()
    {
        return mediaPlayer == null;
    }

    public void Reset()
    {
        if(mediaPlayer != null)
            mediaPlayer.reset();
    }

    public boolean IsPlaying()
    {
        return mediaPlayer.isPlaying();
    }

    public int GetCurrentTrackId()
    {
        return currentSongId;
    }
}
