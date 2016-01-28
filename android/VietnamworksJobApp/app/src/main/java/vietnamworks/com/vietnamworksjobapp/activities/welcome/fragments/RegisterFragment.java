package vietnamworks.com.vietnamworksjobapp.activities.welcome.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import R.helper.BaseActivity;
import R.helper.BaseFragment;
import R.helper.Callback;
import R.helper.CallbackResult;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vnwcore.VNWAPI;
import vietnamworks.com.vnwcore.entities.RegisterInfo;
import vietnamworks.com.vnwcore.errors.ERegisterError;

/**
 * Created by duynk on 1/27/16.
 */
public class RegisterFragment extends BaseFragment {
    @Bind(R.id.email)
    EditText email;

    @Bind(R.id.firstname)
    EditText firstName;

    @Bind(R.id.lastname)
    EditText lastName;

    @Bind(R.id.btn_register)
    Button btnRegister;

    @Bind(R.id.btn_cancel_register)
    Button btnCancel;

    @Bind(R.id.error)
    TextView error;

    @Bind(R.id.progressBar_register)
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_onboarding_register, container, false);
        ButterKnife.bind(this, rootView);

        email.requestFocus();
        BaseActivity.showKeyboard();

        error.setVisibility(View.INVISIBLE);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.hideKeyboard();
                BaseActivity.removeFragment(RegisterFragment.this);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgress();
                RegisterInfo info = new RegisterInfo();
                info.setEmail(email.getText().toString());
                info.setFirstName(firstName.getText().toString());
                info.setLastName(lastName.getText().toString());

                VNWAPI.register(getContext(), info, new Callback<Object>() {
                    @Override
                    public void onCompleted(Context context, CallbackResult result) {
                        if (result.hasError()) {
                            int code = result.getError().getCode();
                            if (ERegisterError.EMPTY_EMAIL.is(code)) {
                                showError(R.string.email_is_required);
                                email.requestFocus();
                            } else if (ERegisterError.INVALID_EMAIL.is(code)) {
                                showError(R.string.invalid_email_format);
                                email.requestFocus();
                            } else if (ERegisterError.FIRST_NAME_MISSING.is(code)) {
                                showError(R.string.firstname_is_required);
                                firstName.requestFocus();
                            } else if (ERegisterError.LAST_NAME_MISSING.is(code)) {
                                showError(R.string.lastname_is_required);
                                lastName.requestFocus();
                            } else if (ERegisterError.DUPLICATED.is(code)) {
                                showError(R.string.duplicated_email);
                                email.requestFocus();
                            } else {
                                showError(R.string.oops_something_wrong);
                            }
                        } else {
                            BaseActivity.openFragment(new RegisterSuccessFragment(), R.id.fragment_holder);
                        }
                        endProgress();
                    }
                });
            }
        });
        progressBar.setVisibility(View.GONE);

        BaseActivity.timeout(new Runnable() {
            @Override
            public void run() {
                email.requestFocus();
                BaseActivity.showKeyboard();
            }
        }, 50);

        return rootView;
    }

    private void showError(int ResId) {
        error.setText(getString(ResId));
        error.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        error.setVisibility(View.INVISIBLE);
    }

    private void startProgress() {
        email.setEnabled(false);
        lastName.setEnabled(false);
        firstName.setEnabled(false);
        btnCancel.setEnabled(false);
        btnRegister.setEnabled(false);
        hideError();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void endProgress() {
        email.setEnabled(true);
        lastName.setEnabled(true);
        firstName.setEnabled(true);
        btnCancel.setEnabled(true);
        btnRegister.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }
}
