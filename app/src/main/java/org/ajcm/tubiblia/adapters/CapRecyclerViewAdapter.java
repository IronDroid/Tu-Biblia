package org.ajcm.tubiblia.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
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
    private int color;

    public CapRecyclerViewAdapter(Context context, ArrayList<Verse> items, int color) {
        mValues = items;
        this.context = context;
        this.color = color;
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
        holder.mIdView.setTextColor(this.color);
        holder.mContentView.setText(verse.getText());
        final boolean hasNote = verse.getTextNote().length() > 0;
        holder.verseMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.inflate(R.menu.verse_menu);

                if (verse.isFav()) {
                    popupMenu.getMenu().findItem(R.id.menu_fav).setTitle(R.string.unbookmark);
                }

                if (hasNote) {
                    popupMenu.getMenu().findItem(R.id.menu_note).setTitle(R.string.show_note);
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        menuItemClick(item, verse, pos, hasNote);
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.inflate(R.menu.verse_menu);

                if (verse.isFav()) {
                    popupMenu.getMenu().findItem(R.id.menu_fav).setTitle(R.string.unbookmark);
                }

                if (hasNote) {
                    popupMenu.getMenu().findItem(R.id.menu_note).setTitle(R.string.show_note);
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        menuItemClick(item, verse, pos, hasNote);
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        updateUIItem(verse, holder, hasNote);
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

    private void menuItemClick(MenuItem item, Verse verse, int pos, boolean hasNote) {
        DBAdapter dbAdapter = new DBAdapter(context);
        switch (item.getItemId()) {
            case R.id.menu_fav:
                dbAdapter.addFav(verse.getIdBook(), verse.getChapter(), verse.getVerse(), !verse.isFav());
                mValues.get(pos).setFav(!mValues.get(pos).isFav());
                notifyItemChanged(pos);
                break;
            case R.id.menu_note:
                Log.e(TAG, "menuItemClick: nota");
                if (hasNote) {
                    showDialogNoteText(verse, pos);
                } else {
                    showDialogNote(verse, pos);
                }
                break;
        }
        dbAdapter.close();
    }

    private void updateUIItem(Verse verse, ViewHolder holder, boolean hasNote) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);

        holder.layoutMark.removeAllViews();
        if (verse.isFav()) {
            View mark = LayoutInflater.from(context).inflate(R.layout.view_mark, null);
            mark.setBackgroundColor(context.getResources().getColor(R.color.colorFavMark));
            holder.layoutMark.addView(mark, params);
        }
        if (hasNote) {
            View mark = LayoutInflater.from(context).inflate(R.layout.view_mark, null);
            mark.setBackgroundColor(context.getResources().getColor(R.color.colorNoteMark));
            holder.layoutMark.addView(mark, params);
        }
    }

    private void showDialogNote(final Verse verse, final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_Dialog);
        builder.setTitle("Agregar Nota");

        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_note, null);
        final EditText et = (EditText) layout.findViewById(R.id.edittext_note);
        if (!verse.getTextNote().isEmpty()) {
            et.setText(verse.getTextNote());
        }
        builder.setView(layout);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!et.getText().toString().isEmpty()) {
                    // guardar nota en DB
                    DBAdapter dbAdapter = new DBAdapter(context);
                    dbAdapter.addNote(verse.getIdBook(), verse.getChapter(), verse.getVerse(), et.getText().toString());
                    dbAdapter.close();

                    mValues.get(pos).setTextNote(et.getText().toString());
                    // actualizar el view
                    notifyItemChanged(pos);
                }
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void showDialogNoteText(final Verse verse, final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_Dialog);
        builder.setTitle("Mi Nota");
        builder.setMessage(verse.getTextNote());
        builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showDialogNote(verse, pos);
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
}
