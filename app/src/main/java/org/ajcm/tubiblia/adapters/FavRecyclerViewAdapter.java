package org.ajcm.tubiblia.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ajcm.tubiblia.R;
import org.ajcm.tubiblia.activities.BookActivity;
import org.ajcm.tubiblia.dataset.DBAdapter;
import org.ajcm.tubiblia.models.Book;
import org.ajcm.tubiblia.models.Verse;

import java.util.List;

public class FavRecyclerViewAdapter extends RecyclerView.Adapter<FavRecyclerViewAdapter.ViewHolder> {

    private final List<Verse> mValues;
    private Context context;

    public FavRecyclerViewAdapter(Context context, List<Verse> items) {
        this.context = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_fav, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        DBAdapter dbAdapter = new DBAdapter(context);
        Cursor cursor = dbAdapter.getBook(mValues.get(position).getIdBook());
        final Book book = Book.fromCursor(cursor);

        holder.mIdView.setText(book.getNameBook() + " " + holder.mItem.getChapter() + ":" + holder.mItem.getVerse());
        holder.mContentView.setText(mValues.get(position).getText());

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
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Verse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
