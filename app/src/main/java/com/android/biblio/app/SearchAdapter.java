package com.android.biblio.app;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends BaseAdapter {

    private JSONArray results;
    private Context context;
    private Button bookButton;
    private Button chapterButton;
    private JSONBible kjv;
    private ViewPager biblePager;
    private FragmentManager fm;
    private Dialog parent;
    private JSONObject eachResult;

    public SearchAdapter(Context context, FragmentManager fm, Dialog parent, JSONArray results, Button bookButton, Button chapterButton, ViewPager biblePager) {
        this.context = context;
        this.results = results;
        this.bookButton = bookButton;
        this.chapterButton = chapterButton;
        this.parent = parent;
        this.biblePager = biblePager;
        this.fm = fm;
    }

    @Override
    public int getCount() {
        return results.length();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        TextView searchResultText;

        if (view == null) {
            searchResultText = new TextView(context);
            searchResultText.setLayoutParams(new GridView.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            searchResultText.setPadding(3, 3, 3, 3);
            searchResultText.setBackgroundColor(Color.WHITE);
            searchResultText.setTextSize((float)11);

            searchResultText.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String book = "";
                    int chapter = -1;

                    try {
                        eachResult = results.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        book = eachResult.get("book_id").toString();
                        chapter = eachResult.getInt("chapter");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    GlobalVariable.getInstance().setBook(book);
                    // WARNING FUTURE BUG ALERT. CHAPTER INITIALIZED AS -1!!!
                    // SO IF THIS BUGS, COULD BE CUZ OF THAT
                    GlobalVariable.getInstance().setChapter(chapter);

                    kjv = GlobalVariable.getInstance().getKjv();

                    // create the array of strings containing the chapters' texts
                    // for this book
                    int chapterNum = kjv.getChapterCount(book);

                    List<String> arr = new ArrayList<String>();
                    for (int i = 1; i <= chapterNum; i++){
                        arr.add(kjv.get(book, i));
                    }
                    String[] arrList = new String[arr.size()];
                    arrList = arr.toArray(arrList);

                    biblePager.setAdapter(new ReaderPagerAdapter(fm, arrList, bookButton));
                    biblePager.setCurrentItem(chapter-1);

                    Log.i("bookgrabbed", "book: " + book);
                    Log.i("chaptergrabbed", "chapter: " + chapter);

                    bookButton.setText(kjv.getBookFullName(book));
                    parent.dismiss();
                }
            });
        } else {
            searchResultText = (TextView) view;
        }

        try {
            eachResult = results.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String searchResultTextInfo = "Book: " + eachResult.get("book_id") + ", " + "Chapter: " + eachResult.getInt("chapter") + ", " + "Verse: " + eachResult.getInt("verse");
            searchResultTextInfo += "\n" + eachResult.get("text") + "\n";

            searchResultText.setText(searchResultTextInfo);
            searchResultText.setTag(searchResultTextInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return searchResultText;
    }

}
