package com.example.mappe1apputvikling_s188886_s344105;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, FinishedGameDialog.DialogClickListener{
    String[] spm;
    String[] svar;
    ArrayList<Integer> order = new ArrayList<>();
    private int riktigeSvar = 0;
    private int feilSvar = 0;
    private int antallRegnestykker = 0;
    private int svarteRegnestykker = 0;
    private boolean startetSpill = false;
    private int antallSpill = 0;
    private boolean avslutt = false;

    @Override
    public void onCancelClick() {
        SharedPreferences setPrefs = getApplicationContext().getSharedPreferences("com.example.mappe1apputvikling_s188886_s344105", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = setPrefs.edit();

        editor.putString("fragmentAktiv", "ikke-aktiv");
        editor.apply();
        nullStillVerdier();
    }

    @SuppressLint("ResourceType")
    @Override
    public void onContinueClick() {
        if(svarteRegnestykker == order.size()) {
            new AlertDialog.Builder(this).setTitle(R.string.tom_tittel).setMessage(R.string.tom_for_spm).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences setPrefs = getApplicationContext().getSharedPreferences("com.example.mappe1apputvikling_s188886_s344105", MODE_PRIVATE);
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = setPrefs.edit();

                    editor.putString("fragmentAktiv", "ikke-aktiv");
                    editor.apply();
                    nullStillVerdier();
                }
            }).create().show();
        } else {
            startSpill(findViewById(R.layout.game));
        }
    }

    public void nullStillVerdier(){
        //Alle verdier resettes når du trykker på fortsett og det ikke er flere spørsmål igjen
        SharedPreferences editPrefs = getApplicationContext().getSharedPreferences("com.example.mappe1apputvikling_s188886_s344105", MODE_PRIVATE);
        SharedPreferences.Editor editor = editPrefs.edit();
        editor.putString("riktigeSvarMellomlagret", "");
        editor.putString("feilSvarMellomlagret", "");
        editor.putString("antallSpillMellomlagret", "");
        editor.putString("orderTall", "");
        editor.putString("startetSpill", "");
        editor.apply();
        riktigeSvar = 0;
        antallRegnestykker = 0;
        antallSpill = 0;
        feilSvar = 0;
        avslutt = true;
        finish();
    }


    @Override
    public void onBackPressed() {
        if(startetSpill) {
            new AlertDialog.Builder(this).setTitle(R.string.avslutt_fortsett).setMessage(R.string.erdusikker).setPositiveButton(R.string.fortsettspill, null).setNegativeButton(R.string.avsluttspill, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    svarteRegnestykker = 0;
                    SharedPreferences setPrefs = getApplicationContext().getSharedPreferences("com.example.mappe1apputvikling_s188886_s344105", MODE_PRIVATE);
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = setPrefs.edit();

                    editor.putString("fragmentAktiv", "ikke-aktiv");
                    editor.apply();
                    nullStillVerdier();
                }
            }).create().show();
        }
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        avslutt = false;
        order.clear();
        for(int i = 0; i < 15; i++){
            order.add(i);
        }
        Collections.shuffle(order);

        SharedPreferences setPrefs = getApplicationContext().getSharedPreferences("com.example.mappe1apputvikling_s188886_s344105", MODE_PRIVATE);
        String aktivFragment = setPrefs.getString("fragmentAktiv", "ikke-aktiv");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        setButtons();
        if(aktivFragment.equals("aktiv")){
            DialogFragment fortsett2 = new FinishedGameDialog();
            fortsett2.setCancelable(false);
            fortsett2.show(getSupportFragmentManager(), "Avslutt?");
        }

        String tomForRegnestykker = setPrefs.getString("tomForRegnestykker", "false");
        if(tomForRegnestykker.equals("true")) {
            new AlertDialog.Builder(this).setTitle(R.string.tom_tittel).setMessage(R.string.tom_for_spm).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences setPrefs = getApplicationContext().getSharedPreferences("com.example.mappe1apputvikling_s188886_s344105", MODE_PRIVATE);
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = setPrefs.edit();

                    editor.putString("fragmentAktiv", "ikke-aktiv");
                    editor.apply();
                    nullStillVerdier();
                }
            }).create().show();
        }

        startSpill(findViewById(R.layout.game));

    }

    public void rotering(View view){
        ImageView image = (ImageView)findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotation);
        image.startAnimation(animation);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String land = prefs.getString("velgSpråk", "no");

        spm = getResources().getStringArray(R.array.regneStykker);
        svar = getResources().getStringArray(R.array.regneStykkeSvar);

        SharedPreferences editPrefs = getApplicationContext().getSharedPreferences("com.example.mappe1apputvikling_s188886_s344105", MODE_PRIVATE);

        String antSpm = prefs.getString("velgAntSpm", "5");
        antallRegnestykker = Integer.parseInt(antSpm);

        //Hvis strengene har lengde > 0 vil det si at de har blitt lagret i onPause når man bytter landscape - da kan vi sette verdiene tilbake uten at de resettes
        String riktigeSvarString = editPrefs.getString("riktigeSvarMellomlagret", "0");

        if(riktigeSvarString.length() > 0) {
            riktigeSvar = Integer.parseInt(riktigeSvarString);
        }
        String feilSvarString = editPrefs.getString("feilSvarMellomlagret", "0");
        if(feilSvarString.length() > 0) {
            feilSvar = Integer.parseInt(feilSvarString);
        }

        String antallSpillString = editPrefs.getString("antallSpillMellomlagret", "0");
        if(antallSpillString.length() > 0) {
            antallSpill = Integer.parseInt(antallSpillString);
        }

        String svarteRegnestykkerString = editPrefs.getString("svarteRegnestykker", "0");
        if(svarteRegnestykkerString.length() > 0) {
            svarteRegnestykker = Integer.parseInt(svarteRegnestykkerString);
        }

        String startetSpillString = editPrefs.getString("startetSpill", "false");
        if(startetSpillString.length() > 0) {
            startetSpill = Boolean.parseBoolean(startetSpillString);
        }

        String intString = editPrefs.getString("orderTall", "");
        if(intString.length() > 0) {
            order.clear();
            for(String s : intString.split(" ")) {
                order.add(Integer.parseInt(s));
            }
        }

        //Verdiene blir reset etter å ha blitt hentet, for sikkerhets skyld
        SharedPreferences.Editor editor = editPrefs.edit();
        editor.putString("riktigeSvarMellomlagret", "");
        editor.putString("feilSvarMellomlagret", "");
        editor.putString("antallSpillMellomlagret", "");
        editor.putString("svarteRegnestykker", "");
        editor.putString("orderTall", "");
        editor.putString("startetSpill", "");
        editor.apply();

        //Verdiene blir satt inn i TextViews igjen for å ikke miste dem når vi bytter landscape
        TextView regnestykke = findViewById(R.id.regnestykke);
        TextView avsluttSpill = findViewById(R.id.riktigesvar);
        TextView avsluttSpill2 = findViewById(R.id.feilsvar);

        if(svarteRegnestykker == order.size()){
            svarteRegnestykker = 0;
            editor.putString("tomForRegnestykker", "true");
            editor.apply();
        } else {
            editor.putString("tomForRegnestykker", "false");
            editor.apply();
        }

        regnestykke.setText(spm[order.get(svarteRegnestykker)]);
        avsluttSpill.setText(getResources().getString(R.string.riktige_svar) + " " + riktigeSvar);
        avsluttSpill2.setText(getResources().getString(R.string.feil_svar) + " " + feilSvar);

        Locale locale = getResources().getConfiguration().locale;
        if(!locale.toString().equals(land) && !avslutt) {
            byttLocale(land);
            Intent intent = new Intent(this, GameActivity.class);
            finish();
            startActivity(intent);
        }
    }

    // Må legge til at alle verdier blir lagret til shared preferences når onPause kalles - og så må vi hente verdiene tilbake i onResume.
    @SuppressLint("SetTextI18n")
    @Override
    protected void onPause() {
        if(!avslutt) {
            SharedPreferences editPrefs = getApplicationContext().getSharedPreferences("com.example.mappe1apputvikling_s188886_s344105", MODE_PRIVATE);
            SharedPreferences.Editor editor = editPrefs.edit();
            editor.putString("riktigeSvarMellomlagret", String.valueOf(riktigeSvar));
            editor.putString("feilSvarMellomlagret", String.valueOf(feilSvar));
            editor.putString("antallSpillMellomlagret", String.valueOf(antallSpill));
            editor.putString("svarteRegnestykker", String.valueOf(svarteRegnestykker));
            StringBuilder orderTall = new StringBuilder();
            boolean ikkeMellomrom = true;
            for(int i = 0; i < order.size(); i++) {
                if(ikkeMellomrom) {
                    ikkeMellomrom = false;
                } else {
                    orderTall.append(" ");
                }
                orderTall.append(order.get(i));
            }
            editor.putString("orderTall", orderTall.toString());
            editor.putString("startetSpill", String.valueOf(startetSpill));
            editor.apply();
        }
        super.onPause();
    }

    @SuppressLint("SetTextI18n")
    public void startSpill(View v) {
        startetSpill = true;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String antSpm = prefs.getString("velgAntSpm", "5");
        antallRegnestykker = Integer.parseInt(antSpm);
        spm = getResources().getStringArray(R.array.regneStykker);
        svar = getResources().getStringArray(R.array.regneStykkeSvar);

        setRegneStykke();
        TextView avsluttSpill = findViewById(R.id.riktigesvar);
        TextView avsluttSpill2 = findViewById(R.id.feilsvar);
        avsluttSpill.setText(getResources().getString(R.string.riktige_svar) + " " + riktigeSvar);
        avsluttSpill2.setText(getResources().getString(R.string.feil_svar) + " " + feilSvar);
    }

    @SuppressLint("SetTextI18n")
    private void setRegneStykke() {
        TextView regneStykke = findViewById(R.id.regnestykke);
        regneStykke.setText(spm[order.get(svarteRegnestykker)]);
    }

    @SuppressLint("SetTextI18n")
    public void svar(View v) {
        TextView skrevetSvar = findViewById(R.id.svar);
        String gitSvar = (String) skrevetSvar.getText();
        skrevetSvar.setText("");

        SharedPreferences setPrefs = getApplicationContext().getSharedPreferences("com.example.mappe1apputvikling_s188886_s344105", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = setPrefs.edit();

        editor.putString("fragmentAktiv", "ikke-aktiv");
        editor.apply();

        if(gitSvar.length() > 0) {
            int gitSvarInt = Integer.parseInt(gitSvar);
            int korrektSvar = Integer.parseInt(svar[order.get(svarteRegnestykker)]);
            svarteRegnestykker += 1;

            if(gitSvarInt == korrektSvar){
                riktigeSvar += 1;
            } else {
                feilSvar += 1;
            }

            if(svarteRegnestykker % antallRegnestykker == 0 && svarteRegnestykker != 0) {
                startetSpill = false;
            }

            TextView avsluttSpill = findViewById(R.id.riktigesvar);
            TextView avsluttSpill2 = findViewById(R.id.feilsvar);
            avsluttSpill.setText(getResources().getString(R.string.riktige_svar) + " " + riktigeSvar);
            avsluttSpill2.setText(getResources().getString(R.string.feil_svar) + " " + feilSvar);


            if(startetSpill){
                if(svarteRegnestykker == order.size() - 1) {
                    startetSpill = false;
                }
                setRegneStykke();
            } else {
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

                editor.putString("fragmentRiktigeSvar", String.valueOf(riktigeSvar));
                editor.putString("fragmentFeilSvar", String.valueOf(feilSvar));
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
                        .streamFor(300, 1250);

                //Vi må vente til animation er ferdig før vi kan gjøre noe annet:
                // Basert på: https://stackoverflow.com/questions/5321344/android-animation-wait-until-finished
                viewKonfetti.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        editor.putString("fragmentAktiv", "aktiv");
                        editor.apply();
                        //DialogFragment fortsett = new FinishedGameDialog(riktigeSvar, feilSvar);
                        DialogFragment fortsett = new FinishedGameDialog();
                        fortsett.setCancelable(false);
                        fortsett.show(getSupportFragmentManager(), "Avslutt?");
                    }
                }, 250);
            }
        }
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n", "ResourceType"})
    @Override
    public void onClick(View v){ // Denne funksjonen blir aktivert dersom man trykker på 0-9 eller fjern knappen
        TextView svar = findViewById(R.id.svar); // Henter det som er allerede er i TextView svar
        String text = svar.getText().toString(); //

        if(v.getId() == R.id.buttonNullstille){
            svar.setText(""); //Fjerne teksten/start på nytt
        } else if(svar.getText().toString().length() < 3){// Det skal ikke være mulig å skrive mer enn 3 tall
            Button trykketKnapp = findViewById(v.getId());
            String tekstIknappen = (String) trykketKnapp.getText();
            svar.setText(text + tekstIknappen);
        }
    }

    public void setButtons(){ //Svar knappen blir ikke tatt med her siden meste av logikken skjer i denne knappen
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

        Button[] knapper = {button0, button1, button2, button3,button4, button5, button6,button7,button8, button9,button10};
        for (Button button : knapper) {
            button.setOnClickListener(this); //Her setter vi en onClick listner slik at vi vet på hvilke knapp brukeren har trykket.
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
