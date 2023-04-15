package com.example.finalproject_agam_individual;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

/**
 * This activity has a search bar and pulls the word's definition from API.
 * Additionally, it has all UI components in accordance with the guidelines.
 * @author Agam
 * @Date 2-04-2023
 */
public class MainActivity extends AppCompatActivity {
    private ImageView guide;
    private Button wordDay;
    private Button home;
    private Button saveWord;
    private Button searchB;
    private Button searchPage;
    private TextView last;
    private EditText enterWord;
    private ProgressBar progress;

    /**
     * This method initiates the UI when the app starts
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("my_app", MODE_PRIVATE);

        guide = findViewById(R.id.img);
        wordDay = findViewById(R.id.wordDay);
        home = findViewById(R.id.home);
        searchPage = findViewById(R.id.searchPage);
        saveWord = findViewById(R.id.saveWord);
        searchB = findViewById(R.id.search);
        enterWord = findViewById(R.id.word);
        last = findViewById(R.id.meaning);
        progress = findViewById(R.id.progress_bar);

        last.setText(sharedPreferences.getString("str", "note"));


        guide.setOnClickListener(new View.OnClickListener() {
            /**
             * This methods works when guide image button is clicked
             * @param v
             */
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });


        wordDay.setOnClickListener(new View.OnClickListener() {
            /**
             * This diplays a toast when word of the day button is clicked
             * @param v
             */
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.AYuvraj, Toast.LENGTH_SHORT).show();
              //  Intent goToProfile = new Intent(MainActivity.this, WordOfTheDayActivity.class);
              //  startActivity(goToProfile);
            }
        });

        searchPage.setOnClickListener(new View.OnClickListener() {
            /**
             * This diplays a toast when Search button is clicked
             * @param v
             */
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.ASearchWord, Toast.LENGTH_SHORT).show();
                //  Intent goToProfile = new Intent(MainActivity.this, MainActivity.class);
                //  startActivity(goToProfile);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            /**
             * This diplays a toast when Home button is clicked
             * @param v
             */
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.Ahome, Toast.LENGTH_SHORT).show();
            //    Intent goToProfile = new Intent(MainActivity.this, AmanActivity.class);
            //    startActivity(goToProfile);

            }
        });

        saveWord.setOnClickListener(new View.OnClickListener() {
            /**
             * This diplays a toast when save word button is clicked
             * @param v
             */
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.AMuskan, Toast.LENGTH_SHORT).show();
           //     Intent goToProfile = new Intent(MainActivity.this, MainActivity.class);
           //     startActivity(goToProfile);
            }
        });


        searchB.setOnClickListener(new View.OnClickListener() {
            /**
             * This displays snakbars when search button is clicked.
             * It shows progress bar and saves data in shared prefrences when clicked.
             * @param v
             */
            @Override
            public void onClick(View v) {
                Button createButton = findViewById(R.id.search);
                EditText wordField = findViewById(R.id.word);
                String wor = wordField.getText().toString();

                if (TextUtils.isEmpty(wor)) {
                    Snackbar.make(createButton, R.string.Aempty, Snackbar.LENGTH_SHORT).show();
                } else {

                    Snackbar.make(createButton, "OK", Snackbar.LENGTH_LONG)
                            .setAction("Yes", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .show();
                }

                // Show the progress bar
                progress.setVisibility(View.VISIBLE);
                // Simulate search operation by delaying for 3 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Hide progress bar
                        progress.setVisibility(View.GONE);

                    }
                }, 3000);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("str", wor);
                editor.apply();

                last.setText(sharedPreferences.getString("str", "note"));
                Intent goToProfile = new Intent(MainActivity.this, Word_Meaning.class);
                goToProfile.putExtra("WORD", enterWord.getText().toString());
                startActivity(goToProfile);


            }
        });

    }


    /**
     * This method dsiplays the alert dialog box
     */
    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.Aguide);
        builder.setMessage(R.string.Aalert);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public class LanguageChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_LOCALE_CHANGED)) {
                // update the locale of the application context to the new language
                Locale newLocale = Resources.getSystem().getConfiguration().locale;
                context.getResources().updateConfiguration(context.getResources().getConfiguration(), context.getResources().getDisplayMetrics());

                String alertMessage1 = context.getString(R.string.Aalert);
                String alertMessage2 = context.getString(R.string.Aguide);
                String alertMessage3 = context.getString(R.string.ASearchWord);
                String alertMessage4 = context.getString(R.string.AYuvraj);
                String alertMessage5 = context.getString(R.string.AMuskan);
                String alertMessage6 = context.getString(R.string.Aempty);
                String alertMessage7 = context.getString(R.string.AMuskan);

// Show alert message
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(alertMessage1);
                AlertDialog alert = builder.create();
                alert.show();
            }
        }

    }

}

