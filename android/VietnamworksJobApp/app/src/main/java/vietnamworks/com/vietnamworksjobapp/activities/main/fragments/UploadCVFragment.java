package vietnamworks.com.vietnamworksjobapp.activities.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import R.helper.BaseActivity;
import R.helper.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;

/**
 * Created by duynk on 1/21/16.
 */
public class UploadCVFragment extends BaseFragment {

    @Bind(R.id.btn_cancel_upload_cv)
    Button btnCancelUploadCV;

    @Bind(R.id.btn_confirm_cv)
    Button btnConfirmCV;

    @Bind(R.id.btn_upload_from_dropbox)
    Button btnUploadFromDropBox;

    @Bind(R.id.btn_upload_from_sdcard)
    Button btnUploadFromSDCard;

    @Bind(R.id.upload_options)
    RadioGroup uploadOptions;

    @Bind(R.id.upload_option_sdcard)
    RadioButton uploadOptionSDCard;

    @Bind(R.id.upload_option_dropbox)
    RadioButton uploadOptionDropBox;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_upload_cv, container, false);
        ButterKnife.bind(this, rootView);
        BaseActivity.hideActionBar();

        btnCancelUploadCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.popFragment();
                BaseActivity.hideKeyboard();
            }
        });

        btnConfirmCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.hideKeyboard();
                BaseActivity.replaceFragment(new CoverLetterFragment(), R.id.fragment_holder);
            }
        });


        //init disable upload button
        btnUploadFromDropBox.setEnabled(false);
        btnUploadFromSDCard.setEnabled(false);
        uploadOptionSDCard.setChecked(true);

        uploadOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.upload_option_sdcard) {
                    btnUploadFromDropBox.setEnabled(false);
                    btnUploadFromSDCard.setEnabled(false);
                } else {
                    btnUploadFromDropBox.setEnabled(true);
                    btnUploadFromSDCard.setEnabled(true);
                }
            }
        });

        return rootView;
    }
}
