package com.android.biblio.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class BibleFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        JSONBible kjv = GlobalVariable.getInstance().getKjv();
        String book = GlobalVariable.getInstance().getBook();
        int chapter = GlobalVariable.getInstance().getChapter();

        int chapterNum = kjv.getChapterCount(book);

        List<String> arr = new ArrayList<String>();

        for (int i = 1; i <= chapterNum; i++){
            arr.add(kjv.get(book, i));
        }

        String[] arrList = new String[arr.size()];
        arrList = arr.toArray(arrList);

        ReaderPagerAdapter viewAdapter = new ReaderPagerAdapter(getChildFragmentManager(), arrList);

        ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(viewAdapter);
        viewPager.setCurrentItem(chapter-1);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bible, container, false);
    }
}
