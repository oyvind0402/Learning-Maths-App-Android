package com.example.mappe1apputvikling_s188886_s344105;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Locale;

public class PreferanceActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
        if(key.equals("velgSpråk")) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            String land = prefs.getString("velgSpråk", "no");
            byttLocale(land);
            recreate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferanser);

        getSupportFragmentManager().beginTransaction().replace(R.id.settings_container, new PreferanceFragment()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void byttLocale(String land) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration cf = res.getConfiguration();
        cf.setLocale(new Locale(land));
        res.updateConfiguration(cf, dm);
    }
}
