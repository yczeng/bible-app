package com.android.biblio.app;

import android.content.Context;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ReaderPagerAdapter extends FragmentStatePagerAdapter {

    String book;
    int numChapters;
    Button chapterButton;
    Context context;
    JSONBible jsonbible;

    public ReaderPagerAdapter(Context context, FragmentManager fm, JSONBible jsonbible, String book, int numChapters, Button chapterButton){
        super(fm);
        this.context = context;
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
        return new ChapterFragment(context, jsonbible, book, position,
                GlobalVariable.getInstance().getTextScaleSliderProgress(),
                GlobalVariable.getInstance().getTextFontFamily(),
                GlobalVariable.getInstance().getTextThemeRadioButton());
    }
}
