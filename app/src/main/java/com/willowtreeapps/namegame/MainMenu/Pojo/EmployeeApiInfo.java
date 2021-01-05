package com.willowtreeapps.namegame.MainMenu.Pojo;

import com.google.gson.annotations.SerializedName;

public class EmployeeApiInfo
{
    private String id;

    private String firstName;

    private String lastName;

    @SerializedName("headshot")
    private HeadshotApiInfo headshotInfo;

    public String getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public HeadshotApiInfo getHeadshotInfo()
    {
        return headshotInfo;
    }
}
