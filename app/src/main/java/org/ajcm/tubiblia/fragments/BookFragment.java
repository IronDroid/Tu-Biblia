package org.ajcm.tubiblia.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.ajcm.tubiblia.R;
import org.ajcm.tubiblia.adapters.StickyListAdapter;
import org.ajcm.tubiblia.dataset.DBAdapter;
import org.ajcm.tubiblia.models.Book;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class BookFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public BookFragment() {
    }

    public static BookFragment newInstance() {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        StickyListHeadersListView stickyList = (StickyListHeadersListView) view.findViewById(R.id.list);
        DBAdapter dbAdapter = new DBAdapter(getActivity());
        StickyListAdapter adapter = new StickyListAdapter(getActivity(), dbAdapter.getAllBooks(), dbAdapter.getAllDivider());
        dbAdapter.close();
        stickyList.setAdapter(adapter);

        return view;
    }

}
