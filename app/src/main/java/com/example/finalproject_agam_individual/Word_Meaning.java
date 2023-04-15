package com.example.finalproject_agam_individual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Word_Meaning extends AppCompatActivity {

    String word = "";
    private static final String API_KEY = "526d3f4cec98fbb4ca0a27bc2f87e42c85d83071";
    private static String API_URL;

    private TextView wordTextView;
    private TextView definitionTextView;

    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordmeaning);

        wordTextView = findViewById(R.id.wordTextView);
        definitionTextView = findViewById(R.id.definitionTextView);
        back = findViewById(R.id.back);

        Intent fromMain = getIntent();
        word = fromMain.getStringExtra("WORD");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToProfile = new Intent(Word_Meaning.this, MainActivity.class);
                startActivity(goToProfile);
            }
        });

        // call the AsyncTask to get meaning of word
        new GetWordMeaning().execute();
    }

    private class GetWordMeaning extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            API_URL = "https://owlbot.info/api/v4/dictionary/" + word;
            try {
                URL url = new URL(API_URL + "?random=true");
                // add "?random=true" to API URL to get a random word

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Authorization", "Token " + API_KEY);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                connection.disconnect();

                return response.toString();
            }
            catch (IOException e) {
                Log.e("GetWordMeaning", "IOException", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    if (!jsonObject.has("word")) {
                         Log.e("GetWordMeaning", "API response did not contain a word");
                        Toast.makeText(Word_Meaning.this,R.string.Afailed, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    JSONArray definitionsArray = jsonObject.getJSONArray("definitions");

                    if (definitionsArray.length() == 0) {
                        // If the JSON response contains an empty array of definitions, log an error and show a Toast message
                        Log.e("GetWordMeaning", "API response did not contain any definitions");
                        Toast.makeText(Word_Meaning.this,R.string.Afailed, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String word = jsonObject.getString("word");
                    String definition = definitionsArray.getJSONObject(0).getString("definition");

                    wordTextView.setText(word);
                    definitionTextView.setText(definition);
                }

                catch (JSONException e) {
                    // If an error occurs while parsing the JSON response, log an error and show a Toast message
                    Log.e("GetWordMeaning", "Error parsing JSON response", e);
                    Toast.makeText(Word_Meaning.this,R.string.Afailed, Toast.LENGTH_SHORT).show();
                }
            }
            else {
                // If the API response is null, log an error and show a Toast message
                Log.e("GetWordMeaning", "API response was null");
                Toast.makeText(Word_Meaning.this,R.string.Afailed, Toast.LENGTH_SHORT).show();
            }
        }

    }
}