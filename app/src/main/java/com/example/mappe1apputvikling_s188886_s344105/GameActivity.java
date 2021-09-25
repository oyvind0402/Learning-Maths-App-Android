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
    SharedPreferences prefs;
    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor;
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
    public void onCancelClick() {    // Metodene fra interfacet i FinishedGameDialog
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
                    editor.putString("fragmentAktiv", "ikke-aktiv");
                    editor.apply();
                    nullStillVerdier();
                }
            }).create().show();
        } else {
            startSpill(findViewById(R.layout.game));
        }
    }

    public void nullStillVerdier(){    //Resetter alle verdier i shared preferences som lagres i onPause - for når man skal avslutte spilling
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
    public void onBackPressed() {    //Det kommer en popup som spør om du vil avslutte spiller når du trykker tilbake
        if(startetSpill) {
            new AlertDialog.Builder(this).setTitle(R.string.avslutt_fortsett).setMessage(R.string.erdusikker).setPositiveButton(R.string.fortsettspill, null).setNegativeButton(R.string.avsluttspill, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    svarteRegnestykker = 0;
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
        prefs = getApplicationContext().getSharedPreferences("com.example.mappe1apputvikling_s188886_s344105", MODE_PRIVATE);
        editor = prefs.edit();

        avslutt = false;
        order.clear(); //Order settes når vi starter spillet / bytter orientasjon - det går fint at den settes hver gang onCreate kjøres ettersom onResume setter verdien til det som er lagret i shared preferences etter at onCreate har kjørt
        for(int i = 0; i < 15; i++){
            order.add(i);
        }
        Collections.shuffle(order);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        setButtons();

        //Hvis du endrer orientasjon når en dialogboks er oppe så settes aktivFragment til aktiv, og hvis den er aktiv så skal vi lage en ny popup. Her henter vi verdien.
        String aktivFragment = prefs.getString("fragmentAktiv", "ikke-aktiv");
        if(aktivFragment.equals("aktiv")){
            DialogFragment fortsett2 = new FinishedGameDialog();
            fortsett2.setCancelable(false);
            fortsett2.show(getSupportFragmentManager(), "Avslutt?");
        }

        //Sjekker om man er tom for regnestykker, hvis det stemmer og vi er i onCreate så må vi ha byttet orientasjon og da skal vi lage en ny AlertDialog.
        String tomForRegnestykker = prefs.getString("tomForRegnestykker", "false");
        if(tomForRegnestykker.equals("true")) {
            new AlertDialog.Builder(this).setTitle(R.string.tom_tittel).setMessage(R.string.tom_for_spm).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    editor.putString("fragmentAktiv", "ikke-aktiv");
                    editor.apply();
                    nullStillVerdier();
                }
            }).create().show();
        }
        startSpill(findViewById(R.layout.game));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        spm = getResources().getStringArray(R.array.regneStykker);
        svar = getResources().getStringArray(R.array.regneStykkeSvar);

        SharedPreferences preferanser = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String antSpm = preferanser.getString("velgAntSpm", "5");
        antallRegnestykker = Integer.parseInt(antSpm);

        //Hvis strengene har lengde > 0 vil det si at de har blitt lagret i onPause når man bytter orientasjon - da kan vi sette verdiene tilbake uten at de resettes til 0
        String riktigeSvarString = prefs.getString("riktigeSvarMellomlagret", "0");
        String feilSvarString = prefs.getString("feilSvarMellomlagret", "0");
        String antallSpillString = prefs.getString("antallSpillMellomlagret", "0");
        String svarteRegnestykkerString = prefs.getString("svarteRegnestykker", "0");
        String startetSpillString = prefs.getString("startetSpill", "false");
        String intString = prefs.getString("orderTall", "");

        if(riktigeSvarString.length() > 0 && feilSvarString.length() > 0 && antallSpillString.length() > 0 && svarteRegnestykkerString.length() > 0 && startetSpillString.length() > 0 && intString.length() > 0) {
            riktigeSvar = Integer.parseInt(riktigeSvarString);
            feilSvar = Integer.parseInt(feilSvarString);
            antallSpill = Integer.parseInt(antallSpillString);
            svarteRegnestykker = Integer.parseInt(svarteRegnestykkerString);
            startetSpill = Boolean.parseBoolean(startetSpillString);
            order.clear();
            for(String s : intString.split(" ")) {
                order.add(Integer.parseInt(s));
            }
        }

        if(svarteRegnestykker == order.size()){ //Hvis vi er tom for regnestykker skal vi legge det til i shared preferences
            svarteRegnestykker = 0;
            editor.putString("tomForRegnestykker", "true");
        } else {
            editor.putString("tomForRegnestykker", "false");
        }
        editor.apply();

        //Verdiene blir satt inn i TextViews igjen for å ikke miste dem når vi bytter orientasjon
        TextView regnestykke = findViewById(R.id.regnestykke);
        TextView avsluttSpill = findViewById(R.id.riktigesvar);
        TextView avsluttSpill2 = findViewById(R.id.feilsvar);
        regnestykke.setText(spm[order.get(svarteRegnestykker)]);
        avsluttSpill.setText(getResources().getString(R.string.riktige_svar) + " " + riktigeSvar);
        avsluttSpill2.setText(getResources().getString(R.string.feil_svar) + " " + feilSvar);

        String land = preferanser.getString("velgSpråk", "no");
        Locale locale = getResources().getConfiguration().locale;
        if(!locale.toString().equals(land) && !avslutt) {
            byttLocale(land);
            Intent intent = new Intent(this, GameActivity.class);
            finish();
            startActivity(intent);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onPause() {// Legger til at alle verdier blir lagret til shared preferences når onPause kalles - og så må vi hente verdiene tilbake i onResume.
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

        SharedPreferences preferanser = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String antSpm = preferanser.getString("velgAntSpm", "5");
        antallRegnestykker = Integer.parseInt(antSpm);
        spm = getResources().getStringArray(R.array.regneStykker);
        svar = getResources().getStringArray(R.array.regneStykkeSvar);

        TextView regneStykke = findViewById(R.id.regnestykke);
        regneStykke.setText(spm[order.get(svarteRegnestykker)]);
        TextView avsluttSpill = findViewById(R.id.riktigesvar);
        TextView avsluttSpill2 = findViewById(R.id.feilsvar);
        avsluttSpill.setText(getResources().getString(R.string.riktige_svar) + " " + riktigeSvar);
        avsluttSpill2.setText(getResources().getString(R.string.feil_svar) + " " + feilSvar);
    }

    @SuppressLint("SetTextI18n")
    public void svar(View v) {
        TextView skrevetSvar = findViewById(R.id.svar);
        String gittSvar = (String) skrevetSvar.getText();
        skrevetSvar.setText("");

        editor.putString("fragmentAktiv", "ikke-aktiv");
        editor.apply();

        if(gittSvar.length() > 0) {//Hvis det er skrevet noe i svar textviewet
            int gittSvarInt = Integer.parseInt(gittSvar);
            int korrektSvar = Integer.parseInt(svar[order.get(svarteRegnestykker)]);
            svarteRegnestykker += 1;

            if(gittSvarInt == korrektSvar){//Hvis svaret er riktig eller feil
                riktigeSvar += 1;
            } else {
                feilSvar += 1;
            }

            if(svarteRegnestykker % antallRegnestykker == 0 && svarteRegnestykker != 0) {//Hvis antallet svarte regnestykker er det samme som totalt antall regnestykker så skal spillet avsluttes
                startetSpill = false;
            }

            TextView avsluttSpill = findViewById(R.id.riktigesvar);
            TextView avsluttSpill2 = findViewById(R.id.feilsvar);
            avsluttSpill.setText(getResources().getString(R.string.riktige_svar) + " " + riktigeSvar);
            avsluttSpill2.setText(getResources().getString(R.string.feil_svar) + " " + feilSvar);

            if(startetSpill){//Hvis det er nest siste spill skal vi sette startetSpill lik false, for så å sette det siste regnestykket
                if(svarteRegnestykker == order.size() - 1) {
                    startetSpill = false;
                }
                TextView regneStykke = findViewById(R.id.regnestykke);
                regneStykke.setText(spm[order.get(svarteRegnestykker)]);
            }
            else { //Hvis spillet er ferdig skal statistikker lagres og konfetti animasjonen spilles av og en popup komme opp som spør om man vil fortsette eller avslutte
                String totalRiktigeString = prefs.getString("riktigeSvar", "");
                int totalRiktige = 0;
                try {
                    totalRiktige = Integer.parseInt(totalRiktigeString);
                } catch(Exception e) {System.out.println(e.getMessage());}

                String totalFeilString = prefs.getString("feilSvar", "");
                int totalFeil = 0;
                try {
                    totalFeil = Integer.parseInt(totalFeilString);
                } catch(Exception e) {System.out.println(e.getMessage());}

                String totalAntallSpillString = prefs.getString("antallSpill", "");
                try {
                    antallSpill = Integer.parseInt(totalAntallSpillString);
                } catch(Exception e) { System.out.println(e.getMessage());}

                antallSpill += 1;
                totalRiktige += riktigeSvar;
                totalFeil += feilSvar;

                editor.putString("fragmentRiktigeSvar", String.valueOf(riktigeSvar));
                editor.putString("fragmentFeilSvar", String.valueOf(feilSvar));
                editor.putString("riktigeSvar", String.valueOf(totalRiktige));
                editor.putString("feilSvar", String.valueOf(totalFeil));
                editor.putString("antallSpill", String.valueOf(antallSpill));
                editor.apply();

                KonfettiView viewKonfetti = findViewById(R.id.viewKonfetti);  //Hentet fra https://stackoverflow.com/questions/52228999/celebration-animation-in-android-studio
                viewKonfetti.build()
                        .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(1000L)
                        .addShapes(Shape.RECT, Shape.CIRCLE)
                        .addSizes(new Size(12, 5))
                        .setPosition(-50f, viewKonfetti.getWidth() + 50f, -50f, -50f)
                        .streamFor(300, 1000);

                //Vi må vente til animation er ferdig før vi kan gjøre noe annet. Basert på: https://stackoverflow.com/questions/5321344/android-animation-wait-until-finished
                viewKonfetti.postDelayed(new Runnable() { //Ellers starter alt samtidig.
                    @Override
                    public void run() {
                        editor.putString("fragmentAktiv", "aktiv");
                        editor.apply();
                        DialogFragment fortsett = new FinishedGameDialog();
                        fortsett.setCancelable(false);
                        fortsett.show(getSupportFragmentManager(), "Avslutt?");
                    }
                }, 250);
            }
        }
    }

    @SuppressLint({"SetTextI18n"})
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

    public void rotering(View view){
        ImageView image = (ImageView)findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotation);
        image.startAnimation(animation);
    }
}