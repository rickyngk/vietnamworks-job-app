package vietnamworks.com.vietnamworksjobapp.activities.main.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import R.helper.BaseActivity;
import R.helper.BaseFragment;
import R.helper.Callback;
import R.helper.CallbackResult;
import R.helper.LocalStorage;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.services.ShareContext;
import vietnamworks.com.vnwcore.Auth;
import vietnamworks.com.vnwcore.VNWAPI;
import vietnamworks.com.vnwcore.entities.JobApplyForm;
import vietnamworks.com.vnwcore.errors.EApplyJobError;

/**
 * Created by duynk on 1/20/16.
 */
public class CoverLetterFragment extends BaseFragment {
    @Bind(R.id.cover_letter)
    EditText coverLetter;

    @Bind(R.id.cover_letter_scroll_view)
    ViewGroup coverLetterScrollView;

    @Bind(R.id.btn_cancel_cover_letter)
    Button btnCancelCoverLetter;

    @Bind(R.id.btn_confirm_cover_letter)
    Button btnConfirmCoverLetter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_input_cover_letter, container, false);
        ButterKnife.bind(this, rootView);
        //BaseActivity.hideActionBar();

        vietnamworks.com.vnwcore.entities.Auth auth = Auth.getAuthData();
        coverLetter.setText(auth.getCoverLetter());
        String lastCover = LocalStorage.getString("last_cover_letter", "");
        if (!lastCover.isEmpty()) {
            coverLetter.setText(lastCover);
        }


        coverLetterScrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coverLetter.requestFocus();
                BaseActivity.showKeyboard();
            }
        });

        btnCancelCoverLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.popFragment();
                BaseActivity.hideKeyboard();
            }
        });

        btnConfirmCoverLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.hideKeyboard();
                BaseActivity.timeout(new Runnable() {
                    @Override
                    public void run() {
                        if (coverLetter.getText().toString().trim().length() == 0) {
                            BaseActivity.toast(R.string.cover_letter_is_required);
                        } else {
                            JobApplyForm jf = (JobApplyForm) ShareContext.get(ShareContext.SELECTED_JOB);
                            jf.setCoverLetter(coverLetter.getText().toString());
                            jf.setCredential(Auth.getCredential());

                            LocalStorage.set("last_cover_letter", coverLetter.getText().toString().trim());

                            VNWAPI.applyJob(getContext(), jf, new Callback<Object>() {
                                @Override
                                public void onCompleted(Context context, CallbackResult result) {
                                    if (result.hasError()) {
                                        String errorMessage = getString(R.string.oops_something_wrong);
                                        int errorNumber = result.getError().getCode();
                                        if (EApplyJobError.BAD_REQUEST.is(errorNumber)) {
                                            String tmpMessage = result.getError().getMessage();
                                            if (tmpMessage != null && !tmpMessage.isEmpty()) {
                                                errorMessage = tmpMessage;
                                            }
                                        }
                                        BaseActivity.toast(errorMessage);
                                        BaseActivity.popFragment();
                                    } else {
                                        BaseActivity.toast(R.string.apply_job_success);
                                        BaseActivity.popFragment();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        return rootView;
    }
}
