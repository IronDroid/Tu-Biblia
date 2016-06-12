package org.ajcm.tubiblia.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.ajcm.tubiblia.R;
import org.ajcm.tubiblia.activities.BookActivity;
import org.ajcm.tubiblia.models.Book;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by jhonlimaster on 09-06-16.
 */
public class StickyListAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    public static final String ID_BOOK = "idBook";
    public static final String NUM_CAPS = "numCaps";
    public static final String NAME_BOOK = "nameBook";
    private Context context;
    private ArrayList<Book> booksList;

    public StickyListAdapter(Context context, ArrayList<Book> list) {
        this.context = context;
        booksList = list;
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
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_book, null);
            vh.nameBook = (TextView) convertView.findViewById(R.id.book_name);
            vh.bookInfo = (TextView) convertView.findViewById(R.id.book_info);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.nameBook.setText(booksList.get(position).getNameBook());
        vh.bookInfo.setText(booksList.get(position).getNumCap() + " capitulo(s)");
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookActivity.class);
                intent.putExtra(ID_BOOK, booksList.get(position).getIdBook());
                intent.putExtra(NUM_CAPS, booksList.get(position).getNumCap());
                intent.putExtra(NAME_BOOK, booksList.get(position).getNameBook());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView nameBook;
        TextView bookInfo;
    }
}
