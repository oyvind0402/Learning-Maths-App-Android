package com.example.mappe1apputvikling_s188886_s344105;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class FrontPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontpage);

        Button button = findViewById(R.id.start_spill);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), Spillet.class);
            startActivity(intent);
        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String land = prefs.getString("velgSpråk", "no");
        String antSpm = prefs.getString("velgAntSpm", "15");
        Locale locale = getResources().getConfiguration().locale;

        if(!locale.toString().equals(land)) {
            byttLocale(land);
            recreate();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String land = prefs.getString("velgSpråk", "no");
        String antSpm = prefs.getString("velgAntSpm", "15");
        Locale locale = getResources().getConfiguration().locale;

        if(!locale.toString().equals(land)) {
            byttLocale(land);
            recreate();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String land = prefs.getString("velgSpråk", "no");
        String antSpm = prefs.getString("velgAntSpm", "15");
        Locale locale = getResources().getConfiguration().locale;

        if(!locale.toString().equals(land)) {
            byttLocale(land);
            recreate();
        }
    }

    public void byttLocale(String land) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration cf = res.getConfiguration();
        cf.setLocale(new Locale(land));
        res.updateConfiguration(cf, dm);
    }

    public void visPreferanser(View v) {
        Intent intent = new Intent(this, Preferanser.class);
        startActivity(intent);
    }
}