package org.ajcm.tubiblia.activities;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.ajcm.tubiblia.App;
import org.ajcm.tubiblia.R;
import org.ajcm.tubiblia.adapters.StickyListAdapter;
import org.ajcm.tubiblia.dataset.DBAdapter;
import org.ajcm.tubiblia.models.Book;
import org.ajcm.tubiblia.utils.UserPreferences;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Log.e(TAG, "onCreate: Main");
        initGUI();
    }

    private void initGUI() {
        StickyListHeadersListView stickyList = (StickyListHeadersListView) findViewById(R.id.list);
        DBAdapter dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        Cursor allBooks = dbAdapter.getAllBooks();
        ArrayList<Book> books = new ArrayList<>();
        while (allBooks.moveToNext()) {
            books.add(Book.fromCursor(allBooks));
        }
        StickyListAdapter adapter = new StickyListAdapter(this, books);
        dbAdapter.close();
        allBooks.close();
        stickyList.setAdapter(adapter);
    }
}
