package com.willowtreeapps.namegame.MainMenu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.willowtreeapps.namegame.MainMenu.Pojo.EmployeeViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.MainMenuViewModel;
import com.willowtreeapps.namegame.MainMenu.Singleton.ListRandomizerInstance;
import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.core.NameGameApplication;
import com.willowtreeapps.namegame.MainMenu.FragHelper.FragHelper;

public class MainActivity extends AppCompatActivity {

    private NameGameApplication application;

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
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }
}
