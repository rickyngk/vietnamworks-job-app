package vietnamworks.com.vietnamworksjobapp.activities.onboarding.input.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.helper.BaseFragment;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.activities.onboarding.input.InputInfoActivity;
import vietnamworks.com.vietnamworksjobapp.models.UserLocalProfileModel;

/**
 * Created by duynk on 1/4/16.
 */
public class InputTitleFragment extends BaseFragment {
    @Bind(R.id.btn_next)
    Button btnNext;

    @Bind(R.id.job_title)
    EditText jobTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_input_title, container, false);
        ButterKnife.bind(this, rootView);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLocalProfileModel.getEntity().setJobTitle(jobTitle.getText().toString());
                getActivityRef(InputInfoActivity.class).setPageIndex(1);
            }
        });

        return rootView;
    }
}
