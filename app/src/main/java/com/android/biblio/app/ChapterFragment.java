package com.android.biblio.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChapterFragment extends Fragment {

    private String str;

    public ChapterFragment(String str) {
        super();
        this.str = str;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chapter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView text = view.findViewById(R.id.pager_text);
        text.setText(this.str);
        int textScale = GlobalVariable.getInstance().getTextScaleSliderProgress();
        text.setTextSize((float)(12 + 12.0 * textScale / 100));
    }
}
