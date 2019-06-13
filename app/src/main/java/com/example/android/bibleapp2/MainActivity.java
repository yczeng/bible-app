package com.example.android.bibleapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;
    private TextView textView1;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.text);
        textView1 = findViewById(R.id.textView1);

        String json = null;
        JSONArray array = null;
        JSONObject obj = null;
        try {
            InputStream is = context.getAssets().open("kjvTest.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // grabs json object from the buffer
            try {
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // saves the json string as a JSONObject
            try {
                array = new JSONArray(json);
                obj = array.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            textView1.setText(json);

            try {
                textView1.setText(obj.getString("book_name"));
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing item " + e.toString());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textView.setText("boobies");
            }
        });



    }
}
