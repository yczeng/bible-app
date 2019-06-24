package com.android.biblio.app;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class ReaderActivity extends AppCompatActivity {
    Button bookButton;
    Button chapterButton;
    JSONBible kjv;
    ViewPager biblePager;
    SearchView searchView;
    TextView searchResults;

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
        List<String> arr = new ArrayList<String>();
        for (int i = 1; i <= chapterNum; i++){
            arr.add(kjv.get(book, i));
        }
        String[] arrList = new String[arr.size()];
        arrList = arr.toArray(arrList);

        // create the viewpager and corresponding adapter
        // that will scroll through the chapter fragments
        biblePager = findViewById(R.id.biblepager);
        biblePager.setAdapter(new ReaderPagerAdapter(getSupportFragmentManager(), arrList, chapterButton));
        biblePager.setCurrentItem(chapter-1);
        biblePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageSelected(int position) {
                GlobalVariable.getInstance().setChapter(position);
                chapterButton.setText("" + (position + 1));
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final BottomNavigationView botnavbar = findViewById(R.id.botnavbar);
        botnavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                AlertDialog popupDialog = null;
                switch(menuItem.getItemId()) {
                    case R.id.botnavbar_bookmark:
                        popupDialog = generic_popup(R.layout.bookmark_popup);
                        break;
                    case R.id.botnavbar_compare:
                        popupDialog = generic_popup(R.layout.compare_popup);
                        break;
                    case R.id.botnavbar_notes:
                        popupDialog = generic_popup(R.layout.notes_popup);
                        break;
                    case R.id.botnavbar_autoread:
                        popupDialog = generic_popup(R.layout.autoread_popup);
                        break;
                    case R.id.botnavbar_textoptions:
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
                                TextView pagertext = findViewById(R.id.pager_text);
                                pagertext.setTextSize((float)(12 + 12.0 * i / 100));
                                biblePager.getAdapter().notifyDataSetChanged();
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {

                            }
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
                            }
                        });
                        theme1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                GlobalVariable.getInstance().setTextThemeRadioButton(1);
                            }
                        });
                        theme2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                GlobalVariable.getInstance().setTextThemeRadioButton(2);
                            }
                        });





                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

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

        final GridView results_grid = dialogView.findViewById(R.id.buttongrid_searchresults);
        final AlertDialog searchgridPanel = searchpopupDialog.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("search_view", "search worked");
                JSONArray resultsJson = kjv.search(query);
                results_grid.setAdapter(new SearchAdapter(context, getSupportFragmentManager(), searchgridPanel, resultsJson, bookButton, chapterButton, biblePager));
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

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
                chapterButton, null, chapnums, false, true, biblePager));

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
            BottomNavigationView botnavbar = findViewById(R.id.botnavbar);
            wmlp.y = botnavbar.getTop() - botnavbar.getBottom()/2 - botnavbar.getHeight();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
        popupDialog.show();
        return popupDialog;
    }
}
