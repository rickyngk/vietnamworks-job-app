package vietnamworks.com.vietnamworksjobapp.activities.signup.fragments;
/**
 * Created by duynk on 1/27/16.
 *
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import R.helper.BaseActivity;
import R.helper.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.activities.onboarding.WelcomeActivity;

public class RegisterSuccessFragment extends BaseFragment {
    @Bind(R.id.btn_ok)
    Button btnOk;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_register_success, container, false);
        ButterKnife.bind(this, rootView);
        BaseActivity.hideKeyboard();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.openActivity(WelcomeActivity.class);
            }
        });
        return rootView;
    }
}
