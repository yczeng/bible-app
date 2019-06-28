package com.android.biblio.app;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VerseAdapter extends BaseAdapter {

    private JSONArray results;
    private JSONBible kjv;
    private Context context;
    private String eachResult;
    private String hexColor;
    private float fontSize;
    private Typeface typeFace;
    private int textTheme;


    public VerseAdapter(Context context, JSONArray results, String hexColor, float fontSize, Typeface typeFace, int textTheme) {
        this.results = results;
        this.context = context;
        this.hexColor = hexColor;
        this.fontSize = fontSize;
        this.typeFace = typeFace;
        this.textTheme = textTheme;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView verseText;

        verseText = new TextView(context);
        verseText.setLayoutParams(new GridView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        verseText.setPadding(20, 5, 20, 5);
        verseText.setBackgroundColor(Color.WHITE);
        verseText.setTextSize(fontSize);
        verseText.setTypeface(typeFace);

        switch(textTheme) {
            case 0:
                viewGroup.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTextLightBackground));
                verseText.setTextColor(ContextCompat.getColor(context, R.color.colorTextLightForeground));
                break;
            case 1:
                viewGroup.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTextCreamBackground));
                verseText.setTextColor(ContextCompat.getColor(context, R.color.colorTextCreamForeground));
                break;
            case 2:
                viewGroup.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTextDarkBackground));
                verseText.setTextColor(ContextCompat.getColor(context, R.color.colorTextDarkForeground));
                break;
        }

        Log.i("each_result", results.toString());
        try {
            eachResult = results.getString(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        eachResult = "<sup><small>" + (i+1) + "</sup></small>" + "  " + eachResult;
        verseText.setText(Html.fromHtml(eachResult));

        return verseText;
    }
}
