package com.willowtreeapps.namegame.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.core.NameGameApplication;
import com.willowtreeapps.namegame.util.FragHelper.FragHelper;

public class NameGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_game_activity);

        if(savedInstanceState == null)
        {
            NameGameApplication.get(this).component().inject(this);

            NameGameApplication.get(this)
                               .SetFrag(new FragHelper(getSupportFragmentManager()));

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
