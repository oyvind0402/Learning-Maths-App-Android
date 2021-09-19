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
    private boolean startetSpill = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alleRegneStykker = getResources().getStringArray(R.array.regneStykker);
        alleRegneStykkerSvar = getResources().getStringArray(R.array.regneStykkeSvar);
        setContentView(R.layout.spill);
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

    public void fjern(View v) {
        TextView svar = findViewById(R.id.svar);
        String text = svar.getText().toString();
        if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
            svar.setText(text);
        }
    }

    public void trykk0(View v) {
        TextView svar = findViewById(R.id.svar);
        if(startetSpill && svar.getText().toString().length() < 3) {
            String text = svar.getText().toString();
            svar.setText(text + "0");
        }
    }

    public void trykk1(View v) {
        TextView svar = findViewById(R.id.svar);
        if(startetSpill && svar.getText().toString().length() < 3) {
            String text = svar.getText().toString();
            svar.setText(text + "1");
        }
    }

    public void trykk2(View v) {
        TextView svar = findViewById(R.id.svar);
        if(startetSpill && svar.getText().toString().length() < 3) {
            String text = svar.getText().toString();
            svar.setText(text + "2");
        }
    }

    public void trykk3(View v) {
        TextView svar = findViewById(R.id.svar);
        if(startetSpill && svar.getText().toString().length() < 3) {
            String text = svar.getText().toString();
            svar.setText(text + "3");
        }
    }

    public void trykk4(View v) {
        TextView svar = findViewById(R.id.svar);
        if(startetSpill && svar.getText().toString().length() < 3) {
            String text = svar.getText().toString();
            svar.setText(text + "4");
        }
    }

    public void trykk5(View v) {
        TextView svar = findViewById(R.id.svar);
        if(startetSpill && svar.getText().toString().length() < 3) {
            String text = svar.getText().toString();
            svar.setText(text + "5");
        }
    }

    public void trykk6(View v) {
        TextView svar = findViewById(R.id.svar);
        if(startetSpill && svar.getText().toString().length() < 3) {
            String text = svar.getText().toString();
            svar.setText(text + "6");
        }
    }

    public void trykk7(View v) {
        TextView svar = findViewById(R.id.svar);
        if(startetSpill && svar.getText().toString().length() < 3) {
            String text = svar.getText().toString();
            svar.setText(text + "7");
        }
    }

    public void trykk8(View v) {
        TextView svar = findViewById(R.id.svar);
        if(startetSpill && svar.getText().toString().length() < 3) {
            String text = svar.getText().toString();
            svar.setText(text + "8");
        }
    }

    public void trykk9(View v) {
        TextView svar = findViewById(R.id.svar);
        if(startetSpill && svar.getText().toString().length() < 3) {
            String text = svar.getText().toString();
            svar.setText(text + "9");
        }
    }
}
