package org.ajcm.tubiblia.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import org.ajcm.tubiblia.R;
import org.ajcm.tubiblia.adapters.SearchRecyclerViewAdapter;
import org.ajcm.tubiblia.dataset.DBAdapter;
import org.ajcm.tubiblia.models.Verse;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "SearchActivity";
    private String filter;
    private RecyclerView recyclerView;
    private TextView countResult;
    private SwitchCompat switchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        switchCompat = (SwitchCompat) findViewById(R.id.sw_filter);
        countResult = (TextView) findViewById(R.id.count_result);
        recyclerView = (RecyclerView) findViewById(R.id.listSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint("Buscar una palabra");
        searchView.requestFocus();
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
                .setHintTextColor(getResources().getColor(android.R.color.white));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!filter.isEmpty()) {
            Log.e(TAG, "onQueryTextSubmit: ");
            DBAdapter dbAdapter = new DBAdapter(this);
            ArrayList<Verse> search = dbAdapter.getSearch(filter, switchCompat.isChecked());
            dbAdapter.close();
            countResult.setText(search.size() + " coincidencias.");
            countResult.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new SearchRecyclerViewAdapter(this, search, filter));
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filter = newText;
        return false;
    }
}
