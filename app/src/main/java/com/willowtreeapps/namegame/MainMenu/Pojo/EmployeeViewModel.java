package com.willowtreeapps.namegame.MainMenu.Pojo;

import androidx.lifecycle.ViewModel;

import com.willowtreeapps.namegame.Gameplay.Pojo.EmployeeInfo;
import com.willowtreeapps.namegame.core.ListRandomizer;

import java.util.ArrayList;
import java.util.List;

public class EmployeeViewModel extends ViewModel
{
    private EmployeeInfo randomEmployee;

    private List<EmployeeInfo> allEmployees;

    private List<EmployeeInfo> randomListOf6;

    public EmployeeViewModel()
    {
        randomListOf6 = new ArrayList<>();

        allEmployees = new ArrayList<>();
    }

    public EmployeeInfo PickRandomEmployee(List<EmployeeInfo> employeeInfos, ListRandomizer listRandomizer) throws Exception
    {
        randomEmployee = listRandomizer.GenerateRandomItemFromList(employeeInfos);

        return randomEmployee;
    }

    public EmployeeInfo GetRandomEmployee()
    {
        return randomEmployee;
    }

    public List<EmployeeInfo> GetRandomListOf6()
    {
        return randomListOf6;
    }

    public List<EmployeeInfo> GenerateNewRandomListOf6(List<EmployeeInfo> randomSeed, ListRandomizer listRandomizer) throws Exception
    {
        randomListOf6 = listRandomizer.GenerateRandomListFromList(randomSeed, 6);

        return randomListOf6;
    }

    public List<EmployeeInfo> GetAllEmployees()
    {
        return allEmployees;
    }

    public void SetAllEmployees(List<EmployeeInfo> allEmployees)
    {
        this.allEmployees = allEmployees;
    }
}
