package com.example.mappe1apputvikling_s188886_s344105;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    String[] alleRegneStykker;
    String[] alleRegneStykkerSvar;
    private int riktigeSvar = 0;
    private int feilSvar = 0;
    private int startAntall = 0;
    private boolean startetSpill = false;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alleRegneStykker = getResources().getStringArray(R.array.regneStykker);
        alleRegneStykkerSvar = getResources().getStringArray(R.array.regneStykkeSvar);
        setContentView(R.layout.spill);
        setButtons();
        startSpill(findViewById(R.layout.spill));
    }

    // Mangler å gjøre sånn at man velger 5 tilfeldige spørsmål per spill, og at de 5 ikke skal gjentas i samme spillsession. Og at når man da har svart på 5 spørsmål 3 ganger at det skal komme f.eks
    // en dialogboks for å si ifra til spilleren at det er tomt for spørsmål.

    // Mangler også å lagre alle verdier til SharedPreferences sånn at spillet henter verdier fra SharedPreferences i stedet for fra variabler her ettersom de blir reset når man bytter til landscape modus.
    public void startSpill(View v) {
        startAntall = 0;
        riktigeSvar = 0;
        feilSvar = 0;
        String forsteSpm = alleRegneStykker[startAntall];
        TextView regneStykke = findViewById(R.id.regnestykke);
        regneStykke.setText(forsteSpm);
        TextView avsluttSpill = findViewById(R.id.riktigesvar);
        TextView avsluttSpill2 = findViewById(R.id.feilsvar);
        avsluttSpill.setText(getResources().getString(R.string.riktige_svar));
        avsluttSpill2.setText(getResources().getString(R.string.feil_svar));
        startetSpill = true;
    }

    public void svar(View v) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String antSpm = prefs.getString("velgAntSpm", "5");
        int maxAntall = Integer.parseInt(antSpm);

        TextView svar = findViewById(R.id.svar);
        TextView regneStykke = findViewById(R.id.regnestykke);
        if(startetSpill) {
            if(startAntall < maxAntall - 1) {
                for (int i = 0; i < maxAntall - 1; i++) {
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
            } else {
                if(regneStykke.getText().equals(alleRegneStykker[maxAntall - 1])) {
                    if(svar.getText().equals(alleRegneStykkerSvar[maxAntall - 1])) {
                        riktigeSvar += 1;
                    } else {
                        feilSvar += 1;
                    }
                }
                TextView avsluttSpill = findViewById(R.id.riktigesvar);
                String text = avsluttSpill.getText().toString();
                avsluttSpill.setText(text + " " + String.valueOf(riktigeSvar));
                TextView avsluttSpill2 = findViewById(R.id.feilsvar);
                String text2 = avsluttSpill2.getText().toString();
                avsluttSpill2.setText(text2 + " " + String.valueOf(feilSvar));
                regneStykke.setText("");
                startetSpill = false;
                SharedPreferences setPrefs = getApplicationContext().getSharedPreferences("com.example.mappe1apputvikling_s188886_s344105", MODE_PRIVATE);
                SharedPreferences.Editor editor = setPrefs.edit();
                editor.putString("riktigeSvar", String.valueOf(riktigeSvar));
                editor.putString("feilSvar", String.valueOf(feilSvar));
            }
            svar.setText("");
        }
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n", "ResourceType"})
    @Override
    public void onClick(View v){ // Denne funksjonen blir aktivert dersom man trykker på 0-9 eller fjern knappen
        TextView svar = findViewById(R.id.svar);
        String text = svar.getText().toString();

        if(startetSpill && svar.getText().toString().length() < 3) { // Det skal ikke være mulig å skrive mer enn 3 tall
            switch (v.getId()) {
                case R.id.button0:
                    svar.setText(text + "0");
                    break;
                case R.id.button1:
                    svar.setText(text + "1");
                    break;
                case R.id.button2:
                    svar.setText(text + "2");
                    break;
                case R.id.button3:
                    svar.setText(text + "3");
                    break;
                case R.id.button4:
                    svar.setText(text + "4");
                    break;
                case R.id.button5:
                    svar.setText(text + "5");
                    break;
                case R.id.button6:
                    svar.setText(text + "6");
                    break;
                case R.id.button7:
                    svar.setText(text + "7");
                    break;
                case R.id.button8:
                    svar.setText(text + "8");
                    break;
                case R.id.button9:
                    svar.setText(text + "9");
                    break;
                case R.id.buttonNullstille:
                    svar.setText("");
                    break;
            }
        } else {

            //feedback i tillfelle det er allerede 3 sifre

        }
    }

    public void setButtons(){
        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button button10 = findViewById(R.id.buttonNullstille);

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);
    }
}
