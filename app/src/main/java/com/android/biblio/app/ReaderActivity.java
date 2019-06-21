package com.android.biblio.app;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;


public class ReaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        // grab references to global variables
        JSONBible kjv = GlobalVariable.getInstance().getKjv();
        String book = GlobalVariable.getInstance().getBook();
        int chapter = GlobalVariable.getInstance().getChapter();

        // create the array of strings containing the chapters' texts
        // for this book
        int chapterNum = kjv.getChapterCount(book);
        List<String> arr = new ArrayList<String>();
        for (int i = 1; i <= chapterNum; i++){
            arr.add(kjv.get(book, i));
        }
        String[] arrList = new String[arr.size()];
        arrList = arr.toArray(arrList);

        // create the viewpager and corresponding adapter
        // that will scroll through the chapterfragments
        ViewPager biblePager = findViewById(R.id.biblepager);
        biblePager.setAdapter(new ReaderPagerAdapter(getSupportFragmentManager(), arrList));
    }
}
