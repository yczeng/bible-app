package com.android.biblio.app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.tabs.TabLayout;


public class ReaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        final DisabledViewPager tabPager = findViewById(R.id.tab_pager);
        final TabPagerAdapter tabAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        tabPager.setAdapter(tabAdapter);
        tabPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }
}
