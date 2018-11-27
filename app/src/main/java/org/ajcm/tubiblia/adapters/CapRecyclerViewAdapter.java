package org.ajcm.tubiblia.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ajcm.tubiblia.ColorPalette;
import org.ajcm.tubiblia.R;
import org.ajcm.tubiblia.dataset.DBAdapter;
import org.ajcm.tubiblia.models.Book;
import org.ajcm.tubiblia.models.Verse;

import java.util.ArrayList;

public class CapRecyclerViewAdapter extends RecyclerView.Adapter<CapRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "CapRecyclerViewAdapter";
    private final ArrayList<Verse> mValues;
    private Context context;
    private int color;
    private Book book;

    public CapRecyclerViewAdapter(Context context, ArrayList<Verse> items, int color) {
        mValues = items;
        this.context = context;
        this.color = color;
        DBAdapter dbAdapter = new DBAdapter(this.context);
        book = dbAdapter.getBook(mValues.get(0).getIdBook());
        dbAdapter.close();
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
        holder.verse = mValues.get(position);
        holder.mIdView.setText(String.valueOf(verse.getVerse()));
        holder.mIdView.setTextColor(this.color);
        holder.mContentView.setText(verse.getText());
        final boolean hasNote = verse.getTextNote().length() > 0;
        final PopupMenu popupMenu = new PopupMenu(context, holder.mContentView);
        popupMenu.inflate(R.menu.menu_verse);
        setupTouchDelegate(context, holder.mContentView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        public LinearLayout layoutMark;
        public Verse verse;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            layoutMark = (LinearLayout) view.findViewById(R.id.layout_mark);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    private void menuItemClick(MenuItem item, Verse verse, int pos, boolean hasNote) {
        DBAdapter dbAdapter = new DBAdapter(context);
        switch (item.getItemId()) {
            case R.id.menu_fav:
                dbAdapter.addFav(verse.getIdBook(), verse.getChapter(), verse.getVerse(), !verse.isFav());
                mValues.get(pos).setFav(!mValues.get(pos).isFav());
                notifyItemChanged(pos);
                break;
            case R.id.menu_note:
                if (hasNote) {
                    showDialogNoteText(verse, pos);
                } else {
                    showDialogNote(verse, pos);
                }
                break;
            case R.id.menu_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Versiculo");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, verse.getText() + "" +
                        "\n" + book.getNameBook() +" " + verse.getChapter() + ":" + verse.getVerse());
                context.startActivity(Intent.createChooser(sharingIntent, "Compartir via..."));
                break;
            case R.id.menu_copy:
                int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                            .getSystemService(context.CLIPBOARD_SERVICE);
                    clipboard.setText(verse.getText() +"\n"+ book.getNameBook()+ " " + verse.getChapter()+ ":" + verse.getVerse());
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
                            .getSystemService(context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData
                            .newPlainText(context.getResources().getString(R.string.message), verse.getText() +"\n"+ book.getNameBook()+ " " + verse.getChapter()+ ":" + verse.getVerse());
                    clipboard.setPrimaryClip(clip);
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
//            mark.setBackgroundColor(context.getResources().getColor(R.color.colorFavMark));
            mark.setBackgroundColor(color);
            holder.layoutMark.addView(mark, params);
        }
        if (hasNote) {
            View mark = LayoutInflater.from(context).inflate(R.layout.view_mark, null);
            String[] colors400 = ColorPalette.getColors400(context);
            mark.setBackgroundColor(Color.parseColor(colors400[book.getIdDivider()]));
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

    private static void setupTouchDelegate(Context context, final View menu) {
        final int offset = context.getResources().getDimensionPixelSize(R.dimen.menu_touchdelegate);
        assert menu.getParent() != null;
        ((View) menu.getParent()).post(new Runnable() {
            public void run() {
                Rect delegateArea = new Rect();
                menu.getHitRect(delegateArea);
                delegateArea.top -= offset;
                delegateArea.bottom += offset;
                delegateArea.left -= offset;
                delegateArea.right += offset;
                TouchDelegate expandedArea = new TouchDelegate(delegateArea, menu);
                ((View) menu.getParent()).setTouchDelegate(expandedArea);
            }
        });
    }
}
