package org.ajcm.tubiblia.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.ajcm.tubiblia.fragments.CapFragment;

/**
 * Created by jhonlimaster on 09-06-16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private int verse;
    private int idBook;
    private int numCap;
    private int color;

    public PagerAdapter(FragmentManager fm, int idBook, int numCap, int verse, int color) {
        super(fm);
        this.idBook = idBook;
        this.numCap = numCap;
        this.verse = verse;
        this.color = color;
    }

    @Override
    public Fragment getItem(int position) {
        return CapFragment.newInstance(idBook, position + 1, verse, color);
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
