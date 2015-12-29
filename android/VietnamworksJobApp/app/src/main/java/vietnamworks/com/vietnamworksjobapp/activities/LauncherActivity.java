package vietnamworks.com.vietnamworksjobapp.activities;

import android.os.Bundle;

import vietnamworks.com.vietnamworksjobapp.activities.onboarding.WelcomeActivity;

public class LauncherActivity extends vietnamworks.com.vnwcore.LauncherActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeout(new Runnable() {
            @Override
            public void run() {
                openActivity(WelcomeActivity.class);
            }
        }, 3000);
    }
}
