package com.willowtreeapps.namegame.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.core.NameGameApplication;
import com.willowtreeapps.namegame.ui.gameplay.GameplayDef;
import com.willowtreeapps.namegame.ui.gameplay.GameplayFragment;

public class MainMenuFragment extends Fragment
{
    public static final String TAG = "MainMenuFragment",
    MODE_TAG = "MODE";

    public static MainMenuFragment newInstance()
    {
        return new MainMenuFragment();
    }

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        context = getContext();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.practiceModeBtn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ReplaceModeFragment(GameplayDef.Mode.PRACTICE, context);
            }
        });

        view.findViewById(R.id.timedModeBtn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ReplaceModeFragment(GameplayDef.Mode.TIMED, context);
            }
        });
    }

    private void ReplaceModeFragment( @GameplayDef.Mode int mode, Context context)
    {
        final Bundle ARGS = new Bundle();

        ARGS.putInt(MODE_TAG, mode);

        NameGameApplication.get(context).GetFrag().Replace(R.id.fragmentContainer, GameplayFragment.newInstance(ARGS), GameplayFragment.TAG, true);
    }
}