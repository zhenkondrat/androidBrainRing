package com.example.zhenkondrat.brainringapp.Server;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

import com.example.zhenkondrat.brainringapp.R;

public class MainGameActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        ActionBar supportActionBar = getSupportActionBar();
//       supportActionBar.show();
        supportActionBar.setLogo(R.drawable.logo);
//        supportActionBar.hide();


        ActionBar actionBar = new ActionBar() {
            @Override
            public void setCustomView(View view) {

            }

            @Override
            public void setCustomView(View view, LayoutParams layoutParams) {

            }

            @Override
            public void setCustomView(int resId) {

            }

            @Override
            public void setIcon(@DrawableRes int resId) {

            }

            @Override
            public void setIcon(Drawable icon) {

            }

            @Override
            public void setLogo(@DrawableRes int resId) {

            }

            @Override
            public void setLogo(Drawable logo) {

            }

            @Override
            public void setListNavigationCallbacks(SpinnerAdapter adapter, OnNavigationListener callback) {

            }

            @Override
            public void setSelectedNavigationItem(int position) {

            }

            @Override
            public int getSelectedNavigationIndex() {
                return 0;
            }

            @Override
            public int getNavigationItemCount() {
                return 0;
            }

            @Override
            public void setTitle(CharSequence title) {

            }

            @Override
            public void setTitle(@StringRes int resId) {

            }

            @Override
            public void setSubtitle(CharSequence subtitle) {

            }

            @Override
            public void setSubtitle(int resId) {

            }

            @Override
            public void setDisplayOptions(int options) {

            }

            @Override
            public void setDisplayOptions(int options, int mask) {

            }

            @Override
            public void setDisplayUseLogoEnabled(boolean useLogo) {

            }

            @Override
            public void setDisplayShowHomeEnabled(boolean showHome) {

            }

            @Override
            public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {

            }

            @Override
            public void setDisplayShowTitleEnabled(boolean showTitle) {

            }

            @Override
            public void setDisplayShowCustomEnabled(boolean showCustom) {

            }

            @Override
            public void setBackgroundDrawable(@Nullable Drawable d) {

            }

            @Override
            public View getCustomView() {
                return null;
            }

            @Nullable
            @Override
            public CharSequence getTitle() {
                return null;
            }

            @Nullable
            @Override
            public CharSequence getSubtitle() {
                return null;
            }

            @Override
            public int getNavigationMode() {
                return 0;
            }

            @Override
            public void setNavigationMode(int mode) {

            }

            @Override
            public int getDisplayOptions() {
                return 0;
            }

            @Override
            public Tab newTab() {
                return null;
            }

            @Override
            public void addTab(Tab tab) {

            }

            @Override
            public void addTab(Tab tab, boolean setSelected) {

            }

            @Override
            public void addTab(Tab tab, int position) {

            }

            @Override
            public void addTab(Tab tab, int position, boolean setSelected) {

            }

            @Override
            public void removeTab(Tab tab) {

            }

            @Override
            public void removeTabAt(int position) {

            }

            @Override
            public void removeAllTabs() {

            }

            @Override
            public void selectTab(Tab tab) {

            }

            @Nullable
            @Override
            public Tab getSelectedTab() {
                return null;
            }

            @Override
            public Tab getTabAt(int index) {
                return null;
            }

            @Override
            public int getTabCount() {
                return 0;
            }

            @Override
            public int getHeight() {
                return 0;
            }

            @Override
            public void show() {

            }

            @Override
            public void hide() {

            }

            @Override
            public boolean isShowing() {
                return false;
            }

            @Override
            public void addOnMenuVisibilityListener(OnMenuVisibilityListener listener) {

            }

            @Override
            public void removeOnMenuVisibilityListener(OnMenuVisibilityListener listener) {

            }
        };
        //getSupportActionBar();//ActionBar();
        actionBar.setCustomView(R.layout.action_bar_item); //load your layout
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_SHOW_CUSTOM);

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayShowTitleEnabled(false);
        ViewGroup v = (ViewGroup) LayoutInflater.from(this)
                .inflate(R.layout.activity_guid, null);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v,
                new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        Gravity.END | Gravity.RIGHT));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.round:
                Intent intent = new Intent(MainGameActivity.this, RoundEditor.class);
                Log.v("---", "---");
                startActivity(intent);
                Log.v("---","---");
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
