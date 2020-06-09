package org.ajcm.tubiblia.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.ajcm.tubiblia.ColorPalette;
import org.ajcm.tubiblia.R;
import org.ajcm.tubiblia.adapters.PagerAdapter;
import org.ajcm.tubiblia.adapters.StickyListAdapter;

public class BookActivity extends AppCompatActivity {

    private static final String TAG = "BookActivity";
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter adapter;
    private int idBook;
    private int numCap;
    private int intChapter;
    private int intVerse;
    private int idDivider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Bundle extras = getIntent().getExtras();

        idBook = extras.getInt(StickyListAdapter.ID_BOOK);
        numCap = extras.getInt(StickyListAdapter.NUM_CAPS);
        intChapter = extras.getInt(StickyListAdapter.CHAPTER_BOOK);
        intVerse = extras.getInt(StickyListAdapter.VERSE_BOOK);
        idDivider = extras.getInt(StickyListAdapter.ID_DIVIDER);

        String[] colors = ColorPalette.getColors(this);
        String[] colorsDark = ColorPalette.getColorsDark(this);

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setBackgroundColor(Color.parseColor(colors[idDivider]));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor(colors[idDivider]));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(extras.getString(StickyListAdapter.NAME_BOOK));
        setStatusBarColor(Color.parseColor(colorsDark[idDivider]));

        adapter = new PagerAdapter(getSupportFragmentManager(), idBook, numCap, intVerse, Color.parseColor(colorsDark[idDivider]));
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(intChapter - 1);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

    public void setStatusBarColor(int statusBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(statusBarColor);
        }
    }
}
