package com.willowtreeapps.namegame.MainMenu.Repository;

import androidx.lifecycle.MutableLiveData;

import com.willowtreeapps.namegame.Gameplay.Pojo.EmployeeInfo;
import com.willowtreeapps.namegame.MainMenu.Pojo.EmployeeApiInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeRepository
{
    private MutableLiveData<List<EmployeeInfo>> allEmployees = new MutableLiveData<>();

    private String responseFailedMsg = "";

    public EmployeeRepository()
    {
        EmployeeRetrofit.GetInstance(EmployeeAPI.BASE_URL)
                        .create(EmployeeAPI.class)
                        .GetAllEmployees()
                        .enqueue(new Callback<List<EmployeeApiInfo>>()
                        {
                            @Override
                            public void onResponse(Call<List<EmployeeApiInfo>> call, Response<List<EmployeeApiInfo>> response)
                            {
                                if(response.isSuccessful())
                                {
                                    final List<EmployeeInfo> EMPLOYEE_INFOS = new ArrayList<>();

                                    for (EmployeeApiInfo e: response.body())
                                    {
                                        EMPLOYEE_INFOS.add(new EmployeeInfo(e));
                                    }

                                    allEmployees.postValue(EMPLOYEE_INFOS);
                                }

                                else
                                    responseFailedMsg = response.errorBody().toString();
                            }

                            @Override
                            public void onFailure(Call<List<EmployeeApiInfo>> call, Throwable t)
                            {
                                responseFailedMsg = t.getLocalizedMessage();
                            }
                        });
    }

    public String GetResponseFailedMsg()
    {
        return responseFailedMsg;
    }

    public MutableLiveData<List<EmployeeInfo>> GetAllEmployees()
    {
        return allEmployees;
    }
}
