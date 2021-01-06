package com.willowtreeapps.namegame.Gameplay;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.willowtreeapps.namegame.Gameplay.Pojo.EmployeeInfo;
import com.willowtreeapps.namegame.Gameplay.Pojo.HeadshotInfo;
import com.willowtreeapps.namegame.MainMenu.Pojo.EmployeeViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.MainMenuViewModel;
import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.MainMenu.MainMenuFragment;
import com.willowtreeapps.namegame.core.NameGameApplication;

import java.util.ArrayList;
import java.util.List;

public class GameplayFragment extends Fragment
{
    public static final String TAG = "GameplayFragment",
            GAME_OVER_KEY = "GAME_OVER_KEY",
            CORRECT_COUNTER_KEY = "CORRECT_COUNTER_KEY",
            ATTEMPTS_COUNTER_KEY = "ATTEMPTS_COUNTER_KEY";

    private AppCompatActivity currentActivity;

    private List<ImageView> employeeImgVws = new ArrayList<>();

    private List<View> resultImgVws = new ArrayList<>();

    private int gameplayMode = 0,
            attemptsCounter = 0,
            correctCounter = 0;

    private EmployeeViewModel employeeViewModel;

    private MainMenuViewModel mainMenuViewModel;

    private GameplayViewModule gameplayViewModule;

    public static GameplayFragment newInstance(Bundle args)
    {
        GameplayFragment FRAGMENT = new GameplayFragment();

        FRAGMENT.setArguments(args);

        return FRAGMENT;
    }

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

        mainMenuViewModel = NameGameApplication.get(currentActivity).GetMainMenuViewModel();

        employeeViewModel = NameGameApplication.get(currentActivity).GetEmployeeViewModel();

        gameplayViewModule = NameGameApplication.get(currentActivity).GetGameplayViewModule();

        //region Get all Views

        //region Employees

        employeeImgVws.add(view.findViewById(R.id.employeeImage));

        employeeImgVws.add(view.findViewById(R.id.employeeImage2));

        employeeImgVws.add(view.findViewById(R.id.employeeImage3));

        employeeImgVws.add(view.findViewById(R.id.employeeImage4));

        employeeImgVws.add(view.findViewById(R.id.employeeImage5));

        employeeImgVws.add(view.findViewById(R.id.employeeImage6));

        //endregion

        //region Results

        resultImgVws.add(view.findViewById(R.id.resultView1));

        resultImgVws.add(view.findViewById(R.id.resultView2));

        resultImgVws.add(view.findViewById(R.id.resultView3));

        resultImgVws.add(view.findViewById(R.id.resultView4));

        resultImgVws.add(view.findViewById(R.id.resultView5));

        resultImgVws.add(view.findViewById(R.id.resultView6));

        //endregion

        //endregion

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

        if (ARGS != null)
        {
            if (ARGS.containsKey(MainMenuFragment.MODE_TAG))
                gameplayMode = ARGS.getInt(MainMenuFragment.MODE_TAG);
        }

        //endregion

        switch (gameplayMode)
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

        final EmployeeInfo RANDOM_EMPLOYEE = employeeViewModel.GetRandomEmployee();

        ((TextView)view.findViewById(R.id.employeeName)).setText
                (
                        String.format("%s %s", RANDOM_EMPLOYEE.GetFirstName(), RANDOM_EMPLOYEE.GetLastName())
                );

        SetData(Picasso.get(), RANDOM_EMPLOYEE, employeeViewModel.GetRandomListOf6(), currentActivity, employeeImgVws, resultImgVws);

