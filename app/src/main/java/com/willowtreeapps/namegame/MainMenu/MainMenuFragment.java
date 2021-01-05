package com.willowtreeapps.namegame.MainMenu;

import android.content.Context;
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
import com.willowtreeapps.namegame.MainMenu.Pojo.EmployeeApiInfo;
import com.willowtreeapps.namegame.MainMenu.Pojo.MainMenuViewModel;
import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.core.NameGameApplication;
import com.willowtreeapps.namegame.Gameplay.GameplayDef;
import com.willowtreeapps.namegame.Gameplay.GameplayFragment;

import java.util.ArrayList;
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

    private List<EmployeeInfo> employeeInfos = new ArrayList<>();

    private MainMenuViewModel mainMenuViewModel;

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

        mainMenuViewModel.GetAllEmployees().observe(getViewLifecycleOwner(), new Observer<List<EmployeeInfo>>()
        {
            @Override
            public void onChanged(List<EmployeeInfo> employeeInfos)
            {
                MainMenuFragment.this.employeeInfos = employeeInfos;

                if(!employeeInfos.isEmpty())
                {
                    try
                    {
                        mainMenuViewModel.GenerateNewRandomListOf6(employeeInfos, null);

                        mainMenuViewModel.PickRandomEmployee(mainMenuViewModel.GetRandomListOf6());
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
                if(!employeeInfos.isEmpty())
                {
                    if(!mainMenuViewModel.GetRandomListOf6().isEmpty())
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
                if(!employeeInfos.isEmpty())
                {
                    if(!mainMenuViewModel.GetRandomListOf6().isEmpty())
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
    }

    private void ReplaceModeFragment(@GameplayDef.Mode int mode, Context context)
    {
        final Bundle ARGS = new Bundle();

        ARGS.putInt(MODE_TAG, mode);

        NameGameApplication.get(context).GetFrag().Replace(R.id.fragmentContainer, GameplayFragment.newInstance(ARGS), GameplayFragment.TAG, true);
    }
}