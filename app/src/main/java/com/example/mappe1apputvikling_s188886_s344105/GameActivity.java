package com.example.mappe1apputvikling_s188886_s344105;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    String[] alleRegneStykker;
    String[] alleRegneStykkerSvar;
    private int riktigeSvar = 0;
    private int feilSvar = 0;
    private int startAntall = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alleRegneStykker = getResources().getStringArray(R.array.regneStykker);
        alleRegneStykkerSvar = getResources().getStringArray(R.array.regneStykkeSvar);
        setContentView(R.layout.spill);
    }

    public void startSpill(View v) {
        String forsteSpm = alleRegneStykker[startAntall];
        TextView regneStykke = findViewById(R.id.regnestykke);
        regneStykke.setText(forsteSpm);
    }

    public void svar(View v) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String antSpm = prefs.getString("velgAntSpm", "5");
        int maxAntall = Integer.parseInt(antSpm);

        TextView svar = findViewById(R.id.svar);
        TextView regneStykke = findViewById(R.id.regnestykke);
        if(startAntall < maxAntall) {
            for (int i = 0; i <maxAntall; i++) {
                if(regneStykke.getText().equals(alleRegneStykker[i])) {
                    if (svar.getText().equals(alleRegneStykkerSvar[i])) {
                        riktigeSvar += 1;
                    } else {
                        feilSvar += 1;
                    }
                }
            }
            startAntall += 1;
            regneStykke.setText(alleRegneStykker[startAntall]);
            svar.setText("");
        } else {

        }
    }
}
