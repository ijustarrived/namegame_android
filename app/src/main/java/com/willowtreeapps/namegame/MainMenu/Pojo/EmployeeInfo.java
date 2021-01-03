package com.willowtreeapps.namegame.MainMenu.Pojo;

import com.google.gson.annotations.SerializedName;

public class EmployeeInfo
{
    private String id;

    private String firstName;

    private String lastName;

    @SerializedName("headshot")
    private HeadshotInfo headshotInfo;
}
