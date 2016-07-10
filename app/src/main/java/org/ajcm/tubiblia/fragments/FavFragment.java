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
import org.ajcm.tubiblia.dataset.DBAdapter;
import org.ajcm.tubiblia.models.Verse;

import java.util.ArrayList;

public class FavFragment extends Fragment {

    private static final String TAG = "FavFragment";
    private FavRecyclerViewAdapter favRecyclerViewAdapter;

    public FavFragment() {
    }

    public static FavFragment newInstance(int columnCount) {
        FavFragment fragment = new FavFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            DBAdapter dbAdapter = new DBAdapter(context);
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            favRecyclerViewAdapter = new FavRecyclerViewAdapter(getActivity(), dbAdapter.getAllFav());
            recyclerView.setAdapter(favRecyclerViewAdapter);
            dbAdapter.close();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: para hacer refresh");
        DBAdapter dbAdapter = new DBAdapter(getContext());
        favRecyclerViewAdapter.setVerses(dbAdapter.getAllFav());
        favRecyclerViewAdapter.notifyDataSetChanged();
        dbAdapter.close();
    }
}
