package com.example.mappe1apputvikling_s188886_s344105;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class FerdigSpillDialog extends DialogFragment {
    private DialogClickListener listener;
    private final int antallRiktig;
    private final int antallFeil;

    public FerdigSpillDialog(int riktig, int feil) {
        antallRiktig = riktig;
        antallFeil = feil;
    }

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
