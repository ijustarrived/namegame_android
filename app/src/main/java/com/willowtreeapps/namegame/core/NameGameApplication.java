package com.willowtreeapps.namegame.core;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;

import com.willowtreeapps.namegame.Gameplay.Pojo.GameplayViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.EmployeeViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.MainMenuViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.MediaPlayerViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.SoundPoolViewModel;
import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.network.NetworkModule;
import com.willowtreeapps.namegame.MainMenu.FragHelper.FragHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NameGameApplication extends Application {

    private ApplicationComponent component;

    public static NameGameApplication get(@NonNull Context context) {
        return (NameGameApplication) context.getApplicationContext();
    }

    private FragHelper frag;

    public FragHelper GetFrag()
    {
        return frag;
    }

    public void SetFrag(FragHelper frag)
    {
        this.frag = frag;
    }

    private MainMenuViewModel mainMenuViewModel;

    public void SetMainMenuViewModel(MainMenuViewModel mainMenuViewModel)
    {
        this.mainMenuViewModel = mainMenuViewModel;
    }

    public MainMenuViewModel GetMainMenuViewModel()
    {
        return mainMenuViewModel;
    }

    private EmployeeViewModel employeeViewModel;

    private GameplayViewModel gameplayViewModel;

    private MediaPlayerViewModel mediaPlayerViewModel;

    private SoundPoolViewModel soundPoolViewModel;

    private List<Integer> soundIds = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();

        soundIds = Arrays.asList(R.raw.correct_sfx, R.raw.intro_sfx, R.raw.time_up_sfx, R.raw.wrong_sfx, R.raw.timer_slow_sfx, R.raw.timer_fast_sfx);
    }

    public ApplicationComponent component() {
        return component;
    }

    protected ApplicationComponent buildComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule("https://willowtreeapps.com/"))
                .build();
    }

    public EmployeeViewModel GetEmployeeViewModel()
    {
        return employeeViewModel;
    }

    public void SetEmployeeViewModel(EmployeeViewModel employeeViewModel)
    {
        this.employeeViewModel = employeeViewModel;
    }

    public GameplayViewModel GetGameplayViewModel()
    {
        return gameplayViewModel;
    }

    public void SetGameplayViewModel(GameplayViewModel gameplayViewModel)
    {
        this.gameplayViewModel = gameplayViewModel;
    }

    public SoundPoolViewModel GetSoundPoolViewModel()
    {
        return soundPoolViewModel;
    }

    public void SetSoundPoolViewModel(SoundPoolViewModel soundPoolViewModel)
    {
        this.soundPoolViewModel = soundPoolViewModel;
    }

    public MediaPlayerViewModel GetMediaPlayerViewModel()
    {
        return mediaPlayerViewModel;
    }

    public void SetMediaPlayerViewModel(MediaPlayerViewModel mediaPlayerViewModel)
    {
        this.mediaPlayerViewModel = mediaPlayerViewModel;
    }

    public List<Integer> GetSoundIds()
    {
        return soundIds;
    }
}
