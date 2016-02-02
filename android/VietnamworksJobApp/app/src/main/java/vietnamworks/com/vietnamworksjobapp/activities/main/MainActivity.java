package vietnamworks.com.vietnamworksjobapp.activities.main;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import R.helper.BaseActivity;
import R.helper.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.activities.launcher.LauncherActivity;
import vietnamworks.com.vietnamworksjobapp.activities.main.fragments.AppliedJobsFragment;
import vietnamworks.com.vietnamworksjobapp.activities.main.fragments.CardsFragment;
import vietnamworks.com.vietnamworksjobapp.activities.main.fragments.CoverLetterFragment;
import vietnamworks.com.vietnamworksjobapp.activities.main.fragments.JobDetailFragment;
import vietnamworks.com.vietnamworksjobapp.activities.main.fragments.UploadCVFragment;
import vietnamworks.com.vietnamworksjobapp.activities.welcome.WelcomeActivity;
import vietnamworks.com.vietnamworksjobapp.models.UserLocalSearchDataModel;
import vietnamworks.com.vnwcore.Auth;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final static int PICK_FILE_REQUEST_CODE = 30000;

    @Bind(R.id.job_title)
    AutoCompleteTextView jobTitle;
    ArrayAdapter<String> jobTitleAdapter;

    @Bind(R.id.main_title)
    TextView mainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        openFragment(new CardsFragment(), R.id.fragment_holder);
        setActivityTitle(R.string.title_cards);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                updateActionBar();
            }
        });

        updateActionBar();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            UserLocalSearchDataModel.removeLocal();
            openActivity(LauncherActivity.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_applied_jobs) {
            pushFragment(new AppliedJobsFragment(), R.id.fragment_holder);
        } else if (id == R.id.nav_logout) {
            Auth.logout();
            MainActivity.openActivity(WelcomeActivity.class);
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateActionBar() {
        BaseFragment currentFragment = (BaseFragment)sInstance.getSupportFragmentManager().findFragmentById(R.id.fragment_holder);
        if (currentFragment != null) {
            if (currentFragment instanceof CardsFragment) {
                jobTitle.setVisibility(View.VISIBLE);
                mainTitle.setVisibility(View.GONE);
            } else {
                jobTitle.setVisibility(View.GONE);
                mainTitle.setVisibility(View.VISIBLE);

                String title = "";
                if (currentFragment instanceof CoverLetterFragment) {
                    title = getString(R.string.cover_letter);
                } else if (currentFragment instanceof JobDetailFragment) {
                    title = ((JobDetailFragment)currentFragment).getJobTitle();
                } else if (currentFragment instanceof UploadCVFragment) {
                    title = "Apply job"; //TODO: localization
                }
                mainTitle.setText(title);
            }
        } else {
            jobTitle.setVisibility(View.VISIBLE);
            mainTitle.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_FILE_REQUEST_CODE) {
            BaseFragment currentFragment = (BaseFragment) sInstance.getSupportFragmentManager().findFragmentById(R.id.fragment_holder);
            if (resultCode == RESULT_OK) {
                if (currentFragment instanceof UploadCVFragment) {
                    ((UploadCVFragment) currentFragment).onPickedFile(data.getData().getPath());
                }
            } else {
                ((UploadCVFragment) currentFragment).onPickedFile(null);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onLayoutChanged(Rect r, boolean isSoftKeyShown) {
        BaseFragment currentFragment = (BaseFragment) sInstance.getSupportFragmentManager().findFragmentById(R.id.fragment_holder);
        if (currentFragment instanceof CardsFragment) {
            ((CardsFragment)currentFragment).onLayoutChanged(r, isSoftKeyShown);
        }
    }
}
