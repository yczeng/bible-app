package com.android.biblio.app;

import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ReaderPagerAdapter extends FragmentStatePagerAdapter {

    private String[] stringList;
    Button chapterButton;

    public ReaderPagerAdapter(FragmentManager fm, String[] stringList, Button chapterButton){
        super(fm);
        this.stringList = stringList;
        this.chapterButton = chapterButton;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
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
