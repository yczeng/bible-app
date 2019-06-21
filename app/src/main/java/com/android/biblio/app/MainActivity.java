package com.android.biblio.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
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
        //bookDialog = new Dialog(this);
        //chapterDialog = new Dialog(this);

        textView = findViewById(R.id.text);

        bookButton = findViewById(R.id.bookButton);
        bookButton.setText(GlobalVariable.getInstance().getBook());

        chapterButton = findViewById(R.id.chapterButton);
        chapterButton.setText(String.valueOf(GlobalVariable.getInstance().getChapter()));

        kjv = new JSONBible(this, "kjv.json", "reformattedKjv2.json");
        GlobalVariable.getInstance().setKjv(kjv);
    }

    // Generates a pop up of all books
    public void bookPopUp(View view){
        // make a popup builder
        AlertDialog.Builder bookpopupDialog = new AlertDialog.Builder(this);

        // inflate the view, but keep a reference to it
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.book_pop_up, null);
        bookpopupDialog.setView(dialogView);

        // get the linear layout and add the button(s)
        String[] old_test = {"Gen", "Exod", "Lev", "Num", "Deut", "Josh",
                            "Judg", "Ruth", "1Sam", "2Sam", "1Kgs", "1Chr",
                            "2Chr", "Ezra", "Neh", "Esth", "Job", "Ps",
                            "Prov", "Eccl", "Song", "Isa", "Jer", "Lam",
                            "Ezek", "Dan", "Hos", "Joel", "Amos", "Obad",
                            "Jona", "Mic", "Nah", "Hab", "Zeph", "Hag",
                            "Zech", "Mal"};
        String[] new_test = {"Matt", "Mark", "Luke", "John",
                            "Acts", "Rom", "1Cor", "2Cor", "Gal", "Eph",
                            "Phil", "Col", "1Thess", "2Thess", "1Tim", "2Tim",
                            "Titus", "Phlm", "Heb", "Jas", "1Pet", "2Pet",
                            "1John", "2John", "3John", "Jude", "Rev"};
        GridView grid_oldtest = dialogView.findViewById(R.id.buttongrid_oldtest);
        grid_oldtest.setAdapter(new ButtonGridAdapter(this, bookButton, old_test, true));
        GridView grid_newtest = dialogView.findViewById(R.id.buttongrid_newtest);
        grid_newtest.setAdapter(new ButtonGridAdapter(this, bookButton, new_test, true));

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
