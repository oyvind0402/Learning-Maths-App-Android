package com.example.mappe1apputvikling_s188886_s344105;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Locale;

public class Preferanser extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferanser);

        getSupportFragmentManager().beginTransaction().replace(R.id.settings_container, new PreferanseFragment()).commit();

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
}
