package com.willowtreeapps.namegame.MainMenu;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.rule.ActivityTestRule;

import com.willowtreeapps.namegame.Gameplay.Pojo.EmployeeInfo;
import com.willowtreeapps.namegame.MainMenu.Pojo.EmployeeApiInfo;
import com.willowtreeapps.namegame.MainMenu.Pojo.MainMenuViewModel;
import com.willowtreeapps.namegame.core.NameGameApplication;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class APITests
{
    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    //Run tasks synchronously
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private MainActivity activity;

    private MainMenuViewModel mainMenuViewModel;

    @Before
    public void Init()
    {
        activity = activityActivityTestRule.getActivity();

        mainMenuViewModel = NameGameApplication.get(activity)
                                               .GetMainMenuViewModel();
    }

    @Test
    public void GetAllEmployeesTest()
    {
        mainMenuViewModel.GetAllEmployees().observeForever(new Observer<List<EmployeeInfo>>()
        {
            @Override
            public void onChanged(List<EmployeeInfo> employeeInfos)
            {
                assertFalse(employeeInfos.isEmpty());
            }
        });
    }

    @Test
    public void GenerateListOf6RandomEmployeesTest()
    {
        mainMenuViewModel.GetAllEmployees().observeForever(new Observer<List<EmployeeInfo>>()
        {
            @Override
            public void onChanged(List<EmployeeInfo> employeeInfos)
            {
                try
                {
                    assertEquals(6 ,mainMenuViewModel.GenerateNewRandomListOf6(employeeInfos, null).size());
                }

                catch (Exception e)
                {
                    throw new AssertionError(e.getMessage());
                }
            }
        });
    }
}
