package com.android.biblio.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class BibleFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String lorem = "[32] Sed ut perspiciatis, unde omnis iste natus error sit voluptatem" +
                "accusantium doloremque laudantium, totam rem aperiam eaque ipsa, quae ab illo" +
                "inventore veritatis et quasi architecto beatae vitae dicta sunt, explicabo." +
                "Nemo enim ipsam voluptatem, quia voluptas sit, aspernatur aut odit aut fugit," +
                "sed quia consequuntur magni dolores eos, qui ratione voluptatem sequi nesciunt," +
                "neque porro quisquam est, qui dolorem ipsum, quia dolor sit amet consectetur";
        String[] arr = {"bible chapter 1: " + lorem + lorem + lorem + lorem + lorem,
                "bible chapter 2: " + lorem + lorem + lorem + lorem + lorem + lorem,
                "bible chapter 3: " + lorem + lorem + lorem,
                "bible chapter 4: " + lorem + lorem + lorem + lorem};
        ReaderPagerAdapter viewAdapter = new ReaderPagerAdapter(getChildFragmentManager(), arr);

        ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(viewAdapter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bible, container, false);
    }
}