        if(savedInstanceState != null)
        {
            if(savedInstanceState.getBoolean(GAME_OVER_KEY))
            {
                CreateGameOverDialog(currentActivity);

                alertDialog.show();
            }

            attemptsCounter = savedInstanceState.getInt(ATTEMPTS_COUNTER_KEY, 1);

            correctCounter = savedInstanceState.getInt(CORRECT_COUNTER_KEY, 0);
        }
    }

    /**
     * Used to increase wait time between rounds.
     */
    private Handler waitForAnswerHandler;

    private void SetData(Picasso picasso, EmployeeInfo randomEmployee, List<EmployeeInfo> randomEmployees, FragmentActivity activity, List<ImageView> employeeImgVws, List<View> resultVws)
    {
        waitForAnswerHandler = new Handler();

        for (int i = 0; i < randomEmployees.size(); i++)
        {
            final EmployeeInfo EMPLOYEE_INFO = randomEmployees.get(i);

            final ImageView IMG_VW = employeeImgVws.get(i);

            IMG_VW.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    attemptsCounter++;

                    final int CORRECT_EMPLOYEE_INDEX = randomEmployees.indexOf(randomEmployee);

                    final View RESULT_VW = resultVws.get(employeeImgVws.indexOf(view));

                    RESULT_VW.setVisibility(View.VISIBLE);

                    if(employeeImgVws.get(CORRECT_EMPLOYEE_INDEX).getId() == view.getId())
                    {
                        correctCounter++;

                        RESULT_VW.setBackgroundResource(R.drawable.correct_vector);

                        waitForAnswerHandler.postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                List<EmployeeInfo> newRandomList = new ArrayList<>();

                                EmployeeInfo newRandomEmployee = new EmployeeInfo("", "", "", new HeadshotInfo("", "", "", ""));

                                try
                                {
                                    newRandomList = employeeViewModel.GenerateNewRandomListOf6(employeeViewModel.GetAllEmployees(), mainMenuViewModel.GetListRandomizer());

                                    newRandomEmployee = employeeViewModel.PickRandomEmployee(newRandomList, mainMenuViewModel.GetListRandomizer());
                                }

                                catch (Exception ignore)
                                {
                                }

                                if(!newRandomEmployee.GetId().isEmpty())
                                {
                                    SetData(picasso, newRandomEmployee, newRandomList, activity, employeeImgVws, resultVws);

                                    RESULT_VW.setBackgroundResource(0);

                                    RESULT_VW.setVisibility(View.GONE);
                                }

                                else
                                    Toast.makeText(activity, activity.getText(R.string.randomizeEmployeesErrorMsg), Toast.LENGTH_LONG)
                                         .show();
                            }
                        }, gameplayViewModule.getAnswerHandlerDelay());
                    }

                    else
                    {
                        RESULT_VW.setBackgroundResource(R.drawable.incorrect_vector);

                        waitForAnswerHandler.postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                switch (gameplayMode)
                                {
                                    case GameplayDef.Mode.PRACTICE:

                                        //The only instance it will be null is if the orientation changes
                                        if(alertDialog == null)
                                            CreateGameOverDialog(activity);

                                        alertDialog.show();

                                        break;

                                    case GameplayDef.Mode.TIMED:

                                        break;

                                }
                            }
                        }, gameplayViewModule.getAnswerHandlerDelay());
                    }
                }
            });

            IMG_VW.setContentDescription(EMPLOYEE_INFO.GetHeadshotInfo().GetAlt());

            picasso.load(Uri.parse("https:" + EMPLOYEE_INFO.GetHeadshotInfo().GetUrl()))
                   .resize((int)currentActivity.getResources().getDimension(R.dimen.imgDimension), (int)currentActivity.getResources().getDimension(R.dimen.imgDimension))
                   .centerCrop()
                   .placeholder(R.drawable.face_ic_dark)
                   .error(R.drawable.error_ic)
                   .into(IMG_VW);
        }
    }

    private AlertDialog alertDialog;

    private void CreateGameOverDialog(FragmentActivity activity)
    {
        final String ALERT_MSG = activity.getText(R.string.gameOverBodyTxt).toString();

        alertDialog = new AlertDialog.Builder(activity)
                .setTitle(activity.getText(R.string.gameOverTitleTxt))
                .setMessage(gameplayMode == GameplayDef.Mode.PRACTICE ? String.format("%s %s", ALERT_MSG, correctCounter ): ALERT_MSG + String.format(" %s/%s", correctCounter, attemptsCounter))
                .setPositiveButton(activity.getText(R.string.gameOverBtnTxt), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        activity.onBackPressed();
                    }
                })
                .create();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        outState.putInt(ATTEMPTS_COUNTER_KEY, attemptsCounter);

        outState.putInt(CORRECT_COUNTER_KEY, correctCounter);

        if(alertDialog != null)
            outState.putBoolean(GAME_OVER_KEY, alertDialog.isShowing());

        super.onSaveInstanceState(outState);
    }
}