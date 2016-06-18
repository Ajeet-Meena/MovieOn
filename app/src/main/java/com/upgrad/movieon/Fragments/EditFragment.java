package com.upgrad.movieon.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.upgrad.movieon.Activities.MainActivity;
import com.upgrad.movieon.BaseClasses.BaseActivity;
import com.upgrad.movieon.Models.Movie;
import com.upgrad.movieon.R;


/**
 * Created by Ajeet Kumar Meena on 10-06-2016.
 */

public class EditFragment extends Fragment {


    private View rootView;

    public static final String EXTRA_NOTE_ID = "note_id";
    public static final String TAG = "editFragment";


    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment getInstance(int noteId) {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_NOTE_ID, noteId);
        EditFragment editFragment = new EditFragment();
        editFragment.setArguments(bundle);
        return editFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_edit, container, false);
            ((BaseActivity) getActivity()).getToolbar().setTitle("Add Movie");
            initViews();
        }
        return rootView;
    }


    private void initViews() {

    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
