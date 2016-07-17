package org.ajcm.tubiblia.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ajcm.tubiblia.ColorPalette;
import org.ajcm.tubiblia.R;
import org.ajcm.tubiblia.activities.BookActivity;
import org.ajcm.tubiblia.dataset.DBAdapter;
import org.ajcm.tubiblia.models.Book;
import org.ajcm.tubiblia.models.Verse;

import java.util.ArrayList;
import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "SearchRecyclerViewAdapter";
    private List<Verse> mValues;
    private Context context;
    private String filter;

    public SearchRecyclerViewAdapter(Context context, List<Verse> items, String filter) {
        this.context = context;
        mValues = items;
        this.filter = filter;
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
        final Book book = dbAdapter.getBook(mValues.get(position).getIdBook());
        dbAdapter.close();
        String[] colorsDark = ColorPalette.getColorsDark(context);
        holder.mIdView.setTextColor(Color.parseColor(colorsDark[book.getIdDivider()]));
        holder.mIdView.setText(book.getNameBook() + " " + holder.mItem.getChapter() + ":" + holder.mItem.getVerse());

        String text = mValues.get(position).getText();
        mValues.get(position).setText(text.replaceAll(filter,"<strong>"+filter+"</strong>"));
        holder.mContentView.setText(Html.fromHtml(mValues.get(position).getText()));

//        SpannableStringBuilder sb = new SpannableStringBuilder(mValues.get(position).getText());
//        StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
//        sb.setSpan(bss, 2, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        holder.mContentView.setText(sb);

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
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public TextView mContentView;
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

    public void setVerses(List<Verse> verses) {
        this.mValues = verses;
    }
}
