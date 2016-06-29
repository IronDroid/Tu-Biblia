package org.ajcm.tubiblia.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.ajcm.tubiblia.R;
import org.ajcm.tubiblia.models.Verse;

import java.util.ArrayList;

public class CapRecyclerViewAdapter extends RecyclerView.Adapter<CapRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "CapRecyclerViewAdapter";
    private final ArrayList<Verse> mValues;
    private Context context;

    public CapRecyclerViewAdapter(Context context, ArrayList<Verse> items) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cap, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIdView.setText(mValues.get(position).getVerse() + "");
        holder.mContentView.setText(mValues.get(position).getText());
        holder.verseMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click menu", Toast.LENGTH_SHORT).show();
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.inflate(R.menu.verse_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "LOL!!!  ", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);

        View mark = LayoutInflater.from(context).inflate(R.layout.view_mark, null);
        mark.setBackgroundColor(Color.YELLOW);
        holder.layoutMark.addView(mark, params);
        holder.layoutMark.addView(LayoutInflater.from(context).inflate(R.layout.view_mark, null, true), params);
        mark = LayoutInflater.from(context).inflate(R.layout.view_mark, null);
        mark.setBackgroundColor(Color.YELLOW);
        holder.layoutMark.addView(mark, params);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView verseMenu;
        public LinearLayout layoutMark;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            verseMenu = (ImageView) view.findViewById(R.id.verse_menu);
            layoutMark = (LinearLayout) view.findViewById(R.id.layout_mark);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
