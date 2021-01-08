package com.willowtreeapps.namegame.Gameplay;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.willowtreeapps.namegame.Gameplay.Pojo.EmployeeInfo;
import com.willowtreeapps.namegame.Gameplay.Pojo.GameplayViewModel;
import com.willowtreeapps.namegame.Gameplay.Pojo.HeadshotInfo;
import com.willowtreeapps.namegame.MainMenu.Pojo.EmployeeViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.MainMenuViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.MediaPlayerViewModel;
import com.willowtreeapps.namegame.MainMenu.Pojo.SoundPoolViewModel;
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
            ATTEMPTS_COUNTER_KEY = "ATTEMPTS_COUNTER_KEY",
            PROGRESSBAR_KEY = "PROGRESSBAR_KEY";

    private AppCompatActivity currentActivity;

    private List<ImageView> employeeImgVws = new ArrayList<>();

    private List<View> resultImgVws = new ArrayList<>();

    private int gameplayMode = 0,
            attemptsCounter = 0,
            correctCounter = 0;

    private EmployeeViewModel employeeViewModel;

    private MainMenuViewModel mainMenuViewModel;

    private GameplayViewModel gameplayViewModel;

    private MediaPlayerViewModel mediaPlayerViewModel;

    private SoundPoolViewModel soundPoolViewModel;

    private ProgressBar progressBar;

    private static final short ANSWER_HANDLER_DELAY = 1000;

    private static final long DEFAULT_TIMED_MODE_DURATION = 30000,
    TIME_MODE_TICK_INTERVAL = 1000;

    private CountDownTimer countDownTimer;

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

        gameplayViewModel = NameGameApplication.get(currentActivity).GetGameplayViewModel();

        mediaPlayerViewModel = NameGameApplication.get(currentActivity).GetMediaPlayerViewModel();

        soundPoolViewModel = NameGameApplication.get(currentActivity).GetSoundPoolViewModel();

        //region Get all Views

        progressBar = view.findViewById(R.id.toolbarProgress);

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
                mediaPlayerViewModel.Release();

                //== null if it's practice mode
                if(countDownTimer != null)
                    countDownTimer.cancel();

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

        if(savedInstanceState != null)
        {
            if(savedInstanceState.getBoolean(GAME_OVER_KEY))
            {
                progressBar.setProgress(0);

                CreateGameOverDialog(currentActivity);

                alertDialog.show();
            }

            else
                progressBar.setProgress(savedInstanceState.getInt(PROGRESSBAR_KEY, 100));

            attemptsCounter = savedInstanceState.getInt(ATTEMPTS_COUNTER_KEY, 1);

            correctCounter = savedInstanceState.getInt(CORRECT_COUNTER_KEY, 0);
        }

        else
            gameplayViewModel.SetTimeModeDuration(DEFAULT_TIMED_MODE_DURATION);

        switch (gameplayMode)
        {
            case GameplayDef.Mode.PRACTICE:

                TOOLBAR.setTitle(currentActivity.getResources()
                                                .getText(R.string.practiceModeBtnTxt));

                break;

            case GameplayDef.Mode.TIMED:

                progressBar.setVisibility(View.VISIBLE);

                TOOLBAR.setTitle(currentActivity.getResources()
                                                .getText(R.string.timedModeBtnTxt));

                break;
        }

        SetData(Picasso.get(), employeeViewModel.GetRandomEmployee(), employeeViewModel.GetRandomListOf6(), currentActivity, employeeImgVws, resultImgVws);
    }

    /**
     * Used to increase wait time between rounds.
     */
    private Handler waitForAnswerHandler;

    private void SetData(Picasso picasso, EmployeeInfo randomEmployee, List<EmployeeInfo> randomEmployees, FragmentActivity activity, List<ImageView> employeeImgVws, List<View> resultVws)
    {
        waitForAnswerHandler = new Handler();

        final String LAST_NAME = activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                                 ? System.lineSeparator() + randomEmployee.GetLastName()
                                 : randomEmployee.GetLastName();

        ((TextView) activity.findViewById(R.id.employeeName)).setText
                (
                        String.format("%s %s", randomEmployee.GetFirstName(), LAST_NAME)
                );

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

                    //Correct answer
                    if(employeeImgVws.get(CORRECT_EMPLOYEE_INDEX).getId() == view.getId())
                    {
                        soundPoolViewModel.Play(R.raw.correct_sfx, 0);

                        correctCounter++;

                        RESULT_VW.setBackgroundResource(R.drawable.correct_vector);

                        waitForAnswerHandler.postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                if(progressBar.getProgress() > 10)
                                    LoadNextRound(picasso, activity, employeeImgVws, resultVws, RESULT_VW);
                            }
                        }, ANSWER_HANDLER_DELAY);
                    }

                    //Incorrect answer
                    else
                    {
                        soundPoolViewModel.Play(R.raw.wrong_sfx, 0);

                        RESULT_VW.setBackgroundResource(R.drawable.incorrect_vector);

                        waitForAnswerHandler.postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                if (gameplayMode == GameplayDef.Mode.PRACTICE)
                                {
                                    //The only instance it will be null is if the orientation changes
                                    if (alertDialog == null)
                                        CreateGameOverDialog(activity);

                                    alertDialog.show();
                                }
                                else
                                    LoadNextRound(picasso, activity, employeeImgVws, resultVws, RESULT_VW);
                            }
                        }, ANSWER_HANDLER_DELAY);
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
        mediaPlayerViewModel.Reset();

        final String ALERT_MSG = activity.getText(R.string.gameOverBodyTxt).toString();

        alertDialog = new AlertDialog.Builder(activity)
                .setTitle(activity.getText(R.string.gameOverTitleTxt))
                .setMessage(gameplayMode == GameplayDef.Mode.PRACTICE ? String.format("%s %s", ALERT_MSG, correctCounter ): ALERT_MSG + String.format(" %s/%s", correctCounter, attemptsCounter))
                .setPositiveButton(activity.getText(R.string.gameOverBtnTxt), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        soundPoolViewModel.Play(R.raw.btn_sfx, 0);

                        gameplayViewModel.SetTimeModeDuration(DEFAULT_TIMED_MODE_DURATION);

                        activity.onBackPressed();
                    }
                })
                .create();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        if(countDownTimer != null)
            countDownTimer.cancel();

        outState.putInt(PROGRESSBAR_KEY, progressBar.getProgress());

        outState.putInt(ATTEMPTS_COUNTER_KEY, attemptsCounter);

        outState.putInt(CORRECT_COUNTER_KEY, correctCounter);

        if(alertDialog != null)
            outState.putBoolean(GAME_OVER_KEY, alertDialog.isShowing());

        super.onSaveInstanceState(outState);
    }

    private void LoadNextRound(Picasso picasso, FragmentActivity activity, List<ImageView> employeeImgVws, List<View> resultVws, View resultVw)
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

            resultVw.setBackgroundResource(0);

            resultVw.setVisibility(View.GONE);
        }

        else
            Toast.makeText(activity, activity.getText(R.string.randomizeEmployeesErrorMsg), Toast.LENGTH_LONG)
                 .show();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        switch (gameplayMode)
        {
            case GameplayDef.Mode.PRACTICE:

                //Play it on this switch instead of the one on the oncreate because I know this will always be called even after onpause
                mediaPlayerViewModel.PlayNewTrack(true, R.raw.practice_mode_song, currentActivity, AudioManager.STREAM_MUSIC);

                break;

            case GameplayDef.Mode.TIMED:

                //Create a new countdown if the mode wasn't done.
                if(progressBar.getProgress() > 0)
                {
                    countDownTimer = GetNewCountDownTimer(gameplayViewModel.GetTimeModeDuration(), TIME_MODE_TICK_INTERVAL);

                    countDownTimer.start();
                }

                break;
        }

    }

    @Override
    public void onPause()
    {
        super.onPause();

        mediaPlayerViewModel.Release();
    }

    private CountDownTimer GetNewCountDownTimer(long duration, long tickInterval)
    {
        return new CountDownTimer(duration, tickInterval)
        {
            @Override
            public void onTick(long l)
            {
                if(mediaPlayerViewModel.IsReleased() && progressBar.getProgress() > 30)
                    mediaPlayerViewModel.PlayNewTrack(true, R.raw.timer_slow_sfx, currentActivity, AudioManager.STREAM_MUSIC);

                else if(progressBar.getProgress() < 30 && (mediaPlayerViewModel.GetCurrentTrackId() != R.raw.timer_fast_sfx))
                    mediaPlayerViewModel.PlayNewTrack(true, R.raw.timer_fast_sfx, currentActivity, AudioManager.STREAM_MUSIC);

                gameplayViewModel.SetTimeModeDuration(l);

                //1.0 * l = converts to floating point value. *100 = converts decimal value to an integer value.
                progressBar.setProgress((int) (((1.0 * l) / DEFAULT_TIMED_MODE_DURATION) * 100));
            }

            @Override
            public void onFinish()
            {
                progressBar.setProgress(0);

                mediaPlayerViewModel.Reset();

                if (alertDialog == null)
                    CreateGameOverDialog(currentActivity);

                alertDialog.show();
            }
        };
    }
}