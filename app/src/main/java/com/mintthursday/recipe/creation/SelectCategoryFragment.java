package com.mintthursday.recipe.creation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.mintthursday.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SelectCategoryFragment extends DialogFragment {

    private static final String ARG_CATEGORY_LIST = "ARG_CATEGORY_LIST";
    final String[] listCat = {"Закуска", "Суп", "Основное Блюдо", "Гарнир", "Выпечка", "Салат", "Соусы и Приправы", "Десерт", "Напиток", "Завтрак", "Обед", "Ужин", "Другое"};
    private Set<String> chooseData = new HashSet<>();
    private NoticeDialogListener listener;

    public static SelectCategoryFragment newInstance(Set<String> data) {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(data);
        SelectCategoryFragment fragment = new SelectCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ARG_CATEGORY_LIST, list);
        fragment.setArguments(bundle);
        return fragment;
    }

    private boolean[] getMultiChoiceItems(Set<String> chooseData) {
        boolean[] choice = new boolean[13];
        if (chooseData != null) {
            for (int i = 0; i < listCat.length - 1; i++) {
                if (chooseData.contains(listCat[i])) {
                    choice[i] = true;
                } else choice[i] = false;
            }
        }
        return choice;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ArrayList<String> chooseDataList = getArguments().getStringArrayList(ARG_CATEGORY_LIST);
        chooseData.addAll(chooseDataList);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setMultiChoiceItems(listCat, getMultiChoiceItems(chooseData), new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    chooseData.add(listCat[which]);
                } else {
                    chooseData.remove(listCat[which]);
                }
            }
        });

        builder.setTitle(R.string.category_add)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(SelectCategoryFragment.this, chooseData);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(SelectCategoryFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            Fragment parentFragment = getParentFragment();
            if (parentFragment != null) {
                listener = (NoticeDialogListener) parentFragment;
            } else {
                listener = (NoticeDialogListener) context;
            }
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, Set<String> chooseData);

        void onDialogNegativeClick(DialogFragment dialog);
    }


}