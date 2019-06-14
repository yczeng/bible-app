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

        String lorem = "[32] Sed ut perspiciatis, unde omnis iste natus error sit voluptatem" +
                "accusantium doloremque laudantium, totam rem aperiam eaque ipsa, quae ab illo" +
                "inventore veritatis et quasi architecto beatae vitae dicta sunt, explicabo." +
                "Nemo enim ipsam voluptatem, quia voluptas sit, aspernatur aut odit aut fugit," +
                "sed quia consequuntur magni dolores eos, qui ratione voluptatem sequi nesciunt," +
                "neque porro quisquam est, qui dolorem ipsum, quia dolor sit amet consectetur";
        String[] arr = {lorem + lorem + lorem + lorem + lorem,
                        lorem};

        ReaderPagerAdapter adapter = new ReaderPagerAdapter(this, arr);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
    }
}
