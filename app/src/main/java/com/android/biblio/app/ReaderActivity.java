package com.android.biblio.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReaderActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        String[] arr = {"test1", "test2", "test3"};
        ReaderPagerAdapter adapter = new ReaderPagerAdapter(this, arr);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
    }
}
