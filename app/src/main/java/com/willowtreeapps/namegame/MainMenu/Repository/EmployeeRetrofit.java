package com.willowtreeapps.namegame.MainMenu.Repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeRetrofit
{
    private static Retrofit retrofit;

    public static synchronized Retrofit GetInstance(String baseURL)
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .client(EmployeeOkHttpClient.GetInstance())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
