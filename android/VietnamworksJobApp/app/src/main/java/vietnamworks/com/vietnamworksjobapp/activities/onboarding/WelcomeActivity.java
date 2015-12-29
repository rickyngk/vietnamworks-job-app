package vietnamworks.com.vietnamworksjobapp.activities.onboarding;

import android.os.Bundle;

import vietnamworks.com.helper.BaseActivity;
import vietnamworks.com.vietnamworksjobapp.R;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemUI();
        setContentView(R.layout.activity_welcome);
    }
}
