package com.willowtreeapps.namegame.MainMenu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.willowtreeapps.namegame.Gameplay.Pojo.GameplayViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.EmployeeViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.MainMenuViewModel;
import com.willowtreeapps.namegame.MainMenu.Singleton.ListRandomizerInstance;
import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.core.NameGameApplication;
import com.willowtreeapps.namegame.MainMenu.FragHelper.FragHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_game_activity);

        final NameGameApplication APPLICATION = NameGameApplication.get(this);

        APPLICATION.component().inject(this);

        APPLICATION.SetFrag(new FragHelper(getSupportFragmentManager()));

        //Run only once
        if(savedInstanceState == null)
        {
            APPLICATION.SetMainMenuViewModel(ViewModelProviders.of(this).get(MainMenuViewModel.class));

            APPLICATION.GetMainMenuViewModel().SetListRandomizer(ListRandomizerInstance.GetInstance(null));

            APPLICATION.SetEmployeeViewModel(ViewModelProviders.of(this).get(EmployeeViewModel.class));

            APPLICATION.GetFrag()
                       .Replace(R.id.fragmentContainer, MainMenuFragment.newInstance(), MainMenuFragment.TAG, false);

            APPLICATION.SetGameplayViewModel(ViewModelProviders.of(this).get(GameplayViewModel.class));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }
}
