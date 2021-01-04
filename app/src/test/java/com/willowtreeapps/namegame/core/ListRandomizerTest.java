package com.willowtreeapps.namegame.core;

import android.util.Log;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class ListRandomizerTest
{
    private static ListRandomizer listRandomizer;

    @BeforeClass
    public static void init()
    {
        listRandomizer = new ListRandomizer(new Random());
    }

    @Test
    public void GenerateListOf3From6()
    {
        final List<String> ITEMS = Arrays.asList("t1", "t2", "t3","t4", "t5", "t6");

        List<String> newList;

        try
        {
            newList = listRandomizer.GenerateRandomListFromList(ITEMS, 3);
        }

        catch (Exception e)
        {
            throw new AssertionError(e.getMessage());
        }

        assertEquals(3, newList.size());
    }

    @Test
    public void GenerateListOf5From6()
    {
        final List<String> ITEMS = Arrays.asList("t1", "t2", "t3","t4", "t5", "t6");

        List<String> newList;
        try
        {
            newList = listRandomizer.GenerateRandomListFromList(ITEMS, 5);
        }

        catch (Exception e)
        {
            throw new AssertionError(e.getMessage());
        }

        assertEquals(5, newList.size());
    }

    @Test
    public void GenerateListOf6From20()
    {
        final List<String> ITEMS = Arrays.asList("1", "2", "3","4", "5", "6", "7", "8", "9","10", "11", "12", "13","14", "15", "16", "17", "18", "19","20");

        List<String> newList;

        try
        {
            newList = listRandomizer.GenerateRandomListFromList(ITEMS, 6);
        }

        catch (Exception e)
        {
            throw new AssertionError(e.getMessage());
        }

        assertEquals(6, newList.size());
    }

    @Test
    public void GenerateListOf6From3()
    {
        final List<String> ITEMS = Arrays.asList("1", "2", "3");

        List<String> newList;

        try
        {
            newList = listRandomizer.GenerateRandomListFromList(ITEMS, 6);
        }

        catch (Exception e)
        {
            throw new AssertionError(e.getMessage());
        }

        assertEquals(6, newList.size());
    }
}