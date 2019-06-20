package com.android.biblio.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private TextView textView;

    Dialog bookDialog;
    Button bookButton;
    Dialog chapterDialog;
    Button chapterButton;

    JSONArray verseList;
    JSONObject bibleMap;
    JSONBible kjv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creates the popup windows for book and chapter
        bookDialog = new Dialog(this);
        chapterDialog = new Dialog(this);

        textView = findViewById(R.id.text);

        bookButton = findViewById(R.id.bookButton);
        bookButton.setText(GlobalVariable.getInstance().getBook());

        chapterButton = findViewById(R.id.chapterButton);
        chapterButton.setText(String.valueOf(GlobalVariable.getInstance().getChapter()));

        ConstraintLayout ll = findViewById(R.id.mainActivity);
        generateButtons(this, ll);
    }

    public void generateButtons(Context context, ConstraintLayout ll){
        Button myButton = new Button(context);
        myButton.setText("Add Me");

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ll.addView(myButton, lp);
    }

    // Generates a pop up of all books
    public void bookPopUp(View view){
        // make a popup builder
        AlertDialog.Builder bookpopupDialog = new AlertDialog.Builder(this);
        bookpopupDialog.setTitle("bookpopupdialog");
        bookpopupDialog.setMessage("book choose it");

        // inflate the view, but keep a reference to it
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.book_pop_up, null);
        bookpopupDialog.setView(dialogView);

        // get the linear layout and add the button(s)
        LinearLayout ll = dialogView.findViewById(R.id.bookpopup_layout);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        ll.removeAllViews();
        for (int i = 0; i < 3; i++) {
            Button myButton1 = new Button(this);
            myButton1.setText("1");
            myButton1.setMaxWidth(100);
            ll.addView(myButton1); // could use ll.addView(myButton1, lp);
        }

        // display the dialog
        AlertDialog myAlert = bookpopupDialog.create();
        myAlert.show();
    }

    // Updates global variable when click on new book
    public void changeBook(View v){
        String newBook = v.getTag().toString();
        GlobalVariable.getInstance().setBook(newBook);

        bookButton.setText(newBook);
        bookDialog.dismiss();
    }

    // Generates a pop up of all the chapters for given book
    public void chapterPopUp(View view){
        chapterDialog.setContentView(R.layout.chapter_pop_up);
        chapterDialog.show();
    }

    // Updates global variable when click on new chapter
    public void changeChapter(View v){
        int newChapter = Integer.parseInt(v.getTag().toString());
        GlobalVariable.getInstance().setChapter(newChapter);

        chapterButton.setText(v.getTag().toString());
        chapterDialog.dismiss();
    }

    // Moves on to the next page with json result of verse
    public void moveActivity(View view) {
        Intent intent = new Intent(this, ReaderActivity.class);

        kjv = new JSONBible(this, "kjv.json", "reformattedKjv2.json");

        String resultVerse = kjv.get(GlobalVariable.getInstance().getBook(), GlobalVariable.getInstance().getChapter());
        textView.setText(resultVerse);

//        Context context = getApplicationContext();
//        CharSequence text = resultVerse;
//        int duration = Toast.LENGTH_LONG;
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();
        intent.putExtra("json", resultVerse);
        startActivity(intent);
        finish();
    }
}
