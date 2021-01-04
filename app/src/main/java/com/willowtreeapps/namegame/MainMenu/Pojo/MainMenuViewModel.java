package com.willowtreeapps.namegame.MainMenu.Pojo;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.willowtreeapps.namegame.MainMenu.Repository.EmployeeRepository;
import com.willowtreeapps.namegame.MainMenu.Singleton.ListRandomizerInstance;
import com.willowtreeapps.namegame.core.ListRandomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainMenuViewModel extends ViewModel
{
    public MainMenuViewModel()
    {
        repository = new EmployeeRepository();

        randomListOf6 = new ArrayList<>();
    }

    private EmployeeRepository repository;

    public LiveData<List<EmployeeInfo>> GetAllEmployees()
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
        randomListOf6 = ListRandomizerInstance.GetInstance(random).GenerateRandomListFromList(randomSeed, 6);

        return randomListOf6;
    }
}
