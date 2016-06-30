package org.ajcm.tubiblia.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.ajcm.tubiblia.R;
import org.ajcm.tubiblia.dataset.DBAdapter;
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
        final int pos = position;
        final Verse verse = mValues.get(position);
        holder.mIdView.setText(String.valueOf(verse.getVerse()));
        holder.mContentView.setText(verse.getText());
        holder.verseMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.inflate(R.menu.verse_menu);

                if (verse.isFav()) {
                    popupMenu.getMenu().findItem(R.id.menu_fav).setTitle(R.string.unbookmark);
                }

//                if (verse.hasNote()){
//                    popupMenu.getMenu().findItem(R.id.menu_note).setTitle(R.string.show_note);
//                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        menuItemClick(item, verse, pos);
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

        updateUIItem(verse, holder);
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

    private void menuItemClick(MenuItem item, Verse verse, int pos) {
        DBAdapter dbAdapter = new DBAdapter(context);
        switch (item.getItemId()) {
            case R.id.menu_fav:
                dbAdapter.addFav(verse.getIdBook(), verse.getChapter(), verse.getVerse(), !verse.isFav());
                mValues.get(pos).setFav(!mValues.get(pos).isFav());
                notifyItemChanged(pos);
                break;
            case R.id.menu_note:
                Log.e(TAG, "menuItemClick: nota");
                showDialogNote(verse);
                notifyItemChanged(pos);
                break;
        }
        dbAdapter.close();
    }

    private void updateUIItem(Verse verse, ViewHolder holder) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);

        holder.layoutMark.removeAllViews();
        if (verse.isFav()) {
            View mark = LayoutInflater.from(context).inflate(R.layout.view_mark, null);
            mark.setBackgroundColor(context.getResources().getColor(R.color.colorFavMark));
            holder.layoutMark.addView(mark, params);
        }
//        if (verse.hasNote()){
//            View mark = LayoutInflater.from(context).inflate(R.layout.view_mark, null);
//            mark.setBackgroundColor(context.getResources().getColor(R.color.colorNoteMark));
//            holder.layoutMark.addView(mark, params);
//        }
    }

    private void showDialogNote(final Verse verse) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Agregar Nota");
        final EditText et = new EditText(context);
        builder.setView(et);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.e(TAG, "onClick: text " + et.getText());
                Log.e(TAG, "onClick: verse" + verse.getChapter() + ":" + verse.getVerse());
                // guardar nota en DB
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
}
