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
    private ListRandomizer listRandomizer;

    public MainMenuViewModel()
    {
        repository = new EmployeeRepository();
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


    public ListRandomizer GetListRandomizer()
    {
        return listRandomizer;
    }

    public void SetListRandomizer(ListRandomizer listRandomizer)
    {
        this.listRandomizer = listRandomizer;
    }
}
