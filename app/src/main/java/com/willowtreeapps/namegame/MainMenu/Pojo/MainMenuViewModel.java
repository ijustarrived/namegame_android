package com.willowtreeapps.namegame.MainMenu.Pojo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.willowtreeapps.namegame.MainMenu.Repository.EmployeeRepository;

import java.util.List;

public class MainMenuViewModel extends ViewModel
{
    public MainMenuViewModel()
    {
        repository = new EmployeeRepository();
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
}
