package vietnamworks.com.vnwcore;

import android.os.Bundle;

import vietnamworks.com.helper.BaseActivity;

/**
 * Created by duynk on 12/29/15.
 */
public class LauncherActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.NoActionBar);
        super.onCreate(savedInstanceState);
        hideSystemUI();
        setContentView(R.layout.activity_launcher);
    }
}
