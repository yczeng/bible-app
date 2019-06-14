package com.android.biblio.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;
    private TextView textView1;
    Context context = this;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDialog = new Dialog(this);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.text);
        textView1 = findViewById(R.id.textView1);

        JSONArray verseList;
        JSONBible kjv;

        verseList = readJSONArray("kjv.json");
        kjv = new JSONBible(verseList);
        final String resultVerse = kjv.get("Gen", 3, 10);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(resultVerse);
            }
        });

    }

    public void bookPopUp(View view){
        myDialog.setContentView(R.layout.custompopup);
        myDialog.show();
    }

    public void moveActivity(View view) {
        Intent intent = new Intent(this, ReaderActivity.class);
        startActivity(intent);
        finish();
    }

    // Reads a json array from a file
    public JSONArray readJSONArray(String filename) {
        byte[] buffer = null;
        Context context = this;

        // grabs the file locally
        try{
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String json = null;
        JSONArray array = null;
        try {
            // grabs json object from the buffer
            json = new String(buffer, "UTF-8");

            // saves the json string as a JSONObject
            array = new JSONArray(json);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return array;
    }
}
