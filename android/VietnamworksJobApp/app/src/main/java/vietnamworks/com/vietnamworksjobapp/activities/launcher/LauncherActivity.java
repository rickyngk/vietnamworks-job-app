package vietnamworks.com.vietnamworksjobapp.activities.launcher;

import android.os.Bundle;

import vietnamworks.com.vietnamworksjobapp.activities.main.MainActivity;
import vietnamworks.com.vietnamworksjobapp.activities.onboarding.WelcomeActivity;
import vietnamworks.com.vietnamworksjobapp.models.UserLocalProfileModel;

public class LauncherActivity extends vietnamworks.com.vnwcore.LauncherActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (UserLocalProfileModel.loadLocal()) {
            timeout(new Runnable() {
                @Override
                public void run() {
                    openActivity(MainActivity.class);
                }
            }, 1000);
        } else {
            timeout(new Runnable() {
                @Override
                public void run() {
                    openActivity(WelcomeActivity.class);
                }
            }, 1000);
        }
    }
}
