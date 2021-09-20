package com.example.mappe1apputvikling_s188886_s344105;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class StatisticsActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistikk);

        TextView antallSpill = findViewById(R.id.antallSpill);
        TextView antallRiktige = findViewById(R.id.riktigeSvarStatistikk);
        TextView antallFeil = findViewById(R.id.feilSvarStatistikk);

        SharedPreferences setPrefs = getApplicationContext().getSharedPreferences("com.example.mappe1apputvikling_s188886_s344105", MODE_PRIVATE);
        //@SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = setPrefs.edit();

        String totalRiktigeString = setPrefs.getString("riktigeSvar", "");
        String totalFeilString = setPrefs.getString("feilSvar", "");

        antallRiktige.setText("Total antall riktige: " + totalRiktigeString);
        antallFeil.setText("Total antall feil: " + totalFeilString);
    }
}
