package com.android.biblio.app;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private int count;

    public TabPagerAdapter(FragmentManager fm, int count){
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new BibleFragment();
        }

        return new ChapterFragment("test page");
    }

    @Override
    public int getCount() {
        return count;
    }

}
