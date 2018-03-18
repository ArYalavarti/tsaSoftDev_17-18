package com.tsa.hths.colorpal;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

public class InstructionsDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.instructions_dialog_layout, null, false);

        return new AlertDialog.Builder(getActivity())
                        .setTitle(getActivity().getResources().getString(R.string.instructions))
                        .setView(v)
                        .setPositiveButton(R.string.OK, null)
                        .create();

    }
}
