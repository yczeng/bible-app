package com.android.biblio.app;

import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ReaderPagerAdapter extends FragmentStatePagerAdapter {

    private String[] stringList;
    Button bookButton;
    Button chapterButton;

    public ReaderPagerAdapter(FragmentManager fm, String[] stringList, Button chapterButton){
        super(fm);
        this.stringList = stringList;
        this.chapterButton = chapterButton;
    }

    @Override
    public int getCount() {
        return stringList.length;
    }

    @Override
    public Fragment getItem(int position) {
          // set chapter button to be current chapter
        chapterButton.setText("" + position);
        GlobalVariable.getInstance().setChapter(position);
        return new ChapterFragment(stringList[position]);
    }
}
