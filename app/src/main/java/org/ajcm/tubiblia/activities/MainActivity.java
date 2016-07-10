package org.ajcm.tubiblia.activities;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.ajcm.tubiblia.R;
import org.ajcm.tubiblia.fragments.BookFragment;
import org.ajcm.tubiblia.fragments.FavFragment;
import org.ajcm.tubiblia.fragments.NoteFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    float elevation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_appbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, new BookFragment()).commit();

        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        elevation = 4 * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass = null;
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        switch (item.getItemId()) {
            case R.id.nav_book:
                fragmentClass = BookFragment.class;
                getSupportActionBar().setTitle(R.string.app_name);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    appBarLayout.setElevation(0);
                }
                break;
            case R.id.nav_note:
                fragmentClass = NoteFragment.class;
                getSupportActionBar().setTitle(R.string.title_fragment_note);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    appBarLayout.setElevation(elevation);
                }
                break;
            case R.id.nav_fav:
                fragmentClass = FavFragment.class;
                getSupportActionBar().setTitle(R.string.title_fragment_fav);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    appBarLayout.setElevation(elevation);
                }
                break;
            case R.id.nav_share:
                Toast.makeText(MainActivity.this, "nav", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_rate:
                Toast.makeText(MainActivity.this, "nav", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                Toast.makeText(MainActivity.this, "nav", Toast.LENGTH_SHORT).show();
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
        } catch (Exception e) {
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
