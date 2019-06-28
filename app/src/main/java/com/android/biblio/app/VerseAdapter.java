package com.android.biblio.app;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
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

public class VerseAdapter extends BaseAdapter {

    private JSONArray results;
    private JSONBible kjv;
    private Context context;
    private String eachResult;

    public VerseAdapter(Context context, JSONArray results) {
        this.results = results;
        this.context = context;
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
        verseText.setTextSize((float)14);

        Log.i("each_result", results.toString());
        try {
            eachResult = results.getString(i);
            Log.i("eachResult", eachResult.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        eachResult = "<sup><font color=\"" + "#696969" + "\"><small>" + (i+1) + "</sup></font></small>" + "  " + eachResult;

//
        verseText.setText(Html.fromHtml(eachResult));

        return verseText;
    }
}
