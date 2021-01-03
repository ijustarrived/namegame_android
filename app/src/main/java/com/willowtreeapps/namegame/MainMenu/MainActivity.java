package com.willowtreeapps.namegame.MainMenu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.willowtreeapps.namegame.MainMenu.Pojo.MainMenuViewModel;
import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.core.NameGameApplication;
import com.willowtreeapps.namegame.MainMenu.FragHelper.FragHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_game_activity);

        NameGameApplication.get(this).component().inject(this);

        NameGameApplication.get(this)
                           .SetFrag(new FragHelper(getSupportFragmentManager()));

        //Run only once
        if(savedInstanceState == null)
        {
            NameGameApplication.get(this).SetMainMenuViewModel(ViewModelProviders.of(this).get(MainMenuViewModel.class));

            NameGameApplication.get(this)
                               .GetFrag()
                               .Replace(R.id.fragmentContainer, MainMenuFragment.newInstance(), MainMenuFragment.TAG, false);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }
}
