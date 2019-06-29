package com.android.biblio.app;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.w3c.dom.Text;

import static android.graphics.Typeface.MONOSPACE;

public class ChapterFragment extends Fragment {

    private JSONArray chapterVerses;
    private Context context;
    private int textScale;
    private String textFontFamily;
    private int textTheme;
    private int chapter;
    private int verse;

    public ChapterFragment(Context context, JSONBible jsonbible, String book, int chapter, int textScale, String textFontFamily, int textTheme, int verse) {
        super();
        this.chapterVerses = jsonbible.get(book, chapter+1);
        this.textScale = textScale;
        this.context = context;
        this.chapter = chapter;
        this.textFontFamily = textFontFamily;
        this.textTheme = textTheme;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chapter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridView verse_grid = view.findViewById(R.id.pagertext_grid);
        VerseAdapter mAdapter = new VerseAdapter(context, this.chapterVerses, (float)(12 + 12.0 * textScale / 100),
                                                 FontCache.get(textFontFamily, getContext()), textTheme);
        verse_grid.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
////      Figure out how to scroll, jump to textview
//        TextView jumpVerse = (TextView) mAdapter.getView(0, view, verse_grid);
//        TextView jumpVerse2 = (TextView) mAdapter.getView(1, view, verse_grid);
//        TextView jumpVerse3 = (TextView) mAdapter.getView(2, view, verse_grid);
//        Log.i("jumpVersetext", jumpVerse.getHeight() +  "");
//        Log.i("jumpVersetext2", jumpVerse2.getLineCount() +  "");
//        Log.i("jumpVersetext3", jumpVerse3.getLineCount() +  "");
//
//
//        int[] location = new int[2];
//        jumpVerse.getLocationOnScreen(location);
//        int x = location[0];
//        int y = location[1];
//
//        verse_grid.scrollTo(0, jumpVerse3.getBottom());
    }
}
