package com.willowtreeapps.namegame.MainMenu;

import android.media.AudioAttributes;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.willowtreeapps.namegame.Gameplay.Pojo.GameplayViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.EmployeeViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.MainMenuViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.MediaPlayerViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.SoundPoolViewModel;
import com.willowtreeapps.namegame.MainMenu.Singleton.ListRandomizerInstance;
import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.core.NameGameApplication;
import com.willowtreeapps.namegame.MainMenu.FragHelper.FragHelper;

public class MainActivity extends AppCompatActivity {

    private NameGameApplication application;

    private SoundPoolViewModel soundPoolViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_game_activity);

        application = NameGameApplication.get(this);

        application.component().inject(this);

        application.SetFrag(new FragHelper(getSupportFragmentManager()));

        //Run only once
        if(savedInstanceState == null)
        {
            application.SetMainMenuViewModel(ViewModelProviders.of(this).get(MainMenuViewModel.class));

            application.GetMainMenuViewModel().SetListRandomizer(ListRandomizerInstance.GetInstance(null));

            application.SetEmployeeViewModel(ViewModelProviders.of(this).get(EmployeeViewModel.class));

            application.GetFrag()
                       .Replace(R.id.fragmentContainer, MainMenuFragment.newInstance(), MainMenuFragment.TAG, false);

            application.SetGameplayViewModel(ViewModelProviders.of(this).get(GameplayViewModel.class));

            application.SetMediaPlayerViewModel(ViewModelProviders.of(this).get(MediaPlayerViewModel.class));

            application.SetSoundPoolViewModel(ViewModelProviders.of(this).get(SoundPoolViewModel.class));
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        soundPoolViewModel = application.GetSoundPoolViewModel();

        soundPoolViewModel.Create(application.GetSoundIds(), AudioAttributes.USAGE_GAME, AudioAttributes.CONTENT_TYPE_SONIFICATION, this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        soundPoolViewModel.Release();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }
}
