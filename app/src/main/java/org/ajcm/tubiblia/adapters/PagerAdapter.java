package org.ajcm.tubiblia.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.ajcm.tubiblia.fragments.CapFragment;

import java.util.ArrayList;

/**
 * Created by jhonlimaster on 09-06-16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private int verse;
    private int idBook;
    private int numCap;

    public PagerAdapter(FragmentManager fm, int idBook, int numCap, int verse) {
        super(fm);
        this.idBook = idBook;
        this.numCap = numCap;
        this.verse = verse;
    }

    @Override
    public Fragment getItem(int position) {
        return CapFragment.newInstance(idBook, position + 1, verse);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(position + 1);
    }

    @Override
    public int getCount() {
        return numCap;
    }
}
