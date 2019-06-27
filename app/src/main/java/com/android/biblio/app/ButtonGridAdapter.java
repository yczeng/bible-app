package com.android.biblio.app;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class ButtonGridAdapter extends BaseAdapter {

    private Context context;
    private FragmentManager fm;
    private Dialog parent;
    private Button bookButton;
    private Button chapterButton;
    private String[] buttons;
    private boolean isBookGrid;
    private boolean isReaderActivity;
    private ViewPager biblePager;
    private JSONBible kjv;

    public ButtonGridAdapter(Context context, FragmentManager fm, Dialog parent, Button bookButton, Button chapterButton,
                             String[] buttons, boolean isBookGrid, boolean isReaderActivity, ViewPager biblePager) {
        this.context = context;
        this.fm = fm;
        this.parent = parent;
        this.bookButton = bookButton;
        this.chapterButton = chapterButton;
        this.buttons = buttons;
        this.isBookGrid = isBookGrid;
        this.isReaderActivity = isReaderActivity;
        this.biblePager = biblePager;
    }

    @Override
    public int getCount() {
        return buttons.length;
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
        Button button;

        if (view == null) {
            button = new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(100, 55));
            button.setPadding(0, 0, 0, 0);
            button.setTextSize((float)11);
            button.setBackgroundColor(Color.parseColor("#f2e5e5"));
            button.setBackgroundResource(R.drawable.rectangle);

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                if (isBookGrid) {
                    String newBook = v.getTag().toString();
                    GlobalVariable.getInstance().setBook(newBook);

                    kjv = GlobalVariable.getInstance().getKjv();
                    bookButton.setText(kjv.getBookFullName(newBook));
                    if (isReaderActivity){
                        // create the array of strings containing the chapters' texts
                        // for this book
                        int chapterNum = kjv.getChapterCount(newBook);
                        biblePager.setAdapter(new ReaderPagerAdapter(fm, kjv, newBook, chapterNum, bookButton));
                        biblePager.setCurrentItem(0);
                    }
                    GlobalVariable.getInstance().setChapter(1);
                    chapterButton.setText("1");

                } else {
                    String newChapter_str = v.getTag().toString();
                    int newChapter = Integer.parseInt(newChapter_str);
                    GlobalVariable.getInstance().setChapter(newChapter);
                    chapterButton.setText(newChapter_str);
                    if (isReaderActivity) {
                        biblePager.setCurrentItem(newChapter-1);
                    }
                }
                parent.dismiss();
                }
            });
        } else {
            button = (Button) view;
        }

        button.setText(buttons[i]);
        button.setTag(buttons[i]);
        return button;
    }
}
