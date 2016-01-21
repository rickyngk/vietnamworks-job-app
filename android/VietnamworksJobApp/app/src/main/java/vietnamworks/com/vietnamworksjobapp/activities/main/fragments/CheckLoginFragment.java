package vietnamworks.com.vietnamworksjobapp.activities.main.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import R.helper.BaseActivity;
import R.helper.BaseFragment;
import R.helper.Callback;
import R.helper.CallbackResult;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.services.ShareContext;
import vietnamworks.com.vnwcore.Auth;
import vietnamworks.com.vnwcore.entities.JobApplyForm;

/**
 * Created by duynk on 1/20/16.
 */
public class CheckLoginFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_check_login, container, false);
        ButterKnife.bind(this, rootView);

        Auth.autoLogin(getContext(), new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (result.hasError()) {
                    BaseActivity.replaceFragment(new LoginFragment(), R.id.fragment_holder);
                } else {
                    ((JobApplyForm)ShareContext.get(ShareContext.SELECTED_JOB)).setToken(Auth.getAuthData().getProfile().getLoginToken());
                    BaseActivity.replaceFragment(new UploadCVFragment(), R.id.fragment_holder);
                }
            }
        });

        return rootView;
    }
}
