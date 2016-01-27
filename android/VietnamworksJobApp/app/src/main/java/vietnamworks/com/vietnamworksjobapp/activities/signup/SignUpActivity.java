package vietnamworks.com.vietnamworksjobapp.activities.signup;

import android.os.Bundle;

import R.helper.BaseActivity;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.activities.onboarding.WelcomeActivity;
import vietnamworks.com.vietnamworksjobapp.activities.signup.fragments.RegisterFragment;
import vietnamworks.com.vietnamworksjobapp.activities.signup.fragments.SignInFragment;

/**
 * Created by duynk on 1/26/16.
 *
 */
public class SignUpActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle b = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        if (b != null) {
            if (b.getInt("mode", 0) == 0) {
                openFragment(new SignInFragment(), R.id.fragment_holder);
            } else {
                openFragment(new RegisterFragment(), R.id.fragment_holder);
            }
        } else {
            openFragment(new SignInFragment(), R.id.fragment_holder);
        }
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            BaseActivity.hideKeyboard();
            BaseActivity.openActivity(WelcomeActivity.class);
        }
    }
}
