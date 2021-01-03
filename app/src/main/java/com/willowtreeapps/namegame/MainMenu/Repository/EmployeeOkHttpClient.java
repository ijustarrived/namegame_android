package com.willowtreeapps.namegame.MainMenu.Repository;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class EmployeeOkHttpClient
{
    private static final short TIMEOUT = 20;

    private static OkHttpClient httpClient;

    public static synchronized OkHttpClient GetInstance()
    {
        if(httpClient == null)
        {
            httpClient = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .build();
        }

        return httpClient;
    }
}
