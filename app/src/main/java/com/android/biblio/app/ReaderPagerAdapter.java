package com.android.biblio.app;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ReaderPagerAdapter extends FragmentStatePagerAdapter {

    private String[] stringList;

    public ReaderPagerAdapter(FragmentManager fm, String[] stringList){
        super(fm);
        this.stringList = stringList;
    }

    @Override
    public int getCount() {
        return stringList.length;
    }

    @Override
    public Fragment getItem(int position) {
        return new ChapterFragment(stringList[position]);
    }
}
