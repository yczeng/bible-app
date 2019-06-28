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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;

import static android.graphics.Typeface.MONOSPACE;

public class ChapterFragment extends Fragment {

    private JSONArray chapterVerses;
    private JSONBible jsonbible;
    private String book;
    private int chapter;
    private String hexcolor;
    private Context context;
    private int textScale;
    private String textFontFamily;
    private int textTheme;

    public ChapterFragment(Context context, JSONBible jsonbible, String book, int chapter, String hexcolor, int textScale, String textFontFamily, int textTheme) {
        super();
        this.chapterVerses = jsonbible.get(book, chapter+1);
        this.jsonbible = jsonbible;
        this.book = book;
        this.chapter = chapter;
        this.hexcolor = hexcolor;
        this.textScale = textScale;
        this.context = context;
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

        String newcolor = GlobalVariable.getInstance().getTextThemeHighlight();
        GridView verse_grid = view.findViewById(R.id.pagertext_grid);
        VerseAdapter mAdapter = new VerseAdapter(context, this.chapterVerses, hexcolor, (float)(12 + 12.0 * textScale / 100),
                                                 FontCache.get(textFontFamily, getContext()), textTheme);
        verse_grid.setAdapter(mAdapter);

        if (!this.hexcolor.equals(newcolor)) {
            mAdapter.notifyDataSetChanged();
        }

//
//        final UpdaterScrollView updaterscrollview = view.findViewById(R.id.pager_scroll);
//        updaterscrollview.setOnScrollListener(new UpdaterScrollView.OnScrollListener() {
//            @Override
//            public void onScrollChanged(UpdaterScrollView scrollView, int x, int y, int oldX, int oldY) {
//
//            }
//
//            @Override
//            public void onEndScroll(UpdaterScrollView scrollView) {
//                int scrollY = scrollView.getScrollY();
//                int scrollMax = scrollView.getChildAt(0).getHeight();
//                double scrollPercent = ((double) scrollY) / scrollMax;
//                GlobalVariable.getInstance().setScrollPercent(scrollPercent);
//            }
//        });
//
//        updaterscrollview.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                double scrollPercent = GlobalVariable.getInstance().getScrollPercent();
//                int scrollY = (int)(scrollPercent * updaterscrollview.getChildAt(0).getHeight());
//                updaterscrollview.scrollTo(0, scrollY);
//            }
//        }, 50);
    }
}
