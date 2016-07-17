package org.ajcm.tubiblia.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.ajcm.tubiblia.ColorPalette;
import org.ajcm.tubiblia.R;
import org.ajcm.tubiblia.activities.BookActivity;
import org.ajcm.tubiblia.models.Book;
import org.ajcm.tubiblia.models.DividerBook;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by jhonlimaster on 09-06-16.
 */
public class StickyListAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private static final String TAG = "StickyListAdapter";

    public static final String ID_BOOK = "idBook";
    public static final String NUM_CAPS = "numCaps";
    public static final String NAME_BOOK = "nameBook";
    public static final String CHAPTER_BOOK = "chapterBook";
    public static final String VERSE_BOOK = "verseBook";
    public static final String ID_DIVIDER = "idDivider";
    private Context context;
    private ArrayList<Book> booksList;
    private ArrayList<DividerBook> dividerBooks;

    public StickyListAdapter(Context context, ArrayList<Book> list, ArrayList<DividerBook> dividerBooks) {
        this.context = context;
        booksList = list;
        this.dividerBooks = dividerBooks;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.header, null);
            holder.text = (TextView) convertView.findViewById(R.id.header);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        if (position < 39) {
            holder.text.setText("Antiguo Testamento");
        } else {
            holder.text.setText("Nuevo Testamento");
        }
        return convertView;
    }

    class HeaderViewHolder {
        TextView text;
    }

    @Override
    public long getHeaderId(int position) {
        if (position < 39) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getCount() {
        return booksList.size();
    }

    @Override
    public Object getItem(int i) {
        return booksList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder vh;
        final Book book = booksList.get(position);
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_book, null);
            vh.icidBook = (TextView) convertView.findViewById(R.id.icid_book);
            vh.nameBook = (TextView) convertView.findViewById(R.id.book_name);
            vh.bookInfo = (TextView) convertView.findViewById(R.id.book_info);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.nameBook.setText(book.getNameBook());
        String[] colorsDark = ColorPalette.getColors400(context);

        GradientDrawable bgShape = (GradientDrawable) vh.icidBook.getBackground();
        bgShape.setColor(Color.parseColor(colorsDark[book.getIdDivider()]));
        String[] split = book.getNameBook().split(" ");
        if (split.length == 2){
            String s = split[0] + split[1].substring(0, 1);
            vh.icidBook.setText(s.toUpperCase());
        } else{
            vh.icidBook.setText(book.getNameBook().substring(0,2).toUpperCase());
        }

        vh.bookInfo.setText(book.getNumChapter() + " capitulo(s)");
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookActivity.class);
                intent.putExtra(ID_BOOK, book.getIdBook());
                intent.putExtra(NUM_CAPS, book.getNumChapter());
                intent.putExtra(NAME_BOOK, book.getNameBook());
                intent.putExtra(ID_DIVIDER, book.getIdDivider());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView icidBook;
        TextView nameBook;
        TextView bookInfo;
    }
}
