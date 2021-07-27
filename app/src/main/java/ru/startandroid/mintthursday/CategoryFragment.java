package ru.startandroid.mintthursday;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.HashSet;
import java.util.Set;

public class CategoryFragment extends DialogFragment {

    private Set<String> chooseData = new HashSet<>();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final String listCat[] = {"Закуска", "Суп", "Основное Блюдо", "Гарнир", "Выпечка", "Салат", "Соусы и Приправы", "Десерт", "Напиток", "Завтрак", "Обед", "Ужин", "Другое"};
        builder.setMultiChoiceItems(listCat, new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false}, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    chooseData.add(listCat[which]);
                } else {
                    chooseData.remove(listCat[which]);
                }
            }
        });

        builder.setTitle(R.string.categoryadd)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(CategoryFragment.this, chooseData);// FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(CategoryFragment.this);// User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, Set<String> chooseData);

        public void onDialogNegativeClick(DialogFragment dialog);
    }

    private NoticeDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }


}