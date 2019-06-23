package com.android.biblio.app;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends BaseAdapter {

    JSONArray results;
    Context context;
    Button bookButton;
    Button chapterButton;
    private JSONBible kjv;
    private ViewPager biblePager;
    private FragmentManager fm;
    private Dialog parent;

    public SearchAdapter(Context context, FragmentManager fm, Dialog parent, JSONArray results, Button bookButton, Button chapterButton, ViewPager biblePager) {
        this.context = context;
        this.results = results;
        this.bookButton = bookButton;
        this.chapterButton = chapterButton;
        this.parent = parent;
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
        Button button;

        if (view == null) {
            button = new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setPadding(0, 0, 0, 0);
            button.setTextSize((float)11);

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.i("searchClick", "a click was made");
                    String book = "";
                    int chapter = -1;

                    JSONObject eachResult = null;
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

                    Log.i("searchBook", "book chosen:" + book);
                    Log.i("searchChapter", "chapter chosen:" + chapter);
//
//                    biblePager.setAdapter(new ReaderPagerAdapter(fm, arrList, bookButton));
                    biblePager.setCurrentItem(chapter-1);

//                    bookButton.setText(book);
//                    chapterButton.setText(chapter);
                    parent.dismiss();
                }
            });
        } else {
            button = (Button) view;
        }

        JSONObject eachResult = null;
        try {
            eachResult = results.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String buttonText = "Book: " + eachResult.get("book_id") + ", " + "Chapter: " + Integer.toString(eachResult.getInt("chapter"));
            buttonText += eachResult.get("text");
            button.setText(buttonText);
            button.setTag(buttonText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return button;
    }

}
