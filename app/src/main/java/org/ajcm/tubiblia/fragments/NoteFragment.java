package org.ajcm.tubiblia.fragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
            Cursor allNotes = dbAdapter.getAllNotes();
            ArrayList<Verse> verses = new ArrayList<>();
            while (allNotes.moveToNext()){
                verses.add(Verse.fromCursor(allNotes));
            }
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new NoteRecyclerViewAdapter(getActivity(), verses));
        }
        return view;
    }
}
