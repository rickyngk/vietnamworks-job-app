package vietnamworks.com.vietnamworksjobapp.activities.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import R.helper.BaseFragment;
import R.helper.Common;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;

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


        return rootView;
    }
}
