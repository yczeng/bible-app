package com.android.biblio.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import androidx.arch.core.util.Function;

public class ButtonGridAdapter extends BaseAdapter {

    private Context context;
    private Button mainButton;
    private String[] buttons;
    private boolean isBookGrid;

    public ButtonGridAdapter(Context context, Button mainButton, String[] buttons, boolean isBookGrid) {
        this.context = context;
        this.mainButton = mainButton;
        this.buttons = buttons;
        this.isBookGrid = isBookGrid;
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
            button.setLayoutParams(new GridView.LayoutParams(110, 60));
            button.setPadding(0, 0, 0, 0);
            button.setTextSize((float)11);

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (isBookGrid) {
                        String newBook = v.getTag().toString();
                        GlobalVariable.getInstance().setBook(newBook);
                        mainButton.setText(newBook);
                    } else {
                        String newChapter_str = v.getTag().toString();
                        int newChapter = Integer.parseInt(newChapter_str);
                        GlobalVariable.getInstance().setChapter(newChapter);
                        mainButton.setText(newChapter_str);
                    }
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
