package vietnamworks.com.vietnamworksjobapp.activities.onboarding;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import R.helper.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.activities.onboarding.input.InputInfoActivity;
import vietnamworks.com.vietnamworksjobapp.activities.signup.SignUpActivity;

public class WelcomeActivity extends BaseActivity {

    @Bind(R.id.btn_get_started)
    Button btnGetStarted;

    @Bind(R.id.btn_sign_in)
    Button btnSignIn;

    @Bind(R.id.btn_sign_up)
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemUI();
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        btnGetStarted.setAlpha(0);
        btnGetStarted.setY(5);
        btnGetStarted.animate().setDuration(500).alpha(1).translationY(0).start();

        btnSignIn.setAlpha(0);
        btnSignIn.setY(5);
        btnSignIn.animate().setDuration(500).alpha(1).translationY(0).setStartDelay(250).start();

        btnSignUp.setAlpha(0);
        btnSignUp.setY(5);
        btnSignUp.animate().setDuration(500).alpha(1).translationY(0).setStartDelay(500).start();
    }

    public void onGettingStarted(View v) {
        openActivity(InputInfoActivity.class);
    }

    public void onLogin(View v) {
        openActivity(SignUpActivity.class);
    }

    public void onSignUp(View v) {
        Bundle b = new Bundle();
        b.putInt("mode", 1);
        openActivity(SignUpActivity.class, b);
    }
}
