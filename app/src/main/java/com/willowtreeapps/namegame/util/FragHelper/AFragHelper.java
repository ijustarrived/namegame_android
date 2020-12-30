package com.willowtreeapps.namegame.util.FragHelper;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public abstract class AFragHelper
{
    private FragmentManager fm;

    public AFragHelper(FragmentManager fm)
    {
        this.fm = fm;
    }

    /**
     *  Replace the current fragment with the provided fragment
     *
     * @param fragmentContainerID ID of the layout that while contain the fragment
     * @param tag Fragment tag
     */
    public void Replace(int fragmentContainerID, Fragment fragment, String tag, boolean shouldAddToBackStack)
    {
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(fragmentContainerID, fragment, tag);

        if(shouldAddToBackStack)
            transaction.addToBackStack(tag);

        transaction.commit();
    }
}
