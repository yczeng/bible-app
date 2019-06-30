package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class TestMain{

	// where the root of the application is stored (so we can switch)
	public static final String zackpi_app_folder = "/home/zackpi/Documents/Work/bible-app/";
	public static final String yczeng_app_folder = "/home/yczeng/Documents/bibleapp2/";
	public static final String app_folder = zackpi_app_folder;	// ** SWITCH THIS **

	// this is a json array of every verse in kjv
	public static final String kjvfile = app_folder + "app/src/main/assets/kjv.json";

	// This file shows the kjv as a map with the book strings as the keys
  // within the book strings, the chapters are the key
	public static final String kjvfile2 = app_folder + "app/src/main/assets/reformattedKjv2.json";

	// This file maps a book -> number of chapters in the book
	public static final String chapterNumFile = app_folder + "app/src/main/assets/chapterCount.json";

	// This file contains a trie that can be used to easily search for any word in the Bible
	public static final String searchTrieFile = app_folder + "app/src/main/assets/searchTrie.json";

	public static JSONArray verseJson;
	public static JSONObject bibleMap;
	public static JSONObject chapterNum;
	public static JSONObject searchTrie;

	public static JSONBible kjv;

	public static void main(String[] args) {
		long startTime = System.nanoTime();

		verseJson = readJSONArray(kjvfile);
		bibleMap = readJSONObject(kjvfile2);
		chapterNum = readJSONObject(chapterNumFile);
		searchTrie = readJSONObject(searchTrieFile);

		System.out.println("Loaded properly");


		kjv = new JSONBible(bibleMap, searchTrie);

		// String book, int chapter, int verse

		for (int i = 0; i < 1; i++) {
			System.out.println(kjv.search("serpent"));
		}
		System.out.println((System.nanoTime() - startTime) / 1000000000.0 / 1000);
	}


	// Reads a json object from a file
	public static JSONObject readJSONObject(String filename) {
		byte[] buffer = null;

		// grabs the file locally
		try{
			InputStream is = new FileInputStream(filename);
	        int size = is.available();
	        buffer = new byte[size];
	        is.read(buffer);
	        is.close();
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	    }

        String json = null;
        JSONObject obj = null;
        try {
        	// grabs json object from the buffer
            json = new String(buffer, "UTF-8");

            // saves the json string as a JSONObject
            obj = new JSONObject(json);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

	    return obj;
	}


	// Reads a json array from a file
	public static JSONArray readJSONArray(String filename) {
		byte[] buffer = null;

		// grabs the file locally
		try{
			InputStream is = new FileInputStream(filename);
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
