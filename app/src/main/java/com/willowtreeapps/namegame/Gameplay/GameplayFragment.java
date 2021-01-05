package com.willowtreeapps.namegame.Gameplay;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.willowtreeapps.namegame.Gameplay.Pojo.EmployeeInfo;
import com.willowtreeapps.namegame.MainMenu.Pojo.MainMenuViewModel;
import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.MainMenu.MainMenuFragment;
import com.willowtreeapps.namegame.core.NameGameApplication;

import java.util.ArrayList;
import java.util.List;

public class GameplayFragment extends Fragment
{
    public static final String TAG = "GameplayFragment";

    private AppCompatActivity currentActivity;

    private List<ImageView> employeeImgVws = new ArrayList<>(),
    resultImgVws = new ArrayList<>();

    private Picasso picasso;

    private int gameplayMode = 0,
            triesCounter = 1,
    correctCounter = 0;

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

        final MainMenuViewModel MAIN_MENU_VIEW_MODEL = NameGameApplication.get(currentActivity).GetMainMenuViewModel();

        //region Get all imgVws

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

        final EmployeeInfo RANDOM_EMPLOYEE = MAIN_MENU_VIEW_MODEL.GetRandomEmployee();

        ((TextView)view.findViewById(R.id.employeeName)).setText
                (
                        String.format("%s %s", RANDOM_EMPLOYEE.GetFirstName(), RANDOM_EMPLOYEE.GetLastName())
                );

        picasso = Picasso.get();

        SetData(picasso, RANDOM_EMPLOYEE, MAIN_MENU_VIEW_MODEL.GetRandomListOf6(), currentActivity);

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
    }

    private void SetData(Picasso picasso, EmployeeInfo randomEmployee, List<EmployeeInfo> randomEmployees, FragmentActivity activity)
    {
        String ALERT_MSG = activity.getText(R.string.gameOverBodyTxt).toString();

        final AlertDialog ALERT_DIALOG = new AlertDialog.Builder(activity)
                .setTitle(activity.getText(R.string.gameOverTitleTxt))
                .setMessage(gameplayMode == GameplayDef.Mode.PRACTICE ? ALERT_MSG + correctCounter : ALERT_MSG + String.format("%s/%s", correctCounter, triesCounter))
                .setCancelable(true)
                .setPositiveButton(activity.getText(R.string.gameOverBtnTxt), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        activity.onBackPressed();
                    }
                })
                .create();

        for (int i = 0; i < randomEmployees.size(); i++)
        {
            final EmployeeInfo EMPLOYEE_INFO = randomEmployees.get(i);

            final ImageView IMG_VW = employeeImgVws.get(i);

            IMG_VW.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    triesCounter++;

                    if(randomEmployee.GetId().equals(EMPLOYEE_INFO.GetId()))
                    {
                        correctCounter++;
                    }

                    else
                    {
                        switch (gameplayMode)
                        {
                            case GameplayDef.Mode.PRACTICE:

                                ALERT_DIALOG.show();

                                break;

                            case GameplayDef.Mode.TIMED:

                                break;

                        }
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
}