package vietnamworks.com.vietnamworksjobapp.activities;

import android.os.Bundle;

import vietnamworks.com.helper.BaseActivity;
import vietnamworks.com.vietnamworksjobapp.R;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemUI();
        setContentView(R.layout.activity_launcher);
    }
}
