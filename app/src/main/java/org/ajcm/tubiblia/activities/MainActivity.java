package org.ajcm.tubiblia.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.ajcm.tubiblia.R;
import org.ajcm.tubiblia.fragments.BookFragment;
import org.ajcm.tubiblia.fragments.FavFragment;
import org.ajcm.tubiblia.fragments.NoteFragment;

import se.emilsjolander.stickylistheaders.BuildConfig;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private float elevation;
    private String packageName;

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

        packageName = getPackageName();
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            MenuItem menuItem = navigationView.getMenu().getItem(i);
            if (menuItem.getSubMenu() != null) {
                for (int j = 0; j < menuItem.getSubMenu().size(); j++) {
                    menuItem.getSubMenu().getItem(j).getIcon().clearColorFilter();
                }
            } else {
                menuItem.getIcon().clearColorFilter();
            }
        }

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
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Tu Biblia RV 1960");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + packageName);
                startActivity(Intent.createChooser(sharingIntent, "Compartir via..."));
                break;
            case R.id.nav_rate:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                break;
            case R.id.nav_about:
                dialogAbout();
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

    private void dialogAbout() {
        new android.app.AlertDialog.Builder(this).setTitle(getResources().getString(R.string.app_name) + " " +
                "v" + BuildConfig.VERSION_NAME)
                .setMessage("Reina Valera 1960" +
                        "\nDesarrollado por:" +
                        "\nAlex Jhonny Cruz Mamani" +
                        "\nDesarrollador Android Entusiasta" +
                        "\nEmail: jhonlimaster@gmail.com" +
                        "\nTwitter: @jhonlimaster")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}
