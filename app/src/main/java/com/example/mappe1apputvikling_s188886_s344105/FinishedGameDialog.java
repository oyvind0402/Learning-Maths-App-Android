package com.example.mappe1apputvikling_s188886_s344105;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class FinishedGameDialog extends DialogFragment {
    private DialogClickListener listener;
    private int antallRiktig = 0;
    private int antallFeil = 0;

    public FinishedGameDialog(){

    }

    /*public static FinishedGameDialog newInstance(int riktig, int feil) {
        antallRiktig = riktig;
        antallFeil = feil;
    }*/

    public interface DialogClickListener {
        void onCancelClick();
        void onContinueClick();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences setPrefs = getActivity().getSharedPreferences("com.example.mappe1apputvikling_s188886_s344105", Context.MODE_PRIVATE);
        String totalRiktigeString = setPrefs.getString("fragmentRiktigeSvar", "");
        int totalRiktige = 0;
        try {
            totalRiktige = Integer.parseInt(totalRiktigeString);
            antallRiktig = totalRiktige;
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }String totalFeilString = setPrefs.getString("fragmentFeilSvar", "");
        int totalFeil = 0;
        try {
            totalFeil = Integer.parseInt(totalFeilString);
            antallFeil = totalFeil;
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        super.onCreate(savedInstanceState);
        try {
            listener = (DialogClickListener) getActivity();
        } catch (ClassCastException exception) {
            throw new ClassCastException(exception.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String text = getString(R.string.riktige_svar) + " " + antallRiktig + "\n" + getString(R.string.feil_svar) + " " + antallFeil + "\n\n" + getString(R.string.gratulerer);
        return new AlertDialog.Builder(getActivity()).setTitle(R.string.avslutt_fortsett).setMessage(text).setPositiveButton(R.string.fortsettspill, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onContinueClick();
            }
        }).setNegativeButton(R.string.avsluttspill, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onCancelClick();
            }
        }).create();
    }
}
