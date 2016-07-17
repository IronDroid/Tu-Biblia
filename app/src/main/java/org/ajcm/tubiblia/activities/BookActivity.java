package org.ajcm.tubiblia.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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
    private AdView adView;

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

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setBackgroundColor(Color.parseColor(colors[idDivider]));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor(colors[idDivider]));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(extras.getString(StickyListAdapter.NAME_BOOK));
        setStatusBarColor(Color.parseColor(colorsDark[idDivider]));

        adapter = new PagerAdapter(getSupportFragmentManager(), idBook, numCap, intVerse, Color.parseColor(colorsDark[idDivider]));
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(intChapter - 1);
        tabLayout.setupWithViewPager(viewPager);

        adView = (AdView) findViewById(R.id.adViewVerse);
//        AdRequest adRequest = new AdRequest.Builder().addTestDevice("9229DC0B97D1B5E5392C3125B2BD6FA2").build();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                adView.setVisibility(View.VISIBLE);
            }
        });
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
