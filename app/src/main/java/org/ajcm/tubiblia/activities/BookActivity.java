package org.ajcm.tubiblia.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.ajcm.tubiblia.R;
import org.ajcm.tubiblia.adapters.PagerAdapter;
import org.ajcm.tubiblia.adapters.StickyListAdapter;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    private static final String TAG = "BookActivity";
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter adapter;
    private int idBook;
    private int numCap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getExtras().getString(StickyListAdapter.NAME_BOOK));

        idBook = getIntent().getExtras().getInt(StickyListAdapter.ID_BOOK);
        numCap = getIntent().getExtras().getInt(StickyListAdapter.NUM_CAPS);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        adapter = new PagerAdapter(getSupportFragmentManager(), idBook, numCap);
        viewPager.setAdapter(adapter);
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
}
