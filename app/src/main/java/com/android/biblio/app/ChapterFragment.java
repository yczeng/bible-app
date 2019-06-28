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

public class ChapterFragment extends Fragment {

    private JSONArray chapterVerses;
    private JSONBible jsonbible;
    private String book;
    private int chapter;
    private String hexcolor;
    private Context context;

    public ChapterFragment(Context context, JSONBible jsonbible, String book, int chapter, String hexcolor) {
        super();
        this.chapterVerses = jsonbible.get(book, chapter+1, hexcolor);
        this.jsonbible = jsonbible;
        this.book = book;
        this.chapter = chapter;
        this.hexcolor = hexcolor;
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chapter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        TextView text = view.findViewById(R.id.pager_text);
//        String newcolor = GlobalVariable.getInstance().getTextThemeHighlight();
//        if (!this.hexcolor.equals(newcolor)) {
//            this.chapterVerses = this.jsonbible.get(book, chapter+1, hexcolor);
//        }

        GridView verse_grid = view.findViewById(R.id.pagertext_grid);
        verse_grid.setAdapter(new VerseAdapter(context, this.chapterVerses));

//        text.setText(Html.fromHtml(""));

        // set text size
        int textScale = GlobalVariable.getInstance().getTextScaleSliderProgress();
//        text.setTextSize((float)(12 + 12.0 * textScale / 100));

        // set text typeface
        String textFontFamily = GlobalVariable.getInstance().getTextFontFamily();
        Typeface face = FontCache.get(textFontFamily, getContext());
//        text.setTypeface(face);

        // set text color theme
        int textTheme = GlobalVariable.getInstance().getTextThemeRadioButton();
        Log.i("theme", "textTheme: " + textTheme);
        switch(textTheme) {
            case 0:
                view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorTextLightBackground));
//                text.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorTextLightForeground));
                break;
            case 1:
                view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorTextCreamBackground));
//                text.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorTextCreamForeground));
                break;
            case 2:
                view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorTextDarkBackground));
//                text.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorTextDarkForeground));
                break;
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
