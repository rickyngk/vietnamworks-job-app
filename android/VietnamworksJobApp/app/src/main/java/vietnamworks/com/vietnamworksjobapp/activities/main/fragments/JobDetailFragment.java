package vietnamworks.com.vietnamworksjobapp.activities.main.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import R.helper.BaseFragment;
import R.helper.Callback;
import R.helper.CallbackResult;
import R.helper.Common;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.models.JobDetailModel;

/**
 * Created by duynk on 1/15/16.
 */
public class JobDetailFragment extends BaseFragment {
    @Bind(R.id.job_detail_job_title)
    TextView jobTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_job_detail, container, false);
        ButterKnife.bind(this, rootView);

        if (Common.isLollipopOrLater()) {
            jobTitle.setTransitionName(getString(R.string.transition_job_card_to_job_detail));
        }
        jobTitle.setText(bundle.getString("jobTitle"));
        String jobId = bundle.getString("jobId");

        JobDetailModel.load(getContext(), jobId, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (!result.hasError()) {

                }
            }
        });
        return rootView;
    }
}
