package vietnamworks.com.vietnamworksjobapp.activities.main.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import vietnamworks.com.vietnamworksjobapp.services.ShareContext;
import vietnamworks.com.vnwcore.Auth;
import vietnamworks.com.vnwcore.entities.JobApplyForm;
import vietnamworks.com.vnwcore.errors.ELoginError;

/**
 * Created by duynk on 1/20/16.
 */
public class LoginFragment extends BaseFragment {

    @Bind(R.id.email)
    AutoCompleteTextView email;

    @Bind(R.id.password)
    EditText password;

    @Bind(R.id.btn_login)
    Button btnLogin;

    @Bind(R.id.btn_cancel_login)
    Button btnCancelLogin;

    @Bind(R.id.error)
    TextView error;

    @Bind(R.id.progressBar_login)
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BaseActivity.hideActionBar();

        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);

        error.setVisibility(View.INVISIBLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, Auth.getRecentEmails());
        email.setAdapter(adapter);

        btnCancelLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.hideKeyboard();
                BaseActivity.popFragment();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgress();
                Auth.login(getContext(), email.getText().toString(), password.getText().toString(), new Callback<Object>() {
                    @Override
                    public void onCompleted(Context context, CallbackResult result) {
                        if (result.hasError()) {
                            int code = result.getError().getCode();
                            if (ELoginError.EMPTY_EMAIL.is(code)) {
                                showError(R.string.email_is_required);
                            } else if (ELoginError.INVALID_EMAIL.is(code)) {
                                showError(R.string.invalid_email_format);
                            } else if (ELoginError.EMPTY_PASSWORD.is(code)) {
                                showError(R.string.password_is_required);
                            } else if (ELoginError.WRONG_CREDENTIAL.is(code)) {
                                showError(R.string.invalid_credential);
                            } else {
                                showError(R.string.oops_something_wrong);
                            }
                        } else {
                            ((JobApplyForm) ShareContext.get(ShareContext.SELECTED_JOB)).setToken(Auth.getAuthData().getProfile().getLoginToken());
                            BaseActivity.replaceFragment(new UploadCVFragment(), R.id.fragment_holder);
                        }
                        endProgress();
                    }
                });
            }
        });

        progressBar.setVisibility(View.GONE);
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
        password.setEnabled(false);
        btnCancelLogin.setEnabled(false);
        btnLogin.setEnabled(false);
        hideError();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void endProgress() {
        email.setEnabled(true);
        password.setEnabled(true);
        btnCancelLogin.setEnabled(true);
        btnLogin.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }
}
