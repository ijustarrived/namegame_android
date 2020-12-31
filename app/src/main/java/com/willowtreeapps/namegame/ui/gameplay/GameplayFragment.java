package com.willowtreeapps.namegame.ui.gameplay;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.ui.MainMenuFragment;

public class GameplayFragment extends Fragment
{
    public static final String TAG = "GameplayFragment";

    public static GameplayFragment newInstance(Bundle args)
    {
        GameplayFragment FRAGMENT = new GameplayFragment();

        FRAGMENT.setArguments(args);

        return FRAGMENT;
    }

    private AppCompatActivity currentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        currentActivity = (AppCompatActivity)getActivity();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gameplay, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //region Init Toolbar

        final Toolbar TOOLBAR = view.findViewById(R.id.gameplayToolbar);

        currentActivity.setSupportActionBar(TOOLBAR);

        TOOLBAR.setNavigationIcon(R.drawable.back_ic_light);

        TOOLBAR.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                currentActivity.onBackPressed();
            }
        });

        //endregion

        //region Extract arguments

        final Bundle ARGS = getArguments();

        int mode = 0;

        if (ARGS != null)
        {
            if (ARGS.containsKey(MainMenuFragment.MODE_TAG))
                mode = ARGS.getInt(MainMenuFragment.MODE_TAG);
        }

        //endregion

        switch (mode)
        {
            case GameplayDef.Mode.PRACTICE:

                TOOLBAR.setTitle(currentActivity.getResources()
                                                .getText(R.string.practiceModeBtnTxt));

                break;

            case GameplayDef.Mode.TIMED:

                TOOLBAR.setTitle(currentActivity.getResources()
                                                .getText(R.string.timedModeBtnTxt));

                break;

            default:

                break;

        }
    }
}