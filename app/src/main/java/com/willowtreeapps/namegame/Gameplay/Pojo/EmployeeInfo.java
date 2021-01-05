package com.willowtreeapps.namegame.Gameplay.Pojo;

import com.willowtreeapps.namegame.MainMenu.Pojo.EmployeeApiInfo;

public class EmployeeInfo
{
    private String id;

    private String firstName;

    private String lastName;
    
    private HeadshotInfo headshotInfo;

    public EmployeeInfo(EmployeeApiInfo employeeApiInfo)
    {
        id = employeeApiInfo.getId();
        
        firstName = employeeApiInfo.getFirstName();
        
        lastName = employeeApiInfo.getLastName();
        
        headshotInfo = new HeadshotInfo(employeeApiInfo.getHeadshotInfo());
    }

    public EmployeeInfo(String id, String firstName, String lastName, HeadshotInfo headshotInfo)
    {
        this.id = id;

        this.firstName = firstName;

        this.lastName = lastName;

        this.headshotInfo = headshotInfo;
    }

    public String GetId()
    {
        return id;
    }

    public void SetId(String id)
    {
        this.id = id;
    }

    public String GetFirstName()
    {
        return firstName;
    }

    public void SetFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String GetLastName()
    {
        return lastName;
    }

    public void SetLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public HeadshotInfo GetHeadshotInfo()
    {
        return headshotInfo;
    }

    public void SetHeadshotInfo(HeadshotInfo headshotInfo)
    {
        this.headshotInfo = headshotInfo;
    }
}
