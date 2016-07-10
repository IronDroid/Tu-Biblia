package org.ajcm.tubiblia.fragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ajcm.tubiblia.R;
import org.ajcm.tubiblia.adapters.FavRecyclerViewAdapter;
import org.ajcm.tubiblia.adapters.NoteRecyclerViewAdapter;
import org.ajcm.tubiblia.dataset.DBAdapter;
import org.ajcm.tubiblia.models.Verse;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment {

    private NoteRecyclerViewAdapter noteRecyclerViewAdapter;

    public NoteFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Set the adapter
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            DBAdapter dbAdapter = new DBAdapter(context);
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            noteRecyclerViewAdapter = new NoteRecyclerViewAdapter(getActivity(), dbAdapter.getAllNotes());
            recyclerView.setAdapter(noteRecyclerViewAdapter);
            dbAdapter.close();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DBAdapter dbAdapter = new DBAdapter(getContext());
        noteRecyclerViewAdapter.setVerses(dbAdapter.getAllNotes());
        noteRecyclerViewAdapter.notifyDataSetChanged();
        dbAdapter.close();
    }
}
