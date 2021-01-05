package com.willowtreeapps.namegame.Gameplay.Pojo;

import com.willowtreeapps.namegame.MainMenu.Pojo.HeadshotApiInfo;

public class HeadshotInfo
{
    private String mimeType;

    private String id;

    private String url;

    private String alt;

    public HeadshotInfo(HeadshotApiInfo headshotApiInfo)
    {
        mimeType = headshotApiInfo.GetMimeType();
        
        id = headshotApiInfo.GetId();
        
        url = headshotApiInfo.GetUrl();
        
        alt = headshotApiInfo.GetAlt();
    }

    public HeadshotInfo(String mimeType, String id, String url, String alt)
    {
        this.mimeType = mimeType;
        
        this.id = id;
        
        this.url = url;
        
        this.alt = alt;
    }

    public String GetMimeType()
    {
        return mimeType;
    }

    public String GetId()
    {
        return id;
    }

    public String GetUrl()
    {
        return url;
    }

    public String GetAlt()
    {
        return alt;
    }
}
