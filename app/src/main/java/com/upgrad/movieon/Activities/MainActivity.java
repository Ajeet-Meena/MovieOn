package com.upgrad.movieon.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.upgrad.movieon.BaseClasses.BaseActivity;
import com.upgrad.movieon.Fragments.EditFragment;
import com.upgrad.movieon.Fragments.GridFragment;
import com.upgrad.movieon.Listeners.OnFragmentInteractionListener;
import com.upgrad.movieon.R;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Ajeet Kumar Meena on 10-06-2016.
 */

@RuntimePermissions
public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        OnFragmentInteractionListener,
        FragmentManager.OnBackStackChangedListener {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FragmentManager fragmentManager;
    private AppBarLayout appBarLayout;
    private boolean doubleBackToExitPressedOnce = false;
    public static final String EXTRA_ATTACH_FRAGMENT_NO = "extra_tab_no";
    private static final int EXTRA_ATTACH_EDIT_NOTE_FRAGMENT = 1;
    private ImageView toolbarActionImageView;

    private void attachNotesFragment() {
        clearBackStack();
        GridFragment fragment = new GridFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, GridFragment.TAG);
        fragmentTransaction.commit();
        appBarLayout.setExpanded(true, true);
    }

    private void attachEditNoteFragment() {
        EditFragment fragment = new EditFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment).addToBackStack(EditFragment.TAG);
        fragmentTransaction.commit();
        appBarLayout.setExpanded(true, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        setTitle("My Notes");
        shouldHandleDrawer();
        initViews();
        setupNavigationDrawer();
        setStatusBarTranslucent(true);
        attachNotesFragment();
    }

    private void initViews() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        toolbarActionImageView = (ImageView) findViewById(R.id.toolbar_action);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.call_developer: {
                return true;
            }
        }
        return true;
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    public void callDeveloper() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "7597202177"));
        startActivity(intent);
    }

    public ImageView getToolbarActionImageView() {
        return toolbarActionImageView;
    }

    public void setToolbarActionImageView(ImageView toolbarActionImageView) {
        this.toolbarActionImageView = toolbarActionImageView;
    }

    @OnPermissionDenied(Manifest.permission.CALL_PHONE)
    void showDeniedForCamera() {
        Toast.makeText(this, "Give phone call permission to call developer", Toast.LENGTH_SHORT).show();
    }



    private void setupNavigationDrawer() {
        fragmentManager.addOnBackStackChangedListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        setToolbar((Toolbar) findViewById(R.id.toolbar));
        setSupportActionBar(getToolbar());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, getToolbar(), R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getContentView().getWindowToken(), 0);
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }


    @Override
    public void showDrawerToggle(boolean showDrawerToggle) {

    }

    @Override
    public void onBackStackChanged() {
        actionBarDrawerToggle.setDrawerIndicatorEnabled(fragmentManager.getBackStackEntryCount() == 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(fragmentManager.getBackStackEntryCount() > 0);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void clearBackStack() {
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int tabNo = intent.getIntExtra(EXTRA_ATTACH_FRAGMENT_NO, 0);
        switch (tabNo) {
            case EXTRA_ATTACH_EDIT_NOTE_FRAGMENT: {
                attachEditNoteFragment();
                break;
            }
            case 2:{
                attachEditNoteFragment(intent.getIntExtra(EditFragment.EXTRA_NOTE_ID,0));
                break;
            }
        }
    }

    private void attachEditNoteFragment(int noteId) {
        EditFragment fragment = EditFragment.getInstance(noteId);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment).addToBackStack(EditFragment.TAG);
        fragmentTransaction.commit();
        appBarLayout.setExpanded(true, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.isDrawerIndicatorEnabled() &&
                actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == android.R.id.home &&
                getSupportFragmentManager().popBackStackImmediate()) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
    }
}
