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
import android.widget.TextView;

import R.helper.BaseActivity;
import R.helper.BaseFragment;
import R.helper.Callback;
import R.helper.CallbackResult;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vnwcore.Auth;

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
                hideError();
                Auth.login(getContext(), email.getText().toString(), password.getText().toString(), new Callback() {
                    @Override
                    public void onCompleted(Context context, CallbackResult result) {
                        if (result.hasError()) {
                            CallbackResult.CallbackError r = result.getError();
                            int code = r.getCode();
                            if (Auth.ELoginError.EMPTY_EMAIL.is(code)) {
                                showError(R.string.email_is_required);
                            } else if (Auth.ELoginError.INVALID_EMAIL.is(code)) {
                                showError(R.string.invalid_email_format);
                            } else if (Auth.ELoginError.EMPTY_PASSWORD.is(code)) {
                                showError(R.string.password_is_required);
                            } else if (Auth.ELoginError.WRONG_CREDENTIAL.is(code)) {
                                showError(R.string.invalid_credential);
                            } else {
                                showError(R.string.oops_something_wrong);
                            }
                        } else {
                            BaseActivity.replaceFragment(new CoverLetterFragment(), R.id.fragment_holder);
                        }
                    }
                });
            }
        });

        return rootView;
    }

    private void showError(int ResId) {
        error.setText(getString(ResId));
        error.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        error.setVisibility(View.INVISIBLE);
    }
}
