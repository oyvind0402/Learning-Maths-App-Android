package com.example.mappe1apputvikling_s188886_s344105;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Locale;

public class StatisticsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        TextView antallSpill = findViewById(R.id.antallSpill);
        TextView antallRiktige = findViewById(R.id.riktigeSvarStatistikk);
        TextView antallFeil = findViewById(R.id.feilSvarStatistikk);

        SharedPreferences setPrefs = getApplicationContext().getSharedPreferences("com.example.mappe1apputvikling_s188886_s344105", MODE_PRIVATE);

        String totalAntallSpill = setPrefs.getString("antallSpill", "");
        String totalRiktigeString = setPrefs.getString("riktigeSvar", "");
        String totalFeilString = setPrefs.getString("feilSvar", "");

        String text = getString(R.string.totalt_antall_spill) + " " + totalAntallSpill;
        String text2 = getString(R.string.totalt_antall_riktige) + " " + totalRiktigeString;
        String text3 = getString(R.string.totalt_antall_feil) + " " + totalFeilString;

        antallSpill.setText(text);
        antallRiktige.setText(text2);
        antallFeil.setText(text3);
    }

    public void rotering(View view){
        ImageView image = (ImageView)findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotation);
        image.startAnimation(animation);
    }

    public void slettStatistikk(View view) {

        SharedPreferences nullstill = getApplicationContext().getSharedPreferences("com.example.mappe1apputvikling_s188886_s344105", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = nullstill.edit();

        editor.putString("antallSpill", "0");
        editor.putString("riktigeSvar", "0");
        editor.putString("feilSvar", "0");
        editor.apply();

        recreate();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String land = prefs.getString("velgSpråk", "no");
        Locale locale = getResources().getConfiguration().locale;

        if(!locale.toString().equals(land)) {
            byttLocale(land);
            Intent intent = new Intent(this, StatisticsActivity.class);
            finish();
            startActivity(intent);
        }
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
