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
    private final int antallRiktig = 0;
    private final int antallFeil = 2;

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
        super.onCreate(savedInstanceState);
        try {
            listener = (DialogClickListener) getActivity();
        } catch (ClassCastException exception) {
            throw new ClassCastException(exception.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        /*SharedPreferences setPrefs = this.getActivity().getSharedPreferences("com.example.mappe1apputvikling_s188886_s344105", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = setPrefs.edit();
        editor.putString("fragmentAktiv", "ikke-aktiv");
        editor.apply();*/
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
