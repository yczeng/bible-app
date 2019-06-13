package com.example.android.bibleapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String json = null;
                JSONObject obj = null;
                try {
                    InputStream is = context.getAssets().open("kjvTest.json");

                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();

                    json = new String(buffer, "UTF-8");

                    obj = new JSONObject(json);

                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                textView.setText(json);

                try {
                    textView1.setText(obj.getString("chapter"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }
}
