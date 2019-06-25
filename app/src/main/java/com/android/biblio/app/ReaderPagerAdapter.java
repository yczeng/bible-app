package com.android.biblio.app;

import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ReaderPagerAdapter extends FragmentStatePagerAdapter {

    JSONBible jsonbible;
    String book;
    int numChapters;
    Button chapterButton;

    public ReaderPagerAdapter(FragmentManager fm, JSONBible jsonbible, String book, int numChapters, Button chapterButton){
        super(fm);
        this.jsonbible = jsonbible;
        this.book = book;
        this.numChapters = numChapters;
        this.chapterButton = chapterButton;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return this.numChapters;
    }

    @Override
    public Fragment getItem(int position) {
        return new ChapterFragment(jsonbible, book, position,
                GlobalVariable.getInstance().getTextThemeHighlight());
    }
}
