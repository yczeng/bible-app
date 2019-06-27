package com.android.biblio.app;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;


public class ReaderActivity extends AppCompatActivity {
    Button bookButton;
    Button chapterButton;
    JSONBible kjv;
    ViewPager biblePager;
    SearchView searchView;
    String searchBook;
    Boolean searchByBook;
    TextView noResultsFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        // grab references to global variables
        kjv = GlobalVariable.getInstance().getKjv();
        String book = GlobalVariable.getInstance().getBook();
        final int chapter = GlobalVariable.getInstance().getChapter();

        // set the bookbutton name to be book title
        bookButton = findViewById(R.id.reader_bookButton);
        bookButton.setText(kjv.getBookFullName(book));

        // set chapter button to be current chapter
        chapterButton = findViewById(R.id.reader_chapterButton);
        chapterButton.setText(String.valueOf(chapter));


        // create the array of strings containing the chapters' texts
        // for this book
        int chapterNum = kjv.getChapterCount(book);

        // create the viewpager and corresponding adapter
        // that will scroll through the chapter fragments
        biblePager = findViewById(R.id.biblepager);
        biblePager.setAdapter(new ReaderPagerAdapter(getSupportFragmentManager(), book, chapterNum, chapterButton));
        biblePager.setCurrentItem(chapter-1);
        biblePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                GlobalVariable.getInstance().setChapter(position+1);
                chapterButton.setText("" + (position + 1));
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    public void textBlockPopUp(View view){
        AlertDialog popupDialog = null;
        popupDialog = generic_popup(R.layout.textopts_popup);
        int textTheme = GlobalVariable.getInstance().getTextThemeRadioButton();
        int textScale = GlobalVariable.getInstance().getTextScaleSliderProgress();
        int textFont = GlobalVariable.getInstance().getTextFontFamButton();

        SeekBar slider = popupDialog.findViewById(R.id.fontslider);
        slider.setProgress(textScale);
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                GlobalVariable.getInstance().setTextScaleSliderProgress(i);
                biblePager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        RadioButton theme0 = popupDialog.findViewById(R.id.themebutton_light);
        RadioButton theme1 = popupDialog.findViewById(R.id.themebutton_cream);
        RadioButton theme2 = popupDialog.findViewById(R.id.themebutton_dark);
        switch(textTheme) {
            case 0:
                theme0.setChecked(true);
                theme1.setChecked(false);
                theme2.setChecked(false);
                break;
            case 1:
                theme0.setChecked(false);
                theme1.setChecked(true);
                theme2.setChecked(false);
                break;
            case 2:
                theme0.setChecked(false);
                theme1.setChecked(false);
                theme2.setChecked(true);
                break;
            default:
                break;
        }
        theme0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariable.getInstance().setTextThemeRadioButton(0);
                String newHighlight = "#" + Integer.toHexString(ContextCompat.getColor(getApplicationContext(),
                        R.color.colorTextLightHighlight) & 0x00ffffff);
                GlobalVariable.getInstance().setTextThemeHighlight(newHighlight);
                biblePager.getAdapter().notifyDataSetChanged();
            }
        });
        theme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariable.getInstance().setTextThemeRadioButton(1);
                String newHighlight = "#" + Integer.toHexString(ContextCompat.getColor(getApplicationContext(),
                        R.color.colorTextCreamHighlight) & 0x00ffffff);
                GlobalVariable.getInstance().setTextThemeHighlight(newHighlight);
                biblePager.getAdapter().notifyDataSetChanged();
            }
        });
        theme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariable.getInstance().setTextThemeRadioButton(2);
                String newHighlight = "#" + Integer.toHexString(ContextCompat.getColor(getApplicationContext(),
                        R.color.colorTextDarkHighlight) & 0x00ffffff);
                GlobalVariable.getInstance().setTextThemeHighlight(newHighlight);
                biblePager.getAdapter().notifyDataSetChanged();
            }
        });

        final Button fontfam0 = popupDialog.findViewById(R.id.fontfam_mono);
        final Button fontfam1 = popupDialog.findViewById(R.id.fontfam_serifmono);
        final Button fontfam2 = popupDialog.findViewById(R.id.fontfam_serif);
        final Button fontfam3 = popupDialog.findViewById(R.id.fontfam_sansserif);
        final Button fontfam4 = popupDialog.findViewById(R.id.fontfam_sansserifcondlight);

        fontfam0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariable.getInstance().setTextFontFamButton(0);
                GlobalVariable.getInstance().setTextFontFamily("fonts/CarroisGothicSC-Regular.ttf");
                biblePager.getAdapter().notifyDataSetChanged();
                fontfam0.setBackgroundColor(Color.argb(30, 150, 50, 50));
                fontfam1.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam2.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam3.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam4.setBackgroundColor(Color.argb(0, 0, 0, 0));
            }
        });
        fontfam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariable.getInstance().setTextFontFamButton(1);
                GlobalVariable.getInstance().setTextFontFamily("fonts/CutiveMono.ttf");
                biblePager.getAdapter().notifyDataSetChanged();
                fontfam0.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam1.setBackgroundColor(Color.argb(30, 150, 50, 50));
                fontfam2.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam3.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam4.setBackgroundColor(Color.argb(0, 0, 0, 0));
            }
        });
        fontfam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariable.getInstance().setTextFontFamButton(2);
                GlobalVariable.getInstance().setTextFontFamily("fonts/NotoSerif-Regular.ttf");
                biblePager.getAdapter().notifyDataSetChanged();
                fontfam0.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam1.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam2.setBackgroundColor(Color.argb(30, 150, 50, 50));
                fontfam3.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam4.setBackgroundColor(Color.argb(0, 0, 0, 0));
            }
        });
        fontfam3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariable.getInstance().setTextFontFamButton(3);
                GlobalVariable.getInstance().setTextFontFamily("fonts/Roboto-Regular.ttf");
                biblePager.getAdapter().notifyDataSetChanged();
                fontfam0.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam1.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam2.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam3.setBackgroundColor(Color.argb(30, 150, 50, 50));
                fontfam4.setBackgroundColor(Color.argb(0, 0, 0, 0));
            }
        });
        fontfam4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariable.getInstance().setTextFontFamButton(4);
                GlobalVariable.getInstance().setTextFontFamily("fonts/Roboto-Thin.ttf");
                biblePager.getAdapter().notifyDataSetChanged();
                fontfam0.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam1.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam2.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam3.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam4.setBackgroundColor(Color.argb(30, 150, 50, 50));
            }
        });

        switch(textFont) {
            case 0:
                fontfam0.setBackgroundColor(Color.argb(30, 150, 50, 50));
                fontfam1.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam2.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam3.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam4.setBackgroundColor(Color.argb(0, 0, 0, 0));
                break;
            case 1:
                fontfam0.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam1.setBackgroundColor(Color.argb(30, 150, 50, 50));
                fontfam2.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam3.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam4.setBackgroundColor(Color.argb(0, 0, 0, 0));
                break;
            case 2:
                fontfam0.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam1.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam2.setBackgroundColor(Color.argb(30, 150, 50, 50));
                fontfam3.setBackgroundColor(Color.argb(0, 0,0, 0));
                fontfam4.setBackgroundColor(Color.argb(0, 0, 0, 0));
                break;
            case 3:
                fontfam0.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam1.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam2.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam3.setBackgroundColor(Color.argb(30, 150, 50, 50));
                fontfam4.setBackgroundColor(Color.argb(0, 0, 0, 0));
                break;
            case 4:
                fontfam0.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam1.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam2.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam3.setBackgroundColor(Color.argb(0, 0, 0, 0));
                fontfam4.setBackgroundColor(Color.argb(30, 150, 50, 50));
                break;
            default:
                break;
        }

    }

    public void searchPopUp(View view){
        // make a popup builder
        AlertDialog.Builder searchpopupDialog = new AlertDialog.Builder(this);

        final Context context = this;

        // inflate the view, but keep a reference to it
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.search_pop_up, null);

        searchpopupDialog.setView(dialogView);

        // searches within dialogView for the search box
        searchView = dialogView.findViewById(R.id.searchViewDialog);
        searchBook = "all";
        searchByBook = false;

        noResultsFound = new TextView(context);
        noResultsFound.setBackgroundColor(Color.WHITE);
        noResultsFound.setPadding(10, 10, 10, 10);
        noResultsFound.setTextSize((float)17);
        noResultsFound.setText("");
        noResultsFound.setVisibility(GONE);
        LinearLayout linearLayout = dialogView.findViewById(R.id.searchLinearLayout);
        linearLayout.addView(noResultsFound);

        final Spinner spinner = dialogView.findViewById(R.id.spinner);

        RadioButton radioButton = dialogView.findViewById(R.id.radioButton);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchByBook = true;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                searchBook = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }

        });

        final GridView results_grid = dialogView.findViewById(R.id.buttongrid_searchresults);
        final AlertDialog searchgridPanel = searchpopupDialog.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("search_view", "search worked");
                JSONArray resultsJson;
                if (searchByBook){
                    resultsJson = kjv.search(query, searchBook);
                } else {
                    resultsJson = kjv.search(query, "all");
                }
                results_grid.setAdapter(new SearchAdapter(context, getSupportFragmentManager(), searchgridPanel, resultsJson, bookButton, chapterButton, biblePager));
                // this means that there were no results
                if(results_grid.getChildCount() == 0) {
                    noResultsFound.setText(Html.fromHtml("<font color=\"#696969\">" + "No results found for \"" + query + "\"" + "</font>"));
                    noResultsFound.setVisibility(View.VISIBLE);
                }

                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }});

        searchgridPanel.show();
    }

    // Generates a pop up of all books
    public void bookPopUp(View view){
        // make a popup builder
        AlertDialog.Builder bookpopupDialog = new AlertDialog.Builder(this);

        // inflate the view, but keep a reference to it
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.book_pop_up, null);
        bookpopupDialog.setView(dialogView);
        AlertDialog bookgridPane = bookpopupDialog.create();

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
        grid_oldtest.setAdapter(new ButtonGridAdapter(this, getSupportFragmentManager(),
                bookgridPane, bookButton, chapterButton, old_test, true, true, biblePager));

        GridView grid_newtest = dialogView.findViewById(R.id.buttongrid_newtest);
        grid_newtest.setAdapter(new ButtonGridAdapter(this, getSupportFragmentManager(),
                bookgridPane, bookButton, chapterButton, new_test, true, true, biblePager));

        // display the dialog
        bookgridPane.show();
    }

    // Generates a pop up of all the chapters for given book
    public void chapterPopUp(View view){
        // make a popup builder
        AlertDialog.Builder chapterpopupDialog = new AlertDialog.Builder(this);

        // inflate the view, but keep a reference to it
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.chapter_pop_up, null);
        chapterpopupDialog.setView(dialogView);
        AlertDialog chaptergridPane = chapterpopupDialog.create();

        // create the button text dynamically depending on number of chapters in the selected book
        String book = GlobalVariable.getInstance().getBook();
        int numChapters = kjv.getChapterCount(book);
        String[] chapnums = new String[numChapters];
        for (int i = 0; i < numChapters; i++) {
            chapnums[i] = "" + (i+1);
        }

        // get the layout and add the button(s)
        GridView grid_chapters = dialogView.findViewById(R.id.buttongrid_chapters);
        grid_chapters.setAdapter(new ButtonGridAdapter(this, null, chaptergridPane,
                bookButton, chapterButton, chapnums, false, true, biblePager));

        // display the dialog
        chaptergridPane.show();
    }

    private AlertDialog generic_popup(int layout) {
        // make a popup builder
        AlertDialog.Builder popupDialogBuilder = new AlertDialog.Builder(this);

        // inflate the view, but keep a reference to it
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(layout, null);
        popupDialogBuilder.setView(dialogView);
        AlertDialog popupDialog = popupDialogBuilder.create();

        // display the dialog
        try{
            popupDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams wmlp = popupDialog.getWindow().getAttributes();
            LinearLayout botnavbar = findViewById(R.id.toolbar_bot);
            wmlp.y = botnavbar.getTop() - botnavbar.getBottom()/2 - botnavbar.getHeight()*3/2;
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
        popupDialog.show();
        return popupDialog;
    }
}
