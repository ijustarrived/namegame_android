package com.willowtreeapps.namegame.MainMenu;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.rule.ActivityTestRule;

import com.willowtreeapps.namegame.MainMenu.Pojo.EmployeeInfo;
import com.willowtreeapps.namegame.core.NameGameApplication;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.List;

import static org.junit.Assert.assertFalse;


public class APITests
{
    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    //Run tasks synchronously
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private MainActivity activity;

    @Before
    public void Init()
    {
        activity = activityActivityTestRule.getActivity();
    }

    @Test
    public void GetAllEmployeesTest()
    {
        final List<EmployeeInfo> employeeInfos = NameGameApplication.get(activity)
                                                              .GetMainMenuViewModel()
                                                              .GetAllEmployees()
                                                              .getValue();

        assertFalse(employeeInfos.isEmpty());
    }
}
