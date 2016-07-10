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
import org.ajcm.tubiblia.adapters.CapRecyclerViewAdapter;
import org.ajcm.tubiblia.dataset.DBAdapter;
import org.ajcm.tubiblia.models.Verse;

import java.util.ArrayList;

public class CapFragment extends Fragment {

    private static final String ARG_CAP = "capitulo";
    private static final String ARG_ID_BOOK = "idBook";
    private static final String ARG_VERSE = "verse";
    private static final String ARG_COLOR= "color";
    private int idBook;
    private int idCap;
    private int verse;

    public static CapFragment newInstance(int idBook, int idCap, int verse, int color) {
        CapFragment fragment = new CapFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_BOOK, idBook);
        args.putInt(ARG_CAP, idCap);
        args.putInt(ARG_VERSE, verse);
        args.putInt(ARG_COLOR, color);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            idBook = getArguments().getInt(ARG_ID_BOOK);
            idCap = getArguments().getInt(ARG_CAP);
            verse = getArguments().getInt(ARG_VERSE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cap_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            if (verse > 0) {
                linearLayoutManager.scrollToPosition(verse - 1);
            }
            recyclerView.setLayoutManager(linearLayoutManager);
            DBAdapter dbAdapter = new DBAdapter(context);
            dbAdapter.open();
            Cursor capitulo = dbAdapter.getCapitulo(idBook, idCap);
            ArrayList<Verse> versiculos = new ArrayList<>();
            while (capitulo.moveToNext()) {
                versiculos.add(Verse.fromCursor(capitulo));
            }
            dbAdapter.close();
            recyclerView.setAdapter(new CapRecyclerViewAdapter(context, versiculos, getArguments().getInt(ARG_COLOR)));
        }
        return view;
    }
}
