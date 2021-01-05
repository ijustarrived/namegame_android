package com.willowtreeapps.namegame.MainMenu.Pojo;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.willowtreeapps.namegame.Gameplay.Pojo.EmployeeInfo;
import com.willowtreeapps.namegame.MainMenu.Repository.EmployeeRepository;
import com.willowtreeapps.namegame.MainMenu.Singleton.ListRandomizerInstance;
import com.willowtreeapps.namegame.core.ListRandomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainMenuViewModel extends ViewModel
{
    private EmployeeInfo randomEmployee;

    private ListRandomizer listRandomizer;

    public MainMenuViewModel()
    {
        repository = new EmployeeRepository();

        randomListOf6 = new ArrayList<>();
    }

    private EmployeeRepository repository;

    public MutableLiveData<List<EmployeeInfo>> GetAllEmployees()
    {
        return repository.GetAllEmployees();
    }

    public String GetResponseFailedMsg()
    {
        return repository.GetResponseFailedMsg();
    }

    private List<EmployeeInfo> randomListOf6;

    public List<EmployeeInfo> GetRandomListOf6()
    {
        return randomListOf6;
    }

    public List<EmployeeInfo> GenerateNewRandomListOf6(List<EmployeeInfo> randomSeed, @Nullable Random random) throws Exception
    {
        listRandomizer = ListRandomizerInstance.GetInstance(random);

        randomListOf6 = listRandomizer.GenerateRandomListFromList(randomSeed, 6);

        return randomListOf6;
    }

    public EmployeeInfo PickRandomEmployee(List<EmployeeInfo> employeeInfos) throws Exception
    {
        randomEmployee = listRandomizer.GenerateRandomItemFromList(employeeInfos);

        return randomEmployee;
    }

    public EmployeeInfo GetRandomEmployee()
    {
        return randomEmployee;
    }
}
