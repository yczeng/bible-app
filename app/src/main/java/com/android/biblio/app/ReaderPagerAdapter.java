package com.android.biblio.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ReaderPagerAdapter extends PagerAdapter {

    private String[] stringList;
    private LayoutInflater layoutInflater;

    public ReaderPagerAdapter(Context context, String[] stringList){
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.stringList = stringList;
    }

    @Override
    public int getCount() {
        return stringList.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View)object);
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = this.layoutInflater.inflate(R.layout.pager_list_items, container, false);
        TextView text = view.findViewById(R.id.pager_text);
        text.setText(this.stringList[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
