package vietnamworks.com.vietnamworksjobapp.activities.welcome;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import R.helper.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.BuildConfig;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.activities.input.InputInfoActivity;
import vietnamworks.com.vietnamworksjobapp.activities.welcome.fragments.RegisterFragment;
import vietnamworks.com.vietnamworksjobapp.activities.welcome.fragments.SignInFragment;

public class WelcomeActivity extends BaseActivity {

    @Bind(R.id.btn_get_started)
    Button btnGetStarted;

    @Bind(R.id.btn_sign_in)
    Button btnSignIn;

    @Bind(R.id.btn_sign_up)
    Button btnSignUp;

    @Bind(R.id.version)
    TextView version;

    @Bind(R.id.footer)
    View footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        btnGetStarted.setAlpha(0);
        btnGetStarted.setY(5);
        btnGetStarted.animate().setDuration(250).alpha(1).translationY(0).start();

        btnSignIn.setAlpha(0);
        btnSignIn.setY(5);
        btnSignIn.animate().setDuration(250).alpha(1).translationY(0).setStartDelay(100).start();

        btnSignUp.setAlpha(0);
        btnSignUp.setY(5);
        btnSignUp.animate().setDuration(250).alpha(1).translationY(0).setStartDelay(200).start();

        String ver = String.format("%s.%s", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
        version.setText(String.format(getString(R.string.version), ver));
        footer.setAlpha(0);
        footer.animate().setDuration(250).alpha(1).setStartDelay(300).start();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_holder) !=null ) {
            removeFragment(R.id.fragment_holder);
        } else {
            super.onBackPressed();
        }
    }

    public void onGettingStarted(View v) {
        openActivity(InputInfoActivity.class);
    }

    public void onLogin(View v) {
        openFragment(new SignInFragment(), R.id.fragment_holder);
    }

    public void onSignUp(View v) {
        openFragment(new RegisterFragment(), R.id.fragment_holder);
    }
}
