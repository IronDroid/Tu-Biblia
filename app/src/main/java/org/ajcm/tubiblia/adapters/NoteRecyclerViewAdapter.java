package org.ajcm.tubiblia.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ajcm.tubiblia.R;
import org.ajcm.tubiblia.activities.BookActivity;
import org.ajcm.tubiblia.dataset.DBAdapter;
import org.ajcm.tubiblia.models.Book;
import org.ajcm.tubiblia.models.Verse;

import java.util.List;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {

    private final List<Verse> mValues;
    private Context context;
    private DBAdapter dbAdapter;

    public NoteRecyclerViewAdapter(Context context, List<Verse> items) {
        this.context = context;
        mValues = items;
        dbAdapter = new DBAdapter(this.context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        Cursor cursor = dbAdapter.getBook(mValues.get(position).getIdBook());
        final Book book = Book.fromCursor(cursor);

        holder.mIdView.setText(book.getNameBook() + " " + holder.mItem.getChapter() + ":" + holder.mItem.getVerse());
        holder.mContentView.setText(mValues.get(position).getTextNote());
        holder.editTextNote.setText(mValues.get(position).getTextNote());

        holder.mIdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookActivity.class);
                intent.putExtra(StickyListAdapter.ID_BOOK, book.getIdBook());
                intent.putExtra(StickyListAdapter.NUM_CAPS, book.getNumChapter());
                intent.putExtra(StickyListAdapter.NAME_BOOK, book.getNameBook());

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

                dbAdapter.addNote(mValues.get(position).get_id(), holder.editTextNote.getText().toString());
                dbAdapter.close();
                mValues.get(position).setTextNote(holder.editTextNote.getText().toString());
                notifyItemChanged(position);
            }
        });


        holder.deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.layoutEditNote.setVisibility(View.GONE);
                holder.layoutShowNote.setVisibility(View.VISIBLE);
                dbAdapter.deleteNote(mValues.get(position).get_id());
                notifyItemRemoved(position);
                mValues.remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
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
