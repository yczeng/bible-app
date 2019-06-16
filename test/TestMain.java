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

	// this is a json array of every verse in kjv
	public static final String kjvfile = "/home/yczeng/Documents/bibleapp2/app/src/main/assets/kjv.json";
	// This file shows the kjv as a map with the book strings as the keys
	// within the book strings, the chapters are the key
	public static final String kjvfile2 = "/home/yczeng/Documents/bibleapp2/app/src/main/assets/reformattedKjv2.json";

	public static JSONArray verseJson;
	public static JSONObject bibleMap;
	public static JSONBible kjv;

	public static void main(String[] args) {
		verseJson = readJSONArray(kjvfile);
		bibleMap = readJSONObject(kjvfile2);
		
		kjv = new JSONBible(bibleMap, verseJson);

		// String book, int chapter, int verse
		// System.out.println(kjv.get("Gen", 3, 10));
		System.out.println(kjv.search("serpent"));
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
