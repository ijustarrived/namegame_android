package com.willowtreeapps.namegame.MainMenu;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.willowtreeapps.namegame.Gameplay.Pojo.EmployeeInfo;
import com.willowtreeapps.namegame.MainMenu.Pojo.EmployeeViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.MainMenuViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.MediaPlayerViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.SoundPoolViewModel;
import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.core.NameGameApplication;
import com.willowtreeapps.namegame.Gameplay.GameplayDef;
import com.willowtreeapps.namegame.Gameplay.GameplayFragment;
import java.util.List;

public class MainMenuFragment extends Fragment
{
    public static final String TAG = "MainMenuFragment",
    MODE_TAG = "MODE";

    public static MainMenuFragment newInstance()
    {
        return new MainMenuFragment();
    }

    private Context context;

    private MainMenuViewModel mainMenuViewModel;

    private EmployeeViewModel employeeViewModel;

    private MediaPlayerViewModel mediaPlayerViewModel;

    private SoundPoolViewModel soundPoolViewModel;

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

        mainMenuViewModel = NameGameApplication.get(context).GetMainMenuViewModel();

        employeeViewModel = NameGameApplication.get(context).GetEmployeeViewModel();

        mediaPlayerViewModel = NameGameApplication.get(context).GetMediaPlayerViewModel();

        soundPoolViewModel = NameGameApplication.get(context).GetSoundPoolViewModel();

        mainMenuViewModel.GetAllEmployees().observe(getViewLifecycleOwner(), new Observer<List<EmployeeInfo>>()
        {
            @Override
            public void onChanged(List<EmployeeInfo> employeeInfos)
            {
                employeeViewModel.SetAllEmployees(employeeInfos);

                if(!employeeInfos.isEmpty())
                {
                    try
                    {
                        employeeViewModel.GenerateNewRandomListOf6(employeeInfos, mainMenuViewModel.GetListRandomizer());

                        employeeViewModel.PickRandomEmployee(employeeViewModel.GetRandomListOf6(), mainMenuViewModel.GetListRandomizer());
                    }

                    catch (Exception ignore)
                    {
                        Toast.makeText(context, context.getText(R.string.randomizeEmployeesErrorMsg), Toast.LENGTH_LONG)
                             .show();
                    }
                }
            }
        });

        view.findViewById(R.id.practiceModeBtn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mediaPlayerViewModel.Release();

                if(!employeeViewModel.GetAllEmployees().isEmpty())
                {
                    if(!employeeViewModel.GetRandomListOf6().isEmpty())
                        ReplaceModeFragment(GameplayDef.Mode.PRACTICE, context);

                    else
                        Toast.makeText(context, context.getText(R.string.randomizeEmployeesErrorMsg), Toast.LENGTH_LONG)
                             .show();
                }

                else
                {
                    Toast.makeText(context, context.getText(R.string.internetConnectionErrorMsg), Toast.LENGTH_LONG)
                         .show();
                }
            }
        });

        view.findViewById(R.id.timedModeBtn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mediaPlayerViewModel.Release();

                if(!employeeViewModel.GetAllEmployees().isEmpty())
                {
                    if(!employeeViewModel.GetRandomListOf6().isEmpty())
                        ReplaceModeFragment(GameplayDef.Mode.TIMED, context);

                    else
                        Toast.makeText(context, context.getText(R.string.randomizeEmployeesErrorMsg), Toast.LENGTH_LONG)
                             .show();
                }

                else
                {
                    Toast.makeText(context, context.getText(R.string.internetConnectionErrorMsg), Toast.LENGTH_LONG)
                         .show();
                }
            }
        });

        if(savedInstanceState == null)
        {
            mediaPlayerViewModel.PlayNewTrack(false, R.raw.intro_sfx, context, AudioManager.STREAM_MUSIC);

            mediaPlayerViewModel.SetOnCompleteListener(new MediaPlayer.OnCompletionListener()
            {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer)
                {
                    mediaPlayerViewModel.PlayNewTrack(true, R.raw.name_game_song, context, AudioManager.STREAM_MUSIC);
                }
            });
        }

        else
            mediaPlayerViewModel.PlayNewTrack(true, R.raw.name_game_song, context, AudioManager.STREAM_MUSIC);
    }

    private void ReplaceModeFragment(@GameplayDef.Mode int mode, Context context)
    {
        final Bundle ARGS = new Bundle();

        ARGS.putInt(MODE_TAG, mode);

        NameGameApplication.get(context).GetFrag().Replace(R.id.fragmentContainer, GameplayFragment.newInstance(ARGS), GameplayFragment.TAG, true);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        //soundPoolViewModel.Play(R.raw.timer_fast_sfx, -1);

        if(mediaPlayerViewModel.IsReleased())
            mediaPlayerViewModel.PlayNewTrack(true, R.raw.name_game_song, context, AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);

        soundPoolViewModel.Release();

        mediaPlayerViewModel.Release();
    }
}