package com.example.mappe1apputvikling_s188886_s344105;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Locale;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, FerdigSpillDialog.DialogClickListener{
    ArrayList<String> alleRegneStykker = new ArrayList();
    ArrayList<Integer> alleRegneStykkerSvar = new ArrayList();
    private int riktigeSvar = 0;
    private int feilSvar = 0;
    private int antallRegnestykker = 0;
    private boolean startetSpill = false;
    private int antallSpill = 0;

    @Override
    public void onCancelClick() {
        finish();
    }

    @SuppressLint("ResourceType")
    @Override
    public void onContinueClick() {
        startSpill(findViewById(R.layout.spill));
    }

    /*  Metode for å lage en popup når man trykker på tilbakeknappen - skal skrive den i morgen!
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
    }
     */

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spill);
        setButtons();
        startSpill(findViewById(R.layout.spill));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String land = prefs.getString("velgSpråk", "no");
        Locale locale = getResources().getConfiguration().locale;

        if(!locale.toString().equals(land)) {
            byttLocale(land);
            recreate();
        }
    }

    public void rotering(View view){
        ImageView image = (ImageView)findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotation);
        image.startAnimation(animation);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String land = prefs.getString("velgSpråk", "no");
        Locale locale = getResources().getConfiguration().locale;

        if(!locale.toString().equals(land)) {
            byttLocale(land);
            recreate();
        }
    }

    // Må legge til at alle verdier blir lagret til shared preferences når onPause kalles - og så må vi hente verdiene tilbake i onResume.
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String land = prefs.getString("velgSpråk", "no");
        Locale locale = getResources().getConfiguration().locale;

        if(!locale.toString().equals(land)) {
            byttLocale(land);
            recreate();
        }
    }

    // Mangler å gjøre sånn at man velger 5 tilfeldige spørsmål per spill, og at de 5 ikke skal gjentas i samme spillsession. Og at når man da har svart på 5 spørsmål 3 ganger at det skal komme f.eks
    // en dialogboks for å si ifra til spilleren at det er tomt for spørsmål.

    // Mangler også å lagre alle verdier til SharedPreferences sånn at spillet henter verdier fra SharedPreferences i stedet for fra variabler her ettersom de blir reset når man bytter til landscape modus.
    public void startSpill(View v) {
        alleRegneStykker.clear();
        alleRegneStykkerSvar.clear();
        riktigeSvar = 0;
        feilSvar = 0;
        startetSpill = true;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String antSpm = prefs.getString("velgAntSpm", "5");
        antallRegnestykker = Integer.parseInt(antSpm);

        nyRegneStykke();



        /*TextView avsluttSpill = findViewById(R.id.riktigesvar);
        TextView avsluttSpill2 = findViewById(R.id.feilsvar);
        avsluttSpill.setText(getResources().getString(R.string.riktige_svar));
        avsluttSpill2.setText(getResources().getString(R.string.feil_svar));*/

    }

    public void nyRegneStykke(){
        int tall1 = (int)(Math.random() * 10);
        int tall2 = (int)(Math.random() * 10);

        String regneStykkeSpm = tall1 + " + " + tall2 + " =";
        alleRegneStykker.add(regneStykkeSpm);
        TextView regneStykke = findViewById(R.id.regnestykke);
        regneStykke.setText(regneStykkeSpm);

        int svar = tall1 + tall2;
        alleRegneStykkerSvar.add(svar);
    }

    public void svar(View v) {
        if(alleRegneStykker.size() == antallRegnestykker) {
            startetSpill = false;
        }

        TextView svar = findViewById(R.id.svar);
        String gitSvar = (String) svar.getText();
        int gitSvarInt = Integer.parseInt(gitSvar);
        int korrektSvar = alleRegneStykkerSvar.get(alleRegneStykkerSvar.size() - 1);
        svar.setText("");

        if(gitSvarInt == korrektSvar){
            riktigeSvar += 1;
        } else {
            feilSvar += 1;
        }


        if(startetSpill){
            nyRegneStykke();
        } else {
            SharedPreferences setPrefs = getApplicationContext().getSharedPreferences("com.example.mappe1apputvikling_s188886_s344105", MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = setPrefs.edit();

            String totalRiktigeString = setPrefs.getString("riktigeSvar", "");
            int totalRiktige = 0;
            try {
                totalRiktige = Integer.parseInt(totalRiktigeString);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }

            String totalFeilString = setPrefs.getString("feilSvar", "");
            int totalFeil = 0;
            try {
                totalFeil = Integer.parseInt(totalFeilString);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }

            String totalAntallSpillString = setPrefs.getString("antallSpill", "");
            try {
                antallSpill = Integer.parseInt(totalAntallSpillString);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }

            antallSpill += 1;
            totalRiktige += riktigeSvar;
            totalFeil += feilSvar;

            editor.putString("riktigeSvar", String.valueOf(totalRiktige));
            editor.putString("feilSvar", String.valueOf(totalFeil));
            editor.putString("antallSpill", String.valueOf(antallSpill));
            editor.apply();

            //Hentet fra https://stackoverflow.com/questions/52228999/celebration-animation-in-android-studio
            KonfettiView viewKonfetti = findViewById(R.id.viewKonfetti);

            viewKonfetti.build()
                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.RECT, Shape.CIRCLE)
                    .addSizes(new Size(12, 5))
                    .setPosition(-50f, viewKonfetti.getWidth() + 50f, -50f, -50f)
                    .streamFor(300, 2500);

            //Vi må vente til animation er ferdig før vi kan gjøre noe annet:
            // Basert på: https://stackoverflow.com/questions/5321344/android-animation-wait-until-finished
            viewKonfetti.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DialogFragment fortsett = new FerdigSpillDialog(riktigeSvar, feilSvar);
                    fortsett.setCancelable(false);
                    fortsett.show(getSupportFragmentManager(), "Avslutt?");
                }
            }, 1000);

            /*Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.rotation);
            LinearLayout spill = findViewById(R.id.spill);
            spill.startAnimation(animation);

            //Vi må vente til animation er ferdig før vi kan gjøre noe annet:
            // Basert på: https://stackoverflow.com/questions/5321344/android-animation-wait-until-finished
            spill.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DialogFragment fortsett = new FerdigSpillDialog(riktigeSvar, feilSvar);
                    fortsett.setCancelable(false);
                    fortsett.show(getSupportFragmentManager(), "Avslutt?");
                }
            }, 1100);*/
        }


    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n", "ResourceType"})
    @Override
    public void onClick(View v){ // Denne funksjonen blir aktivert dersom man trykker på 0-9 eller fjern knappen
        TextView svar = findViewById(R.id.svar);
        String text = svar.getText().toString();

        if(svar.getText().toString().length() < 3) { // Det skal ikke være mulig å skrive mer enn 3 tall
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
            if (v.getId() == R.id.buttonNullstille) {
                svar.setText("");
            }

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

    public void byttLocale(String land) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration cf = res.getConfiguration();
        cf.setLocale(new Locale(land));
        res.updateConfiguration(cf, dm);
    }
}
