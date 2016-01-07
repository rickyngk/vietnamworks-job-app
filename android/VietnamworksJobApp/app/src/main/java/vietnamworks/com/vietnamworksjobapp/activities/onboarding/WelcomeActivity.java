package vietnamworks.com.vietnamworksjobapp.activities.onboarding;

import android.os.Bundle;
import android.view.View;

import R.helper.BaseActivity;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.activities.onboarding.input.InputInfoActivity;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemUI();
        setContentView(R.layout.activity_welcome);
    }

    public void onGettingStarted(View v) {
        openActivity(InputInfoActivity.class);
    }

    public void onLogin(View v) {

    }
}
