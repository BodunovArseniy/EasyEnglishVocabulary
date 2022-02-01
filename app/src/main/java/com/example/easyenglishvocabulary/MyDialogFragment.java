package com.example.easyenglishvocabulary;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {

    private String text;
    private TestActivity.Callback callback;

    MyDialogFragment(String text, TestActivity.Callback callback){
        this.text = text;
        this.callback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = "Результат";
        String message = text;
        String button = "OK";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                callback.callback();
                dialog.cancel();
            }
        });


        return builder.create();
    }
}
