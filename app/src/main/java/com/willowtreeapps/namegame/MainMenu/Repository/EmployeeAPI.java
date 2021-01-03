package com.willowtreeapps.namegame.MainMenu.Repository;

import com.willowtreeapps.namegame.MainMenu.Pojo.EmployeeInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EmployeeAPI
{
    String BASE_URL = "https://willowtreeapps.com";

    @GET("/api/v1.0/profiles")
    Call<List<EmployeeInfo>> GetAllEmployees();
}
