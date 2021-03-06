package org.ajcm.tubiblia.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.ajcm.tubiblia.ColorPalette;
import org.ajcm.tubiblia.R;
import org.ajcm.tubiblia.activities.BookActivity;
import org.ajcm.tubiblia.dataset.DBAdapter;
import org.ajcm.tubiblia.models.Book;
import org.ajcm.tubiblia.models.Verse;

import java.util.ArrayList;
import java.util.List;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "NoteRecyclerViewAdapter";
    private Context context;
    private List<Verse> verses;

    public NoteRecyclerViewAdapter(Context context, List<Verse> items) {
        this.context = context;
        verses = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.e(TAG, "onBindViewHolder: ");
        holder.mItem = verses.get(position);
        DBAdapter dbAdapter = new DBAdapter(context);
        final Book book = dbAdapter.getBook(verses.get(position).getIdBook());
        dbAdapter.close();
        String[] colorsDark = ColorPalette.getColorsDark(context);
        holder.mIdView.setTextColor(Color.parseColor(colorsDark[book.getIdDivider()]));
        holder.mIdView.setText(book.getNameBook() + " " + holder.mItem.getChapter() + ":" + holder.mItem.getVerse());
        holder.mContentView.setText(verses.get(position).getTextNote());
        holder.editTextNote.setText(verses.get(position).getTextNote());

        holder.mIdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookActivity.class);
                intent.putExtra(StickyListAdapter.ID_BOOK, book.getIdBook());
                intent.putExtra(StickyListAdapter.NUM_CAPS, book.getNumChapter());
                intent.putExtra(StickyListAdapter.NAME_BOOK, book.getNameBook());
                intent.putExtra(StickyListAdapter.ID_DIVIDER, book.getIdDivider());
                intent.putExtra(StickyListAdapter.CHAPTER_BOOK, holder.mItem.getChapter());
                intent.putExtra(StickyListAdapter.VERSE_BOOK, holder.mItem.getVerse());
                context.startActivity(intent);
            }
        });
        holder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show controls to edit
                holder.layoutShowNote.setVisibility(View.GONE);
                holder.layoutEditNote.setVisibility(View.VISIBLE);
                holder.editTextNote.requestFocus();
            }
        });

        holder.saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.layoutEditNote.setVisibility(View.GONE);
                holder.layoutShowNote.setVisibility(View.VISIBLE);
                DBAdapter dbAdapter = new DBAdapter(context);
                dbAdapter.addNote(verses.get(position).get_id(), holder.editTextNote.getText().toString());
                dbAdapter.close();
                verses.get(position).setTextNote(holder.editTextNote.getText().toString());
                notifyItemChanged(position);
            }
        });


        holder.deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.layoutEditNote.setVisibility(View.GONE);
                holder.layoutShowNote.setVisibility(View.VISIBLE);
                DBAdapter dbAdapter = new DBAdapter(context);
                dbAdapter.deleteNote(verses.get(position).get_id());
                dbAdapter.close();
                notifyItemRemoved(position);
                verses.remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return verses.size();
    }

    public void setVerses(ArrayList<Verse> verses) {
        this.verses = verses;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final LinearLayout layoutShowNote;
        public final LinearLayout layoutEditNote;
        public final TextView mIdView;
        public final TextView mContentView;
        public final EditText editTextNote;
        public final ImageView imageViewEdit;
        public final Button saveNote;
        public final Button deleteNote;
        public Verse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            layoutShowNote = (LinearLayout) view.findViewById(R.id.layout_show_note);
            layoutEditNote = (LinearLayout) view.findViewById(R.id.layout_edit_note);
            editTextNote = (EditText) view.findViewById(R.id.edittext_note);
            saveNote = (Button) view.findViewById(R.id.save_note);
            deleteNote = (Button) view.findViewById(R.id.delete_note);
            mContentView = (TextView) view.findViewById(R.id.content);
            imageViewEdit = (ImageView) view.findViewById(R.id.action_edit_note);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
