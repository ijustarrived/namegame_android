package com.willowtreeapps.namegame.MainMenu.Repository;

import androidx.lifecycle.MutableLiveData;

import com.willowtreeapps.namegame.MainMenu.Pojo.EmployeeInfo;

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
                        .enqueue(new Callback<List<EmployeeInfo>>()
                        {
                            @Override
                            public void onResponse(Call<List<EmployeeInfo>> call, Response<List<EmployeeInfo>> response)
                            {
                                if(response.isSuccessful())
                                    allEmployees.postValue(response.body());

                                else
                                    responseFailedMsg = response.errorBody().toString();
                            }

                            @Override
                            public void onFailure(Call<List<EmployeeInfo>> call, Throwable t)
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
