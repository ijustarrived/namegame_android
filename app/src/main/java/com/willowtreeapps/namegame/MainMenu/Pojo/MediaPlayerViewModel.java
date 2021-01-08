package com.willowtreeapps.namegame.MainMenu.Pojo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.willowtreeapps.namegame.MainMenu.Repository.MediaPlayerRepository;
import com.willowtreeapps.namegame.R;

public class MediaPlayerViewModel extends ViewModel
{
    private MediaPlayerRepository mediaPlayerRepository;

    public void Start()
    {
        mediaPlayerRepository.Start();
    }

    public void Release()
    {
        mediaPlayerRepository.Release();
    }

    public void Create(boolean looping, @RawRes int resId, Context context, int streamType)
    {
        mediaPlayerRepository = new MediaPlayerRepository(looping, resId, context, streamType);
    }

    public void PlayNewTrack(boolean looping, @RawRes int resId, Context context, int streamType)
    {
        //This is null on app launched
        if(mediaPlayerRepository != null)
            Reset();

        Create(looping, resId, context, streamType);

        Start();
    }

    public void SetOnCompleteListener(MediaPlayer.OnCompletionListener onCompletionListener)
    {
        mediaPlayerRepository.SetOnCompleteListener(onCompletionListener);
    }

    public boolean IsReleased()
    {
        return mediaPlayerRepository.IsReleased();
    }

    public void Reset()
    {
        mediaPlayerRepository.Reset();
    }

    public boolean IsPlaying()
    {
        return mediaPlayerRepository.IsPlaying();
    }

    public int GetCurrentTrackId()
    {
        return mediaPlayerRepository.GetCurrentTrackId();
    }
}
